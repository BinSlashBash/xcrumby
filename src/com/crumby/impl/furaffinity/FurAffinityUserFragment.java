package com.crumby.impl.furaffinity;

import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityUserFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://furaffinity.net/user/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837632;
  public static final String BREADCRUMB_NAME = "furAffinity";
  public static final Class BREADCRUMB_PARENT_CLASS = FurAffinityFragment.class;
  public static final String DISPLAY_NAME = "furAffinity";
  public static final String REGEX_URL = FurAffinityFragment.REGEX_BASE + "/user/" + "(.*)";
  public static final String SUFFIX = "/user/";
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "http://www.furaffinity.net";
      }
      
      protected String getRegexForMatchingId()
      {
        return FurAffinityUserFragment.REGEX_URL;
      }
      
      public String getScriptName()
      {
        return FurAffinityUserFragment.class.getSimpleName();
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/furaffinity/FurAffinityUserFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */