/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewPropertyAnimator
 *  android.widget.AbsListView
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 */
package com.crumby.lib.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryListFragment;
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
import java.util.Iterator;
import java.util.List;

public abstract class GalleryGridFragment
extends GalleryListFragment {
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

    private GrowGridView grid() {
        return (GrowGridView)this.list;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showGrowGridTutorial(RelativeLayout relativeLayout) {
        if (shownTutorial || relativeLayout == null || this.getErrorView().isShowing() || this.producer == null || this.producer.getImages() == null || this.producer.getImages().isEmpty()) return;
        {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.list.getContext());
            if (sharedPreferences.getBoolean("shownTutorial", false)) {
                shownTutorial = true;
                return;
            } else {
                if (relativeLayout.findViewById(2131493010) != null) return;
                {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "show grid", null);
                    View view = View.inflate((Context)this.list.getContext(), (int)2130903083, (ViewGroup)null);
                    relativeLayout.addView(view, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                    view.setAlpha(0.0f);
                    view.findViewById(2131493010).setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            GalleryGridFragment.this.getView().findViewById(2131493008).setVisibility(8);
                            view = sharedPreferences.edit();
                            view.putBoolean("shownTutorial", true);
                            view.commit();
                            shownTutorial = true;
                        }
                    });
                    view.animate().alpha(1.0f).setDuration(500).start();
                    return;
                }
            }
        }
    }

    protected void alterThisBreadcrumbText(List<Breadcrumb> object, String string2, String string3) {
        object = object.iterator();
        while (object.hasNext()) {
            Breadcrumb breadcrumb = (Breadcrumb)((Object)object.next());
            if (!breadcrumb.getFragmentIndex().getFragmentClass().equals(this.getClass())) continue;
            breadcrumb.setBreadcrumbText(string2 + ": " + string3);
            break;
        }
    }

    protected void appendSearchQuery(List<Breadcrumb> list, String string2, String string3) {
        Uri uri = Uri.parse((String)this.getUrl());
        if (GalleryProducer.getQueryParameter(uri, this.getUrl(), string3) != null) {
            this.alterThisBreadcrumbText(list, string2, GalleryProducer.getQueryParameter(uri, this.getUrl(), string3));
        }
        super.setBreadcrumbs(list);
    }

    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.createFragmentView(layoutInflater, viewGroup, bundle);
        layoutInflater.findViewById(2131492983).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryGridFragment.this.getViewer().showOmnibar();
            }
        });
        if (this.getUserVisibleHint()) {
            this.showGrowGridTutorial((RelativeLayout)layoutInflater);
        }
        return layoutInflater;
    }

    @Override
    public GalleryListAdapter createListAdapter() {
        return new GalleryGridAdapter();
    }

    @Override
    public void deferSetDescription(String string2) {
        this.descriptionToBeAdded = string2;
    }

    public void expandToImage(View view, GalleryImage galleryImage, int n2) {
        this.grid().zoomIntoSequence(view, galleryImage.getPosition());
    }

    protected int getHeaderLayout() {
        return 0;
    }

    public String getSearchArgumentName() {
        return null;
    }

    public String getSearchPrefix() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected View inflateAbslistView(LayoutInflater object) {
        GrowGridView growGridView;
        View view = object.inflate(2130903073, null, false);
        this.list = growGridView = (GrowGridView)view.findViewById(2131492982);
        growGridView.initialize((ImageView)view.findViewById(2131492984), (ColumnCounter)view.findViewById(2131492985), this.getImage());
        if (this.getHeaderLayout() != 0) {
            object = (ViewGroup)object.inflate(this.getHeaderLayout(), null);
            growGridView.addHeaderView((View)object);
            this.setupHeaderLayout((ViewGroup)object);
        } else {
            object = (GalleryGridDefaultHeader)object.inflate(2130903077, null);
            object.initialize(this.getImage());
            growGridView.addHeaderView((View)object);
        }
        this.web = (ImageButton)view.findViewById(2131492973);
        this.selectAll = (ImageButton)view.findViewById(2131492972);
        this.searchGallery = (CustomToggleButton)view.findViewById(2131492968);
        return view;
    }

    @Override
    protected void initializeAdapter() {
        super.initializeAdapter();
        if (this.descriptionToBeAdded != null) {
            this.getImage().setDescription(this.descriptionToBeAdded);
        }
        this.selectAll.setVisibility(0);
        this.selectAll.setOnClickListener((View.OnClickListener)new SelectAllOnClickListener(this.producer.getImages(), ((GalleryViewer)this.getActivity()).getMultiSelect()){

            public void onClick(View view) {
                this.toggle();
                GalleryGridFragment.this.adapter.notifyDataSetChanged();
                if (showSelectAllSaveHint) {
                    showSelectAllSaveHint = false;
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "complete: " + GalleryGridFragment.this.getImage().getLinkUrl() + " @ position " + GalleryGridFragment.this.list.getFirstVisiblePosition());
                    TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).remove(0);
                    TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).create(1).anchor(GalleryGridFragment.this.getActivity().findViewById(2131493057), TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000).activateDelay(500).withStyleId(2131361823).text("With 'Select All Images', you can save hundreds of images at a time. Try it out!").maxWidth(1000).show();
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void prepareForRefresh() {
        if (this.producer != null) {
            this.producer.shareAndSetCurrentImageFocus(this.list.getFirstVisiblePosition());
        }
    }

    @Override
    public void redraw() {
        super.redraw();
        ViewGroup viewGroup = (ViewGroup)this.selectAll.getParent();
        viewGroup.removeView((View)this.selectAll);
        viewGroup.addView((View)this.selectAll, 1);
    }

    public void refreshGalleryMetadataView() {
        if (this.adapter == null) {
            // empty if block
        }
    }

    @Override
    public void setBreadcrumbs(List<Breadcrumb> list) {
        if (this.getSearchPrefix() != null && this.getSearchArgumentName() != null) {
            this.appendSearchQuery(list, this.getSearchPrefix(), this.getSearchArgumentName());
        }
        super.setBreadcrumbs(list);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        super.setUserVisibleHint(bl2);
        if (bl2 && this.getView() != null) {
            if (!shownTutorial) {
                this.showGrowGridTutorial((RelativeLayout)this.getView());
            }
            if (this.list != null) {
                this.grid().checkForPersistence();
            }
        }
    }

    protected void setupHeaderLayout(ViewGroup viewGroup) {
    }

    public void showGrowGridTutorial() {
        this.showGrowGridTutorial((RelativeLayout)this.getView());
    }

    public void showSelectAllImagesHint() {
        if (shownSelectAllImagesHint || PreferenceManager.getDefaultSharedPreferences((Context)this.list.getContext()).getBoolean("shownSelectAllImagesHint", false)) {
            return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "start: " + this.getImage().getLinkUrl() + " @ position " + this.list.getFirstVisiblePosition());
        TooltipManager.getInstance(this.getActivity()).create(0).toggleArrow(true).anchor((View)this.selectAll, TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000).activateDelay(500).withStyleId(2131361823).text("Did you try the 'select all images' button yet?").maxWidth(1000).show();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.list.getContext()).edit();
        editor.putBoolean("shownSelectAllImagesHint", true);
        editor.commit();
        shownSelectAllImagesHint = true;
        showSelectAllSaveHint = true;
    }

    @Override
    public boolean willAllowPaging(MotionEvent motionEvent) {
        return false;
    }

}

