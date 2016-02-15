package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class DerpibooruImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
  public static final String DISPLAY_NAME = "Derpibooru - MLP Imageboard";
  public static final String REGEX_URL = DerpibooruFragment.REGEX_BASE + "/([0-9]+)" + "/?" + "\\??" + ".*";
  public static final boolean SUGGESTABLE = true;
  
  public static String[] getTags(JsonObject paramJsonObject)
  {
    paramJsonObject = paramJsonObject.get("tags").getAsString().split(",");
    int i = 0;
    while (i < paramJsonObject.length)
    {
      paramJsonObject[i] = paramJsonObject[i].trim();
      i += 1;
    }
    return paramJsonObject;
  }
  
  protected GalleryProducer createProducer()
  {
    new SingleGalleryProducer()
    {
      protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramAnonymousArrayList)
        throws Exception
      {
        Object localObject = GalleryViewerFragment.matchIdFromUrl(DerpibooruImageFragment.REGEX_URL, getHostUrl());
        new GalleryImage("");
        new StringBuilder().append("https://derpibooru.org/").append((String)localObject).toString();
        localObject = fetchUrl("https://derpibooru.org/" + (String)localObject + ".json?comments=true");
        localObject = DerpibooruProducer.convertDerpJsonToDerpImage(JSON_PARSER.parse((String)localObject).getAsJsonObject());
        if (localObject == null) {
          return;
        }
        paramAnonymousArrayList.add(localObject);
      }
    };
  }
  
  protected void fillMetadataView()
  {
    super.fillMetadataView();
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://derpibooru.org/search?sbq=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */