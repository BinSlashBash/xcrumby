package com.crumby.impl.imgur;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class ImgurFragment
  extends ImgurBaseFragment
{
  public static final String BASE_URL = "http://imgur.com";
  public static final int BREADCRUMB_ICON = 2130837657;
  public static final String BREADCRUMB_NAME = "imgur";
  public static final String DISPLAY_NAME = "Imgur";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("imgur.com");
  public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
  public static final String ROOT_NAME = "imgur.com";
  public static final int SEARCH_FORM_ID = 2130903094;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH });
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new ImgurProducer();
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "q";
  }
  
  public String getSearchPrefix()
  {
    return "imgur";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */