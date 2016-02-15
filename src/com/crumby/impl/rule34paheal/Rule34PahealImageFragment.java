package com.crumby.impl.rule34paheal;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class Rule34PahealImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = Rule34PahealFragment.class;
  public static final String REGEX_URL = Rule34PahealFragment.REGEX_BASE + "/post/view/([0-9]+).*";
  
  protected GalleryProducer createProducer()
  {
    new UniversalImageProducer()
    {
      protected String getBaseUrl()
      {
        return "http://rule34.paheal.net";
      }
      
      protected String getRegexForMatchingId()
      {
        return Rule34PahealImageFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return Rule34PahealImageFragment.class.getSimpleName();
      }
    };
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://rule34.paheal.net?tags=" + Uri.encode(paramString.replace(" ", "_"));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/rule34paheal/Rule34PahealImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */