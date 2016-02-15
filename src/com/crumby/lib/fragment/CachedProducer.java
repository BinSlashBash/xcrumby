package com.crumby.lib.fragment;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class CachedProducer
  extends SingleGalleryProducer
{
  private boolean cached;
  private ArrayList<GalleryImage> images;
  
  public CachedProducer(String paramString)
  {
    paramString = new GalleryImage(null, paramString, null);
    paramString.setLinksToGallery(true);
    paramString.setReload(true);
    this.images = new ArrayList();
    this.images.add(paramString);
  }
  
  public CachedProducer(ArrayList<GalleryImage> paramArrayList)
  {
    this.images = paramArrayList;
    this.cached = true;
  }
  
  protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
  {
    paramArrayList.size();
  }
  
  public boolean initialize()
  {
    boolean bool = false;
    if (super.initialize())
    {
      addProducedImagesToCache(this.images);
      shareAndSetCurrentImageFocus(0);
      bool = true;
    }
    return bool;
  }
  
  protected void postInitialize()
  {
    if (this.cached)
    {
      addImagesToConsumers(this.images);
      notifyHandlerDataSetsChanged();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/CachedProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */