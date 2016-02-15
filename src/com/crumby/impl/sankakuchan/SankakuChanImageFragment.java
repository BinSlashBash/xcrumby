package com.crumby.impl.sankakuchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.CachedProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import java.util.ArrayList;
import java.util.List;

public class SankakuChanImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
  public static final String REGEX_URL = SankakuChanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
  protected HomeGalleryList homeGalleryList;
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    boolean bool = super.addImages(paramList);
    if (paramList.size() > 0)
    {
      paramList.remove(0);
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(paramList);
      getImage().setAttributes(new DeviantArtAttributes(localArrayList));
    }
    return bool;
  }
  
  protected void alternateFillMetadata()
  {
    try
    {
      ArrayList localArrayList = ((DeviantArtAttributes)getImage().attr()).getSimilarImages();
      if ((localArrayList == null) || (localArrayList.isEmpty()))
      {
        this.homeGalleryList.setVisibility(8);
        return;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      this.homeGalleryList.setVisibility(8);
      return;
    }
    GalleryImage localGalleryImage = new GalleryImage("", null, "Similar Images");
    this.homeGalleryList.initialize(localGalleryImage, new CachedProducer(localNullPointerException), getViewer().getMultiSelect(), true);
  }
  
  protected GalleryProducer createProducer()
  {
    return new SankakuChanImageProducer();
  }
  
  protected void fillMetadataView()
  {
    if (this.reloading) {
      return;
    }
    super.fillMetadataView();
  }
  
  protected int getBooruLayout()
  {
    return 2130903108;
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://chan.sankakucomplex.com?&tags=" + Uri.encode(paramString.replace(" ", "_"));
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = super.inflateMetadataLayout(paramLayoutInflater);
    this.homeGalleryList = ((HomeGalleryList)paramLayoutInflater.findViewById(2131492988));
    return paramLayoutInflater;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/sankakuchan/SankakuChanImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */