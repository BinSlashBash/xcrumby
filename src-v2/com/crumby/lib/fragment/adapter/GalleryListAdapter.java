/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.TransitionDrawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.BaseAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 */
package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.fragment.adapter.ListFragmentProgress;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import twowayview.TwoWayView;

public class GalleryListAdapter
extends BaseAdapter
implements AbsListView.OnScrollListener,
GalleryConsumer,
TwoWayView.OnScrollListener {
    private int cachedSizeOfNextBatch;
    private int cachedThumbnailWidth;
    private int currentScrollState;
    private GalleryList fragment;
    private int greatestViewPosition;
    TransitionDrawable highlight;
    private boolean highlighted;
    protected int imageWrapperId;
    protected ArrayList<GalleryImage> images;
    private LayoutInflater inflater;
    private AdapterView list;
    private int loadingThumbnailPosition;
    private boolean noMoreLoading;
    private Handler padHandler;
    protected Runnable padOnScroll;
    protected GalleryProducer producer;
    protected ListFragmentProgress progress;
    private boolean resetThumbnailPosition;
    private int unfilledImages;

    private void addUnfilledImage() {
        this.images.add(new GalleryImage(true));
        ++this.unfilledImages;
    }

    private void clearWrapper(GridImageWrapper gridImageWrapper) {
        this.progress.removeLoadingImage(gridImageWrapper.getImage());
        if (this.fragment.getContext() == null) {
            return;
        }
        Picasso.with(this.fragment.getContext()).cancelRequest(gridImageWrapper.imageView);
        gridImageWrapper.clear();
    }

    private void fetchMoreImages() {
        if (this.noMoreLoading) {
            return;
        }
        this.progress.signalFetching();
        this.producer.requestFetch();
    }

    private int getMaxUnfilled() {
        return this.getSizeOfNextBatch();
    }

    private int getSizeOfNextBatchFromDimensions(int n2, int n3) {
        int n4 = this.list.getMeasuredWidth();
        n2 = n4 * this.list.getMeasuredHeight() / (n3 * n2);
        if (n4 == 0) {
            this.cachedThumbnailWidth = -1;
        }
        return Math.max(2, Math.min(n2 + 1, 20));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void highlightStartImage(int n2, View view) {
        view = (FrameLayout)view;
        if (this.fragment.getContext() == null) {
            return;
        }
        if (n2 == this.loadingThumbnailPosition && !this.resetThumbnailPosition && !this.highlighted) {
            this.highlighted = true;
            this.highlight = (TransitionDrawable)this.fragment.getContext().getResources().getDrawable(2130837691);
            view.setForeground((Drawable)this.highlight);
            this.highlight.resetTransition();
            this.highlight.setCrossFadeEnabled(true);
            this.highlight.startTransition(2000);
            return;
        }
        if (view.getForeground() != this.highlight) return;
        if (n2 == this.loadingThumbnailPosition) return;
        view.setForeground(this.fragment.getContext().getResources().getDrawable(2130837656));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void loadThumbnail(GridImageWrapper gridImageWrapper, int n2) {
        GalleryImage galleryImage = gridImageWrapper.getImage();
        boolean bl2 = this.currentScrollState == 2 && this.getNumColumns() != 1;
        if (galleryImage == null || bl2 || gridImageWrapper.hasRendered(galleryImage) || !this.fragment.getUserVisibleHint()) {
            if (gridImageWrapper.hasRendered(galleryImage)) {
                CrDb.d("adapter", "already rendered image at " + n2);
            }
            return;
        }
        if (!galleryImage.isVisited()) {
            this.progress.addLoadingImage(galleryImage);
            galleryImage.setVisited(true);
        }
        if (galleryImage.isAnimated() && this.getNumColumns() == 1) {
            gridImageWrapper.loadWithWebView(this.fragment.getContext());
        } else {
            this.loadThumbnailWithPicasso(galleryImage, gridImageWrapper.imageView, n2);
        }
        gridImageWrapper.setRenderedPosition();
    }

    private boolean nearEndOfFilledGrid(int n2, int n3) {
        if (n2 + n3 + 1 >= this.images.size() - this.unfilledImages) {
            return true;
        }
        return false;
    }

    private boolean nearEndOfGrid(int n2, int n3) {
        if (n2 + n3 + this.getSizeOfNextBatch() > this.images.size()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void padBottom(boolean bl2) {
        if (!this.producer.isAvailable()) {
            CrDb.d("adapter", "padBottom: Gallery Producer not available. Can't pad bottom");
            return;
        } else {
            int n2 = this.getMaxUnfilled() - this.unfilledImages;
            CrDb.d("adapter", "notify/padBottom: " + n2 + " unfilled images added");
            int n3 = this.getMaxUnfilled();
            while (this.unfilledImages < n3) {
                this.addUnfilledImage();
            }
            if (!bl2 || n2 == 0) return;
            {
                this.notifyDataSetChanged();
                return;
            }
        }
    }

    private void refresh() {
    }

    private void removeEmptyWrappers() {
        int n2;
        while (!this.images.isEmpty() && this.images.get(n2 = this.images.size() - 1).isUnfilled()) {
            this.images.remove(n2);
        }
    }

    @Override
    public boolean addImages(List<GalleryImage> object) {
        int n2;
        boolean bl2 = false;
        object = object.iterator();
        while (object.hasNext()) {
            GalleryImage galleryImage = (GalleryImage)object.next();
            if (this.unfilledImages == 0) {
                this.addUnfilledImage();
            }
            ArrayList<GalleryImage> arrayList = this.images;
            n2 = this.images.size();
            int n3 = this.unfilledImages;
            this.unfilledImages = n3 - 1;
            arrayList.set(n2 - n3, galleryImage);
        }
        n2 = this.list.getFirstVisiblePosition();
        if (this.nearEndOfFilledGrid(n2, this.list.getLastVisiblePosition() - n2)) {
            CrDb.d("adapter", "Need to download more images!");
            this.padBottom(false);
            bl2 = true;
        }
        if (this.fragment instanceof GalleryGridFragment) {
            ((GalleryGridFragment)this.fragment).showGrowGridTutorial();
        }
        CrDb.d("adapter", "Total images size: " + this.images.size());
        CrDb.d("adapter", "Actual images size: " + (this.images.size() - this.unfilledImages));
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void cancelPicassoLoad() {
        if (this.fragment.getContext() != null) {
            for (int i2 = this.list.getChildCount() - 1; i2 >= 0; --i2) {
                Object object = this.list.getChildAt(i2).getTag();
                if (object == null) continue;
                Picasso.with(this.fragment.getContext()).cancelRequest(((GridImageWrapper)object).getImageView());
            }
        }
    }

    public void destroy() {
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        this.cancelPicassoLoad();
        for (int i2 = this.list.getChildCount() - 1; i2 >= 0; --i2) {
            Object object = this.list.getChildAt(i2).getTag();
            if (object == null) continue;
            ((GridImageWrapper)object).destroy();
        }
        if (this.images != null) {
            this.images.clear();
        }
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();
    }

    @Override
    public void finishLoading() {
        while (this.unfilledImages > 0) {
            this.images.remove(this.images.size() - 1);
            --this.unfilledImages;
        }
        this.progress.done();
        this.removeEmptyWrappers();
        this.notifyDataSetChanged();
        if (this.images.isEmpty() && this.fragment != null) {
            this.fragment.showError(DisplayError.EMPTY_GALLERY, "No images or galleries were found.", this.producer.getHostUrl());
        }
    }

    protected int getColumnWidth() {
        return GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH;
    }

    public int getCount() {
        return this.images.size();
    }

    protected GalleryList getGalleryList() {
        return this.fragment;
    }

    public Object getItem(int n2) {
        return this.images.get(n2);
    }

    public long getItemId(int n2) {
        return 0;
    }

    protected AdapterView getList() {
        return this.list;
    }

    protected int getNumColumns() {
        return 1;
    }

    public int getSizeOfNextBatch() {
        if (this.cachedThumbnailWidth != this.getColumnWidth()) {
            this.cachedThumbnailWidth = this.getColumnWidth();
            this.cachedSizeOfNextBatch = this.getSizeOfNextBatchFromDimensions(this.getColumnWidth(), this.getColumnWidth());
        }
        return this.cachedSizeOfNextBatch;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View view, ViewGroup object) {
        CrDb.d("adapter", "getting view for position " + n2);
        if (view == null || view.getTag() == null) {
            view = this.inflater.inflate(this.imageWrapperId, null);
            object = new GridImageWrapper(view, this.isInSequence());
            view.setTag(object);
        } else {
            object = (GridImageWrapper)view.getTag();
        }
        GalleryImage galleryImage = this.images.get(n2);
        this.highlightStartImage(n2, view);
        if (!object.isFilledWith(galleryImage)) {
            this.clearWrapper((GridImageWrapper)object);
            if (!galleryImage.isUnfilled()) {
                object.set(galleryImage);
            }
        } else {
            CrDb.d("adapter", "already filled");
        }
        CrDb.d("adapter", "loading imageView position:" + this.loadingThumbnailPosition + " wrapper: " + n2);
        this.loadThumbnail((GridImageWrapper)object, n2);
        boolean bl2 = object.getImage() != null && object.getImage().isChecked();
        ((ImagePressWrapper)view).setChecked(bl2);
        return view;
    }

    public void initialize(GalleryList galleryList) {
        this.loadingThumbnailPosition = -1;
        this.fragment = galleryList;
        this.list = galleryList.getList();
        this.images = new ArrayList();
        this.producer = galleryList.getProducer();
        this.producer.initialize(this, galleryList.getImage(), galleryList.getArguments());
        this.inflater = LayoutInflater.from((Context)galleryList.getContext());
        this.imageWrapperId = 2130903096;
        this.progress = new ListFragmentProgress(galleryList);
        this.padHandler = new Handler();
        this.padOnScroll = new Runnable(){

            @Override
            public void run() {
                GalleryListAdapter.this.padBottom();
            }
        };
    }

    protected boolean isInSequence() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void loadThumbnailWithPicasso(GalleryImage galleryImage, ImageView imageView, int n2) {
        Object object;
        if (this.fragment.getContext() == null || (object = galleryImage.getThumbnailUrl()) == null || object.equals("")) {
            return;
        }
        imageView.setMinimumWidth(GalleryListFragment.THUMBNAIL_HEIGHT);
        imageView.setMinimumHeight(GalleryListFragment.THUMBNAIL_HEIGHT);
        object = Picasso.with(this.fragment.getContext()).load((String)object);
        object.resize(GalleryGridFragment.THUMBNAIL_HEIGHT, GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
        object.error(2130837617).into(imageView, this.progress.getCallback(galleryImage, imageView));
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!GalleryViewer.IsInTest() || this.images == null || !this.images.isEmpty()) {
            // empty if block
        }
    }

    public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
        if (this.nearEndOfGrid(n2, n3)) {
            this.padBottom();
        }
        if (this.nearEndOfFilledGrid(n2, n3) && this.producer.isAvailable()) {
            CrDb.d("adapter", "near end of filled grid. fetching more images");
            this.fetchMoreImages();
        }
    }

    @Override
    public void onScroll(TwoWayView twoWayView, int n2, int n3, int n4) {
        this.onScroll((AbsListView)null, n2, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onScrollStateChanged(AbsListView absListView, int n2) {
        View view;
        CrDb.d("adapter", " will try to pad once scroll state is idle: " + n2);
        this.padHandler.removeCallbacks(this.padOnScroll);
        if (n2 == 0) {
            this.padHandler.postDelayed(this.padOnScroll, 2000);
            int n3 = this.list.getFirstVisiblePosition();
            int n4 = this.list.getLastVisiblePosition();
            this.producer.setCurrentImageFocus(n3);
            if (n3 > this.getMaxUnfilled() && this.fragment instanceof GalleryGridFragment) {
                ((GalleryGridFragment)this.fragment).showSelectAllImagesHint();
            }
            if (this.currentScrollState == 2) {
                if (this.greatestViewPosition < n4) {
                    this.greatestViewPosition = n4;
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "scroll stop", "" + n4 + "");
                }
                if (this.getNumColumns() != 1) {
                    CrDb.d("list adapter", "scroll state has changed, time to notify!");
                    this.notifyDataSetChanged();
                }
            }
        } else if (n2 == 2 && this.fragment.getContext() != null && this.fragment.getContext() instanceof Activity && (absListView = (InputMethodManager)this.fragment.getContext().getSystemService("input_method")).isAcceptingText() && (view = ((Activity)this.fragment.getContext()).getCurrentFocus()) != null) {
            view.clearFocus();
            absListView.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        this.currentScrollState = n2;
    }

    @Override
    public void onScrollStateChanged(TwoWayView twoWayView, int n2) {
        this.onScrollStateChanged((AbsListView)null, n2);
    }

    protected void padBottom() {
        this.padBottom(true);
    }

    public void pause() {
        if (this.list instanceof AbsListView) {
            ((AbsListView)this.list).smoothScrollBy(0, 0);
        }
        this.currentScrollState = 2;
    }

    public int prepareHighlight(int n2) {
        this.highlighted = false;
        this.loadingThumbnailPosition = n2;
        this.resetThumbnailPosition = false;
        return this.loadingThumbnailPosition;
    }

    @Override
    public void showError(Exception exception) {
        this.fragment.showError(DisplayError.GALLERY_NOT_LOADING, exception.toString(), this.producer.getHostUrl());
    }

    public void startFetching() {
        if (this.producer.requestStartFetch()) {
            this.progress.signalFetching();
            this.padBottom();
        }
    }

    public void startFetchingAndThenFinish() {
        this.startFetching();
        this.noMoreLoading = true;
    }

    public void stopLoading() {
        this.noMoreLoading = true;
        this.producer.haltDownload();
        this.progress.stop();
        this.finishLoading();
    }

    public void unpause() {
        this.currentScrollState = 0;
    }

}

