package com.crumby.impl;

import com.crumby.impl.e621.e621PoolGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruPoolGalleryProducer
  extends BooruProducer
{
  private String poolID;
  private String postUrl;
  private String regexUrl;
  
  public BooruPoolGalleryProducer(String paramString1, Class paramClass, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(paramString1, paramClass, paramBoolean);
    this.regexUrl = paramString2;
    this.postUrl = paramString3;
  }
  
  private Document getPoolXml(int paramInt)
    throws Exception
  {
    String str2 = this.baseUrl + ".xml?&id=" + this.poolID;
    String str1 = str2;
    if (paramInt > 0) {
      str1 = str2 + "&page=" + (paramInt + 1);
    }
    return Jsoup.parse(fetchUrl(str1), "", Parser.xmlParser());
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    this.poolID = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
    Object localObject = getPoolXml(paramInt);
    ArrayList localArrayList = new ArrayList();
    localObject = ((Document)localObject).getElementsByTag("post").iterator();
    while (((Iterator)localObject).hasNext())
    {
      Element localElement = (Element)((Iterator)localObject).next();
      localArrayList.add(BooruProducer.convertElementToImage(this.postUrl, localElement, this.prepend));
    }
    return localArrayList;
  }
  
  protected boolean fetchMetadata()
    throws Exception
  {
    if ((getHostImage().getTitle() == null) || (getHostImage().getDescription() == null))
    {
      Object localObject = Jsoup.parse(fetchUrl(this.baseUrl + "/" + e621PoolGalleryFragment.matchIdFromUrl(getHostUrl()) + ".xml"), "", Parser.xmlParser());
      localObject = BooruPoolProducer.getPoolInfoFromElement(this.baseUrl, ((Document)localObject).getElementsByTag("pool").first());
      setGalleryMetadata(((GalleryImage)localObject).getTitle(), ((GalleryImage)localObject).getDescription());
      return true;
    }
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/BooruPoolGalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */