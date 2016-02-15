package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruPoolFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://danbooru.donmai.us/pools";
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
  public static final String DISPLAY_NAME = "Danbooru - Pools";
  public static final String REGEX_BASE = DanbooruGalleryFragment.REGEX_BASE + "/pools";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final int SEARCH_FORM_ID = 2130903054;
  public static final boolean SUGGESTABLE = true;
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected GalleryProducer createProducer()
  {
    return new DanbooruPoolProducer();
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "search[name_matches]";
  }
  
  public String getSearchPrefix()
  {
    return "pools";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruPoolFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */