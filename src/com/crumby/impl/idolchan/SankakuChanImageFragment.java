package com.crumby.impl.idolchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
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
  private HomeGalleryList homeGalleryList;
  private ArrayList<GalleryImage> images;
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    boolean bool = super.addImages(paramList);
    if (paramList.size() > 0)
    {
      this.images = new ArrayList();
      this.images.addAll(paramList);
      this.images.remove(0);
    }
    return bool;
  }
  
  protected void alternateFillMetadata()
  {
    if ((this.images == null) || (this.images.isEmpty()))
    {
      this.homeGalleryList.setVisibility(8);
      return;
    }
    GalleryImage localGalleryImage = new GalleryImage("", null, "Similar Images");
    this.homeGalleryList.initialize(localGalleryImage, new SingleGalleryProducer()
    {
      protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramAnonymousArrayList)
        throws Exception
      {
        paramAnonymousArrayList.addAll(SankakuChanImageFragment.this.images);
      }
    }, getViewer().getMultiSelect(), true);
  }
  
  protected GalleryProducer createProducer()
  {
    return new SankakuChanImageProducer();
  }
  
  protected int getBooruLayout()
  {
    return 2130903108;
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://idol.sankakucomplex.com?&tags=" + Uri.encode(paramString.replace(" ", "_"));
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = super.inflateMetadataLayout(paramLayoutInflater);
    this.homeGalleryList = ((HomeGalleryList)paramLayoutInflater.findViewById(2131492988));
    return paramLayoutInflater;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/idolchan/SankakuChanImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */