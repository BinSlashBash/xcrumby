/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.imgur;

import com.crumby.impl.imgur.ImgurAlbumFragment;
import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class ImgurAlbumProducer
extends ImgurProducer {
    public static final String API_ALBUM_URL = "https://imgur-apiv3.p.mashape.com/3/album/";
    String albumId;
    JsonArray imgurImages;

    private boolean initImgurImages(int n2) throws Exception {
        if (this.imgurImages == null) {
            Object object = this.fetchImgurUrl(n2);
            object = JSON_PARSER.parse((String)object).getAsJsonObject();
            Object object2 = object.get("data").getAsJsonObject();
            String string2 = JNH.getAttributeString((JsonObject)object2, "title");
            object2 = JNH.getAttributeString((JsonObject)object2, "description");
            if (this.getHostImage().getTitle() == null || this.getHostImage().getTitle().equals("")) {
                this.setGalleryMetadata(string2, (String)object2);
            }
            this.imgurImages = this.getImagesFromJsonObject((JsonObject)object);
            return true;
        }
        return false;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        this.initImgurImages(n2);
        return this.parseImgurImages(this.sliceImages(this.imgurImages, n2));
    }

    @Override
    protected String getApiUrl(int n2) {
        return "https://imgur-apiv3.p.mashape.com/3/album/" + this.albumId;
    }

    @Override
    protected JsonArray getImagesFromJsonObject(JsonObject jsonObject) {
        return jsonObject.get("data").getAsJsonObject().get("images").getAsJsonArray();
    }

    @Override
    public void postInitialize() {
        this.albumId = ImgurAlbumFragment.matchIdFromUrl(this.getHostUrl());
    }
}

