package com.crumby.impl.danbooru;

import android.net.Uri;
import android.os.Bundle;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.router.FragmentRouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class DanbooruGalleryProducer
  extends GalleryParser
{
  public static final String API_URL = "http://danbooru.donmai.us/posts.json?";
  
  public static String buildLink(String paramString1, String paramString2)
  {
    paramString1 = paramString1.replace("_", "\\_");
    paramString2 = paramString2.replace("_", "\\_");
    return "[" + paramString2 + "]" + "(" + paramString1 + ")";
  }
  
  private boolean checkForTags(String[] paramArrayOfString, String paramString)
  {
    if (paramArrayOfString == null) {}
    while (Arrays.binarySearch(paramArrayOfString, paramString) < 0) {
      return false;
    }
    return true;
  }
  
  String buildTagLinks(String paramString)
  {
    String[] arrayOfString = paramString.split(" ");
    paramString = "## Tag Links\n";
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str = arrayOfString[i];
      paramString = paramString + buildLink(new StringBuilder().append("http://danbooru.donmai.us/posts?tags=").append(str).toString(), str) + " ";
      i += 1;
    }
    return paramString;
  }
  
  public URL convertUrlToApi(String paramString)
  {
    String str = "http://danbooru.donmai.us/posts.json?";
    Object localObject = str;
    if (paramString != null)
    {
      localObject = Uri.parse(paramString.toString());
      if ((getQueryParameter((Uri)localObject, paramString, "tags") == null) || (getQueryParameter((Uri)localObject, paramString, "tags").equals(""))) {
        break label87;
      }
      localObject = "http://danbooru.donmai.us/posts.json?" + "&tags=" + Uri.encode(getQueryParameter((Uri)localObject, paramString, "tags"));
    }
    for (;;)
    {
      try
      {
        paramString = new URL((String)localObject);
        return paramString;
      }
      catch (MalformedURLException paramString)
      {
        label87:
        paramString.printStackTrace();
      }
      localObject = str;
      if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DanbooruGalleryFragment.class)) {
        localObject = "http://danbooru.donmai.us/posts.json?" + "&tags=rating%3Asafe";
      }
    }
    return null;
  }
  
  protected final String deriveUrl(JsonElement paramJsonElement, String paramString)
  {
    String str2 = "";
    String str1 = str2;
    if (paramJsonElement != null)
    {
      str1 = str2;
      if (paramJsonElement.toString() != null) {
        str1 = paramJsonElement.toString().replace("\"", "");
      }
    }
    return "http://danbooru.donmai.us" + paramString + str1;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = fetchUrl(this.apiUrl.toString() + getSearchMark() + "&page=" + paramInt);
    try
    {
      localObject = JSON_PARSER.parse((String)localObject).getAsJsonArray();
      int i = 0;
      while (i < ((JsonArray)localObject).size())
      {
        GalleryImage localGalleryImage = getGalleryImage(((JsonArray)localObject).get(i).getAsJsonObject());
        localGalleryImage.setPage(paramInt);
        localArrayList.add(localGalleryImage);
        i += 1;
      }
      filterOutUndesiredDanbooruImages(localArrayList);
      return localArrayList;
    }
    catch (JsonSyntaxException localJsonSyntaxException) {}
    return null;
  }
  
  protected boolean fetchMetadata()
    throws IOException
  {
    setGalleryMetadata(null, getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags"));
    return false;
  }
  
  protected void filterOutUndesiredDanbooruImages(ArrayList<GalleryImage> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    if (GalleryViewer.BLACK_LISTED_TAGS == null) {
      return;
    }
    Iterator localIterator1 = paramArrayList.iterator();
    for (;;)
    {
      if (!localIterator1.hasNext()) {
        break label156;
      }
      GalleryImage localGalleryImage = (GalleryImage)localIterator1.next();
      if (localGalleryImage.getTags() != null)
      {
        DanbooruAttributes localDanbooruAttributes = (DanbooruAttributes)localGalleryImage.attr();
        Iterator localIterator2 = GalleryViewer.BLACK_LISTED_TAGS.iterator();
        if (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          if ((!checkForTags(localGalleryImage.getTags(), str)) && (!checkForTags(localDanbooruAttributes.getArtistTags(), str)) && (!checkForTags(localDanbooruAttributes.getCharacterTags(), str)) && (!checkForTags(localDanbooruAttributes.getCopyrightTags(), str))) {
            break;
          }
          localArrayList.add(localGalleryImage);
        }
      }
    }
    label156:
    paramArrayList.removeAll(localArrayList);
  }
  
  protected GalleryImage getGalleryImage(JsonObject paramJsonObject)
  {
    GalleryImage localGalleryImage = new GalleryImage(deriveUrl(paramJsonObject.get("preview_file_url"), ""), deriveUrl(paramJsonObject.get("id"), "/posts/"), "", JNH.getAttributeInt(paramJsonObject, "image_width"), JNH.getAttributeInt(paramJsonObject, "image_height"));
    localGalleryImage.setImageUrl(deriveUrl(paramJsonObject.get("file_url"), ""));
    localGalleryImage.setTags(paramJsonObject.get("tag_string").getAsString().split(" "));
    localGalleryImage.setAttributes(new DanbooruAttributes(paramJsonObject.get("tag_string_character").getAsString().split(" "), paramJsonObject.get("tag_string_artist").getAsString().split(" "), paramJsonObject.get("tag_string_copyright").getAsString().split(" ")));
    return localGalleryImage;
  }
  
  public void initialize(GalleryConsumer paramGalleryConsumer, GalleryImage paramGalleryImage, Bundle paramBundle)
  {
    super.initialize(paramGalleryConsumer, paramGalleryImage, paramBundle);
    setStartPage(1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */