package com.crumby.impl.ehentai;

import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class HentaiGalleryFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://g.e-hentai.org";
  public static final String BREADCRUMB_NAME = "e-hentai";
  public static final String DISPLAY_NAME = "E-hentai";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("g.e-hentai.org");
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "g.e-hentai.org";
  public static final int SEARCH_FORM_ID = 2130903065;
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new HentaiGalleryProducer();
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "f_search";
  }
  
  public String getSearchPrefix()
  {
    return "e-hentai";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */