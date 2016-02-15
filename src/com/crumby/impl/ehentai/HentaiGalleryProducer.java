package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiGalleryProducer
  extends GalleryScraper
{
  protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document paramDocument)
    throws NullPointerException
  {
    ArrayList localArrayList = new ArrayList();
    paramDocument = paramDocument.getElementsByClass("it2");
    int i = 0;
    paramDocument = paramDocument.iterator();
    while (paramDocument.hasNext())
    {
      Object localObject2 = (Element)paramDocument.next();
      Object localObject1 = ((Element)localObject2).html().split("~", 4);
      localObject2 = ((Element)localObject2).parent().getElementsByClass("it5").first().child(0).attr("href");
      if ((localObject1.length == 4) && (localObject1[0].equals("init")))
      {
        localObject1 = new GalleryImage("http://" + localObject1[1] + "/" + localObject1[2], (String)localObject2, i + " " + localObject1[3]);
        ((GalleryImage)localObject1).setLinksToGallery(true);
        localArrayList.add(localObject1);
        i += 1;
      }
    }
    return localArrayList;
  }
  
  protected void postInitialize()
  {
    this.pageArg = "page=";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */