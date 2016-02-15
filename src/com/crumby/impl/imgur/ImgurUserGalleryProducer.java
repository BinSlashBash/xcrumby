package com.crumby.impl.imgur;

import com.crumby.lib.GalleryImage;
import com.google.gson.JsonObject;

public class ImgurUserGalleryProducer
  extends ImgurProducer
{
  private String userId;
  
  protected String getApiUrl(int paramInt)
  {
    return "https://imgur-apiv3.p.mashape.com/3/account/" + this.userId + "/albums/" + paramInt;
  }
  
  protected String getImgurLink(boolean paramBoolean, String paramString1, String paramString2)
  {
    return paramString2;
  }
  
  protected GalleryImage parseImgurImage(JsonObject paramJsonObject, boolean paramBoolean)
  {
    return super.parseImgurImage(paramJsonObject, true);
  }
  
  public void postInitialize()
  {
    this.userId = ImgurUserGalleryFragment.matchIdFromUrl(getHostUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurUserGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */