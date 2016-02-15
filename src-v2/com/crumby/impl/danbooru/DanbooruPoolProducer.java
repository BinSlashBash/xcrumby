/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.danbooru;

import android.net.Uri;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DanbooruPoolProducer
extends GalleryParser {
    public static final String API_URL = "http://danbooru.donmai.us/pools.json?";

    static GalleryImage getGalleryImage(JsonObject object) {
        GalleryImage galleryImage = new GalleryImage(null, "http://danbooru.donmai.us/pools/" + object.get("id").getAsString(), object.get("name").getAsString().replace("_", " "));
        object = object.get("description").getAsString();
        galleryImage.setThumbnailUrl(CrumbyProducer.getSnapshot(galleryImage.getLinkUrl()));
        galleryImage.setDescription((String)object);
        galleryImage.setLinksToGallery(true);
        galleryImage.setIcon(0);
        return galleryImage;
    }

    private void handleDeferredThumbnail(GalleryImage galleryImage, String string2) {
    }

    @Override
    public URL convertUrlToApi(String object) {
        Uri uri = Uri.parse((String)object.toString());
        String string2 = "http://danbooru.donmai.us/pools.json?";
        if (DanbooruPoolProducer.getQueryParameter(uri, (String)object, "search[name_matches]") != null) {
            string2 = "http://danbooru.donmai.us/pools.json?" + "&search[name_matches]=" + DanbooruPoolProducer.getQueryParameter(uri, (String)object, "search[name_matches]");
        }
        try {
            object = new URL(string2);
            return object;
        }
        catch (MalformedURLException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Object object = DanbooruPoolProducer.fetchUrl(this.apiUrl.toString() + this.getSearchMark() + "&page=" + n2);
        object = new JsonParser().parse((String)object).getAsJsonArray();
        for (int i2 = 0; i2 < object.size(); ++i2) {
            GalleryImage galleryImage = DanbooruPoolProducer.getGalleryImage(object.get(i2).getAsJsonObject());
            galleryImage.setPage(n2);
            arrayList.add(galleryImage);
        }
        return arrayList;
    }

    @Override
    public void postInitialize() {
        this.setStartPage(1);
    }
}

