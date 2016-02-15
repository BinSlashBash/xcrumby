package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;

public class InkbunnyProducer
  extends GalleryProducer
{
  public static String API_ROOT = "https://inkbunny.net/api_";
  public static String GET_SID = API_ROOT + "login.php?username=guest";
  public static String SESSION_ID;
  
  public static void getSessionID()
    throws Exception
  {
    String str = JSON_PARSER.parse(fetchUrl(GET_SID)).getAsJsonObject().get("sid").toString();
    SESSION_ID = str.substring(1, str.length() - 1);
  }
  
  public static GalleryImage jsonObjectToImage(JsonObject paramJsonObject)
  {
    Object localObject1 = paramJsonObject.get("thumbnail_url_medium").toString();
    String str1 = paramJsonObject.get("submission_id").toString();
    Object localObject2 = paramJsonObject.get("title").toString();
    localObject1 = new GalleryImage(((String)localObject1).substring(1, ((String)localObject1).length() - 1), "https://inkbunny.net/submissionview.php?id=" + str1.substring(1, str1.length() - 1), ((String)localObject2).substring(1, ((String)localObject2).length() - 1), 100, 100);
    str1 = paramJsonObject.get("file_name").toString();
    str1 = str1.substring(1, str1.length() - 1);
    if (paramJsonObject.get("keywords") != null)
    {
      localObject2 = paramJsonObject.get("keywords").getAsJsonArray();
      paramJsonObject = "";
      localObject2 = ((JsonArray)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str2 = ((JsonElement)((Iterator)localObject2).next()).getAsJsonObject().get("keyword_name").toString();
        paramJsonObject = paramJsonObject + str2.substring(1, str2.length() - 1) + ",";
      }
      ((GalleryImage)localObject1).setTags(paramJsonObject.split(","));
    }
    ((GalleryImage)localObject1).setImageUrl("https://inkbunny.net/files/screen/" + str1.substring(0, 3) + "/" + str1);
    return (GalleryImage)localObject1;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = getGalleryJson(paramInt).get("submissions").getAsJsonArray().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(jsonObjectToImage(((JsonElement)localIterator.next()).getAsJsonObject()));
    }
    return localArrayList;
  }
  
  public JsonObject getGalleryJson(int paramInt)
    throws Exception
  {
    String str = getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "text");
    Object localObject2 = API_ROOT + "search.php?sid=" + SESSION_ID;
    if (SESSION_ID != "")
    {
      localObject1 = localObject2;
      if (JSON_PARSER.parse(fetchUrl((String)localObject2)).getAsJsonObject().get("sid") != null) {}
    }
    else
    {
      getSessionID();
      localObject1 = API_ROOT + "search.php?sid=" + SESSION_ID;
    }
    localObject2 = localObject1;
    if (paramInt > 0) {
      localObject2 = (String)localObject1 + "&page=" + (paramInt + 1);
    }
    Object localObject1 = localObject2;
    if (str != null)
    {
      localObject1 = localObject2;
      if (!str.equals("")) {
        localObject1 = (String)localObject2 + "&text=" + Uri.encode(str);
      }
    }
    return JSON_PARSER.parse(fetchUrl((String)localObject1)).getAsJsonObject();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/inkbunny/InkbunnyProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */