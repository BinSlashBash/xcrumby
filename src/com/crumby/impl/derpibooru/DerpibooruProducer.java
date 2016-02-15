package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DerpibooruProducer
  extends GalleryProducer
{
  private static final String DEFAULT_FILTER = "f178bcd16361641586000000";
  private static final String EVERYTHING_FILTER = "6758f0d16361640e71480000";
  private static boolean HAS_SIGNED_IN;
  public static boolean nsfwMode;
  private static String sessionAuth;
  
  public static GalleryImage convertDerpJsonToDerpImage(JsonObject paramJsonObject)
  {
    Object localObject4 = paramJsonObject.get("id_number");
    Object localObject5 = paramJsonObject.get("description");
    if (paramJsonObject.get("representations") == null) {
      return null;
    }
    Object localObject2 = paramJsonObject.get("representations").getAsJsonObject();
    Object localObject3 = ((JsonObject)localObject2).get("full");
    Object localObject1 = ((JsonObject)localObject2).get("thumb");
    localObject2 = ((JsonObject)localObject2).get("thumb_small");
    localObject4 = ((JsonElement)localObject4).toString();
    localObject5 = ((JsonElement)localObject5).toString();
    localObject3 = ((JsonElement)localObject3).toString();
    localObject1 = ((JsonElement)localObject1).toString();
    ((JsonElement)localObject2).toString();
    localObject1 = new GalleryImage("https:" + ((String)localObject1).substring(1, ((String)localObject1).length() - 1), "https://derpibooru.org/" + (String)localObject4, null, paramJsonObject.get("width").getAsInt(), paramJsonObject.get("height").getAsInt());
    ((GalleryImage)localObject1).setId(paramJsonObject.get("id").getAsString());
    ((GalleryImage)localObject1).setImageUrl("https:" + ((String)localObject3).substring(1, ((String)localObject3).length() - 1));
    ((GalleryImage)localObject1).setDescription(((String)localObject5).substring(1, ((String)localObject5).length() - 1));
    ((GalleryImage)localObject1).setDescription(((GalleryImage)localObject1).getDescription().replace("\\r\\n", "\n").replace("\\\"", "\""));
    try
    {
      localObject2 = paramJsonObject.get("source_url").getAsString();
      ((GalleryImage)localObject1).setDescription(((GalleryImage)localObject1).getDescription() + "\n" + (String)localObject2);
      for (;;)
      {
        try
        {
          localObject3 = paramJsonObject.get("comments").getAsJsonArray();
          localObject2 = new ArrayList();
          localObject3 = ((JsonArray)localObject3).iterator();
          if (((Iterator)localObject3).hasNext())
          {
            localObject4 = ((JsonElement)((Iterator)localObject3).next()).getAsJsonObject();
            ((ArrayList)localObject2).add(new ImageComment(((JsonObject)localObject4).get("author").getAsString(), ((JsonObject)localObject4).get("body").getAsString(), null));
          }
          else
          {
            ((GalleryImage)localObject1).setComments(localNullPointerException);
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          ((GalleryImage)localObject1).setReload(true);
          ((GalleryImage)localObject1).setTags(DerpibooruImageFragment.getTags(paramJsonObject));
          return (GalleryImage)localObject1;
        }
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public static String getAuthKey(String paramString)
    throws Exception
  {
    paramString = Jsoup.parse(paramString).getElementsByAttributeValue("name", "csrf-token");
    if ((paramString != null) && (paramString.first().tagName().equals("meta"))) {
      return paramString.first().attr("content");
    }
    return null;
  }
  
  public static void makeNSFW(boolean paramBoolean)
    throws Exception
  {
    if (nsfwMode == paramBoolean) {
      return;
    }
    CrDb.d("derpibooru producer", "make nsfw: " + paramBoolean);
    nsfwMode = paramBoolean;
    if (sessionAuth == null) {
      sessionAuth = getAuthKey(fetchUrl("https://derpibooru.org"));
    }
    if (paramBoolean) {}
    for (Object localObject = "6758f0d16361640e71480000";; localObject = "f178bcd16361641586000000")
    {
      localObject = (HttpURLConnection)new URL("https://derpibooru.org/filters/select?id=" + (String)localObject).openConnection();
      ((HttpURLConnection)localObject).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      ((HttpURLConnection)localObject).setRequestProperty("Accept", "*/*");
      ((HttpURLConnection)localObject).setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
      ((HttpURLConnection)localObject).setDoInput(true);
      ((HttpURLConnection)localObject).setDoOutput(true);
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(((HttpURLConnection)localObject).getOutputStream());
      localOutputStreamWriter.write("_method=patch&authenticity_token=" + URLEncoder.encode("", "UTF-8"));
      localOutputStreamWriter.close();
      ((HttpURLConnection)localObject).connect();
      if (((HttpURLConnection)localObject).getResponseCode() == 200) {
        break;
      }
      throw new Exception("Derpibooru server could not change the image filter!");
    }
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = getDerpImages(paramInt).iterator();
    while (localIterator.hasNext())
    {
      GalleryImage localGalleryImage = convertDerpJsonToDerpImage(((JsonElement)localIterator.next()).getAsJsonObject());
      if (localGalleryImage != null) {
        localArrayList.add(localGalleryImage);
      }
    }
    return localArrayList;
  }
  
  protected JsonArray getDerpImages(int paramInt)
    throws Exception
  {
    Object localObject1 = getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "sbq");
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = localObject1;
      if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DerpibooruFragment.class)) {
        localObject2 = "safe";
      }
    }
    if ((localObject2 != null) || (!GalleryViewer.BLACK_LISTED_TAGS.isEmpty()))
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = "";
      }
      localObject2 = localObject1;
      if (!GalleryViewer.BLACK_LISTED_TAGS.isEmpty())
      {
        localObject2 = localObject1;
        if (!((String)localObject1).equals("")) {
          localObject2 = (String)localObject1 + ",";
        }
        localObject2 = (String)localObject2 + GalleryViewer.getBlacklist().substring(1).replace(" ", ",").replace("_", " ");
      }
      localObject1 = "https://derpibooru.org" + "/search.json?q=" + Uri.encode((String)localObject2);
    }
    for (localObject2 = "search";; localObject2 = "images")
    {
      Object localObject3 = localObject1;
      if (paramInt != 0) {
        localObject3 = (String)localObject1 + "&page=" + (paramInt + 1);
      }
      return getDerpImages((String)localObject3, (String)localObject2);
      localObject1 = "https://derpibooru.org" + "/images.json?";
    }
  }
  
  protected JsonArray getDerpImages(String paramString1, String paramString2)
    throws Exception
  {
    paramString1 = JSON_PARSER.parse(fetchUrl(paramString1));
    try
    {
      paramString2 = paramString1.getAsJsonObject().get(paramString2);
      paramString1 = paramString2;
    }
    catch (Exception paramString2)
    {
      for (;;) {}
    }
    return paramString1.getAsJsonArray();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */