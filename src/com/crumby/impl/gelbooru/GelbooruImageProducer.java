package com.crumby.impl.gelbooru;

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

public class GelbooruImageProducer
  extends SingleGalleryProducer
{
  private String apiRoot;
  private String baseUrl;
  private String regexUrl;
  
  public GelbooruImageProducer(String paramString1, String paramString2, String paramString3)
  {
    this.baseUrl = paramString1;
    this.regexUrl = paramString2;
    this.apiRoot = paramString3;
  }
  
  protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
    throws Exception
  {
    Object localObject1 = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
    GalleryImage localGalleryImage = new GalleryImage();
    if ((getHostImage() == null) || (getHostImage().getImageUrl() == null) || (getHostImage().getTags() == null)) {
      localGalleryImage = GelbooruProducer.convertElementToImage(Jsoup.parse(fetchUrl(this.apiRoot + "&id=" + (String)localObject1), "", Parser.xmlParser()).getElementsByTag("post").first(), this.baseUrl);
    }
    Object localObject2 = Jsoup.parse(fetchUrl(this.apiRoot + "&s=comment&post_id=" + (String)localObject1), "", Parser.xmlParser()).getElementsByTag("comment");
    localObject1 = new ArrayList();
    localObject2 = ((Elements)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      Element localElement = (Element)((Iterator)localObject2).next();
      ((ArrayList)localObject1).add(new ImageComment(localElement.attr("creator"), localElement.attr("body"), null));
    }
    localGalleryImage.setComments((List)localObject1);
    paramArrayList.add(localGalleryImage);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/gelbooru/GelbooruImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */