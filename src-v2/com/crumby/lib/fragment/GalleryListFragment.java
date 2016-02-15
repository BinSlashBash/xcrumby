/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.ListAdapter
 */
package com.crumby.lib.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public abstract class GalleryListFragment
extends GalleryViewerFragment
implements GalleryList,
GalleryClickHandler {
    public static int MINIMUM_THUMBNAIL_WIDTH = 100;
    public static int THUMBNAIL_HEIGHT = 150;
    protected GalleryListAdapter adapter;
    boolean clicked;
    protected ViewGroup description;
    private ErrorView errorView;
    protected AbsListView list;

    private void attachImageClickListener() {
        ImageClickListener imageClickListener = new ImageClickListener(this, ((GalleryViewer)this.getActivity()).getMultiSelect(), "grid");
        this.list.setOnItemClickListener((AdapterView.OnItemClickListener)imageClickListener);
        this.list.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)imageClickListener);
    }

    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        if (galleryImage.isALinkToGallery()) {
            this.postUrlChangeToBus(galleryImage.getLinkUrl(), null);
            return;
        }
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = this.inflateAbslistView(layoutInflater);
        this.errorView = (ErrorView)layoutInflater.findViewById(2131492959);
        this.attachImageClickListener();
        this.initializeAdapter();
        return layoutInflater;
    }

    public GalleryListAdapter createListAdapter() {
        return new GalleryListAdapter();
    }

    @Override
    public void deferSetDescription(String string2) {
    }

    @Override
    public GalleryConsumer getConsumer() {
        return this.adapter;
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    public ErrorView getErrorView() {
        return this.errorView;
    }

    public AbsListView getList() {
        return this.list;
    }

    @Override
    public void goToImage(View view, GalleryImage galleryImage, int n2) {
        if (this.clicked) {
            return;
        }
        this.clicked = true;
        this.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    public void hideClutter() {
    }

    protected View inflateAbslistView(LayoutInflater layoutInflater) {
        layoutInflater = layoutInflater.inflate(2130903076, null);
        this.list = (AbsListView)layoutInflater.findViewById(2131492982);
        return layoutInflater;
    }

    protected void initializeAdapter() {
        this.adapter = this.createListAdapter();
        this.adapter.initialize(this);
        this.adapter.startFetching();
        this.list.setClipChildren(false);
        this.list.setAdapter((ListAdapter)this.adapter);
        this.list.setOnScrollListener((AbsListView.OnScrollListener)this.adapter);
    }

    @Override
    public void onDestroyView() {
        if (this.adapter != null) {
            this.adapter.destroy();
        }
        if (this.getView() != null) {
            ((ViewGroup)this.getView()).removeView((View)this.list);
        }
        this.list.setAdapter(null);
        this.list = null;
        this.adapter = null;
        super.onDestroyView();
    }

    public void onResume() {
        super.onResume();
        this.resume();
    }

    @Override
    public void prepareForRefresh() {
    }

    @Override
    public void redraw() {
        super.redraw();
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resume() {
        if (this.producer != null && !this.checkIfAllowedToResume()) {
            this.scrollToImageInList(this.producer.getCurrentImageFocus());
        }
    }

    public void scrollToImageInList(int n2) {
        if (n2 == -1) {
            return;
        }
        final int n3 = this.adapter.prepareHighlight(n2);
        CrDb.d("viewer fragment", "setting to:" + n2);
        this.list.setSelection(n2);
        this.list.postDelayed(new Runnable(){

            @Override
            public void run() {
                if (GalleryListFragment.this.getActivity() == null || GalleryListFragment.this.list == null) {
                    return;
                }
                GalleryListFragment.this.list.setSelection(n3);
            }
        }, 500);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setUserVisibleHint(boolean bl2) {
        super.setUserVisibleHint(bl2);
        if (this.adapter == null) return;
        if (bl2) {
            this.adapter.notifyDataSetChanged();
            return;
        }
        this.adapter.cancelPicassoLoad();
    }

    @Override
    public void showClutter() {
    }

    @Override
    public void showError(DisplayError displayError, String string2, String string3) {
        if (this.errorView != null) {
            this.errorView.show(displayError, string2, string3);
        }
    }

    @Override
    public void stopLoading() {
        this.adapter.stopLoading();
    }

    @Override
    public boolean undo() {
        if (this.errorView != null && this.errorView.close()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean willAllowPaging(MotionEvent motionEvent) {
        return false;
    }

}

