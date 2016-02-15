/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.lib.ExtraAttributes;
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
extends SingleGalleryProducer {
    @Override
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
        Document document = Jsoup.parse(DeviantArtImageProducer.legacyfetchUrl(this.getHostUrl()));
        Object object = document.getElementsByTag("h1");
        int n2 = 0;
        if (object.size() > 1) {
            n2 = 1;
        }
        object = document.getElementsByTag("h1").get(n2).getElementsByTag("a").text();
        String string2 = document.getElementsByClass("dev-description").first().getElementsByClass("text").html();
        GalleryImage galleryImage = new GalleryImage(null, null, (String)object);
        object = JSON_PARSER.parse(GalleryProducer.legacyfetchUrl("http://backend.deviantart.com/oembed?url=" + Uri.encode((String)this.getHostUrl()))).getAsJsonObject();
        galleryImage.setImageUrl(object.get("url").getAsString());
        galleryImage.setWidth(object.get("width").getAsInt());
        galleryImage.setHeight(object.get("height").getAsInt());
        object = string2;
        if (string2 == null) {
            object = "";
        }
        galleryImage.setAttributes(new DeviantArtAttributes(document, (String)object));
        arrayList.add(galleryImage);
    }
}

