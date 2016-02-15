package com.crumby.impl.rule34paheal;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class Rule34PahealFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://rule34.paheal.net";
  public static final int BREADCRUMB_ICON = 2130837682;
  public static final String BREADCRUMB_NAME = "rule34 paheal";
  public static final String DISPLAY_NAME = "rule34 paheal";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("rule34.paheal.net");
  public static final String REGEX_URL = REGEX_BASE + "/?" + ".*";
  public static final String ROOT_NAME = "rule34.paheal.net";
  public static final int SEARCH_FORM_ID = 2130903103;
  public static final boolean SUGGESTABLE = true;
  
  public static String getDisplayUrl(String paramString)
  {
    String str;
    if (paramString.contains("?tags=")) {
      str = paramString.replace("?tags=", "/post/list" + "/");
    }
    do
    {
      return str;
      str = paramString;
    } while (paramString.contains("/post/list"));
    return paramString + "/post/list";
  }
  
  protected GalleryProducer createProducer()
  {
    return new Rule34PahealProducer();
  }
  
  public String getDisplayUrl()
  {
    return getDisplayUrl(getUrl());
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
    return "rule34 paheal";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/rule34paheal/Rule34PahealFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */