package com.crumby.impl.pururin;

import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class PururinImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = PururinThumbsFragment.class;
  public static final String REGEX_URL = PururinFragment.REGEX_BASE + "/view/([0-9]+).*/" + CAPTURE_NUMERIC_REPEATING + "/.*";
  
  protected GalleryProducer createProducer()
  {
    new UniversalImageProducer()
    {
      protected String getBaseUrl()
      {
        return "http://pururin.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return PururinImageFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return PururinImageFragment.class.getSimpleName();
      }
    };
  }
  
  protected String getTagUrl(String paramString)
  {
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/pururin/PururinImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */