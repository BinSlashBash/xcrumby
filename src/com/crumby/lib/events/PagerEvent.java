package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class PagerEvent
{
  public GalleryImage image;
  public GalleryProducer producer;
  
  public PagerEvent(GalleryImage paramGalleryImage, GalleryProducer paramGalleryProducer)
  {
    this.image = paramGalleryImage;
    this.producer = paramGalleryProducer;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/events/PagerEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */