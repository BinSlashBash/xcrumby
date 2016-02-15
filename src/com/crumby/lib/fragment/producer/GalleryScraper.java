package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class GalleryScraper
  extends GalleryProducer
{
  final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10";
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    try
    {
      if (getHostImage().getLinkUrl().indexOf("?") == -1) {}
      for (String str = "?";; str = "") {
        return parseGalleryImagesHtml(Jsoup.connect(getHostImage().getLinkUrl() + str + "&" + this.pageArg + paramInt).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10").get());
      }
      ArrayList localArrayList;
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    catch (NullPointerException localNullPointerException)
    {
      try
      {
        Thread.sleep(6000L);
        localArrayList = fetchGalleryImages(paramInt);
        return localArrayList;
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  protected abstract ArrayList<GalleryImage> parseGalleryImagesHtml(Document paramDocument)
    throws NullPointerException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/producer/GalleryScraper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */