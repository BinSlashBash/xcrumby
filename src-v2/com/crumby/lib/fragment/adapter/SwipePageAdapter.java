/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.LruCache
 *  android.view.ViewGroup
 *  android.widget.Toast
 */
package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.DisplayError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SwipePageAdapter
extends FragmentStatePagerAdapter
implements GalleryConsumer {
    public static int NOT_FOUND = -1;
    private Context context;
    private LruCache<Integer, GalleryProducer> fragmentProducers;
    private Map<Integer, GalleryViewerFragment> fragments;
    private List<GalleryImage> images;
    private GalleryProducer producer;

    public SwipePageAdapter(Activity activity, GalleryProducer galleryProducer, LruCache<Integer, GalleryProducer> lruCache) {
        super(activity.getFragmentManager());
        this.context = activity.getApplicationContext();
        this.images = new ArrayList<GalleryImage>();
        this.fragments = new HashMap<Integer, GalleryViewerFragment>();
        this.fragmentProducers = lruCache;
        this.setPagerContents(galleryProducer);
    }

    private GalleryViewerFragment buildFragmentFromImage(int n2) {
        GalleryImage galleryImage = this.images.get(n2);
        GalleryViewerFragment galleryViewerFragment = FragmentRouter.INSTANCE.getGalleryFragmentInstance(galleryImage.getLinkUrl());
        galleryViewerFragment.setImage(galleryImage);
        this.fragments.put(n2, galleryViewerFragment);
        return galleryViewerFragment;
    }

    @Override
    public boolean addImages(List<GalleryImage> list) {
        this.images.addAll(list);
        this.notifyDataSetChanged();
        return false;
    }

    public void clear() {
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        this.producer = null;
        this.images.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n2, Object object) {
        GalleryViewerFragment galleryViewerFragment = this.fragments.remove(n2);
        if (galleryViewerFragment != null && galleryViewerFragment.getProducer() != null && this.fragmentProducers != null && this.fragmentProducers.get((Object)n2) != null) {
            this.fragmentProducers.put((Object)n2, (Object)galleryViewerFragment.getProducer());
        }
        super.destroyItem(viewGroup, n2, object);
    }

    public void dispatchWaitResume() {
        Iterator<GalleryViewerFragment> iterator = this.fragments.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().waitOnResume();
        }
    }

    @Override
    public void finishLoading() {
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    public GalleryViewerFragment getFragment(int n2) {
        return this.fragments.get(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Fragment getItem(int n2) {
        GalleryViewerFragment galleryViewerFragment = null;
        if (n2 < this.images.size()) {
            GalleryViewerFragment galleryViewerFragment2 = galleryViewerFragment = this.fragments.get(n2);
            if (galleryViewerFragment == null) {
                galleryViewerFragment2 = this.buildFragmentFromImage(n2);
            }
            if (this.fragmentProducers.get((Object)n2) != null) {
                galleryViewerFragment2.setProducer((GalleryProducer)this.fragmentProducers.get((Object)n2));
                galleryViewerFragment = galleryViewerFragment2;
            } else {
                galleryViewerFragment = galleryViewerFragment2;
                if (galleryViewerFragment2.getProducer() != null) {
                    this.fragmentProducers.put((Object)n2, (Object)galleryViewerFragment2.getProducer());
                    galleryViewerFragment = galleryViewerFragment2;
                }
            }
        }
        if (n2 > this.images.size() - 2) {
            this.producer.requestFetch();
        }
        return galleryViewerFragment;
    }

    @Override
    public CharSequence getPageTitle(int n2) {
        if (this.images.size() == 1) {
            return "";
        }
        return "" + n2 + "";
    }

    public void omniSearchIsNotShowing() {
        for (GalleryViewerFragment galleryViewerFragment : this.fragments.values()) {
            if (!(galleryViewerFragment instanceof GalleryImageFragment)) continue;
            ((GalleryImageFragment)galleryViewerFragment).omniSearchIsNotShowingHack();
        }
    }

    public void omniSearchIsShowing() {
        for (GalleryViewerFragment galleryViewerFragment : this.fragments.values()) {
            if (!(galleryViewerFragment instanceof GalleryImageFragment)) continue;
            ((GalleryImageFragment)galleryViewerFragment).omniSearchIsShowingHack();
        }
    }

    public void redrawFragments() {
        Iterator<GalleryViewerFragment> iterator = this.fragments.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().redraw();
        }
    }

    public int removeFragmentProducerByHost(GalleryImage galleryImage) {
        for (Map.Entry<Integer, GalleryViewerFragment> entry : this.fragments.entrySet()) {
            if (entry.getValue().getImage() != galleryImage) continue;
            this.removeProducer(entry.getKey());
            return entry.getKey();
        }
        return NOT_FOUND;
    }

    public void removeProducer(int n2) {
        this.fragmentProducers.remove((Object)n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPagerContents(GalleryProducer galleryProducer) {
        this.clear();
        this.producer = galleryProducer;
        if (!this.producer.isInitialized()) {
            this.producer.initialize(this, null, null);
        } else {
            this.producer.addConsumer(this);
            this.addImages(this.producer.getImages());
        }
        this.producer.requestStartFetch();
    }

    public void setStartingFragment(GalleryViewerFragment galleryViewerFragment, int n2) {
        if (galleryViewerFragment == null) {
            return;
        }
        this.fragments.put(n2, galleryViewerFragment);
    }

    @Override
    public void showError(Exception exception) {
        Analytics.INSTANCE.newError(DisplayError.VIEWPAGER_CANNOT_FETCH, exception.getMessage());
        Toast.makeText((Context)this.context, (CharSequence)("Could not fetch additional pages. " + exception.getMessage()), (int)1).show();
    }
}

