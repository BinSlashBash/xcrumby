package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtImageProducer
  extends SingleGalleryProducer
{
  protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> paramArrayList)
    throws Exception
  {
    Document localDocument = Jsoup.parse(legacyfetchUrl(getHostUrl()));
    Object localObject = localDocument.getElementsByTag("h1");
    int i = 0;
    if (((Elements)localObject).size() > 1) {
      i = 1;
    }
    localObject = localDocument.getElementsByTag("h1").get(i).getElementsByTag("a").text();
    String str = localDocument.getElementsByClass("dev-description").first().getElementsByClass("text").html();
    GalleryImage localGalleryImage = new GalleryImage(null, null, (String)localObject);
    localObject = JSON_PARSER.parse(GalleryProducer.legacyfetchUrl("http://backend.deviantart.com/oembed?url=" + Uri.encode(getHostUrl()))).getAsJsonObject();
    localGalleryImage.setImageUrl(((JsonObject)localObject).get("url").getAsString());
    localGalleryImage.setWidth(((JsonObject)localObject).get("width").getAsInt());
    localGalleryImage.setHeight(((JsonObject)localObject).get("height").getAsInt());
    localObject = str;
    if (str == null) {
      localObject = "";
    }
    localGalleryImage.setAttributes(new DeviantArtAttributes(localDocument, (String)localObject));
    paramArrayList.add(localGalleryImage);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */