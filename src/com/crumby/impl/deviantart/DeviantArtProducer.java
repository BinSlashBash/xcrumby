package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtProducer
  extends GalleryProducer
{
  protected static final String API_URL = "http://backend.deviantart.com/";
  protected static final String RSS_API_URL = "http://backend.deviantart.com/rss.xml?";
  
  public static GalleryImage convertLinkToImage(Element paramElement, boolean paramBoolean)
  {
    Object localObject;
    if ((paramElement.hasClass("thumb")) || (paramBoolean))
    {
      localObject = paramElement.getElementsByTag("img").first();
      if (localObject != null) {}
    }
    else
    {
      return null;
    }
    String str1 = paramElement.attr("data-super-full-img");
    try
    {
      i = Integer.parseInt(paramElement.attr("data-super-full-width"));
      j = Integer.parseInt(paramElement.attr("data-super-full-height"));
      localObject = new GalleryImage(((Element)localObject).attr("src"), paramElement.attr("href"), paramElement.attr("title"), i, j);
      CrDb.d("deviantart producer", "===================");
      CrDb.d("deviantart producer", paramElement.attr("data-super-full-img"));
      CrDb.d("deviantart producer", paramElement.attr("data-super-img") + " ");
      CrDb.d("deviantart producer", str1);
      CrDb.d("deviantart producer", "===================");
      ((GalleryImage)localObject).setImageUrl(str1);
      ((GalleryImage)localObject).setReload(true);
      return (GalleryImage)localObject;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      for (;;)
      {
        try
        {
          i = Integer.parseInt(paramElement.attr("data-super-width"));
          j = Integer.parseInt(paramElement.attr("data-super-height"));
          String str2 = paramElement.attr("data-super-img");
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          int i = 0;
          int j = 0;
          String str3 = ((Element)localObject).attr("src");
        }
      }
    }
  }
  
  public static String modifyDescription(String paramString)
  {
    String str = paramString;
    if (paramString != null)
    {
      str = paramString;
      if (paramString.contains("<img")) {
        str = paramString.substring(0, paramString.lastIndexOf("<img"));
      }
    }
    return str;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramInt == 0) || (getParameterInUrl(getHostUrl(), "q") != null))
    {
      getImagesFromReallyStupidSyndication(localArrayList, paramInt);
      return localArrayList;
    }
    getImagesFromWebPage(localArrayList, paramInt);
    return localArrayList;
  }
  
  protected String getDefaultUrl(int paramInt)
  {
    return "http://www.deviantart.com/?order=67108864&offset=" + paramInt * 24;
  }
  
  protected void getImagesFromReallyStupidSyndication(ArrayList<GalleryImage> paramArrayList, int paramInt)
    throws Exception
  {
    Object localObject1 = XML.toJSONObject(legacyfetchUrl(getRssUrl(paramInt))).toString();
    localObject1 = new ObjectMapper().readTree((String)localObject1).findParent("item");
    if (localObject1 == null) {}
    for (;;)
    {
      return;
      if ((getHostImage().getTitle() == null) && (getHostImage().getDescription() == null)) {
        setGalleryMetadata(((JsonNode)localObject1).get("title").asText(), ((JsonNode)localObject1).get("description").asText());
      }
      if (((JsonNode)localObject1).get("item") != null)
      {
        Iterator localIterator = ((ArrayNode)((JsonNode)localObject1).get("item")).iterator();
        while (localIterator.hasNext())
        {
          JsonNode localJsonNode1 = (JsonNode)localIterator.next();
          JsonNode localJsonNode2 = localJsonNode1.get("media:content");
          Object localObject2 = (ArrayNode)localJsonNode1.get("media:thumbnail");
          paramInt = 0;
          localObject1 = "";
          if (localObject2 != null)
          {
            localObject2 = ((ArrayNode)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              JsonNode localJsonNode3 = (JsonNode)((Iterator)localObject2).next();
              i = localJsonNode3.get("height").asInt();
              if (i > paramInt)
              {
                paramInt = i;
                localObject1 = localJsonNode3.get("url").asText();
              }
            }
            paramInt = 0;
            int i = 0;
            if (localJsonNode2.get("width") != null)
            {
              paramInt = localJsonNode2.get("width").asInt();
              i = localJsonNode2.get("height").asInt();
            }
            localObject1 = new GalleryImage((String)localObject1, localJsonNode1.get("link").asText(), localJsonNode1.get("title").asText(), paramInt, i);
            ((GalleryImage)localObject1).setImageUrl(localJsonNode2.get("url").asText());
            ((GalleryImage)localObject1).setReload(true);
            ((GalleryImage)localObject1).setAttributes(new DeviantArtAttributes(modifyDescription(localJsonNode1.get("description").asText())));
            paramArrayList.add(localObject1);
          }
        }
      }
    }
  }
  
  protected void getImagesFromWebPage(ArrayList<GalleryImage> paramArrayList, int paramInt)
    throws Exception
  {
    Object localObject1 = Jsoup.parse(fetchUrl(getDefaultUrl(paramInt))).getElementsByClass("page-results").first();
    if (localObject1 == null) {}
    for (;;)
    {
      return;
      localObject1 = ((Element)localObject1).children().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (Element)((Iterator)localObject1).next();
        try
        {
          localObject2 = convertLinkToImage(((Element)localObject2).getElementsByTag("a").first(), false);
          if (localObject2 != null) {
            paramArrayList.add(localObject2);
          }
        }
        catch (Exception localException) {}
      }
    }
  }
  
  protected String getRssUrl(int paramInt)
  {
    String str = getParameterInUrl(getHostUrl(), "q");
    if (str != null) {
      return getRssUrlForSearch(paramInt, str);
    }
    return "http://backend.deviantart.com/rss.xml?offset=" + paramInt * 24 + "&order=67108864";
  }
  
  protected String getRssUrlForSearch(int paramInt, String paramString)
  {
    return "http://backend.deviantart.com/rss.xml?offset=" + paramInt * 60 + "&q=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */