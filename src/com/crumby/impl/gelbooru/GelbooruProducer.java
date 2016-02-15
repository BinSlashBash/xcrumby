package com.crumby.impl.gelbooru;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class GelbooruProducer
  extends GalleryProducer
{
  private final String apiRoot;
  private final String baseUrl;
  private final String safeApiRoot;
  private final Class topLevelClass;
  
  public GelbooruProducer(String paramString1, String paramString2, String paramString3, Class paramClass)
  {
    this.apiRoot = paramString2;
    this.safeApiRoot = paramString3;
    this.baseUrl = paramString1;
    this.topLevelClass = paramClass;
  }
  
  public static GalleryImage convertElementToImage(Element paramElement, String paramString)
    throws Exception
  {
    String str = paramElement.attr("file_url");
    paramString = new GalleryImage(paramElement.attr("preview_url"), paramString + "index.php?page=post&s=view&id=" + paramElement.attr("id"), null, Integer.parseInt(paramElement.attr("width")), Integer.parseInt(paramElement.attr("height")));
    paramString.setImageUrl(str);
    str = paramElement.attr("tags");
    if (str != null) {
      paramString.setTags(str.split(" "));
    }
    paramString.setDescription(paramElement.attr("source"));
    return paramString;
  }
  
  private Document getGelbooruXml(int paramInt)
    throws Exception
  {
    String str2 = getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags");
    String str1 = this.apiRoot;
    if ((str2 != null) && (!str2.equals(""))) {
      str1 = str1 + "&tags=" + Uri.encode(new StringBuilder().append(str2).append(GalleryViewer.getBlacklist()).toString());
    }
    for (;;)
    {
      return Jsoup.parse(fetchUrl(str1 + "&pid=" + paramInt), "", Parser.xmlParser());
      if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass)) {
        str1 = this.safeApiRoot + Uri.encode(GalleryViewer.getBlacklist());
      }
    }
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    Object localObject = getGelbooruXml(paramInt);
    ArrayList localArrayList = new ArrayList();
    localObject = ((Document)localObject).getElementsByTag("post").iterator();
    while (((Iterator)localObject).hasNext())
    {
      Element localElement = (Element)((Iterator)localObject).next();
      GalleryImage localGalleryImage = convertElementToImage(localElement, this.baseUrl);
      if (Boolean.parseBoolean(localElement.attr("has_comments"))) {
        localGalleryImage.setReload(true);
      }
      localArrayList.add(localGalleryImage);
    }
    return localArrayList;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/gelbooru/GelbooruProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */