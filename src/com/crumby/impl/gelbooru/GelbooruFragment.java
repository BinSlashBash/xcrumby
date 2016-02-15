package com.crumby.impl.gelbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class GelbooruFragment
  extends GalleryGridFragment
{
  public static final String API_ROOT = "http://gelbooru.com/index.php?page=dapi&s=post&q=index";
  public static final String BASE_URL = "http://gelbooru.com";
  public static final int BREADCRUMB_ICON = 2130837590;
  public static final String BREADCRUMB_NAME = "gelbooru";
  public static final String DISPLAY_NAME = "Gelbooru";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("gelbooru.com") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "gelbooru.com";
  public static final String SAFE_API_ROOT = "http://gelbooru.com/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    super.applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruProducer("http://gelbooru.com/", "http://gelbooru.com/index.php?page=dapi&s=post&q=index", "http://gelbooru.com/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe", GelbooruFragment.class);
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
    return "gelbooru";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/gelbooru/GelbooruFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */