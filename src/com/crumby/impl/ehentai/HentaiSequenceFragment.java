package com.crumby.impl.ehentai;

import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class HentaiSequenceFragment
  extends GalleryImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final String BREADCRUMB_NAME = "s";
  public static final Class BREADCRUMB_PARENT_CLASS = HentaiSubGalleryFragment.class;
  public static final String REGEX_URL = HentaiGalleryFragment.REGEX_BASE + "/s/" + ALPHANUMERIC_REPEATING + "/" + NUMERIC_REPEATING + "-" + CAPTURE_NUMERIC_REPEATING + "/?";
  
  protected GalleryProducer createProducer()
  {
    return new HentaiSequenceProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiSequenceFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */