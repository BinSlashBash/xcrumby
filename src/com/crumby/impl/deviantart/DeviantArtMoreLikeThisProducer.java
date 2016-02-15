package com.crumby.impl.deviantart;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtMoreLikeThisProducer
  extends DeviantArtProducer
{
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    getImagesFromWebPage(localArrayList, paramInt);
    return localArrayList;
  }
  
  protected String getDefaultUrl(int paramInt)
  {
    String str = GalleryViewerFragment.matchIdFromUrl(DeviantArtMoreLikeThisFragment.REGEX_URL, getHostUrl());
    return "http://deviantart.com/morelikethis/" + str + "?offset=" + paramInt * 24;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtMoreLikeThisProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */