package com.crumby.lib.fragment.adapter;

import com.crumby.lib.GalleryImage;

public class ImageWrapper
{
  GalleryImage image;
  
  public ImageWrapper() {}
  
  public ImageWrapper(GalleryImage paramGalleryImage)
  {
    this.image = paramGalleryImage;
  }
  
  public GalleryImage getImage()
  {
    return this.image;
  }
  
  protected void setImage(GalleryImage paramGalleryImage)
  {
    this.image = paramGalleryImage;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/ImageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */