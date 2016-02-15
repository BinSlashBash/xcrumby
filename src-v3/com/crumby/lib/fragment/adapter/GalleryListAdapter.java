package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.google.android.gms.games.GamesStatusCodes;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.List;
import twowayview.TwoWayView;

public class GalleryListAdapter extends BaseAdapter implements OnScrollListener, GalleryConsumer, TwoWayView.OnScrollListener {
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

    /* renamed from: com.crumby.lib.fragment.adapter.GalleryListAdapter.1 */
    class C01051 implements Runnable {
        C01051() {
        }

        public void run() {
            GalleryListAdapter.this.padBottom();
        }
    }

    protected GalleryList getGalleryList() {
        return this.fragment;
    }

    public void initialize(GalleryList fragment) {
        this.loadingThumbnailPosition = -1;
        this.fragment = fragment;
        this.list = fragment.getList();
        this.images = new ArrayList();
        this.producer = fragment.getProducer();
        this.producer.initialize(this, fragment.getImage(), fragment.getArguments());
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.imageWrapperId = C0065R.layout.list_image_wrapper;
        this.progress = new ListFragmentProgress(fragment);
        this.padHandler = new Handler();
        this.padOnScroll = new C01051();
    }

    private int getMaxUnfilled() {
        return getSizeOfNextBatch();
    }

    public int getSizeOfNextBatch() {
        if (this.cachedThumbnailWidth != getColumnWidth()) {
            this.cachedThumbnailWidth = getColumnWidth();
            this.cachedSizeOfNextBatch = getSizeOfNextBatchFromDimensions(getColumnWidth(), getColumnWidth());
        }
        return this.cachedSizeOfNextBatch;
    }

    private int getSizeOfNextBatchFromDimensions(int thumbnailWidth, int thumbnailHeight) {
        int width = this.list.getMeasuredWidth();
        int nextBatch = ((width * this.list.getMeasuredHeight()) / (thumbnailHeight * thumbnailWidth)) + 1;
        if (width == 0) {
            this.cachedThumbnailWidth = -1;
        }
        return Math.max(2, Math.min(nextBatch, 20));
    }

    private void padBottom(boolean signal) {
        if (this.producer.isAvailable()) {
            int change = getMaxUnfilled() - this.unfilledImages;
            CrDb.m0d("adapter", "notify/padBottom: " + change + " unfilled images added");
            int maxUnfilled = getMaxUnfilled();
            while (this.unfilledImages < maxUnfilled) {
                addUnfilledImage();
            }
            if (signal && change != 0) {
                notifyDataSetChanged();
                return;
            }
            return;
        }
        CrDb.m0d("adapter", "padBottom: Gallery Producer not available. Can't pad bottom");
    }

    public void startFetching() {
        if (this.producer.requestStartFetch()) {
            this.progress.signalFetching();
            padBottom();
        }
    }

    private void addUnfilledImage() {
        this.images.add(new GalleryImage(true));
        this.unfilledImages++;
    }

    protected void padBottom() {
        padBottom(true);
    }

    public int getCount() {
        return this.images.size();
    }

    public Object getItem(int position) {
        return this.images.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public boolean addImages(List<GalleryImage> newGalleryImages) {
        boolean fetchAgain = false;
        for (GalleryImage galleryImage : newGalleryImages) {
            if (this.unfilledImages == 0) {
                addUnfilledImage();
            }
            ArrayList arrayList = this.images;
            int size = this.images.size();
            int i = this.unfilledImages;
            this.unfilledImages = i - 1;
            arrayList.set(size - i, galleryImage);
        }
        int firstVisible = this.list.getFirstVisiblePosition();
        if (nearEndOfFilledGrid(firstVisible, this.list.getLastVisiblePosition() - firstVisible)) {
            CrDb.m0d("adapter", "Need to download more images!");
            padBottom(false);
            fetchAgain = true;
        }
        if (this.fragment instanceof GalleryGridFragment) {
            ((GalleryGridFragment) this.fragment).showGrowGridTutorial();
        }
        CrDb.m0d("adapter", "Total images size: " + this.images.size());
        CrDb.m0d("adapter", "Actual images size: " + (this.images.size() - this.unfilledImages));
        return fetchAgain;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!GalleryViewer.IsInTest() || this.images == null || !this.images.isEmpty()) {
        }
    }

    private void clearWrapper(GridImageWrapper holder) {
        this.progress.removeLoadingImage(holder.getImage());
        if (this.fragment.getContext() != null) {
            Picasso.with(this.fragment.getContext()).cancelRequest(holder.imageView);
            holder.clear();
        }
    }

    private void highlightStartImage(int position, View convertView) {
        FrameLayout frame = (FrameLayout) convertView;
        if (this.fragment.getContext() != null) {
            if (position == this.loadingThumbnailPosition && !this.resetThumbnailPosition && !this.highlighted) {
                this.highlighted = true;
                this.highlight = (TransitionDrawable) this.fragment.getContext().getResources().getDrawable(C0065R.drawable.select_foreground_fade_reverse);
                frame.setForeground(this.highlight);
                this.highlight.resetTransition();
                this.highlight.setCrossFadeEnabled(true);
                this.highlight.startTransition(GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS);
            } else if (frame.getForeground() == this.highlight && position != this.loadingThumbnailPosition) {
                frame.setForeground(this.fragment.getContext().getResources().getDrawable(C0065R.drawable.image_view_wrapper_bg));
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        GridImageWrapper holder;
        CrDb.m0d("adapter", "getting view for position " + position);
        if (convertView == null || convertView.getTag() == null) {
            convertView = this.inflater.inflate(this.imageWrapperId, null);
            holder = new GridImageWrapper(convertView, isInSequence());
            convertView.setTag(holder);
        } else {
            holder = (GridImageWrapper) convertView.getTag();
        }
        GalleryImage image = (GalleryImage) this.images.get(position);
        highlightStartImage(position, convertView);
        if (holder.isFilledWith(image)) {
            CrDb.m0d("adapter", "already filled");
        } else {
            clearWrapper(holder);
            if (!image.isUnfilled()) {
                holder.set(image);
            }
        }
        CrDb.m0d("adapter", "loading imageView position:" + this.loadingThumbnailPosition + " wrapper: " + position);
        loadThumbnail(holder, position);
        boolean checked = holder.getImage() != null && holder.getImage().isChecked();
        ((ImagePressWrapper) convertView).setChecked(checked);
        return convertView;
    }

    protected void loadThumbnailWithPicasso(GalleryImage image, ImageView thumbnail, int position) {
        if (this.fragment.getContext() != null) {
            String imageUrl = image.getThumbnailUrl();
            if (imageUrl != null && !imageUrl.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                thumbnail.setMinimumWidth(GalleryListFragment.THUMBNAIL_HEIGHT);
                thumbnail.setMinimumHeight(GalleryListFragment.THUMBNAIL_HEIGHT);
                RequestCreator request = Picasso.with(this.fragment.getContext()).load(imageUrl);
                request.resize(GalleryGridFragment.THUMBNAIL_HEIGHT, GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
                request.error((int) C0065R.drawable.ic_action_error).into(thumbnail, this.progress.getCallback(image, thumbnail));
            }
        }
    }

    protected boolean isInSequence() {
        return false;
    }

    private void loadThumbnail(GridImageWrapper holder, int position) {
        GalleryImage image = holder.getImage();
        boolean doNotLoad = this.currentScrollState == 2 && getNumColumns() != 1;
        if (image != null && !doNotLoad && !holder.hasRendered(image) && this.fragment.getUserVisibleHint()) {
            if (!image.isVisited()) {
                this.progress.addLoadingImage(image);
                image.setVisited(true);
            }
            if (image.isAnimated() && getNumColumns() == 1) {
                holder.loadWithWebView(this.fragment.getContext());
            } else {
                loadThumbnailWithPicasso(image, holder.imageView, position);
            }
            holder.setRenderedPosition();
        } else if (holder.hasRendered(image)) {
            CrDb.m0d("adapter", "already rendered image at " + position);
        }
    }

    protected int getNumColumns() {
        return 1;
    }

    protected int getColumnWidth() {
        return GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        CrDb.m0d("adapter", " will try to pad once scroll state is idle: " + scrollState);
        this.padHandler.removeCallbacks(this.padOnScroll);
        if (scrollState == 0) {
            this.padHandler.postDelayed(this.padOnScroll, 2000);
            int firstVisiblePosition = this.list.getFirstVisiblePosition();
            int lastVisiblePosition = this.list.getLastVisiblePosition();
            this.producer.setCurrentImageFocus(firstVisiblePosition);
            if (firstVisiblePosition > getMaxUnfilled() && (this.fragment instanceof GalleryGridFragment)) {
                ((GalleryGridFragment) this.fragment).showSelectAllImagesHint();
            }
            if (this.currentScrollState == 2) {
                if (this.greatestViewPosition < lastVisiblePosition) {
                    this.greatestViewPosition = lastVisiblePosition;
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "scroll stop", lastVisiblePosition + UnsupportedUrlFragment.DISPLAY_NAME);
                }
                if (getNumColumns() != 1) {
                    CrDb.m0d("list adapter", "scroll state has changed, time to notify!");
                    notifyDataSetChanged();
                }
            }
        } else if (scrollState == 2 && this.fragment.getContext() != null && (this.fragment.getContext() instanceof Activity)) {
            InputMethodManager imm = (InputMethodManager) this.fragment.getContext().getSystemService("input_method");
            if (imm.isAcceptingText()) {
                View currentFocus = ((Activity) this.fragment.getContext()).getCurrentFocus();
                if (currentFocus != null) {
                    currentFocus.clearFocus();
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
        this.currentScrollState = scrollState;
    }

    private void refresh() {
    }

    private boolean nearEndOfFilledGrid(int firstVisiblePosition, int visibleItemCount) {
        return (firstVisiblePosition + visibleItemCount) + 1 >= this.images.size() - this.unfilledImages;
    }

    private boolean nearEndOfGrid(int firstVisiblePosition, int visibleItemCount) {
        return (firstVisiblePosition + visibleItemCount) + getSizeOfNextBatch() > this.images.size();
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (nearEndOfGrid(firstVisibleItem, visibleItemCount)) {
            padBottom();
        }
        if (nearEndOfFilledGrid(firstVisibleItem, visibleItemCount) && this.producer.isAvailable()) {
            CrDb.m0d("adapter", "near end of filled grid. fetching more images");
            fetchMoreImages();
        }
    }

    private void fetchMoreImages() {
        if (!this.noMoreLoading) {
            this.progress.signalFetching();
            this.producer.requestFetch();
        }
    }

    public void stopLoading() {
        this.noMoreLoading = true;
        this.producer.haltDownload();
        this.progress.stop();
        finishLoading();
    }

    public void showError(Exception e) {
        this.fragment.showError(DisplayError.GALLERY_NOT_LOADING, e.toString(), this.producer.getHostUrl());
    }

    public void finishLoading() {
        while (this.unfilledImages > 0) {
            this.images.remove(this.images.size() - 1);
            this.unfilledImages--;
        }
        this.progress.done();
        removeEmptyWrappers();
        notifyDataSetChanged();
        if (this.images.isEmpty() && this.fragment != null) {
            this.fragment.showError(DisplayError.EMPTY_GALLERY, "No images or galleries were found.", this.producer.getHostUrl());
        }
    }

    private void removeEmptyWrappers() {
        while (!this.images.isEmpty()) {
            int index = this.images.size() - 1;
            if (((GalleryImage) this.images.get(index)).isUnfilled()) {
                this.images.remove(index);
            } else {
                return;
            }
        }
    }

    public int prepareHighlight(int position) {
        this.highlighted = false;
        this.loadingThumbnailPosition = position;
        this.resetThumbnailPosition = false;
        return this.loadingThumbnailPosition;
    }

    protected AdapterView getList() {
        return this.list;
    }

    public void pause() {
        if (this.list instanceof AbsListView) {
            ((AbsListView) this.list).smoothScrollBy(0, 0);
        }
        this.currentScrollState = 2;
    }

    public void unpause() {
        this.currentScrollState = 0;
    }

    public void cancelPicassoLoad() {
        if (this.fragment.getContext() != null) {
            for (int size = this.list.getChildCount() - 1; size >= 0; size--) {
                Object tag = this.list.getChildAt(size).getTag();
                if (tag != null) {
                    Picasso.with(this.fragment.getContext()).cancelRequest(((GridImageWrapper) tag).getImageView());
                }
            }
        }
    }

    public void onScrollStateChanged(TwoWayView view, int scrollState) {
        onScrollStateChanged((AbsListView) null, scrollState);
    }

    public void onScroll(TwoWayView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        onScroll((AbsListView) null, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    public void startFetchingAndThenFinish() {
        startFetching();
        this.noMoreLoading = true;
    }

    public void destroy() {
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        cancelPicassoLoad();
        for (int size = this.list.getChildCount() - 1; size >= 0; size--) {
            Object tag = this.list.getChildAt(size).getTag();
            if (tag != null) {
                ((GridImageWrapper) tag).destroy();
            }
        }
        if (this.images != null) {
            this.images.clear();
        }
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }
}
