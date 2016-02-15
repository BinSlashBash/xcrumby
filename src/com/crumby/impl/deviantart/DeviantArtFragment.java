package com.crumby.impl.deviantart;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class DeviantArtFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://deviantart.com";
  public static final int BREADCRUMB_ICON = 2130837568;
  public static final String BREADCRUMB_NAME = "deviantArt";
  public static final String DISPLAY_NAME = "Deviantart";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("deviantart.com");
  public static final String REGEX_URL = REGEX_BASE + "/?" + "(?:\\?.*)?";
  public static final String ROOT_NAME = "deviantart.com";
  public static final int SEARCH_FORM_ID = 2130903094;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH });
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new DeviantArtProducer();
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
    return "deviantArt";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */