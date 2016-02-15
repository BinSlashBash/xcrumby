package com.crumby.impl;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruProducer
  extends GalleryProducer
{
  protected final String baseUrl;
  private Set<String> filteredBlacklist;
  protected boolean prepend;
  private int skipTags = 0;
  protected final Class topLevelClass;
  
  public BooruProducer(String paramString, Class paramClass)
  {
    this.baseUrl = paramString;
    this.topLevelClass = paramClass;
  }
  
  public BooruProducer(String paramString, Class paramClass, boolean paramBoolean)
  {
    this(paramString, paramClass);
    this.prepend = paramBoolean;
  }
  
  private boolean checkForTags(String[] paramArrayOfString, String paramString)
  {
    if (paramArrayOfString == null) {}
    while (Arrays.binarySearch(paramArrayOfString, paramString) < 0) {
      return false;
    }
    return true;
  }
  
  public static GalleryImage convertElementToImage(String paramString, Element paramElement, boolean paramBoolean)
    throws Exception
  {
    Object localObject2 = paramElement.attr("file_url");
    Object localObject1 = localObject2;
    if (paramBoolean) {
      localObject1 = paramString + (String)localObject2;
    }
    String str2 = paramElement.attr("description");
    int i = 100;
    int j = 100;
    for (;;)
    {
      try
      {
        k = Integer.parseInt(paramElement.attr("width"));
        i = k;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        int k;
        String str1;
        continue;
      }
      try
      {
        k = Integer.parseInt(paramElement.attr("height"));
        j = k;
      }
      catch (NumberFormatException localNumberFormatException1) {}
    }
    str1 = paramElement.attr("preview_url");
    localObject2 = str1;
    if (paramBoolean) {
      localObject2 = paramString + str1;
    }
    paramString = new GalleryImage((String)localObject2, paramString + "/post/show/" + paramElement.attr("id"), null, i, j);
    paramString.setImageUrl((String)localObject1);
    paramElement = paramElement.attr("tags");
    if (paramElement != null) {
      paramString.setTags(paramElement.split(" "));
    }
    paramString.setDescription(str2);
    return paramString;
  }
  
  private Document getXml(int paramInt)
    throws Exception
  {
    Object localObject3 = getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags");
    Object localObject1 = this.baseUrl + "/post/index.xml?";
    Object localObject2 = localObject1;
    if (paramInt > 0) {
      localObject2 = (String)localObject1 + "&page=" + (paramInt + 1);
    }
    if (localObject3 != null)
    {
      localObject1 = localObject3;
      if (!((String)localObject3).equals("")) {}
    }
    else
    {
      if (!FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass)) {
        break label191;
      }
    }
    label191:
    for (localObject1 = "rating:safe";; localObject1 = "")
    {
      this.skipTags = (6 - ((String)localObject1).split(" ").length);
      paramInt = 0;
      this.filteredBlacklist = new LinkedHashSet();
      localObject3 = GalleryViewer.BLACK_LISTED_TAGS.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        String str = (String)((Iterator)localObject3).next();
        if (paramInt >= this.skipTags) {
          this.filteredBlacklist.add(str);
        }
        paramInt += 1;
      }
    }
    return Jsoup.parse(fetchUrl((String)localObject2 + "&tags=" + Uri.encode(new StringBuilder().append((String)localObject1).append(GalleryViewer.getBlacklist(this.skipTags)).toString())), "", Parser.xmlParser());
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    Object localObject = getXml(paramInt);
    ArrayList localArrayList = new ArrayList();
    localObject = ((Document)localObject).getElementsByTag("post").iterator();
    while (((Iterator)localObject).hasNext())
    {
      Element localElement = (Element)((Iterator)localObject).next();
      GalleryImage localGalleryImage = convertElementToImage(this.baseUrl, localElement, this.prepend);
      if (Boolean.parseBoolean(localElement.attr("has_comments"))) {
        localGalleryImage.setReload(true);
      }
      localArrayList.add(localGalleryImage);
    }
    return localArrayList;
  }
  
  protected void filterOutUndesiredImages(ArrayList<GalleryImage> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    if (this.filteredBlacklist == null) {
      return;
    }
    Iterator localIterator1 = paramArrayList.iterator();
    for (;;)
    {
      if (!localIterator1.hasNext()) {
        break label108;
      }
      GalleryImage localGalleryImage = (GalleryImage)localIterator1.next();
      if (localGalleryImage.getTags() != null)
      {
        Iterator localIterator2 = this.filteredBlacklist.iterator();
        if (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          if (!checkForTags(localGalleryImage.getTags(), str)) {
            break;
          }
          localArrayList.add(localGalleryImage);
        }
      }
    }
    label108:
    paramArrayList.removeAll(localArrayList);
    super.filterOutUndesiredImages(paramArrayList);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/BooruProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */