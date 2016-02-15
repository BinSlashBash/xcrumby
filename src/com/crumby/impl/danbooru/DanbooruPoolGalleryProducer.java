package com.crumby.impl.danbooru;

import com.crumby.lib.GalleryImage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DanbooruPoolGalleryProducer
  extends DanbooruGalleryProducer
{
  private String poolId;
  
  public URL convertUrlToApi(String paramString)
  {
    this.poolId = DanbooruPoolGalleryFragment.matchIdFromUrl(paramString.toString());
    try
    {
      paramString = new URL("http://danbooru.donmai.us/posts.json?" + "tags=pool:" + this.poolId);
      return paramString;
    }
    catch (MalformedURLException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  protected boolean fetchMetadata()
    throws IOException
  {
    if ((getHostImage().getTitle() == null) && (getHostImage().getDescription() == null))
    {
      GalleryImage localGalleryImage = DanbooruPoolProducer.getGalleryImage(JSON_PARSER.parse(fetchUrl("http://danbooru.donmai.us/pools/" + this.poolId + ".json")).getAsJsonObject());
      getHostImage().copy(localGalleryImage);
      return true;
    }
    return false;
  }
  
  protected GalleryImage getGalleryImage(JsonObject paramJsonObject)
  {
    GalleryImage localGalleryImage = super.getGalleryImage(paramJsonObject);
    localGalleryImage.setLinkUrl(deriveUrl(paramJsonObject.get("id"), "/posts/") + "?pool_id=" + this.poolId);
    localGalleryImage.setImageUrl(deriveUrl(paramJsonObject.get("large_file_url"), ""));
    return localGalleryImage;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruPoolGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */