package com.crumby.impl.crumby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class CrumbyGalleryFragment
  extends GalleryGridFragment
{
  public static final int BREADCRUMB_ICON = 2130837640;
  public static final String BREADCRUMB_NAME = "search results";
  public static final String REGEX_URL = ".*";
  public static final String URL_HOST = "http://bing.com/search?";
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected void cleanLinkUrl() {}
  
  protected GalleryProducer createProducer()
  {
    return new CrumbyProducer();
  }
  
  protected void initializeAdapter()
  {
    ((CrumbyProducer)getProducer()).setContext(getActivity().getApplicationContext());
    super.initializeAdapter();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    if (!getUrl().equals("")) {}
    for (String str = "\"" + getUrl() + "\"";; str = "search")
    {
      List localList = paramBreadcrumbListModifier.getChildren();
      ((Breadcrumb)localList.get(0)).setText(str);
      ((Breadcrumb)localList.get(0)).invalidate();
      super.setBreadcrumbs(paramBreadcrumbListModifier);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/crumby/CrumbyGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */