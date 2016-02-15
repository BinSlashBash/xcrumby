package com.crumby.impl.tumblr;

import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.regex.Pattern;

public class TumblrArtistFragment
  extends TumblrFragment
{
  public static final String ALT_SEARCH_BASE = REGEX_BASE;
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837632;
  public static final Class BREADCRUMB_PARENT_CLASS;
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + captureMinimumLength("[a-zA-Z0-9_\\+\\-]", 4) + "\\." + Pattern.quote("tumblr.com");
  public static final String REGEX_URL = REGEX_BASE + ".*";
  
  static
  {
    BREADCRUMB_PARENT_CLASS = TumblrFragment.class;
  }
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "http://api.tumblr.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return TumblrArtistFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return TumblrArtistFragment.class.getSimpleName();
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/tumblr/TumblrArtistFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */