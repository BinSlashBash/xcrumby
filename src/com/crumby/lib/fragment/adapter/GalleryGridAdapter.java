package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.widget.firstparty.grow.GrowGridView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class GalleryGridAdapter
  extends GalleryListAdapter
{
  private int currentScrollState;
  int lastVisibleItem;
  
  protected int getColumnWidth()
  {
    return getList().getColumnWidth();
  }
  
  protected GrowGridView getList()
  {
    return (GrowGridView)super.getList();
  }
  
  protected int getNumColumns()
  {
    if (getList().getNumColumns() != -1) {
      return getList().getNumColumns();
    }
    return getList().getCurrentColumns();
  }
  
  public void initialize(GalleryList paramGalleryList)
  {
    super.initialize(paramGalleryList);
    this.imageWrapperId = 2130903082;
    this.padOnScroll = new Runnable()
    {
      public void run()
      {
        GalleryGridAdapter.this.padBottom();
      }
    };
  }
  
  protected boolean isInSequence()
  {
    return getList().getNumColumns() == 1;
  }
  
  protected void loadThumbnailWithPicasso(GalleryImage paramGalleryImage, ImageView paramImageView, int paramInt)
  {
    Object localObject1;
    if (isInSequence()) {
      localObject1 = paramGalleryImage.getImageUrl();
    }
    for (;;)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = paramGalleryImage.getThumbnailUrl();
      }
      if ((localObject2 != null) && (!((String)localObject2).equals(""))) {
        break;
      }
      return;
      if (GalleryGridFragment.THUMBNAIL_HEIGHT * getColumnWidth() > 60000) {
        localObject1 = paramGalleryImage.getThumbnailUrl();
      } else {
        localObject1 = paramGalleryImage.getSmallThumbnailUrl();
      }
    }
    Object localObject2 = Picasso.with(paramImageView.getContext()).load((String)localObject2);
    if (!paramGalleryImage.isSplit()) {
      if (!isInSequence())
      {
        ((RequestCreator)localObject2).resize(getColumnWidth(), GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
        localObject1 = localObject2;
      }
    }
    for (;;)
    {
      ((RequestCreator)localObject1).error(2130837617).into(paramImageView, this.progress.getCallback(paramGalleryImage, paramImageView));
      return;
      int j = paramGalleryImage.getWidth();
      localObject1 = localObject2;
      if (j != 0)
      {
        int n = paramGalleryImage.getHeight();
        localObject1 = localObject2;
        if (n != 0)
        {
          int k = paramImageView.getResources().getDisplayMetrics().widthPixels;
          int m = paramImageView.getResources().getDisplayMetrics().heightPixels;
          paramInt = k;
          int i = paramGalleryImage.getHeight();
          if (j > k)
          {
            double d = j / n;
            i = (int)(k * d);
          }
          for (;;)
          {
            i = Math.min(i, m * 2);
            localObject1 = localObject2;
            if (i <= 0) {
              break;
            }
            ((RequestCreator)localObject2).resize(paramInt, i).centerInside();
            localObject1 = localObject2;
            break;
            paramInt = j;
          }
          localObject1 = ((RequestCreator)localObject2).transform(new CropTransformation(paramGalleryImage, paramInt));
        }
      }
    }
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    Context localContext = getGalleryList().getContext();
    int i = paramAbsListView.getFirstVisiblePosition();
    this.currentScrollState = paramInt;
    if ((localContext != null) && ((localContext instanceof GalleryViewer)))
    {
      if ((i != 0) && (this.lastVisibleItem <= i)) {
        break label66;
      }
      ((GalleryViewer)localContext).showOmnibar();
    }
    for (;;)
    {
      this.lastVisibleItem = i;
      super.onScrollStateChanged(paramAbsListView, paramInt);
      return;
      label66:
      if (this.currentScrollState == 2) {
        ((GalleryViewer)localContext).hideOmnibar();
      }
    }
  }
  
  public int prepareHighlight(int paramInt)
  {
    return super.prepareHighlight(paramInt);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/GalleryGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */