/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.imgur;

import com.crumby.impl.imgur.ImgurImageFragment;
import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class ImgurImageProducer
extends ImgurProducer {
    private boolean fetched;
    String imageId;

    private boolean imageIdIsAlbum() {
        boolean bl2 = false;
        try {
            String string2 = ImgurImageProducer.fetchUrl("https://imgur-apiv3.p.mashape.com/3/album/" + this.imageId, "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
            int n2 = new ObjectMapper().readTree(string2).get("status").asInt();
            if (n2 != 404) {
                bl2 = true;
            }
            return bl2;
        }
        catch (Exception var3_3) {
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList;
        ArrayList<GalleryImage> arrayList2 = arrayList = new ArrayList<GalleryImage>();
        if (this.fetched) return arrayList2;
        this.fetched = true;
        if (this.imageIdIsAlbum()) {
            this.setSilentRedirectUrl("http://imgur.com/a/" + this.imageId);
            return null;
        }
        arrayList.add(this.parseImgurImage(JSON_PARSER.parse(this.fetchImgurUrl(n2)).getAsJsonObject().get("data")));
        return arrayList;
    }

    @Override
    protected String getApiUrl(int n2) {
        return "https://imgur-apiv3.p.mashape.com/3/image/" + this.imageId;
    }

    @Override
    protected GalleryImage parseImgurImage(JsonElement jsonElement) {
        GalleryImage galleryImage = super.parseImgurImage(jsonElement);
        jsonElement.getAsJsonObject().get("link").getAsString();
        return galleryImage;
    }

    @Override
    public void postInitialize() {
        this.imageId = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, this.getHostUrl());
    }
}

