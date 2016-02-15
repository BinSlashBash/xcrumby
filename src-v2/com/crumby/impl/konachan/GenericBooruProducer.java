/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
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
extends GalleryProducer {
    private String apiRoot;
    private String baseUrl;
    private Class topLevel;

    public GenericBooruProducer(String string2, String string3, Class class_) {
        this.apiRoot = string2;
        this.baseUrl = string3;
        this.topLevel = class_;
    }

    public static void convertArrayToGalleryImages(String string2, String object, ArrayList<GalleryImage> arrayList) {
        object = JSON_PARSER.parse((String)object).getAsJsonArray().iterator();
        while (object.hasNext()) {
            GalleryImage galleryImage = GenericBooruProducer.convertObjectToImage(string2, ((JsonElement)object.next()).getAsJsonObject());
            if (galleryImage == null) continue;
            arrayList.add(galleryImage);
        }
    }

    public static GalleryImage convertObjectToImage(String object, JsonObject jsonObject) {
        String string2 = jsonObject.get("preview_url").toString();
        String string3 = jsonObject.get("file_url").toString();
        object = new GalleryImage(string2.substring(1, string2.length() - 1), (String)object + "/post/show/" + jsonObject.get("id").toString(), null, jsonObject.get("jpeg_width").getAsInt(), jsonObject.get("jpeg_height").getAsInt());
        object.setImageUrl(string3.substring(1, string3.length() - 1));
        object.setTags(jsonObject.get("tags").getAsString().split(" "));
        return object;
    }

    public GalleryImage convertImagePageToGalleryImage(Document document) {
        GalleryImage galleryImage = new GalleryImage();
        galleryImage.setImageUrl(document.getElementById("image").attr("src"));
        galleryImage.setLinkUrl(this.getHostUrl());
        return null;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        String string2 = GenericBooruProducer.fetchUrl(this.getGalleryUrl(n2));
        GenericBooruProducer.convertArrayToGalleryImages(this.baseUrl, string2, arrayList);
        return arrayList;
    }

    public String getGalleryUrl(int n2) throws Exception {
        String string2 = this.apiRoot;
        Uri uri = Uri.parse((String)this.getHostUrl());
        String string3 = string2;
        if (n2 != 0) {
            string3 = string2 + "&page=" + (n2 + 1);
        }
        String string4 = string3 + "&tags=" + Uri.encode((String)GalleryViewer.getBlacklist());
        string3 = string2 = GenericBooruProducer.getQueryParameter(uri, this.getHostUrl(), "tags");
        if (string2 == null) {
            string3 = string2;
            if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevel)) {
                string3 = "rating:safe ";
            }
        }
        string2 = string4;
        if (string3 != null) {
            string2 = string4;
            if (!string3.equals("")) {
                string2 = string4 + Uri.encode((String)new StringBuilder().append(" ").append(string3).toString());
            }
        }
        return string2;
    }
}

