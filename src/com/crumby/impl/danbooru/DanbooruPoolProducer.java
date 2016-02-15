package com.crumby.impl.danbooru;

import android.net.Uri;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DanbooruPoolProducer
  extends GalleryParser
{
  public static final String API_URL = "http://danbooru.donmai.us/pools.json?";
  
  static GalleryImage getGalleryImage(JsonObject paramJsonObject)
  {
    GalleryImage localGalleryImage = new GalleryImage(null, "http://danbooru.donmai.us/pools/" + paramJsonObject.get("id").getAsString(), paramJsonObject.get("name").getAsString().replace("_", " "));
    paramJsonObject = paramJsonObject.get("description").getAsString();
    localGalleryImage.setThumbnailUrl(CrumbyProducer.getSnapshot(localGalleryImage.getLinkUrl()));
    localGalleryImage.setDescription(paramJsonObject);
    localGalleryImage.setLinksToGallery(true);
    localGalleryImage.setIcon(0);
    return localGalleryImage;
  }
  
  private void handleDeferredThumbnail(GalleryImage paramGalleryImage, String paramString) {}
  
  public URL convertUrlToApi(String paramString)
  {
    Uri localUri = Uri.parse(paramString.toString());
    String str = "http://danbooru.donmai.us/pools.json?";
    if (getQueryParameter(localUri, paramString, "search[name_matches]") != null) {
      str = "http://danbooru.donmai.us/pools.json?" + "&search[name_matches]=" + getQueryParameter(localUri, paramString, "search[name_matches]");
    }
    try
    {
      paramString = new URL(str);
      return paramString;
    }
    catch (MalformedURLException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = fetchUrl(this.apiUrl.toString() + getSearchMark() + "&page=" + paramInt);
    localObject = new JsonParser().parse((String)localObject).getAsJsonArray();
    int i = 0;
    while (i < ((JsonArray)localObject).size())
    {
      GalleryImage localGalleryImage = getGalleryImage(((JsonArray)localObject).get(i).getAsJsonObject());
      localGalleryImage.setPage(paramInt);
      localArrayList.add(localGalleryImage);
      i += 1;
    }
    return localArrayList;
  }
  
  public void postInitialize()
  {
    setStartPage(1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruPoolProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */