package com.crumby.lib.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.thirdparty.HackyViewPager;
import java.util.ArrayList;
import java.util.List;

public class GalleryPage
  extends Fragment
  implements BreadcrumbListener
{
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
  
  private GalleryProducer getProducer()
  {
    if (this.producer == null) {
      this.producer = new CachedProducer(getArguments().getString("url"));
    }
    return this.producer;
  }
  
  private void onFragmentSelect(int paramInt)
  {
    String str = "current";
    GalleryViewerFragment localGalleryViewerFragment;
    if (this.producer.getCurrentImageFocus() < paramInt)
    {
      str = "right";
      this.producer.setCurrentImageFocus(paramInt);
      localGalleryViewerFragment = getActiveFragment();
      ((GalleryViewer)getActivity()).pagingToNewFragment();
      if ((!(localGalleryViewerFragment instanceof GalleryGridFragment)) && (ImageScrollView.userWantsFullScreen)) {
        break label85;
      }
      ((GalleryViewer)getActivity()).showOmnibar();
    }
    for (;;)
    {
      if (localGalleryViewerFragment != null) {
        break label104;
      }
      return;
      if (this.producer.getCurrentImageFocus() <= paramInt) {
        break;
      }
      str = "left";
      break;
      label85:
      if (ImageScrollView.userWantsFullScreen) {
        ((GalleryViewer)getActivity()).hideOmnibar();
      }
    }
    label104:
    localGalleryViewerFragment.indicateLastProgressChange();
    Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "paging " + str, paramInt + " " + localGalleryViewerFragment.getUrl());
    this.activeUrl = localGalleryViewerFragment.getUrl();
    changeBreadcrumbs(localGalleryViewerFragment);
  }
  
  public void changeBreadcrumbs(GalleryViewerFragment paramGalleryViewerFragment)
  {
    if (paramGalleryViewerFragment == null) {
      return;
    }
    ((GalleryViewer)getActivity()).alterBreadcrumbPath(paramGalleryViewerFragment);
  }
  
  public void discard()
  {
    if (getView() != null) {
      getView().findViewById(2131492995).setVisibility(0);
    }
  }
  
  public void dispatchWaitResume()
  {
    if (this.adapter != null) {
      this.adapter.dispatchWaitResume();
    }
  }
  
  public GalleryViewerFragment getActiveFragment()
  {
    if (this.adapter == null) {
      return null;
    }
    return this.adapter.getFragment(this.pager.getCurrentItem());
  }
  
  public String getActiveUrl()
  {
    return this.activeUrl;
  }
  
  public void hideTitleStrip()
  {
    if (getView() != null)
    {
      View localView = getView().findViewById(2131492994);
      if (localView != null) {
        localView.setVisibility(8);
      }
    }
  }
  
  public void omniSearchIsNotShowing()
  {
    if (this.adapter != null) {
      this.adapter.omniSearchIsNotShowing();
    }
  }
  
  public void omniSearchIsShowing()
  {
    if (this.adapter != null) {
      this.adapter.omniSearchIsShowing();
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramViewGroup = getProducer();
    paramLayoutInflater = paramLayoutInflater.inflate(2130903078, null);
    this.pager = ((HackyViewPager)paramLayoutInflater.findViewById(2131492993));
    ImageScrollView.userWantsFullScreen = false;
    if (this.fragmentProducers == null)
    {
      this.fragmentProducers = new LruCache(10);
      if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("crumbyUseBreadcrumbPathForDownload", false)) {
        ImageScrollView.userWantsFullScreen = true;
      }
    }
    this.adapter = new SwipePageAdapter(getActivity(), paramViewGroup, this.fragmentProducers);
    this.adapter.setStartingFragment(this.initialFragment, paramViewGroup.getCurrentImageFocus());
    ((GalleryViewer)getActivity()).showOmnibar();
    this.initialFragment = null;
    this.pageChange = new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        if (GalleryPage.this.doNotFireSelectListener) {
          return;
        }
        GalleryPage.this.onFragmentSelect(paramAnonymousInt);
      }
    };
    this.pager.initialize(this.adapter, this.pageChange, paramViewGroup.getCurrentImageFocus());
    if (paramViewGroup.getImages().size() <= 1) {
      paramLayoutInflater.findViewById(2131492994).setVisibility(8);
    }
    paramViewGroup.makeShareable();
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.savedBreadcrumbs != null) {
      this.savedBreadcrumbs.clear();
    }
    if (this.fragmentProducers != null) {
      this.fragmentProducers.evictAll();
    }
  }
  
  public void onDestroyView()
  {
    this.adapter.clear();
    super.onDestroyView();
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
  }
  
  public void redirectFragment(String paramString, GalleryImage paramGalleryImage)
  {
    int i = this.adapter.removeFragmentProducerByHost(paramGalleryImage);
    if (i != SwipePageAdapter.NOT_FOUND)
    {
      this.producer.alterImageAtPosition(i, paramString);
      paramString = this.adapter.getFragment(i);
      if (paramString == getActiveFragment()) {
        changeBreadcrumbs(paramString);
      }
      refresh();
    }
  }
  
  public void redraw()
  {
    if (this.adapter != null) {
      this.adapter.redrawFragments();
    }
  }
  
  public void refresh()
  {
    getActiveFragment().prepareForRefresh();
    int i = this.pager.getCurrentItem();
    if (this.fragmentProducers != null)
    {
      GalleryProducer localGalleryProducer = (GalleryProducer)this.fragmentProducers.get(Integer.valueOf(i));
      if ((localGalleryProducer != null) && (localGalleryProducer.getCurrentImageFocus() == 0))
      {
        this.fragmentProducers.remove(Integer.valueOf(i));
        localGalleryProducer.destroy();
      }
    }
    this.pager.setAdapter(this.adapter);
    this.producer.requestStartFetch();
    this.doNotFireSelectListener = true;
    this.pager.setCurrentItem(i);
    this.doNotFireSelectListener = false;
  }
  
  public void removeNestedFragments()
  {
    if ((getActiveFragment() != null) && (getActiveFragment().getBreadcrumbs() != null))
    {
      this.savedBreadcrumbs = new ArrayList();
      this.savedBreadcrumbs.addAll(getActiveFragment().getBreadcrumbs());
    }
    try
    {
      this.pager.setAdapter(null);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      if (this.adapter != null)
      {
        this.adapter.clear();
        this.adapter.notifyDataSetChanged();
      }
      this.pager.setAdapter(null);
    }
  }
  
  public void resetFocusToSavedRedoPosition()
  {
    this.producer.setCurrentImageFocus(this.savedRedoFocus);
  }
  
  public void setInitialFragment(GalleryViewerFragment paramGalleryViewerFragment)
  {
    this.initialFragment = paramGalleryViewerFragment;
    getProducer().initialize();
    this.initialFragment.setImage((GalleryImage)this.producer.getImages().get(this.producer.getCurrentImageFocus()));
    if ((this.savedBreadcrumbs != null) && (!this.savedBreadcrumbs.isEmpty()))
    {
      this.initialFragment.setBreadcrumbs(this.savedBreadcrumbs);
      this.savedBreadcrumbs = null;
    }
    this.activeUrl = this.initialFragment.getUrl();
  }
  
  public void setProducer(GalleryProducer paramGalleryProducer)
  {
    this.producer = paramGalleryProducer;
  }
  
  public void setSavedRedoFocus()
  {
    this.savedRedoFocus = this.producer.getCurrentImageFocus();
  }
  
  public void showTitleStrip()
  {
    if (getView() != null)
    {
      View localView = getView().findViewById(2131492994);
      if ((localView != null) && (this.producer != null) && (this.producer.getImages() != null) && (this.producer.getImages().size() > 0)) {
        localView.setVisibility(0);
      }
    }
  }
  
  public void stopLoading()
  {
    if (getActiveFragment() == null) {
      return;
    }
    getActiveFragment().stopLoading();
  }
  
  public boolean undo()
  {
    if (getActiveFragment() == null) {
      return false;
    }
    return getActiveFragment().undo();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/GalleryPage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */