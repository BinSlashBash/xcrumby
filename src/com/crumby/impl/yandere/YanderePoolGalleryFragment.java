package com.crumby.impl.yandere;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class YanderePoolGalleryFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://yande.re/pool/show";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = YanderePoolFragment.class;
  public static final String REGEX_BASE = YanderePoolFragment.REGEX_BASE + "/show/";
  public static final String REGEX_URL = REGEX_BASE + "([0-9]+)*";
  public static final boolean SUGGESTABLE = true;
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  protected GalleryProducer createProducer()
  {
    return new BooruPoolGalleryProducer("https://yande.re/pool/show", YandereFragment.class, REGEX_URL, "https://yande.re", false);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/yandere/YanderePoolGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */