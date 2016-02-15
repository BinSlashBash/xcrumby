package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.util.ArrayList;

public abstract class SingleGalleryProducer
  extends GalleryProducer
{
  private boolean fetched;
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if (!this.fetched) {
      fetchGalleryImagesOnce(localArrayList);
    }
    this.fetched = true;
    return localArrayList;
  }
  
  protected abstract void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
    throws Exception;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/producer/SingleGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */