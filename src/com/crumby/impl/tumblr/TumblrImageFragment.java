package com.crumby.impl.tumblr;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.sankakuchan.SankakuChanImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class TumblrImageFragment
  extends SankakuChanImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = TumblrArtistFragment.class;
  public static final String REGEX_URL = TumblrArtistFragment.REGEX_BASE + "/post/([0-9]+)" + "/?" + ".*";
  
  private void alterBreadcrumbs(List<Breadcrumb> paramList)
  {
    if ((paramList == null) || (paramList.size() < 2) || (getImage() == null) || (getImage().getLinkUrl() == null)) {
      return;
    }
    String str = getImage().getLinkUrl();
    str = GalleryViewerFragment.matchIdFromUrl(TumblrArtistFragment.REGEX_URL, str);
    ((Breadcrumb)paramList.get(1)).alter("http://" + str + "." + "tumblr.com");
  }
  
  protected void alternateFillMetadata()
  {
    super.alternateFillMetadata();
    this.homeGalleryList.findViewById(2131492989).setVisibility(8);
  }
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = super.createFragmentView(paramLayoutInflater, paramViewGroup, paramBundle);
    paramLayoutInflater.findViewById(2131492989).setVisibility(8);
    return paramLayoutInflater;
  }
  
  protected GalleryProducer createProducer()
  {
    new UniversalImageProducer()
    {
      protected String getBaseUrl()
      {
        return "http://api.tumblr.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return TumblrImageFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return TumblrImageFragment.class.getSimpleName();
      }
    };
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://tumblr.com?tagged=" + Uri.encode(paramString);
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    alterBreadcrumbs(paramBreadcrumbListModifier.getChildren());
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
  
  public void setUserVisibleHint(boolean paramBoolean)
  {
    super.setUserVisibleHint(paramBoolean);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/tumblr/TumblrImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */