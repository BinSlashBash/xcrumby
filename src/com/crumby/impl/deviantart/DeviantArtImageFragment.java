package com.crumby.impl.deviantart;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.ArrayList;
import java.util.List;

public class DeviantArtImageFragment
  extends GalleryImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = DeviantArtUserFragment.class;
  public static final String REGEX_URL = DeviantArtUserFragment.REGEX_BASE + "/art/.*\\-([0-9]*)";
  public static final boolean SUGGESTABLE = true;
  private WebView description;
  private HomeGalleryList homeGalleryList;
  private View loading;
  
  private GalleryImageFragment getThis()
  {
    return this;
  }
  
  protected GalleryProducer createProducer()
  {
    return new DeviantArtImageProducer();
  }
  
  protected void fillMetadataView()
  {
    if (getImage().attr() == null) {}
    while (getActivity() == null) {
      return;
    }
    String str = ((DeviantArtAttributes)getImage().attr()).getHtmlDescription();
    if (str == null)
    {
      invalidateAlreadyRenderedMetadataFlag();
      return;
    }
    this.loading.setVisibility(8);
    loadWebViewHtml(str, this.description);
    if ((getImage().attr() != null) && (((DeviantArtAttributes)getImage().attr()).getSimilarImages() != null)) {
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if (DeviantArtImageFragment.this.getActivity() == null) {}
          GalleryImage localGalleryImage;
          do
          {
            return;
            localGalleryImage = new GalleryImage("", "http://deviantart.com/morelikethis/" + GalleryViewerFragment.matchIdFromUrl(DeviantArtImageFragment.REGEX_URL, DeviantArtImageFragment.this.getImage().getLinkUrl()), "Similar Images");
          } while ((DeviantArtImageFragment.this.homeGalleryList == null) || (((DeviantArtAttributes)DeviantArtImageFragment.this.getImage().attr()).getSimilarImages() == null));
          DeviantArtImageFragment.this.homeGalleryList.initialize(localGalleryImage, new SingleGalleryProducer()
          {
            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramAnonymous2ArrayList)
              throws Exception
            {
              paramAnonymous2ArrayList.addAll(((DeviantArtAttributes)DeviantArtImageFragment.this.getImage().attr()).getSimilarImages());
            }
          }, DeviantArtImageFragment.this.getViewer().getMultiSelect(), true);
        }
      }, 400L);
    }
    getImageScrollView().fullScroll(33);
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = (ViewGroup)paramLayoutInflater.inflate(2130903059, null);
    this.description = ((WebView)paramLayoutInflater.findViewById(2131492943));
    this.description.setBackgroundColor(0);
    this.loading = paramLayoutInflater.findViewById(2131492944);
    this.homeGalleryList = ((HomeGalleryList)paramLayoutInflater.findViewById(2131492988));
    paramLayoutInflater.findViewById(2131492941).bringToFront();
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    murderWebview(this.description);
    super.onDestroyView();
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    List localList = paramBreadcrumbListModifier.getChildren();
    String str = getImage().getLinkUrl();
    str = matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, str);
    ((Breadcrumb)localList.get(1)).alter("http://" + str + "." + "deviantart.com");
    ((Breadcrumb)localList.get(1)).setAlpha(1.0F);
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */