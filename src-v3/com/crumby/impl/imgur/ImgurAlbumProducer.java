package com.crumby.impl.imgur;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class ImgurAlbumProducer extends ImgurProducer {
    public static final String API_ALBUM_URL = "https://imgur-apiv3.p.mashape.com/3/album/";
    String albumId;
    JsonArray imgurImages;

    public void postInitialize() {
        this.albumId = ImgurAlbumFragment.matchIdFromUrl(getHostUrl());
    }

    protected JsonArray getImagesFromJsonObject(JsonObject jsonObject) {
        return jsonObject.get("data").getAsJsonObject().get("images").getAsJsonArray();
    }

    protected String getApiUrl(int pageNumber) {
        return API_ALBUM_URL + this.albumId;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        initImgurImages(pageNumber);
        return parseImgurImages(sliceImages(this.imgurImages, pageNumber));
    }

    private boolean initImgurImages(int pageNumber) throws Exception {
        if (this.imgurImages != null) {
            return false;
        }
        JsonObject imgurObject = JSON_PARSER.parse(fetchImgurUrl(pageNumber)).getAsJsonObject();
        JsonObject data = imgurObject.get("data").getAsJsonObject();
        String title = JNH.getAttributeString(data, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
        String description = JNH.getAttributeString(data, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        if (getHostImage().getTitle() == null || getHostImage().getTitle().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            setGalleryMetadata(title, description);
        }
        this.imgurImages = getImagesFromJsonObject(imgurObject);
        return true;
    }
}
