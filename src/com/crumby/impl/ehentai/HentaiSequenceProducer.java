package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiSequenceProducer
  extends GalleryProducer
{
  private boolean halt;
  private String imageUrl;
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    Object localObject;
    if (this.halt)
    {
      localObject = null;
      return (ArrayList<GalleryImage>)localObject;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = Jsoup.connect(this.imageUrl).get().getElementsByTag("img").iterator();
    for (;;)
    {
      localObject = localArrayList;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject = (Element)localIterator.next();
      if ((((Element)localObject).attr("style") != null) && (!((Element)localObject).attr("style").equals("")))
      {
        String str = ((Element)localObject).parent().attr("href");
        GalleryImage localGalleryImage = new GalleryImage(null, null, null);
        localGalleryImage.setImageUrl(((Element)localObject).attr("src"));
        if (this.imageUrl.equals(str)) {
          this.halt = true;
        }
        this.imageUrl = str;
        localArrayList.add(localGalleryImage);
      }
    }
  }
  
  public void postInitialize()
  {
    this.imageUrl = getHostImage().getLinkUrl();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiSequenceProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */