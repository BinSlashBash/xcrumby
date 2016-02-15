package com.crumby.impl.rule34xxx;

import android.view.ViewGroup;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class Rule34xxxFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://rule34.xxx";
  public static final int BREADCRUMB_ICON = 2130837683;
  public static final String BREADCRUMB_NAME = "rule34";
  public static final String DISPLAY_NAME = "rule34";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("rule34.xxx");
  public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
  public static final String ROOT_NAME = "rule34.xxx";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruProducer("https://rule34.xxx/", "https://rule34.xxx/index.php?page=dapi&s=post&q=index", "https://rule34.xxx/index.php?page=dapi&s=post&q=index", null);
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
    return "rule34";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/rule34xxx/Rule34xxxFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */