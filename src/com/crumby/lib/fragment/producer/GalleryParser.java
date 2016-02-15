package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.net.URL;

public abstract class GalleryParser
  extends GalleryProducer
{
  protected URL apiUrl;
  
  public abstract URL convertUrlToApi(String paramString);
  
  protected String getSearchMark()
  {
    if (this.apiUrl.toString().indexOf("?") == -1) {
      return "?";
    }
    return "";
  }
  
  public boolean requestStartFetch()
  {
    if (this.apiUrl == null) {
      this.apiUrl = convertUrlToApi(getHostImage().getLinkUrl());
    }
    return super.requestStartFetch();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/producer/GalleryParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */