package com.crumby.impl.idolchan;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class SankakuChanPoolFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://idol.sankakucomplex.com/pool";
  public static final String BREADCRUMB_NAME = "pools";
  public static final Class BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
  public static final String DISPLAY_NAME = "pools";
  public static final String REGEX_BASE = SankakuChanFragment.REGEX_BASE + "/pool";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final int SEARCH_FORM_ID = 2130903064;
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "https://idol.sankakucomplex.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return null;
      }
      
      protected String getScriptName()
      {
        return SankakuChanPoolFragment.class.getSimpleName();
      }
    };
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


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/idolchan/SankakuChanPoolFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */