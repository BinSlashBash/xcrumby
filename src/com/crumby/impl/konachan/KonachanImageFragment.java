package com.crumby.impl.konachan;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.RestrictedBooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class KonachanImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = KonachanFragment.class;
  public static final String DISPLAY_NAME = "konachan";
  public static final String REGEX_URL = KonachanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new RestrictedBooruImageProducer("http://konachan.com", REGEX_URL);
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://konachan.com/post.json?&tags=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/konachan/KonachanImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */