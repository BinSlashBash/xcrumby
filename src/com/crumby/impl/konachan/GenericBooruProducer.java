package com.crumby.impl.konachan;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GenericBooruProducer
  extends GalleryProducer
{
  private String apiRoot;
  private String baseUrl;
  private Class topLevel;
  
  public GenericBooruProducer(String paramString1, String paramString2, Class paramClass)
  {
    this.apiRoot = paramString1;
    this.baseUrl = paramString2;
    this.topLevel = paramClass;
  }
  
  public static void convertArrayToGalleryImages(String paramString1, String paramString2, ArrayList<GalleryImage> paramArrayList)
  {
    paramString2 = JSON_PARSER.parse(paramString2).getAsJsonArray().iterator();
    while (paramString2.hasNext())
    {
      GalleryImage localGalleryImage = convertObjectToImage(paramString1, ((JsonElement)paramString2.next()).getAsJsonObject());
      if (localGalleryImage != null) {
        paramArrayList.add(localGalleryImage);
      }
    }
  }
  
  public static GalleryImage convertObjectToImage(String paramString, JsonObject paramJsonObject)
  {
    String str2 = paramJsonObject.get("preview_url").toString();
    String str1 = paramJsonObject.get("file_url").toString();
    paramString = new GalleryImage(str2.substring(1, str2.length() - 1), paramString + "/post/show/" + paramJsonObject.get("id").toString(), null, paramJsonObject.get("jpeg_width").getAsInt(), paramJsonObject.get("jpeg_height").getAsInt());
    paramString.setImageUrl(str1.substring(1, str1.length() - 1));
    paramString.setTags(paramJsonObject.get("tags").getAsString().split(" "));
    return paramString;
  }
  
  public GalleryImage convertImagePageToGalleryImage(Document paramDocument)
  {
    GalleryImage localGalleryImage = new GalleryImage();
    localGalleryImage.setImageUrl(paramDocument.getElementById("image").attr("src"));
    localGalleryImage.setLinkUrl(getHostUrl());
    return null;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    String str = fetchUrl(getGalleryUrl(paramInt));
    convertArrayToGalleryImages(this.baseUrl, str, localArrayList);
    return localArrayList;
  }
  
  public String getGalleryUrl(int paramInt)
    throws Exception
  {
    Object localObject2 = this.apiRoot;
    Uri localUri = Uri.parse(getHostUrl());
    Object localObject1 = localObject2;
    if (paramInt != 0) {
      localObject1 = (String)localObject2 + "&page=" + (paramInt + 1);
    }
    String str = (String)localObject1 + "&tags=" + Uri.encode(GalleryViewer.getBlacklist());
    localObject2 = getQueryParameter(localUri, getHostUrl(), "tags");
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = localObject2;
      if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevel)) {
        localObject1 = "rating:safe ";
      }
    }
    localObject2 = str;
    if (localObject1 != null)
    {
      localObject2 = str;
      if (!((String)localObject1).equals("")) {
        localObject2 = str + Uri.encode(new StringBuilder().append(" ").append((String)localObject1).toString());
      }
    }
    return (String)localObject2;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/konachan/GenericBooruProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */