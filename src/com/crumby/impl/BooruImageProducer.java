package com.crumby.impl;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruImageProducer
  extends SingleGalleryProducer
{
  protected final String baseUrl;
  private final boolean prepend;
  private final String regexUrl;
  
  public BooruImageProducer(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, false);
  }
  
  public BooruImageProducer(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.regexUrl = paramString2;
    this.baseUrl = paramString1;
    this.prepend = paramBoolean;
  }
  
  protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
    throws Exception
  {
    Object localObject2 = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
    Object localObject1 = new GalleryImage();
    if ((getHostImage() == null) || (getHostImage().getImageUrl() == null) || (getHostImage().getTags() == null))
    {
      localObject1 = Jsoup.parse(fetchUrl(getApiUrlForImage((String)localObject2)), "", Parser.xmlParser()).getElementsByTag("post").first();
      localObject1 = BooruProducer.convertElementToImage(this.baseUrl, (Element)localObject1, this.prepend);
    }
    Object localObject3 = Jsoup.parse(fetchUrl(this.baseUrl + "/comment/index.xml?post_id=" + (String)localObject2), "", Parser.xmlParser()).getElementsByTag("comment");
    localObject2 = new ArrayList();
    localObject3 = ((Elements)localObject3).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      Element localElement = (Element)((Iterator)localObject3).next();
      ((ArrayList)localObject2).add(new ImageComment(localElement.attr("creator"), localElement.attr("body"), null));
    }
    ((GalleryImage)localObject1).setComments((List)localObject2);
    paramArrayList.add(localObject1);
  }
  
  protected String getApiUrlForImage(String paramString)
  {
    return this.baseUrl + "/post/show/" + paramString + ".xml";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/BooruImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */