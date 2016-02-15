package com.crumby.impl.deviantart;

import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtAttributes
  extends ExtraAttributes
{
  private String htmlDescription;
  private ArrayList<GalleryImage> similarImages;
  
  public DeviantArtAttributes(String paramString)
  {
    this.htmlDescription = paramString;
  }
  
  public DeviantArtAttributes(ArrayList<GalleryImage> paramArrayList)
  {
    this.similarImages = paramArrayList;
  }
  
  public DeviantArtAttributes(Document paramDocument, String paramString)
  {
    this(paramString);
    paramDocument = paramDocument.getElementsByClass("deviation-mlt-preview-body");
    if (paramDocument.isEmpty()) {}
    for (;;)
    {
      return;
      paramDocument = paramDocument.get(paramDocument.size() - 1);
      this.similarImages = new ArrayList();
      paramDocument = paramDocument.getElementsByTag("a").iterator();
      while (paramDocument.hasNext())
      {
        paramString = DeviantArtProducer.convertLinkToImage((Element)paramDocument.next(), true);
        if ((paramString != null) && (paramString.getThumbnailUrl() != null) && (!paramString.getThumbnailUrl().equals(""))) {
          this.similarImages.add(paramString);
        }
      }
    }
  }
  
  public String getHtmlDescription()
  {
    return this.htmlDescription;
  }
  
  public ArrayList<GalleryImage> getSimilarImages()
  {
    return this.similarImages;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */