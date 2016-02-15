package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtUserProducer
  extends DeviantArtProducer
{
  private String getUserId()
  {
    return GalleryViewerFragment.matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, getHostUrl());
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    getImagesFromReallyStupidSyndication(localArrayList, paramInt);
    return localArrayList;
  }
  
  protected String getRssUrl(int paramInt)
  {
    return "http://backend.deviantart.com/rss.xml?offset=" + paramInt * 60 + "&q=by:" + Uri.encode(getUserId());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtUserProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */