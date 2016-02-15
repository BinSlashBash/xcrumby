package com.crumby.impl.imgur;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class ImgurAlbumProducer
  extends ImgurProducer
{
  public static final String API_ALBUM_URL = "https://imgur-apiv3.p.mashape.com/3/album/";
  String albumId;
  JsonArray imgurImages;
  
  private boolean initImgurImages(int paramInt)
    throws Exception
  {
    if (this.imgurImages == null)
    {
      Object localObject1 = fetchImgurUrl(paramInt);
      localObject1 = JSON_PARSER.parse((String)localObject1).getAsJsonObject();
      Object localObject2 = ((JsonObject)localObject1).get("data").getAsJsonObject();
      String str = JNH.getAttributeString((JsonObject)localObject2, "title");
      localObject2 = JNH.getAttributeString((JsonObject)localObject2, "description");
      if ((getHostImage().getTitle() == null) || (getHostImage().getTitle().equals(""))) {
        setGalleryMetadata(str, (String)localObject2);
      }
      this.imgurImages = getImagesFromJsonObject((JsonObject)localObject1);
      return true;
    }
    return false;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    initImgurImages(paramInt);
    return parseImgurImages(sliceImages(this.imgurImages, paramInt));
  }
  
  protected String getApiUrl(int paramInt)
  {
    return "https://imgur-apiv3.p.mashape.com/3/album/" + this.albumId;
  }
  
  protected JsonArray getImagesFromJsonObject(JsonObject paramJsonObject)
  {
    return paramJsonObject.get("data").getAsJsonObject().get("images").getAsJsonArray();
  }
  
  public void postInitialize()
  {
    this.albumId = ImgurAlbumFragment.matchIdFromUrl(getHostUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurAlbumProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */