package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.DisplayError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SwipePageAdapter extends FragmentStatePagerAdapter implements GalleryConsumer {
    public static int NOT_FOUND;
    private Context context;
    private LruCache<Integer, GalleryProducer> fragmentProducers;
    private Map<Integer, GalleryViewerFragment> fragments;
    private List<GalleryImage> images;
    private GalleryProducer producer;

    public GalleryViewerFragment getFragment(int key) {
        return (GalleryViewerFragment) this.fragments.get(Integer.valueOf(key));
    }

    public CharSequence getPageTitle(int position) {
        if (this.images.size() == 1) {
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
        return position + UnsupportedUrlFragment.DISPLAY_NAME;
    }

    public SwipePageAdapter(Activity activity, GalleryProducer producer, LruCache<Integer, GalleryProducer> fragmentProducers) {
        super(activity.getFragmentManager());
        this.context = activity.getApplicationContext();
        this.images = new ArrayList();
        this.fragments = new HashMap();
        this.fragmentProducers = fragmentProducers;
        setPagerContents(producer);
    }

    public int getCount() {
        return this.images.size();
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        GalleryViewerFragment fragment = (GalleryViewerFragment) this.fragments.remove(Integer.valueOf(position));
        if (!(fragment == null || fragment.getProducer() == null || this.fragmentProducers == null || this.fragmentProducers.get(Integer.valueOf(position)) == null)) {
            this.fragmentProducers.put(Integer.valueOf(position), fragment.getProducer());
        }
        super.destroyItem(container, position, object);
    }

    private GalleryViewerFragment buildFragmentFromImage(int i) {
        GalleryImage image = (GalleryImage) this.images.get(i);
        GalleryViewerFragment fragment = FragmentRouter.INSTANCE.getGalleryFragmentInstance(image.getLinkUrl());
        fragment.setImage(image);
        this.fragments.put(Integer.valueOf(i), fragment);
        return fragment;
    }

    public Fragment getItem(int i) {
        GalleryViewerFragment fragment = null;
        if (i < this.images.size()) {
            fragment = (GalleryViewerFragment) this.fragments.get(Integer.valueOf(i));
            if (fragment == null) {
                fragment = buildFragmentFromImage(i);
            }
            if (this.fragmentProducers.get(Integer.valueOf(i)) != null) {
                fragment.setProducer((GalleryProducer) this.fragmentProducers.get(Integer.valueOf(i)));
            } else if (fragment.getProducer() != null) {
                this.fragmentProducers.put(Integer.valueOf(i), fragment.getProducer());
            }
        }
        if (i > this.images.size() - 2) {
            this.producer.requestFetch();
        }
        return fragment;
    }

    public void showError(Exception e) {
        Analytics.INSTANCE.newError(DisplayError.VIEWPAGER_CANNOT_FETCH, e.getMessage());
        Toast.makeText(this.context, "Could not fetch additional pages. " + e.getMessage(), 1).show();
    }

    public void finishLoading() {
    }

    public void clear() {
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        this.producer = null;
        this.images.clear();
        notifyDataSetChanged();
    }

    public void setPagerContents(GalleryProducer galleryProducer) {
        clear();
        this.producer = galleryProducer;
        if (this.producer.isInitialized()) {
            this.producer.addConsumer(this);
            addImages(this.producer.getImages());
        } else {
            this.producer.initialize(this, null, null);
        }
        this.producer.requestStartFetch();
    }

    public boolean addImages(List<GalleryImage> images) {
        this.images.addAll(images);
        notifyDataSetChanged();
        return false;
    }

    public void redrawFragments() {
        for (GalleryViewerFragment fragment : this.fragments.values()) {
            fragment.redraw();
        }
    }

    public void removeProducer(int index) {
        this.fragmentProducers.remove(Integer.valueOf(index));
    }

    static {
        NOT_FOUND = -1;
    }

    public int removeFragmentProducerByHost(GalleryImage keyImage) {
        for (Entry<Integer, GalleryViewerFragment> entry : this.fragments.entrySet()) {
            if (((GalleryViewerFragment) entry.getValue()).getImage() == keyImage) {
                removeProducer(((Integer) entry.getKey()).intValue());
                return ((Integer) entry.getKey()).intValue();
            }
        }
        return NOT_FOUND;
    }

    public void setStartingFragment(GalleryViewerFragment initialFragment, int currentItem) {
        if (initialFragment != null) {
            this.fragments.put(Integer.valueOf(currentItem), initialFragment);
        }
    }

    public void dispatchWaitResume() {
        for (GalleryViewerFragment fragment : this.fragments.values()) {
            fragment.waitOnResume();
        }
    }

    public void omniSearchIsShowing() {
        for (GalleryViewerFragment fragment : this.fragments.values()) {
            if (fragment instanceof GalleryImageFragment) {
                ((GalleryImageFragment) fragment).omniSearchIsShowingHack();
            }
        }
    }

    public void omniSearchIsNotShowing() {
        for (GalleryViewerFragment fragment : this.fragments.values()) {
            if (fragment instanceof GalleryImageFragment) {
                ((GalleryImageFragment) fragment).omniSearchIsNotShowingHack();
            }
        }
    }
}
