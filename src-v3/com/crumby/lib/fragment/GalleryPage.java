package com.crumby.lib.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.thirdparty.HackyViewPager;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.List;

public class GalleryPage extends Fragment implements BreadcrumbListener {
    private String activeUrl;
    private SwipePageAdapter adapter;
    private boolean doNotFireSelectListener;
    private LruCache<Integer, GalleryProducer> fragmentProducers;
    private GalleryViewerFragment initialFragment;
    OnPageChangeListener pageChange;
    private HackyViewPager pager;
    private GalleryProducer producer;
    List<Breadcrumb> savedBreadcrumbs;
    private int savedRedoFocus;

    /* renamed from: com.crumby.lib.fragment.GalleryPage.1 */
    class C07371 implements OnPageChangeListener {
        C07371() {
        }

        public void onPageScrolled(int i, float v, int i2) {
        }

        public void onPageSelected(int i) {
            if (!GalleryPage.this.doNotFireSelectListener) {
                GalleryPage.this.onFragmentSelect(i);
            }
        }

        public void onPageScrollStateChanged(int i) {
        }
    }

    public void setProducer(GalleryProducer producer) {
        this.producer = producer;
    }

    private GalleryProducer getProducer() {
        if (this.producer == null) {
            this.producer = new CachedProducer(getArguments().getString(PlusShare.KEY_CALL_TO_ACTION_URL));
        }
        return this.producer;
    }

    private void onFragmentSelect(int i) {
        String paging = "current";
        if (this.producer.getCurrentImageFocus() < i) {
            paging = "right";
        } else if (this.producer.getCurrentImageFocus() > i) {
            paging = "left";
        }
        this.producer.setCurrentImageFocus(i);
        GalleryViewerFragment fragment = getActiveFragment();
        ((GalleryViewer) getActivity()).pagingToNewFragment();
        if ((fragment instanceof GalleryGridFragment) || !ImageScrollView.userWantsFullScreen) {
            ((GalleryViewer) getActivity()).showOmnibar();
        } else if (ImageScrollView.userWantsFullScreen) {
            ((GalleryViewer) getActivity()).hideOmnibar();
        }
        if (fragment != null) {
            fragment.indicateLastProgressChange();
            Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "paging " + paging, i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + fragment.getUrl());
            this.activeUrl = fragment.getUrl();
            changeBreadcrumbs(fragment);
        }
    }

    public void changeBreadcrumbs(GalleryViewerFragment fragment) {
        if (fragment != null) {
            ((GalleryViewer) getActivity()).alterBreadcrumbPath(fragment);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GalleryProducer producer = getProducer();
        View savedView = inflater.inflate(C0065R.layout.gallery_page, null);
        this.pager = (HackyViewPager) savedView.findViewById(C0065R.id.view_pager);
        ImageScrollView.userWantsFullScreen = false;
        if (this.fragmentProducers == null) {
            this.fragmentProducers = new LruCache(10);
            if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(MainMenu.USE_BREADCRUMB_PATH_FOR_DOWNLOAD, false)) {
                ImageScrollView.userWantsFullScreen = true;
            }
        }
        this.adapter = new SwipePageAdapter(getActivity(), producer, this.fragmentProducers);
        this.adapter.setStartingFragment(this.initialFragment, producer.getCurrentImageFocus());
        ((GalleryViewer) getActivity()).showOmnibar();
        this.initialFragment = null;
        this.pageChange = new C07371();
        this.pager.initialize(this.adapter, this.pageChange, producer.getCurrentImageFocus());
        if (producer.getImages().size() <= 1) {
            savedView.findViewById(C0065R.id.pager_title_strip).setVisibility(8);
        }
        producer.makeShareable();
        return savedView;
    }

    public GalleryViewerFragment getActiveFragment() {
        if (this.adapter == null) {
            return null;
        }
        return this.adapter.getFragment(this.pager.getCurrentItem());
    }

    public void stopLoading() {
        if (getActiveFragment() != null) {
            getActiveFragment().stopLoading();
        }
    }

    public void refresh() {
        getActiveFragment().prepareForRefresh();
        int replace = this.pager.getCurrentItem();
        if (this.fragmentProducers != null) {
            GalleryProducer producer = (GalleryProducer) this.fragmentProducers.get(Integer.valueOf(replace));
            if (producer != null && producer.getCurrentImageFocus() == 0) {
                this.fragmentProducers.remove(Integer.valueOf(replace));
                producer.destroy();
            }
        }
        this.pager.setAdapter(this.adapter);
        this.producer.requestStartFetch();
        this.doNotFireSelectListener = true;
        this.pager.setCurrentItem(replace);
        this.doNotFireSelectListener = false;
    }

    public boolean undo() {
        if (getActiveFragment() == null) {
            return false;
        }
        return getActiveFragment().undo();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void redraw() {
        if (this.adapter != null) {
            this.adapter.redrawFragments();
        }
    }

    public void redirectFragment(String silentRedirectUrl, GalleryImage keyImage) {
        int fragmentPosition = this.adapter.removeFragmentProducerByHost(keyImage);
        if (fragmentPosition != SwipePageAdapter.NOT_FOUND) {
            this.producer.alterImageAtPosition(fragmentPosition, silentRedirectUrl);
            GalleryViewerFragment fragment = this.adapter.getFragment(fragmentPosition);
            if (fragment == getActiveFragment()) {
                changeBreadcrumbs(fragment);
            }
            refresh();
        }
    }

    public void setInitialFragment(GalleryViewerFragment fragment) {
        this.initialFragment = fragment;
        getProducer().initialize();
        this.initialFragment.setImage((GalleryImage) this.producer.getImages().get(this.producer.getCurrentImageFocus()));
        if (!(this.savedBreadcrumbs == null || this.savedBreadcrumbs.isEmpty())) {
            this.initialFragment.setBreadcrumbs(this.savedBreadcrumbs);
            this.savedBreadcrumbs = null;
        }
        this.activeUrl = this.initialFragment.getUrl();
    }

    public void onDestroyView() {
        this.adapter.clear();
        super.onDestroyView();
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

    public String getActiveUrl() {
        return this.activeUrl;
    }

    public void removeNestedFragments() {
        if (!(getActiveFragment() == null || getActiveFragment().getBreadcrumbs() == null)) {
            this.savedBreadcrumbs = new ArrayList();
            this.savedBreadcrumbs.addAll(getActiveFragment().getBreadcrumbs());
        }
        try {
            this.pager.setAdapter(null);
        } catch (IllegalStateException e) {
            if (this.adapter != null) {
                this.adapter.clear();
                this.adapter.notifyDataSetChanged();
            }
            this.pager.setAdapter(null);
        }
    }

    public void discard() {
        if (getView() != null) {
            getView().findViewById(C0065R.id.loading_page).setVisibility(0);
        }
    }

    public void setSavedRedoFocus() {
        this.savedRedoFocus = this.producer.getCurrentImageFocus();
    }

    public void resetFocusToSavedRedoPosition() {
        this.producer.setCurrentImageFocus(this.savedRedoFocus);
    }

    public void dispatchWaitResume() {
        if (this.adapter != null) {
            this.adapter.dispatchWaitResume();
        }
    }

    public void omniSearchIsShowing() {
        if (this.adapter != null) {
            this.adapter.omniSearchIsShowing();
        }
    }

    public void omniSearchIsNotShowing() {
        if (this.adapter != null) {
            this.adapter.omniSearchIsNotShowing();
        }
    }

    public void hideTitleStrip() {
        if (getView() != null) {
            View view = getView().findViewById(C0065R.id.pager_title_strip);
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    public void showTitleStrip() {
        if (getView() != null) {
            View view = getView().findViewById(C0065R.id.pager_title_strip);
            if (view != null && this.producer != null && this.producer.getImages() != null && this.producer.getImages().size() > 0) {
                view.setVisibility(0);
            }
        }
    }
}
