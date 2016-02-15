package com.crumby.impl.imgur;

import com.crumby.lib.fragment.producer.GalleryProducer;

public class ImgurSubredditFragment
  extends ImgurBaseFragment
{
  public static final Class ALIAS_CLASS = ImgurAlbumFragment.class;
  public static final String BASE_URL = "http://imgur.com/r/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "/r/";
  public static final Class BREADCRUMB_PARENT_CLASS;
  public static final String REGEX_BASE;
  public static final String REGEX_URL;
  public static final String ROOT_NAME = "imgur.com/r/";
  public static final String SUBREDDIT_REGEX = "/r/" + CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING;
  public static final String SUFFIX = "/r/";
  public static final boolean SUGGESTABLE = true;
  
  static
  {
    REGEX_BASE = ImgurFragment.REGEX_BASE + SUBREDDIT_REGEX;
    REGEX_URL = REGEX_BASE + "/?";
    BREADCRUMB_PARENT_CLASS = ImgurUserGalleryFragment.class;
  }
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  protected GalleryProducer createProducer()
  {
    return new ImgurSubredditProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurSubredditFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */