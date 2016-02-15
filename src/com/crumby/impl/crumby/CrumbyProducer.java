package com.crumby.impl.crumby;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.impl.imgur.ImgurImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class CrumbyProducer
  extends GalleryParser
{
  public static final String ADULT_MODE = "&Adult=%27Off%27";
  private static final String BING_API_BASE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/";
  private static final String BING_API_URL_IMAGE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?";
  private static final String BING_API_URL_WEB = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?";
  public static final String SNAPITO = "http://api.snapito.com/web/sapuk-393f25c4-9be26dbc-df62be89-b79e-8414a8a5-e/sc/?fast=true&delay=0&freshness=-1&url=";
  private static HashMap<String, LayerDrawable> overlays;
  private static int searches = 0;
  final String accountKey = "NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU";
  private Context context;
  private boolean done;
  private String hostUrl;
  Set<String> processedResults = new HashSet();
  private JsonArray results;
  ArrayList<String> sites;
  
  private ArrayList<GalleryImage> bingSearch(int paramInt)
    throws Exception
  {
    if (this.done) {
      return new ArrayList();
    }
    this.done = true;
    searches += 1;
    if (searches < 7) {
      return getBingImages(paramInt, true);
    }
    throw new Exception("Sorry; you have exceeded the maximum 'Search All Galleries' allowed for this session.");
  }
  
  private String getArbitraryThumbnailUrl(String paramString)
    throws IOException
  {
    paramString = fetchUrl("https://api.embed.ly/1/oembed?key=4e8e330da2f34353aa47727946038d8f&url=" + URLEncoder.encode(paramString));
    return JSON_PARSER.parse(paramString).getAsJsonObject().get("thumbnail_url").getAsString();
  }
  
  private ArrayList<GalleryImage> getBingImages(int paramInt, boolean paramBoolean)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = getBingJson(paramInt, paramBoolean);
    JsonArray localJsonArray = new JsonParser().parse(str1).getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
    if (paramBoolean) {}
    JsonObject localJsonObject;
    String str2;
    FragmentIndex localFragmentIndex;
    for (str1 = "SourceUrl";; str1 = "Url")
    {
      paramInt *= 50;
      for (;;)
      {
        if ((localArrayList.size() >= 50) || (paramInt >= localJsonArray.size())) {
          break label582;
        }
        localJsonObject = localJsonArray.get(paramInt).getAsJsonObject();
        str2 = localJsonObject.get(str1).getAsString();
        localFragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(str2);
        if ((localFragmentIndex != null) && (!localFragmentIndex.isIndexOf(UnsupportedUrlFragment.class))) {
          break;
        }
        paramInt += 1;
      }
    }
    GalleryImage localGalleryImage = new GalleryImage(null, str2, localJsonObject.get("Title").getAsString(), 150, 150);
    int i = localFragmentIndex.getBreadcrumbIcon();
    int j = localFragmentIndex.getRootBreadcrumbIcon();
    CrDb.d("crumby producer", j + " " + localFragmentIndex.getFragmentClassName() + " " + i);
    Object localObject1;
    if ((i != j) && (i != -1))
    {
      String str3 = j + " " + i;
      Object localObject2 = (LayerDrawable)overlays.get(str3);
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject2 = this.context.getResources().getDrawable(j);
        Drawable localDrawable = this.context.getResources().getDrawable(i);
        localObject1 = (LayerDrawable)this.context.getResources().getDrawable(2130837688);
        ((LayerDrawable)localObject1).setDrawableByLayerId(2131493169, (Drawable)localObject2);
        ((LayerDrawable)localObject1).setDrawableByLayerId(2131493170, localDrawable);
        overlays.put(str3, localObject1);
      }
      localGalleryImage.setIcon((LayerDrawable)localObject1);
      label368:
      if (!paramBoolean) {
        break label452;
      }
      localGalleryImage.setThumbnailUrl(localJsonObject.get("Thumbnail").getAsJsonObject().get("MediaUrl").getAsString());
      localGalleryImage.setImageUrl(localJsonObject.get("MediaUrl").getAsString());
    }
    for (;;)
    {
      localGalleryImage.setSubtitle(localJsonObject.get("DisplayUrl").getAsString());
      localGalleryImage.setReload(true);
      localArrayList.add(localGalleryImage);
      break;
      localGalleryImage.setIcon(j);
      break label368;
      label452:
      localGalleryImage.setThumbnailUrl(getSnapshot(str2));
      if (localFragmentIndex.isIndexOf(ImgurImageFragment.class))
      {
        localObject1 = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, str2);
        localGalleryImage.setImageUrl("http://i.imgur.com/" + (String)localObject1 + ".jpg");
        localGalleryImage.setThumbnailUrl("http://i.imgur.com/" + (String)localObject1 + "m.jpg");
        localGalleryImage.setSmallThumbnailUrl("http://i.imgur.com/" + (String)localObject1 + "t.jpg");
      }
    }
    label582:
    return localArrayList;
  }
  
  private String getBingJson(int paramInt, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {}
    for (String str = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?";; str = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?")
    {
      str = str + "Query=%27" + URLEncoder.encode(getHostImage().getLinkUrl(), "utf-8") + getWhitelist() + "%27&$format=JSON" + "&Adult=%27Off%27" + "&$skip=" + paramInt * 50;
      byte[] arrayOfByte = Base64.encodeBase64("NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU:NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU".getBytes());
      return fetchUrl(str, "Basic " + new String(arrayOfByte));
    }
  }
  
  public static String getSnapshot(String paramString)
  {
    return "http://api.webthumbnail.org?width=400&height=400&screen=1024&url=" + paramString;
  }
  
  private static ArrayList<GalleryImage> interleave(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Object localObject = new ArrayList(paramArrayList1.size() + paramArrayList2.size());
    int j = 0;
    int i = 0;
    while ((j < paramArrayList1.size()) || (i < paramArrayList2.size()))
    {
      if (j < paramArrayList1.size()) {
        ((ArrayList)localObject).add(paramArrayList1.get(j));
      }
      if (i < paramArrayList2.size()) {
        ((ArrayList)localObject).add(paramArrayList2.get(i));
      }
      j += 1;
      i += 1;
    }
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      GalleryImage localGalleryImage = (GalleryImage)((Iterator)localObject).next();
      if ((localLinkedHashMap.get(localGalleryImage.getLinkUrl()) == null) || (((GalleryImage)localLinkedHashMap.get(localGalleryImage.getLinkUrl())).getImageUrl() == null)) {
        localLinkedHashMap.put(localGalleryImage.getLinkUrl(), localGalleryImage);
      }
    }
    paramArrayList1.clear();
    paramArrayList2.clear();
    return new ArrayList(localLinkedHashMap.values());
  }
  
  public URL convertUrlToApi(String paramString)
  {
    return null;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    return bingSearch(paramInt);
  }
  
  public String getHostUrl()
  {
    return this.hostUrl;
  }
  
  public String getWhitelist()
  {
    Object localObject1 = "(";
    Object localObject2;
    if (this.sites == null)
    {
      this.sites = new ArrayList();
      localObject2 = FragmentRouter.INSTANCE.getAllIndexSettings().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        IndexSetting localIndexSetting = (IndexSetting)((Iterator)localObject2).next();
        IndexAttributeValue localIndexAttributeValue = localIndexSetting.getAttributeValue("show_in_search");
        if ((localIndexAttributeValue != null) && (((Boolean)localIndexAttributeValue.getObject()).booleanValue())) {
          this.sites.add(localIndexSetting.getBaseUrl().replace("http://", "").replace("https://", ""));
        }
      }
    }
    int i = 0;
    while (i < this.sites.size())
    {
      localObject2 = (String)localObject1 + "site%3A" + (String)this.sites.get(i);
      localObject1 = localObject2;
      if (i != this.sites.size() - 1) {
        localObject1 = (String)localObject2 + "+OR+";
      }
      i += 1;
    }
    return (String)localObject1 + ")";
  }
  
  public void postInitialize()
  {
    String str = getHostUrl();
    try
    {
      new URL(str);
      this.pageArg = "first=";
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new BasicNameValuePair("q", getHostUrl()));
        try
        {
          this.hostUrl = new URI("http://bing.com/search?" + URLEncodedUtils.format(localArrayList, "UTF-8")).toString();
        }
        catch (URISyntaxException localURISyntaxException)
        {
          Analytics.INSTANCE.newException(localURISyntaxException);
        }
      }
    }
  }
  
  protected void preInitialize()
  {
    if (overlays == null) {
      overlays = new HashMap();
    }
  }
  
  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/crumby/CrumbyProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */