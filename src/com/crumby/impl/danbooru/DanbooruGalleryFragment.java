package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruGalleryFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://danbooru.donmai.us";
  public static final int BREADCRUMB_ICON = 2130837565;
  public static final String BREADCRUMB_NAME = "danbooru";
  public static final String DISPLAY_NAME = "Danbooru - Anime & Manga";
  public static final String POSTS_URL = "http://danbooru.donmai.us/posts";
  public static final String REGEX_BASE = buildBasicRegexBase("danbooru.donmai.us");
  public static final String REGEX_URL = REGEX_BASE + "/?" + "(?:\\?.*)?";
  public static final String ROOT_NAME = "danbooru.donmai.us";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected GalleryProducer createProducer()
  {
    return new DanbooruGalleryProducer();
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
    return "danbooru";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */