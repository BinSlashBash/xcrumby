package com.crumby.impl.deviantart;

import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;

public class DeviantArtMoreLikeThisFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://deviantart.com/morelikethis/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837628;
  public static final String BREADCRUMB_NAME = "deviantArt";
  public static final Class BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
  public static final String REGEX_URL = DeviantArtFragment.REGEX_BASE + "/morelikethis/" + "([0-9]*)";
  public static final String ROOT_NAME = "deviantart.com/morelikethis/";
  public static final String SUFFIX = "/morelikethis/";
  
  protected GalleryProducer createProducer()
  {
    return new DeviantArtMoreLikeThisProducer();
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    alterThisBreadcrumbText(paramBreadcrumbListModifier.getChildren(), "more like this", GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl()));
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtMoreLikeThisFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */