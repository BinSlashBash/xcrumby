package com.crumby.impl.derpibooru;

import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruUpvotedFragment
  extends DerpibooruNeedsLoginFragment
{
  public static final String BASE_URL = "https://derpibooru.org/images/upvoted";
  public static final int BREADCRUMB_ICON = 2130837623;
  public static final String BREADCRUMB_NAME = "my upvoted";
  public static final Class BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
  public static final String DISPLAY_NAME = "My Derpibooru Upvoted posts";
  public static final String REGEX_URL = DerpibooruFragment.REGEX_BASE + "/images/upvoted" + ".*";
  public static final String SUFFIX = "/images/upvoted";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    new DerpibooruLoggedInUserProducer()
    {
      protected String getSuffix()
      {
        return "/images/upvoted";
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruUpvotedFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */