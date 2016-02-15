package com.crumby.impl.e621;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.BooruPoolProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class e621PoolFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://e621.net/pool";
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = e621Fragment.class;
  public static final String DISPLAY_NAME = "e621 - Pools";
  public static final String REGEX_BASE = e621Fragment.REGEX_BASE + "/pool";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final int SEARCH_FORM_ID = 2130903064;
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
    return new BooruPoolProducer("https://e621.net/pool", e621Fragment.class);
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "query";
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


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/e621/e621PoolFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */