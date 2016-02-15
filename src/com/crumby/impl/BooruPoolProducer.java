package com.crumby.impl;

import android.net.Uri;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruPoolProducer
  extends BooruProducer
{
  public BooruPoolProducer(String paramString, Class paramClass)
  {
    super(paramString, paramClass);
  }
  
  public static GalleryImage getPoolInfoFromElement(String paramString, Element paramElement)
    throws Exception
  {
    String str = paramElement.attr("id");
    paramString = new GalleryImage(CrumbyProducer.getSnapshot(paramString + "/show/" + str), paramString + "/show/" + str, paramElement.attr("name").replace("_", " "));
    try
    {
      paramString.setDescription(paramElement.getElementsByTag("description").text());
      return paramString;
    }
    catch (NullPointerException paramElement) {}
    return paramString;
  }
  
  private Document getPoolXml(int paramInt)
    throws Exception
  {
    Object localObject2 = this.baseUrl + "/index.xml?";
    Uri.parse(((String)localObject2).toString());
    String str = getParameterInUrl(getHostUrl(), "query");
    Object localObject1 = localObject2;
    if (str != null) {
      localObject1 = (String)localObject2 + "&query=" + str;
    }
    localObject2 = localObject1;
    if (paramInt > 0) {
      localObject2 = (String)localObject1 + "&page=" + (paramInt + 1);
    }
    return Jsoup.parse(fetchUrl((String)localObject2), "", Parser.xmlParser());
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    Document localDocument = getPoolXml(paramInt);
    int j = localDocument.getElementsByTag("pool").size();
    int i = 0;
    while (i < j)
    {
      GalleryImage localGalleryImage = getPoolInfoFromElement(this.baseUrl, localDocument.getElementsByTag("pool").get(i));
      localGalleryImage.setPage(paramInt);
      localArrayList.add(localGalleryImage);
      i += 1;
    }
    return localArrayList;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/BooruPoolProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */