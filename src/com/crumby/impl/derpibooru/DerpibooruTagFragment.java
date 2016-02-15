package com.crumby.impl.derpibooru;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import java.util.List;

public class DerpibooruTagFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "https://derpibooru.org/tags/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final Class BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
  public static final String REGEX_URL = DerpibooruFragment.REGEX_BASE + "/tags/" + "(.*)";
  public static final String SUFFIX = "/tags/";
  
  protected GalleryProducer createProducer()
  {
    return new DerpibooruProducer();
  }
  
  public void setBreadcrumbs(List<Breadcrumb> paramList)
  {
    DerpibooruFragment.addSearchQuery(getUrl(), paramList);
    super.setBreadcrumbs(paramList);
  }
  
  public void setImage(GalleryImage paramGalleryImage)
  {
    String str = GalleryViewerFragment.matchIdFromUrl(REGEX_URL, paramGalleryImage.getLinkUrl());
    paramGalleryImage.setLinkUrl("https://derpibooru.org?search=" + str);
    super.setImage(paramGalleryImage);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruTagFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */