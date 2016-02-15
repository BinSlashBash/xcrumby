package com.crumby.impl.imgur;

import com.crumby.lib.GalleryImage;
import com.google.gson.JsonObject;

public class ImgurUserGalleryProducer extends ImgurProducer {
    private String userId;

    public void postInitialize() {
        this.userId = ImgurUserGalleryFragment.matchIdFromUrl(getHostUrl());
    }

    protected GalleryImage parseImgurImage(JsonObject image, boolean isAlbum) {
        return super.parseImgurImage(image, true);
    }

    protected String getImgurLink(boolean isAlbum, String id, String link) {
        return link;
    }

    protected String getApiUrl(int pageNumber) {
        return "https://imgur-apiv3.p.mashape.com/3/account/" + this.userId + "/albums/" + pageNumber;
    }
}
