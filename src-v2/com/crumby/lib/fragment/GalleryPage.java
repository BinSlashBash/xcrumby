/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.util.LruCache
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.lib.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.BreadcrumbListener;
import com.crumby.lib.fragment.CachedProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.thirdparty.HackyViewPager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GalleryPage
extends Fragment
implements BreadcrumbListener {
    private String activeUrl;
    private SwipePageAdapter adapter;
    private boolean doNotFireSelectListener;
    private LruCache<Integer, GalleryProducer> fragmentProducers;
    private GalleryViewerFragment initialFragment;
    ViewPager.OnPageChangeListener pageChange;
    private HackyViewPager pager;
    private GalleryProducer producer;
    List<Breadcrumb> savedBreadcrumbs;
    private int savedRedoFocus;

    private GalleryProducer getProducer() {
        if (this.producer == null) {
            this.producer = new CachedProducer(this.getArguments().getString("url"));
        }
        return this.producer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onFragmentSelect(int n2) {
        String string2 = "current";
        if (this.producer.getCurrentImageFocus() < n2) {
            string2 = "right";
        } else if (this.producer.getCurrentImageFocus() > n2) {
            string2 = "left";
        }
        this.producer.setCurrentImageFocus(n2);
        GalleryViewerFragment galleryViewerFragment = this.getActiveFragment();
        ((GalleryViewer)this.getActivity()).pagingToNewFragment();
        if (galleryViewerFragment instanceof GalleryGridFragment || !ImageScrollView.userWantsFullScreen) {
            ((GalleryViewer)this.getActivity()).showOmnibar();
        } else if (ImageScrollView.userWantsFullScreen) {
            ((GalleryViewer)this.getActivity()).hideOmnibar();
        }
        if (galleryViewerFragment == null) {
            return;
        }
        galleryViewerFragment.indicateLastProgressChange();
        Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "paging " + string2, "" + n2 + " " + galleryViewerFragment.getUrl());
        this.activeUrl = galleryViewerFragment.getUrl();
        this.changeBreadcrumbs(galleryViewerFragment);
    }

    @Override
    public void changeBreadcrumbs(GalleryViewerFragment galleryViewerFragment) {
        if (galleryViewerFragment == null) {
            return;
        }
        ((GalleryViewer)this.getActivity()).alterBreadcrumbPath(galleryViewerFragment);
    }

    public void discard() {
        if (this.getView() != null) {
            this.getView().findViewById(2131492995).setVisibility(0);
        }
    }

    public void dispatchWaitResume() {
        if (this.adapter != null) {
            this.adapter.dispatchWaitResume();
        }
    }

    public GalleryViewerFragment getActiveFragment() {
        if (this.adapter == null) {
            return null;
        }
        return this.adapter.getFragment(this.pager.getCurrentItem());
    }

    public String getActiveUrl() {
        return this.activeUrl;
    }

    public void hideTitleStrip() {
        View view;
        if (this.getView() != null && (view = this.getView().findViewById(2131492994)) != null) {
            view.setVisibility(8);
        }
    }

    public void omniSearchIsNotShowing() {
        if (this.adapter != null) {
            this.adapter.omniSearchIsNotShowing();
        }
    }

    public void omniSearchIsShowing() {
        if (this.adapter != null) {
            this.adapter.omniSearchIsShowing();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        object = this.getProducer();
        layoutInflater = layoutInflater.inflate(2130903078, null);
        this.pager = (HackyViewPager)layoutInflater.findViewById(2131492993);
        ImageScrollView.userWantsFullScreen = false;
        if (this.fragmentProducers == null) {
            this.fragmentProducers = new LruCache(10);
            if (PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("crumbyUseBreadcrumbPathForDownload", false)) {
                ImageScrollView.userWantsFullScreen = true;
            }
        }
        this.adapter = new SwipePageAdapter(this.getActivity(), (GalleryProducer)object, this.fragmentProducers);
        this.adapter.setStartingFragment(this.initialFragment, object.getCurrentImageFocus());
        ((GalleryViewer)this.getActivity()).showOmnibar();
        this.initialFragment = null;
        this.pageChange = new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int n2) {
            }

            @Override
            public void onPageScrolled(int n2, float f2, int n3) {
            }

            @Override
            public void onPageSelected(int n2) {
                if (GalleryPage.this.doNotFireSelectListener) {
                    return;
                }
                GalleryPage.this.onFragmentSelect(n2);
            }
        };
        this.pager.initialize(this.adapter, this.pageChange, object.getCurrentImageFocus());
        if (object.getImages().size() <= 1) {
            layoutInflater.findViewById(2131492994).setVisibility(8);
        }
        object.makeShareable();
        return layoutInflater;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.savedBreadcrumbs != null) {
            this.savedBreadcrumbs.clear();
        }
        if (this.fragmentProducers != null) {
            this.fragmentProducers.evictAll();
        }
    }

    public void onDestroyView() {
        this.adapter.clear();
        super.onDestroyView();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void redirectFragment(String object, GalleryImage galleryImage) {
        int n2 = this.adapter.removeFragmentProducerByHost(galleryImage);
        if (n2 != SwipePageAdapter.NOT_FOUND) {
            this.producer.alterImageAtPosition(n2, (String)object);
            object = this.adapter.getFragment(n2);
            if (object == this.getActiveFragment()) {
                this.changeBreadcrumbs((GalleryViewerFragment)object);
            }
            this.refresh();
        }
    }

    public void redraw() {
        if (this.adapter != null) {
            this.adapter.redrawFragments();
        }
    }

    public void refresh() {
        GalleryProducer galleryProducer;
        this.getActiveFragment().prepareForRefresh();
        int n2 = this.pager.getCurrentItem();
        if (this.fragmentProducers != null && (galleryProducer = (GalleryProducer)this.fragmentProducers.get((Object)n2)) != null && galleryProducer.getCurrentImageFocus() == 0) {
            this.fragmentProducers.remove((Object)n2);
            galleryProducer.destroy();
        }
        this.pager.setAdapter(this.adapter);
        this.producer.requestStartFetch();
        this.doNotFireSelectListener = true;
        this.pager.setCurrentItem(n2);
        this.doNotFireSelectListener = false;
    }

    public void removeNestedFragments() {
        if (this.getActiveFragment() != null && this.getActiveFragment().getBreadcrumbs() != null) {
            this.savedBreadcrumbs = new ArrayList<Breadcrumb>();
            this.savedBreadcrumbs.addAll(this.getActiveFragment().getBreadcrumbs());
        }
        try {
            this.pager.setAdapter(null);
            return;
        }
        catch (IllegalStateException var1_1) {
            if (this.adapter != null) {
                this.adapter.clear();
                this.adapter.notifyDataSetChanged();
            }
            this.pager.setAdapter(null);
            return;
        }
    }

    public void resetFocusToSavedRedoPosition() {
        this.producer.setCurrentImageFocus(this.savedRedoFocus);
    }

    public void setInitialFragment(GalleryViewerFragment galleryViewerFragment) {
        this.initialFragment = galleryViewerFragment;
        this.getProducer().initialize();
        this.initialFragment.setImage(this.producer.getImages().get(this.producer.getCurrentImageFocus()));
        if (this.savedBreadcrumbs != null && !this.savedBreadcrumbs.isEmpty()) {
            this.initialFragment.setBreadcrumbs(this.savedBreadcrumbs);
            this.savedBreadcrumbs = null;
        }
        this.activeUrl = this.initialFragment.getUrl();
    }

    public void setProducer(GalleryProducer galleryProducer) {
        this.producer = galleryProducer;
    }

    public void setSavedRedoFocus() {
        this.savedRedoFocus = this.producer.getCurrentImageFocus();
    }

    public void showTitleStrip() {
        View view;
        if (this.getView() != null && (view = this.getView().findViewById(2131492994)) != null && this.producer != null && this.producer.getImages() != null && this.producer.getImages().size() > 0) {
            view.setVisibility(0);
        }
    }

    public void stopLoading() {
        if (this.getActiveFragment() == null) {
            return;
        }
        this.getActiveFragment().stopLoading();
    }

    public boolean undo() {
        if (this.getActiveFragment() == null) {
            return false;
        }
        return this.getActiveFragment().undo();
    }

}

