/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.imgur;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ImgurProducer
extends GalleryProducer {
    public static final String API_CREDENTIALS = "Client-ID ac562464e4b98f8";
    public static final String API_URL = "https://imgur-apiv3.p.mashape.com/3/";
    public static final String MAIN_GALLERY = "gallery/hot/viral/";
    public static final String MASHAPE_AUTH = "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP";
    public static final String THUMBNAIL_HOST = "http://i.imgur.com/";

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        JsonArray jsonArray = this.getImagesFromJsonElement(JSON_PARSER.parse(this.fetchImgurUrl(n2)));
        if (jsonArray == null || jsonArray.isJsonNull()) {
            throw new IOException("Imgur API not available to parse");
        }
        return this.parseImgurImages(jsonArray);
    }

    protected String fetchImgurUrl(int n2) throws Exception {
        return ImgurProducer.fetchUrl(this.getApiUrl(n2), "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
    }

    protected String getApiUrl(int n2) {
        String string2 = ImgurProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "q");
        if (string2 != null && !string2.equals("")) {
            return "https://imgur-apiv3.p.mashape.com/3/gallery/search/" + n2 + "?q=" + Uri.encode((String)string2);
        }
        return "https://imgur-apiv3.p.mashape.com/3/gallery/hot/viral/" + n2 + ".json";
    }

    protected final JsonArray getImagesFromJsonElement(JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return this.getImagesFromJsonObject(jsonElement.getAsJsonObject());
    }

    protected JsonArray getImagesFromJsonObject(JsonObject jsonObject) {
        return jsonObject.get("data").getAsJsonArray();
    }

    protected String getImgurLink(boolean bl2, String string2, String string3) {
        if (bl2) {
            return string3;
        }
        return "http://imgur.com/gallery/" + string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected GalleryImage parseImgurImage(JsonElement jsonElement) {
        boolean bl2;
        if ((jsonElement = jsonElement.getAsJsonObject()).get("is_album") != null) {
            bl2 = jsonElement.get("is_album").getAsBoolean();
            do {
                return this.parseImgurImage((JsonObject)jsonElement, bl2);
                break;
            } while (true);
        }
        bl2 = false;
        return this.parseImgurImage((JsonObject)jsonElement, bl2);
    }

    protected GalleryImage parseImgurImage(JsonObject object, boolean bl2) {
        Object object2;
        String string2 = JNH.getAttributeString((JsonObject)object, "title");
        String string3 = object.get("link").getAsString();
        int n2 = 0;
        int n3 = 0;
        String string4 = object.get("id").getAsString();
        Object object3 = "";
        boolean bl3 = false;
        int n4 = n2;
        int n5 = n3;
        boolean bl4 = bl3;
        if (bl2) {
            object2 = JNH.getAttributeString((JsonObject)object, "cover");
            n4 = n2;
            n5 = n3;
            bl4 = bl3;
            object3 = object2;
            if (object2 != null) {
                n4 = JNH.getAttributeInt((JsonObject)object, "cover_width");
                n5 = JNH.getAttributeInt((JsonObject)object, "cover_height");
                bl4 = true;
                object3 = object2;
            }
        }
        if (!bl4) {
            object3 = string4;
            n4 = JNH.getAttributeInt((JsonObject)object, "width");
            n5 = JNH.getAttributeInt((JsonObject)object, "height");
        }
        object2 = new GalleryImage("http://i.imgur.com/" + (String)object3 + "m.jpg", this.getImgurLink(bl2, string4, string3), string2, n4, n5);
        object2.setLinksToGallery(bl2);
        object2.setSmallThumbnailUrl("http://i.imgur.com/" + (String)object3 + "t.jpg");
        string4 = "jpg";
        string2 = JNH.getAttributeString((JsonObject)object, "type");
        object = string4;
        if (string2 != null) {
            object = string4;
            if (string2.equals("image/gif")) {
                object = "gif";
            }
        }
        object2.setImageUrl("http://i.imgur.com/" + (String)object3 + "." + (String)object);
        return object2;
    }

    protected final ArrayList<GalleryImage> parseImgurImages(JsonArray object) {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(this.parseImgurImage((JsonElement)object.next()));
        }
        return arrayList;
    }

    @Override
    public void postInitialize() {
        this.getHostImage().setTitle("Imgur - ");
    }

    protected JsonArray sliceImages(JsonArray jsonArray, int n2) throws IOException {
        JsonArray jsonArray2 = new JsonArray();
        int n3 = n2 * 200;
        if (n2 <= 0) {
            n3 = 0;
        }
        n2 = n3;
        n3 = Math.min(n3 + 200, jsonArray.size());
        while (n2 < n3) {
            jsonArray2.add(jsonArray.get(n2));
            ++n2;
        }
        return jsonArray2;
    }
}

