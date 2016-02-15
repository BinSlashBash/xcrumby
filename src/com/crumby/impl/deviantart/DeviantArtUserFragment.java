package com.crumby.impl.deviantart;

import android.view.ViewGroup;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DeviantArtUserFragment
  extends GalleryGridFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837632;
  public static final Class BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
  public static final String REGEX_BASE;
  public static final String REGEX_URL;
  public static final String USER_REGEX = captureMinimumLength(".", 4) + "\\." + "deviantart.com";
  
  static
  {
    REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + USER_REGEX;
    REGEX_URL = REGEX_BASE + "/?";
  }
  
  protected GalleryProducer createProducer()
  {
    return new DeviantArtUserProducer();
  }
  
  protected int getHeaderLayout()
  {
    return 2130903060;
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup.findViewById(2131493079)).showAsInGrid("http://deviantart.com?q=by:" + GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl()));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtUserFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */