package com.crumby.lib.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GalleryGridAdapter;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.SelectAllOnClickListener;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.GalleryGridDefaultHeader;
import com.crumby.lib.widget.firstparty.fragment_options.CustomToggleButton;
import com.crumby.lib.widget.firstparty.grow.ColumnCounter;
import com.crumby.lib.widget.firstparty.grow.GrowGridView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import it.sephiroth.android.library.tooltip.TooltipManager;
import it.sephiroth.android.library.tooltip.TooltipManager.Builder;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.util.Iterator;
import java.util.List;

public abstract class GalleryGridFragment
  extends GalleryListFragment
{
  protected static final int NO_FORM = 0;
  public static final int SELECT_ALL_AND_THEN_SAVE_TOOLTIP = 1;
  public static final int SELECT_ALL_TOOLTIP = 0;
  private static boolean showSelectAllSaveHint;
  private static boolean shownSelectAllImagesHint;
  private static boolean shownTutorial;
  private String descriptionToBeAdded;
  private CustomToggleButton searchGallery;
  private ImageButton selectAll;
  private ImageButton web;
  
  private GrowGridView grid()
  {
    return (GrowGridView)this.list;
  }
  
  private void showGrowGridTutorial(RelativeLayout paramRelativeLayout)
  {
    final SharedPreferences localSharedPreferences;
    if ((!shownTutorial) && (paramRelativeLayout != null) && (!getErrorView().isShowing()) && (this.producer != null) && (this.producer.getImages() != null) && (!this.producer.getImages().isEmpty()))
    {
      localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.list.getContext());
      if (!localSharedPreferences.getBoolean("shownTutorial", false)) {
        break label80;
      }
      shownTutorial = true;
    }
    label80:
    while (paramRelativeLayout.findViewById(2131493010) != null) {
      return;
    }
    Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "show grid", null);
    View localView = View.inflate(this.list.getContext(), 2130903083, null);
    paramRelativeLayout.addView(localView, new RelativeLayout.LayoutParams(-1, -1));
    localView.setAlpha(0.0F);
    localView.findViewById(2131493010).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        GalleryGridFragment.this.getView().findViewById(2131493008).setVisibility(8);
        paramAnonymousView = localSharedPreferences.edit();
        paramAnonymousView.putBoolean("shownTutorial", true);
        paramAnonymousView.commit();
        GalleryGridFragment.access$102(true);
      }
    });
    localView.animate().alpha(1.0F).setDuration(500L).start();
  }
  
  protected void alterThisBreadcrumbText(List<Breadcrumb> paramList, String paramString1, String paramString2)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Breadcrumb localBreadcrumb = (Breadcrumb)paramList.next();
      if (localBreadcrumb.getFragmentIndex().getFragmentClass().equals(getClass())) {
        localBreadcrumb.setBreadcrumbText(paramString1 + ": " + paramString2);
      }
    }
  }
  
  protected void appendSearchQuery(List<Breadcrumb> paramList, String paramString1, String paramString2)
  {
    Uri localUri = Uri.parse(getUrl());
    if (GalleryProducer.getQueryParameter(localUri, getUrl(), paramString2) != null) {
      alterThisBreadcrumbText(paramList, paramString1, GalleryProducer.getQueryParameter(localUri, getUrl(), paramString2));
    }
    super.setBreadcrumbs(paramList);
  }
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = super.createFragmentView(paramLayoutInflater, paramViewGroup, paramBundle);
    paramLayoutInflater.findViewById(2131492983).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        GalleryGridFragment.this.getViewer().showOmnibar();
      }
    });
    if (getUserVisibleHint()) {
      showGrowGridTutorial((RelativeLayout)paramLayoutInflater);
    }
    return paramLayoutInflater;
  }
  
  public GalleryListAdapter createListAdapter()
  {
    return new GalleryGridAdapter();
  }
  
  public void deferSetDescription(String paramString)
  {
    this.descriptionToBeAdded = paramString;
  }
  
  public void expandToImage(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    grid().zoomIntoSequence(paramView, paramGalleryImage.getPosition());
  }
  
  protected int getHeaderLayout()
  {
    return 0;
  }
  
  public String getSearchArgumentName()
  {
    return null;
  }
  
  public String getSearchPrefix()
  {
    return null;
  }
  
  protected View inflateAbslistView(LayoutInflater paramLayoutInflater)
  {
    View localView = paramLayoutInflater.inflate(2130903073, null, false);
    GrowGridView localGrowGridView = (GrowGridView)localView.findViewById(2131492982);
    this.list = localGrowGridView;
    localGrowGridView.initialize((ImageView)localView.findViewById(2131492984), (ColumnCounter)localView.findViewById(2131492985), getImage());
    if (getHeaderLayout() != 0)
    {
      paramLayoutInflater = (ViewGroup)paramLayoutInflater.inflate(getHeaderLayout(), null);
      localGrowGridView.addHeaderView(paramLayoutInflater);
      setupHeaderLayout(paramLayoutInflater);
    }
    for (;;)
    {
      this.web = ((ImageButton)localView.findViewById(2131492973));
      this.selectAll = ((ImageButton)localView.findViewById(2131492972));
      this.searchGallery = ((CustomToggleButton)localView.findViewById(2131492968));
      return localView;
      paramLayoutInflater = (GalleryGridDefaultHeader)paramLayoutInflater.inflate(2130903077, null);
      paramLayoutInflater.initialize(getImage());
      localGrowGridView.addHeaderView(paramLayoutInflater);
    }
  }
  
  protected void initializeAdapter()
  {
    super.initializeAdapter();
    if (this.descriptionToBeAdded != null) {
      getImage().setDescription(this.descriptionToBeAdded);
    }
    this.selectAll.setVisibility(0);
    this.selectAll.setOnClickListener(new SelectAllOnClickListener(this.producer.getImages(), ((GalleryViewer)getActivity()).getMultiSelect())
    {
      public void onClick(View paramAnonymousView)
      {
        toggle();
        GalleryGridFragment.this.adapter.notifyDataSetChanged();
        if (GalleryGridFragment.showSelectAllSaveHint)
        {
          GalleryGridFragment.access$002(false);
          Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "complete: " + GalleryGridFragment.this.getImage().getLinkUrl() + " @ position " + GalleryGridFragment.this.list.getFirstVisiblePosition());
          TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).remove(0);
          TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).create(1).anchor(GalleryGridFragment.this.getActivity().findViewById(2131493057), TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000L).activateDelay(500L).withStyleId(2131361823).text("With 'Select All Images', you can save hundreds of images at a time. Try it out!").maxWidth(1000).show();
        }
      }
    });
  }
  
  public void onPause()
  {
    super.onPause();
  }
  
  public void prepareForRefresh()
  {
    if (this.producer != null) {
      this.producer.shareAndSetCurrentImageFocus(this.list.getFirstVisiblePosition());
    }
  }
  
  public void redraw()
  {
    super.redraw();
    ViewGroup localViewGroup = (ViewGroup)this.selectAll.getParent();
    localViewGroup.removeView(this.selectAll);
    localViewGroup.addView(this.selectAll, 1);
  }
  
  public void refreshGalleryMetadataView()
  {
    if (this.adapter == null) {}
  }
  
  public void setBreadcrumbs(List<Breadcrumb> paramList)
  {
    if ((getSearchPrefix() != null) && (getSearchArgumentName() != null)) {
      appendSearchQuery(paramList, getSearchPrefix(), getSearchArgumentName());
    }
    super.setBreadcrumbs(paramList);
  }
  
  public void setUserVisibleHint(boolean paramBoolean)
  {
    super.setUserVisibleHint(paramBoolean);
    if ((paramBoolean) && (getView() != null))
    {
      if (!shownTutorial) {
        showGrowGridTutorial((RelativeLayout)getView());
      }
      if (this.list != null) {
        grid().checkForPersistence();
      }
    }
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup) {}
  
  public void showGrowGridTutorial()
  {
    showGrowGridTutorial((RelativeLayout)getView());
  }
  
  public void showSelectAllImagesHint()
  {
    if ((shownSelectAllImagesHint) || (PreferenceManager.getDefaultSharedPreferences(this.list.getContext()).getBoolean("shownSelectAllImagesHint", false))) {
      return;
    }
    Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "start: " + getImage().getLinkUrl() + " @ position " + this.list.getFirstVisiblePosition());
    TooltipManager.getInstance(getActivity()).create(0).toggleArrow(true).anchor(this.selectAll, TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000L).activateDelay(500L).withStyleId(2131361823).text("Did you try the 'select all images' button yet?").maxWidth(1000).show();
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.list.getContext()).edit();
    localEditor.putBoolean("shownSelectAllImagesHint", true);
    localEditor.commit();
    shownSelectAllImagesHint = true;
    showSelectAllSaveHint = true;
  }
  
  public boolean willAllowPaging(MotionEvent paramMotionEvent)
  {
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/GalleryGridFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */