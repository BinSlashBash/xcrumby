package com.crumby.impl.ehentai;

import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class HentaiSubGalleryFragment
  extends GalleryGridFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "g";
  public static final Class BREADCRUMB_PARENT_CLASS = HentaiGalleryFragment.class;
  public static final String REGEX_URL = HentaiGalleryFragment.REGEX_BASE + "/g/" + NUMERIC_REPEATING + "/" + CAPTURE_ALPHANUMERIC_REPEATING + "/?";
  
  protected GalleryProducer createProducer()
  {
    return new HentaiSubGalleryProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiSubGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */