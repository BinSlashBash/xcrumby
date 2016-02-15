package com.crumby.impl.e621;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.BooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class e621Fragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://e621.net";
  public static final int BREADCRUMB_ICON = 2130837575;
  public static final String BREADCRUMB_NAME = "e621";
  public static final String DISPLAY_NAME = "e621";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("e621.net") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "e621.net";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    super.applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  protected GalleryProducer createProducer()
  {
    return new BooruProducer("https://e621.net", e621Fragment.class);
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "tags";
  }
  
  public String getSearchPrefix()
  {
    return "e621";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/e621/e621Fragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */