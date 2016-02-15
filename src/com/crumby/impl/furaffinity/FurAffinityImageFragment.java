package com.crumby.impl.furaffinity;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class FurAffinityImageFragment
  extends GalleryImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = FurAffinityUserFragment.class;
  public static final String MATCH_USER_ID = ".*#(.*)";
  public static final String REGEX_URL = FurAffinityFragment.REGEX_BASE + "/view/([0-9]+)#?.*";
  private ImageCommentsView commentsView;
  private WebView description;
  
  protected GalleryProducer createProducer()
  {
    new UniversalImageProducer()
    {
      protected String getBaseUrl()
      {
        return "http://www.furaffinity.net";
      }
      
      protected String getRegexForMatchingId()
      {
        return FurAffinityImageFragment.REGEX_URL;
      }
      
      public String getScriptName()
      {
        return FurAffinityImageFragment.class.getSimpleName();
      }
    };
  }
  
  protected void fillMetadataView()
  {
    loadWebViewHtml(getImage().getDescription(), this.description);
    this.commentsView.initialize(getImage().getComments());
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = (ViewGroup)paramLayoutInflater.inflate(2130903072, null);
    this.description = ((WebView)paramLayoutInflater.findViewById(2131492981));
    this.description.setBackgroundColor(0);
    this.commentsView = ((ImageCommentsView)paramLayoutInflater.findViewById(2131493021));
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    murderWebview(this.description);
    super.onDestroyView();
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    if (getUrl().matches(".*#(.*)"))
    {
      String str = matchIdFromUrl(".*#(.*)", getUrl());
      ((Breadcrumb)paramBreadcrumbListModifier.getChildren().get(1)).alter("http://furaffinity.net/user/" + str);
    }
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/furaffinity/FurAffinityImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */