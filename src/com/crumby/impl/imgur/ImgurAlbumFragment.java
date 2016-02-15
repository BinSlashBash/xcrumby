package com.crumby.impl.imgur;

import com.crumby.lib.fragment.producer.GalleryProducer;

public class ImgurAlbumFragment
  extends ImgurBaseFragment
{
  public static final String ALBUM_REGEX = "/a/" + CAPTURE_ALPHANUMERIC_REPEATING;
  public static final String BASE_URL = "http://imgur.com/a/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "/a/";
  public static final Class BREADCRUMB_PARENT_CLASS = ImgurUserGalleryFragment.class;
  public static final String REGEX_BASE = ImgurFragment.REGEX_BASE + ALBUM_REGEX;
  public static final String REGEX_URL = REGEX_BASE + "/?";
  public static final String ROOT_NAME = "imgur.com/a/";
  public static final String SUFFIX = "/a/";
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  protected GalleryProducer createProducer()
  {
    return new ImgurAlbumProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurAlbumFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */