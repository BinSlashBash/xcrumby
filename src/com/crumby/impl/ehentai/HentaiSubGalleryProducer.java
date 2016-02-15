package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiSubGalleryProducer
  extends GalleryScraper
{
  private Map<String, GalleryImage> generatedImages;
  
  protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document paramDocument)
  {
    if (getCurrentPage() == 1) {
      setGalleryMetadata(paramDocument.getElementsByTag("title").get(0).html(), "");
    }
    ArrayList localArrayList = new ArrayList();
    paramDocument = paramDocument.getElementById("gdt").getElementsByClass("gdtm");
    int i = 0;
    paramDocument = paramDocument.iterator();
    if (paramDocument.hasNext())
    {
      Object localObject2 = (Element)paramDocument.next();
      Object localObject1 = ((Element)localObject2).child(0).attr("style");
      String str2 = ((String)localObject1).substring(((String)localObject1).indexOf("background"));
      String str1 = parseStyle((String)localObject1, "url(", ")");
      localObject2 = ((Element)localObject2).child(0).child(0).attr("href");
      int j = Integer.parseInt(parseStyle((String)localObject1, "height:", "px"));
      localObject1 = new GalleryImage(str1, (String)localObject2, null, Integer.parseInt(parseStyle((String)localObject1, "width:", "px")), j, Integer.parseInt(parseStyle(str2.substring(str2.indexOf(")")), "-", "px")));
      ((GalleryImage)localObject1).setReload(true);
      if (this.generatedImages.get(str1) != null) {
        i += 1;
      }
      for (;;)
      {
        localArrayList.add(localObject1);
        break;
        this.generatedImages.put(str1, localObject1);
      }
    }
    paramDocument = localArrayList;
    if (i == localArrayList.size()) {
      paramDocument = null;
    }
    return paramDocument;
  }
  
  String parseStyle(String paramString1, String paramString2, String paramString3)
  {
    paramString1 = paramString1.substring(paramString1.indexOf(paramString2));
    return paramString1.substring(paramString2.length(), paramString1.indexOf(paramString3));
  }
  
  public void postInitialize()
  {
    this.pageArg = "p=";
    this.generatedImages = new HashMap();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ehentai/HentaiSubGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */