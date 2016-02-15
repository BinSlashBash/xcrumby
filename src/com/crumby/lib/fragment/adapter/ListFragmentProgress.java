package com.crumby.lib.fragment.adapter;

import android.content.res.Resources;
import android.widget.ImageView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Callback;
import java.util.HashSet;
import java.util.Set;

class ListFragmentProgress
{
  private static final int FETCHING = 10;
  private static final int NOT_FETCHING = 0;
  private boolean done = false;
  private GalleryList fragment;
  private int lastProgress;
  private Set<GalleryImage> loadingImages;
  private int maxProgress;
  private boolean stopped = false;
  
  public ListFragmentProgress(GalleryList paramGalleryList)
  {
    this.fragment = paramGalleryList;
    this.loadingImages = new HashSet();
  }
  
  private void postProgress(int paramInt)
  {
    if (this.stopped) {}
    int i;
    do
    {
      return;
      if (this.done) {
        paramInt = 0;
      }
      i = this.loadingImages.size() + paramInt;
    } while (i == this.lastProgress);
    CrDb.d("adapter", "Loading images:" + this.loadingImages.size() + " Fetching:" + paramInt + "  Posting progress:" + i);
    signal(i);
    this.lastProgress = i;
  }
  
  private void signal(int paramInt)
  {
    if (this.fragment == null)
    {
      this.fragment = null;
      this.done = true;
      return;
    }
    this.maxProgress = Math.max(paramInt, this.maxProgress);
    if (this.maxProgress == 0) {
      this.maxProgress = 1;
    }
    float f2 = 1.0F - paramInt / this.maxProgress;
    float f1 = f2;
    if (f2 == 0.0F) {
      f1 = 0.05F;
    }
    this.fragment.indicateProgressChange(f1);
  }
  
  public void addLoadingImage(GalleryImage paramGalleryImage)
  {
    this.loadingImages.add(paramGalleryImage);
  }
  
  public void done()
  {
    signal(0);
    this.done = true;
  }
  
  public LoadingImageCallback getCallback(GalleryImage paramGalleryImage, ImageView paramImageView)
  {
    return new LoadingImageCallback(paramGalleryImage, paramImageView);
  }
  
  public void removeLoadingImage(GalleryImage paramGalleryImage)
  {
    if (this.loadingImages.remove(paramGalleryImage)) {
      postProgress(0);
    }
  }
  
  public void resume()
  {
    this.stopped = false;
  }
  
  public void signalFetching()
  {
    postProgress(10);
  }
  
  public void stop()
  {
    this.stopped = true;
    signal(0);
  }
  
  private class LoadingImageCallback
    implements Callback
  {
    GalleryImage image;
    ImageView thumbnail;
    
    public LoadingImageCallback(GalleryImage paramGalleryImage, ImageView paramImageView)
    {
      this.image = paramGalleryImage;
      this.thumbnail = paramImageView;
    }
    
    public void onError(Exception paramException)
    {
      ListFragmentProgress.this.removeLoadingImage(this.image);
      this.image = null;
      if (this.thumbnail.getResources() != null) {
        this.thumbnail.setBackgroundColor(this.thumbnail.getResources().getColor(2131427534));
      }
      this.thumbnail = null;
    }
    
    public void onSuccess()
    {
      ListFragmentProgress.this.removeLoadingImage(this.image);
      this.thumbnail = null;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/ListFragmentProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */