package com.crumby.impl.konachan;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class GenericBooruImageProducer
  extends SingleGalleryProducer
{
  private final String apiUrl;
  private final String baseUrl;
  private final String regexUrl;
  
  public GenericBooruImageProducer(String paramString1, String paramString2, String paramString3)
  {
    this.regexUrl = paramString3;
    this.apiUrl = paramString1;
    this.baseUrl = paramString2;
  }
  
  protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
    throws Exception
  {
    String str = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
    str = this.apiUrl + "tags=id:" + str;
    GenericBooruProducer.convertArrayToGalleryImages(this.baseUrl, fetchUrl(str), paramArrayList);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/konachan/GenericBooruImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */