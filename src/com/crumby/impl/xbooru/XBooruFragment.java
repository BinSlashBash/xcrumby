package com.crumby.impl.xbooru;

import android.view.ViewGroup;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class XBooruFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://xbooru.com";
  public static final int BREADCRUMB_ICON = 2130837712;
  public static final String BREADCRUMB_NAME = "xbooru";
  public static final String DISPLAY_NAME = "xbooru";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("xbooru.com");
  public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
  public static final String ROOT_NAME = "xbooru.com";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruProducer("https://xbooru.com/", "https://xbooru.com/index.php?page=dapi&s=post&q=index", "https://xbooru.com/index.php?page=dapi&s=post&q=index", null);
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
    return "xbooru";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/xbooru/XBooruFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */