package com.crumby.impl.derpibooru;

import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruUploadedFragment
  extends DerpibooruNeedsLoginFragment
{
  public static final String BASE_URL = "https://derpibooru.org/images/uploaded";
  public static final int BREADCRUMB_ICON = 2130837607;
  public static final String BREADCRUMB_NAME = "my uploaded";
  public static final Class BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
  public static final String DISPLAY_NAME = "My Derpibooru Uploaded Pictures";
  public static final String REGEX_URL = DerpibooruFragment.REGEX_BASE + "/images/uploaded" + ".*";
  public static final String SUFFIX = "/images/uploaded";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    new DerpibooruLoggedInUserProducer()
    {
      protected String getSuffix()
      {
        return "/images/uploaded";
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruUploadedFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */