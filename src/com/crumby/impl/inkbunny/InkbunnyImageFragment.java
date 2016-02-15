package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class InkbunnyImageFragment
  extends BooruImageFragment
{
  public static final String API_ROOT = "https://inkbunny.net/api_submissions.php?";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = InkbunnyFragment.class;
  public static final String DISPLAY_NAME = "inkbunny";
  public static final String REGEX_URL = InkbunnyFragment.REGEX_BASE + "/submissionview\\.php\\?id=([0-9]+)*";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    new SingleGalleryProducer()
    {
      protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramAnonymousArrayList)
        throws Exception
      {
        paramAnonymousArrayList = GalleryViewerFragment.matchIdFromUrl(InkbunnyImageFragment.REGEX_URL, getHostUrl());
        paramAnonymousArrayList = "https://inkbunny.net/api_submissions.php?" + "&sid=" + InkbunnyProducer.SESSION_ID + "&submission_ids=" + paramAnonymousArrayList;
        InkbunnyProducer.jsonObjectToImage(JSON_PARSER.parse(fetchUrl(paramAnonymousArrayList)).getAsJsonObject().get("submissions").getAsJsonArray().get(0).getAsJsonObject());
      }
    };
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://inkbunny.net/api_search.php?sid=" + InkbunnyProducer.SESSION_ID + "&text=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/inkbunny/InkbunnyImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */