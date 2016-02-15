package com.crumby.impl.tbib;

import android.view.ViewGroup;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class TbibFragment
  extends GalleryGridFragment
{
  public static final String API_ROOT = "http://tbib.org/index.php?page=dapi&s=post&q=index";
  public static final String BASE_URL = "http://tbib.org";
  public static final int BREADCRUMB_ICON = 2130837692;
  public static final String BREADCRUMB_NAME = "tbib";
  public static final String DISPLAY_NAME = "tbib";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("tbib.org") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "tbib.org";
  public static final String SAFE_API_ROOT = "http://tbib.org/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruProducer("http://tbib.org/", "http://tbib.org/index.php?page=dapi&s=post&q=index", "http://tbib.org/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe", TbibFragment.class);
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
    return "tbib";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/tbib/TbibFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */