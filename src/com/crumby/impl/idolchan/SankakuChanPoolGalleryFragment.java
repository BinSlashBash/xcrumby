package com.crumby.impl.idolchan;

import com.crumby.impl.e621.e621PoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanPoolGalleryFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://idol.sankakucomplex.com/pool/show";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = e621PoolFragment.class;
  public static final String REGEX_BASE = SankakuChanPoolFragment.REGEX_BASE + "/show\\.xml\\?\\&id=";
  public static final String REGEX_URL = REGEX_BASE + "([0-9]+)*";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "https://idol.sankakucomplex.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return SankakuChanPoolGalleryFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return SankakuChanPoolGalleryFragment.class.getSimpleName();
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/idolchan/SankakuChanPoolGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */