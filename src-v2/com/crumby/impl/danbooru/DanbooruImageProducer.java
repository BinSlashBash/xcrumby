/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.danbooru;

import com.crumby.impl.danbooru.DanbooruGalleryProducer;
import com.crumby.impl.danbooru.DanbooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class DanbooruImageProducer
extends DanbooruGalleryProducer {
    private boolean done;

    private ArrayList<GalleryImage> fetchSingularImage() throws IOException {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        if (this.done) {
            return arrayList;
        }
        this.done = true;
        arrayList.add(this.getGalleryImage(JSON_PARSER.parse(new JsonReader(new StringReader(DanbooruImageProducer.fetchUrl(this.getHostUrl() + ".json")))).getAsJsonObject()));
        return arrayList;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        return this.fetchSingularImage();
    }

    @Override
    protected boolean fetchMetadata() throws IOException {
        return false;
    }

    @Override
    protected GalleryImage getGalleryImage(JsonObject jsonObject) {
        GalleryImage galleryImage = super.getGalleryImage(jsonObject);
        galleryImage.setLinkUrl(null);
        galleryImage.setImageUrl(this.deriveUrl(jsonObject.get("file_url"), ""));
        galleryImage.setId(jsonObject.get("id").getAsString());
        return galleryImage;
    }

    @Override
    public String getHostUrl() {
        String string2 = GalleryViewerFragment.matchIdFromUrl(DanbooruImageFragment.REGEX_URL, super.getHostUrl());
        return "http://danbooru.donmai.us/posts/" + string2;
    }

    public void postIntialize() {
        this.apiUrl = this.convertUrlToApi(this.getHostUrl());
    }
}

