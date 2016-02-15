package com.crumby.lib.events;

import android.os.Bundle;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class UrlChangeEvent
{
  public Bundle bundle;
  public boolean clearPrevious;
  public int position;
  public GalleryProducer producer;
  public boolean silent;
  public String url;
  
  public UrlChangeEvent(String paramString)
  {
    this.url = paramString;
  }
  
  public UrlChangeEvent(String paramString, Bundle paramBundle)
  {
    this.url = paramString;
    this.bundle = paramBundle;
  }
  
  public UrlChangeEvent(String paramString, GalleryProducer paramGalleryProducer)
  {
    this.url = paramString;
    this.producer = paramGalleryProducer;
  }
  
  public UrlChangeEvent(String paramString, boolean paramBoolean)
  {
    this.url = paramString;
    this.clearPrevious = paramBoolean;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/events/UrlChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */