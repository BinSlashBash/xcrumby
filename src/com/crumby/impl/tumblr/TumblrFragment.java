package com.crumby.impl.tumblr;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class TumblrFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://tumblr.com";
  public static final int BREADCRUMB_ICON = 2130837697;
  public static final String BREADCRUMB_NAME = "tumblr";
  public static final String DISPLAY_NAME = "Tumblr";
  public static final String REGEX_BASE = buildBasicRegexBase("tumblr.com");
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "tumblr.com";
  public static final int SEARCH_FORM_ID = 2130903122;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH });
  
  public static String getDisplayUrl(String paramString)
  {
    String str = paramString;
    if (paramString.contains("tumblr.com?tagged=")) {
      str = paramString.replace("tumblr.com?tagged=", "tumblr.com/tagged/");
    }
    return str;
  }
  
  protected GalleryProducer createProducer()
  {
    return new TumblrProducer();
  }
  
  public String getDisplayUrl()
  {
    return getDisplayUrl(getUrl());
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "tagged";
  }
  
  public String getSearchPrefix()
  {
    return "tumblr";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/tumblr/TumblrFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */