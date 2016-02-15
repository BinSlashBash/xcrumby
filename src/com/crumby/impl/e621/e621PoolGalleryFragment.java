package com.crumby.impl.e621;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class e621PoolGalleryFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://e621.net/pool/show";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = e621PoolFragment.class;
  public static final String REGEX_BASE = e621PoolFragment.REGEX_BASE + "/show/";
  public static final String REGEX_URL = REGEX_BASE + "([0-9]+)*";
  public static final boolean SUGGESTABLE = true;
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  protected GalleryProducer createProducer()
  {
    return new BooruPoolGalleryProducer("https://e621.net/pool/show", e621Fragment.class, REGEX_URL, "https://e621.net", false);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/e621/e621PoolGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */