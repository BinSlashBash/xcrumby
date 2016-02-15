package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.util.List;

public abstract interface GalleryConsumer
{
  public abstract boolean addImages(List<GalleryImage> paramList);
  
  public abstract void finishLoading();
  
  public abstract void notifyDataSetChanged();
  
  public abstract void showError(Exception paramException);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/producer/GalleryConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */