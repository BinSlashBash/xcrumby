package com.crumby.impl.imgur;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ImgurProducer
  extends GalleryProducer
{
  public static final String API_CREDENTIALS = "Client-ID ac562464e4b98f8";
  public static final String API_URL = "https://imgur-apiv3.p.mashape.com/3/";
  public static final String MAIN_GALLERY = "gallery/hot/viral/";
  public static final String MASHAPE_AUTH = "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP";
  public static final String THUMBNAIL_HOST = "http://i.imgur.com/";
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    JsonArray localJsonArray = getImagesFromJsonElement(JSON_PARSER.parse(fetchImgurUrl(paramInt)));
    if ((localJsonArray == null) || (localJsonArray.isJsonNull())) {
      throw new IOException("Imgur API not available to parse");
    }
    return parseImgurImages(localJsonArray);
  }
  
  protected String fetchImgurUrl(int paramInt)
    throws Exception
  {
    return fetchUrl(getApiUrl(paramInt), "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
  }
  
  protected String getApiUrl(int paramInt)
  {
    String str = getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "q");
    if ((str != null) && (!str.equals(""))) {
      return "https://imgur-apiv3.p.mashape.com/3/gallery/search/" + paramInt + "?q=" + Uri.encode(str);
    }
    return "https://imgur-apiv3.p.mashape.com/3/gallery/hot/viral/" + paramInt + ".json";
  }
  
  protected final JsonArray getImagesFromJsonElement(JsonElement paramJsonElement)
  {
    if ((paramJsonElement == null) || (paramJsonElement.isJsonNull())) {
      return null;
    }
    return getImagesFromJsonObject(paramJsonElement.getAsJsonObject());
  }
  
  protected JsonArray getImagesFromJsonObject(JsonObject paramJsonObject)
  {
    return paramJsonObject.get("data").getAsJsonArray();
  }
  
  protected String getImgurLink(boolean paramBoolean, String paramString1, String paramString2)
  {
    if (paramBoolean) {
      return paramString2;
    }
    return "http://imgur.com/gallery/" + paramString1;
  }
  
  protected GalleryImage parseImgurImage(JsonElement paramJsonElement)
  {
    paramJsonElement = paramJsonElement.getAsJsonObject();
    if (paramJsonElement.get("is_album") != null) {}
    for (boolean bool = paramJsonElement.get("is_album").getAsBoolean();; bool = false) {
      return parseImgurImage(paramJsonElement, bool);
    }
  }
  
  protected GalleryImage parseImgurImage(JsonObject paramJsonObject, boolean paramBoolean)
  {
    String str2 = JNH.getAttributeString(paramJsonObject, "title");
    String str3 = paramJsonObject.get("link").getAsString();
    int m = 0;
    int n = 0;
    String str1 = paramJsonObject.get("id").getAsString();
    Object localObject1 = "";
    int i1 = 0;
    int j = m;
    int i = n;
    int k = i1;
    if (paramBoolean)
    {
      localObject2 = JNH.getAttributeString(paramJsonObject, "cover");
      j = m;
      i = n;
      k = i1;
      localObject1 = localObject2;
      if (localObject2 != null)
      {
        j = JNH.getAttributeInt(paramJsonObject, "cover_width");
        i = JNH.getAttributeInt(paramJsonObject, "cover_height");
        k = 1;
        localObject1 = localObject2;
      }
    }
    if (k == 0)
    {
      localObject1 = str1;
      j = JNH.getAttributeInt(paramJsonObject, "width");
      i = JNH.getAttributeInt(paramJsonObject, "height");
    }
    Object localObject2 = new GalleryImage("http://i.imgur.com/" + (String)localObject1 + "m.jpg", getImgurLink(paramBoolean, str1, str3), str2, j, i);
    ((GalleryImage)localObject2).setLinksToGallery(paramBoolean);
    ((GalleryImage)localObject2).setSmallThumbnailUrl("http://i.imgur.com/" + (String)localObject1 + "t.jpg");
    str1 = "jpg";
    str2 = JNH.getAttributeString(paramJsonObject, "type");
    paramJsonObject = str1;
    if (str2 != null)
    {
      paramJsonObject = str1;
      if (str2.equals("image/gif")) {
        paramJsonObject = "gif";
      }
    }
    ((GalleryImage)localObject2).setImageUrl("http://i.imgur.com/" + (String)localObject1 + "." + paramJsonObject);
    return (GalleryImage)localObject2;
  }
  
  protected final ArrayList<GalleryImage> parseImgurImages(JsonArray paramJsonArray)
  {
    ArrayList localArrayList = new ArrayList();
    paramJsonArray = paramJsonArray.iterator();
    while (paramJsonArray.hasNext()) {
      localArrayList.add(parseImgurImage((JsonElement)paramJsonArray.next()));
    }
    return localArrayList;
  }
  
  public void postInitialize()
  {
    getHostImage().setTitle("Imgur - ");
  }
  
  protected JsonArray sliceImages(JsonArray paramJsonArray, int paramInt)
    throws IOException
  {
    JsonArray localJsonArray = new JsonArray();
    int i = paramInt * 200;
    if (paramInt <= 0) {
      i = 0;
    }
    paramInt = i;
    i = Math.min(i + 200, paramJsonArray.size());
    while (paramInt < i)
    {
      localJsonArray.add(paramJsonArray.get(paramInt));
      paramInt += 1;
    }
    return localJsonArray;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */