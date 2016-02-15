package com.crumby.impl.wildcritter;

import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class WildCritterImageFragment
  extends GalleryImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = WildCritterFragment.class;
  public static final String REGEX_URL = WildCritterFragment.REGEX_BASE + "/post/show/([0-9]+).*";
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "http://wildcritters.ws";
      }
      
      protected String getRegexForMatchingId()
      {
        return WildCritterImageFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return "DanbooruImageHtmlFragment";
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/wildcritter/WildCritterImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */