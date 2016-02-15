package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;

public class SilentUrlRedirectEvent
{
  public final GalleryImage keyImage;
  public final String silentRedirectUrl;
  
  public SilentUrlRedirectEvent(String paramString, GalleryImage paramGalleryImage)
  {
    this.silentRedirectUrl = paramString;
    this.keyImage = paramGalleryImage;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/events/SilentUrlRedirectEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */