package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import twowayview.TwoWayView;
import twowayview.TwoWayView.OnScrollListener;

public class GalleryListAdapter
  extends BaseAdapter
  implements AbsListView.OnScrollListener, GalleryConsumer, TwoWayView.OnScrollListener
{
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
  
  private void addUnfilledImage()
  {
    this.images.add(new GalleryImage(true));
    this.unfilledImages += 1;
  }
  
  private void clearWrapper(GridImageWrapper paramGridImageWrapper)
  {
    this.progress.removeLoadingImage(paramGridImageWrapper.getImage());
    if (this.fragment.getContext() == null) {
      return;
    }
    Picasso.with(this.fragment.getContext()).cancelRequest(paramGridImageWrapper.imageView);
    paramGridImageWrapper.clear();
  }
  
  private void fetchMoreImages()
  {
    if (this.noMoreLoading) {
      return;
    }
    this.progress.signalFetching();
    this.producer.requestFetch();
  }
  
  private int getMaxUnfilled()
  {
    return getSizeOfNextBatch();
  }
  
  private int getSizeOfNextBatchFromDimensions(int paramInt1, int paramInt2)
  {
    int i = this.list.getMeasuredWidth();
    paramInt1 = i * this.list.getMeasuredHeight() / (paramInt2 * paramInt1);
    if (i == 0) {
      this.cachedThumbnailWidth = -1;
    }
    return Math.max(2, Math.min(paramInt1 + 1, 20));
  }
  
  private void highlightStartImage(int paramInt, View paramView)
  {
    paramView = (FrameLayout)paramView;
    if (this.fragment.getContext() == null) {}
    do
    {
      return;
      if ((paramInt == this.loadingThumbnailPosition) && (!this.resetThumbnailPosition) && (!this.highlighted))
      {
        this.highlighted = true;
        this.highlight = ((TransitionDrawable)this.fragment.getContext().getResources().getDrawable(2130837691));
        paramView.setForeground(this.highlight);
        this.highlight.resetTransition();
        this.highlight.setCrossFadeEnabled(true);
        this.highlight.startTransition(2000);
        return;
      }
    } while ((paramView.getForeground() != this.highlight) || (paramInt == this.loadingThumbnailPosition));
    paramView.setForeground(this.fragment.getContext().getResources().getDrawable(2130837656));
  }
  
  private void loadThumbnail(GridImageWrapper paramGridImageWrapper, int paramInt)
  {
    GalleryImage localGalleryImage = paramGridImageWrapper.getImage();
    if ((this.currentScrollState == 2) && (getNumColumns() != 1)) {}
    for (int i = 1; (localGalleryImage == null) || (i != 0) || (paramGridImageWrapper.hasRendered(localGalleryImage)) || (!this.fragment.getUserVisibleHint()); i = 0)
    {
      if (paramGridImageWrapper.hasRendered(localGalleryImage)) {
        CrDb.d("adapter", "already rendered image at " + paramInt);
      }
      return;
    }
    if (!localGalleryImage.isVisited())
    {
      this.progress.addLoadingImage(localGalleryImage);
      localGalleryImage.setVisited(true);
    }
    if ((localGalleryImage.isAnimated()) && (getNumColumns() == 1)) {
      paramGridImageWrapper.loadWithWebView(this.fragment.getContext());
    }
    for (;;)
    {
      paramGridImageWrapper.setRenderedPosition();
      return;
      loadThumbnailWithPicasso(localGalleryImage, paramGridImageWrapper.imageView, paramInt);
    }
  }
  
  private boolean nearEndOfFilledGrid(int paramInt1, int paramInt2)
  {
    return paramInt1 + paramInt2 + 1 >= this.images.size() - this.unfilledImages;
  }
  
  private boolean nearEndOfGrid(int paramInt1, int paramInt2)
  {
    return paramInt1 + paramInt2 + getSizeOfNextBatch() > this.images.size();
  }
  
  private void padBottom(boolean paramBoolean)
  {
    if (!this.producer.isAvailable()) {
      CrDb.d("adapter", "padBottom: Gallery Producer not available. Can't pad bottom");
    }
    int i;
    do
    {
      return;
      i = getMaxUnfilled() - this.unfilledImages;
      CrDb.d("adapter", "notify/padBottom: " + i + " unfilled images added");
      int j = getMaxUnfilled();
      while (this.unfilledImages < j) {
        addUnfilledImage();
      }
    } while ((!paramBoolean) || (i == 0));
    notifyDataSetChanged();
  }
  
  private void refresh() {}
  
  private void removeEmptyWrappers()
  {
    while (!this.images.isEmpty())
    {
      int i = this.images.size() - 1;
      if (!((GalleryImage)this.images.get(i)).isUnfilled()) {
        break;
      }
      this.images.remove(i);
    }
  }
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    boolean bool = false;
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      GalleryImage localGalleryImage = (GalleryImage)paramList.next();
      if (this.unfilledImages == 0) {
        addUnfilledImage();
      }
      ArrayList localArrayList = this.images;
      i = this.images.size();
      int j = this.unfilledImages;
      this.unfilledImages = (j - 1);
      localArrayList.set(i - j, localGalleryImage);
    }
    int i = this.list.getFirstVisiblePosition();
    if (nearEndOfFilledGrid(i, this.list.getLastVisiblePosition() - i))
    {
      CrDb.d("adapter", "Need to download more images!");
      padBottom(false);
      bool = true;
    }
    if ((this.fragment instanceof GalleryGridFragment)) {
      ((GalleryGridFragment)this.fragment).showGrowGridTutorial();
    }
    CrDb.d("adapter", "Total images size: " + this.images.size());
    CrDb.d("adapter", "Actual images size: " + (this.images.size() - this.unfilledImages));
    return bool;
  }
  
  public void cancelPicassoLoad()
  {
    if (this.fragment.getContext() == null) {}
    for (;;)
    {
      return;
      int i = this.list.getChildCount() - 1;
      while (i >= 0)
      {
        Object localObject = this.list.getChildAt(i).getTag();
        if (localObject != null) {
          Picasso.with(this.fragment.getContext()).cancelRequest(((GridImageWrapper)localObject).getImageView());
        }
        i -= 1;
      }
    }
  }
  
  public void destroy()
  {
    if (this.producer != null) {
      this.producer.removeConsumer(this);
    }
    cancelPicassoLoad();
    int i = this.list.getChildCount() - 1;
    while (i >= 0)
    {
      Object localObject = this.list.getChildAt(i).getTag();
      if (localObject != null) {
        ((GridImageWrapper)localObject).destroy();
      }
      i -= 1;
    }
    if (this.images != null) {
      this.images.clear();
    }
    notifyDataSetChanged();
    notifyDataSetInvalidated();
  }
  
  public void finishLoading()
  {
    while (this.unfilledImages > 0)
    {
      this.images.remove(this.images.size() - 1);
      this.unfilledImages -= 1;
    }
    this.progress.done();
    removeEmptyWrappers();
    notifyDataSetChanged();
    if ((this.images.isEmpty()) && (this.fragment != null)) {
      this.fragment.showError(DisplayError.EMPTY_GALLERY, "No images or galleries were found.", this.producer.getHostUrl());
    }
  }
  
  protected int getColumnWidth()
  {
    return GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH;
  }
  
  public int getCount()
  {
    return this.images.size();
  }
  
  protected GalleryList getGalleryList()
  {
    return this.fragment;
  }
  
  public Object getItem(int paramInt)
  {
    return this.images.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  protected AdapterView getList()
  {
    return this.list;
  }
  
  protected int getNumColumns()
  {
    return 1;
  }
  
  public int getSizeOfNextBatch()
  {
    if (this.cachedThumbnailWidth != getColumnWidth())
    {
      this.cachedThumbnailWidth = getColumnWidth();
      this.cachedSizeOfNextBatch = getSizeOfNextBatchFromDimensions(getColumnWidth(), getColumnWidth());
    }
    return this.cachedSizeOfNextBatch;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    CrDb.d("adapter", "getting view for position " + paramInt);
    if ((paramView == null) || (paramView.getTag() == null))
    {
      paramView = this.inflater.inflate(this.imageWrapperId, null);
      paramViewGroup = new GridImageWrapper(paramView, isInSequence());
      paramView.setTag(paramViewGroup);
      GalleryImage localGalleryImage = (GalleryImage)this.images.get(paramInt);
      highlightStartImage(paramInt, paramView);
      if (paramViewGroup.isFilledWith(localGalleryImage)) {
        break label200;
      }
      clearWrapper(paramViewGroup);
      if (!localGalleryImage.isUnfilled()) {
        paramViewGroup.set(localGalleryImage);
      }
      label114:
      CrDb.d("adapter", "loading imageView position:" + this.loadingThumbnailPosition + " wrapper: " + paramInt);
      loadThumbnail(paramViewGroup, paramInt);
      if ((paramViewGroup.getImage() == null) || (!paramViewGroup.getImage().isChecked())) {
        break label211;
      }
    }
    label200:
    label211:
    for (boolean bool = true;; bool = false)
    {
      ((ImagePressWrapper)paramView).setChecked(bool);
      return paramView;
      paramViewGroup = (GridImageWrapper)paramView.getTag();
      break;
      CrDb.d("adapter", "already filled");
      break label114;
    }
  }
  
  public void initialize(GalleryList paramGalleryList)
  {
    this.loadingThumbnailPosition = -1;
    this.fragment = paramGalleryList;
    this.list = paramGalleryList.getList();
    this.images = new ArrayList();
    this.producer = paramGalleryList.getProducer();
    this.producer.initialize(this, paramGalleryList.getImage(), paramGalleryList.getArguments());
    this.inflater = LayoutInflater.from(paramGalleryList.getContext());
    this.imageWrapperId = 2130903096;
    this.progress = new ListFragmentProgress(paramGalleryList);
    this.padHandler = new Handler();
    this.padOnScroll = new Runnable()
    {
      public void run()
      {
        GalleryListAdapter.this.padBottom();
      }
    };
  }
  
  protected boolean isInSequence()
  {
    return false;
  }
  
  protected void loadThumbnailWithPicasso(GalleryImage paramGalleryImage, ImageView paramImageView, int paramInt)
  {
    if (this.fragment.getContext() == null) {}
    do
    {
      return;
      localObject = paramGalleryImage.getThumbnailUrl();
    } while ((localObject == null) || (((String)localObject).equals("")));
    paramImageView.setMinimumWidth(GalleryListFragment.THUMBNAIL_HEIGHT);
    paramImageView.setMinimumHeight(GalleryListFragment.THUMBNAIL_HEIGHT);
    Object localObject = Picasso.with(this.fragment.getContext()).load((String)localObject);
    ((RequestCreator)localObject).resize(GalleryGridFragment.THUMBNAIL_HEIGHT, GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
    ((RequestCreator)localObject).error(2130837617).into(paramImageView, this.progress.getCallback(paramGalleryImage, paramImageView));
  }
  
  public void notifyDataSetChanged()
  {
    super.notifyDataSetChanged();
    if ((GalleryViewer.IsInTest()) && (this.images != null) && (!this.images.isEmpty())) {}
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (nearEndOfGrid(paramInt1, paramInt2)) {
      padBottom();
    }
    if ((nearEndOfFilledGrid(paramInt1, paramInt2)) && (this.producer.isAvailable()))
    {
      CrDb.d("adapter", "near end of filled grid. fetching more images");
      fetchMoreImages();
    }
  }
  
  public void onScroll(TwoWayView paramTwoWayView, int paramInt1, int paramInt2, int paramInt3)
  {
    onScroll((AbsListView)null, paramInt1, paramInt2, paramInt3);
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    CrDb.d("adapter", " will try to pad once scroll state is idle: " + paramInt);
    this.padHandler.removeCallbacks(this.padOnScroll);
    if (paramInt == 0)
    {
      this.padHandler.postDelayed(this.padOnScroll, 2000L);
      int i = this.list.getFirstVisiblePosition();
      int j = this.list.getLastVisiblePosition();
      this.producer.setCurrentImageFocus(i);
      if ((i > getMaxUnfilled()) && ((this.fragment instanceof GalleryGridFragment))) {
        ((GalleryGridFragment)this.fragment).showSelectAllImagesHint();
      }
      if (this.currentScrollState == 2)
      {
        if (this.greatestViewPosition < j)
        {
          this.greatestViewPosition = j;
          Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "scroll stop", j + "");
        }
        if (getNumColumns() != 1)
        {
          CrDb.d("list adapter", "scroll state has changed, time to notify!");
          notifyDataSetChanged();
        }
      }
    }
    for (;;)
    {
      this.currentScrollState = paramInt;
      return;
      if ((paramInt == 2) && (this.fragment.getContext() != null) && ((this.fragment.getContext() instanceof Activity)))
      {
        paramAbsListView = (InputMethodManager)this.fragment.getContext().getSystemService("input_method");
        if (paramAbsListView.isAcceptingText())
        {
          View localView = ((Activity)this.fragment.getContext()).getCurrentFocus();
          if (localView != null)
          {
            localView.clearFocus();
            paramAbsListView.hideSoftInputFromWindow(localView.getWindowToken(), 0);
          }
        }
      }
    }
  }
  
  public void onScrollStateChanged(TwoWayView paramTwoWayView, int paramInt)
  {
    onScrollStateChanged((AbsListView)null, paramInt);
  }
  
  protected void padBottom()
  {
    padBottom(true);
  }
  
  public void pause()
  {
    if ((this.list instanceof AbsListView)) {
      ((AbsListView)this.list).smoothScrollBy(0, 0);
    }
    this.currentScrollState = 2;
  }
  
  public int prepareHighlight(int paramInt)
  {
    this.highlighted = false;
    this.loadingThumbnailPosition = paramInt;
    this.resetThumbnailPosition = false;
    return this.loadingThumbnailPosition;
  }
  
  public void showError(Exception paramException)
  {
    this.fragment.showError(DisplayError.GALLERY_NOT_LOADING, paramException.toString(), this.producer.getHostUrl());
  }
  
  public void startFetching()
  {
    if (this.producer.requestStartFetch())
    {
      this.progress.signalFetching();
      padBottom();
    }
  }
  
  public void startFetchingAndThenFinish()
  {
    startFetching();
    this.noMoreLoading = true;
  }
  
  public void stopLoading()
  {
    this.noMoreLoading = true;
    this.producer.haltDownload();
    this.progress.stop();
    finishLoading();
  }
  
  public void unpause()
  {
    this.currentScrollState = 0;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/GalleryListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */