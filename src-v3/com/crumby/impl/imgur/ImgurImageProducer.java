package com.crumby.impl.imgur;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.JsonElement;
import java.util.ArrayList;

public class ImgurImageProducer extends ImgurProducer {
    private boolean fetched;
    String imageId;

    public void postInitialize() {
        this.imageId = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, getHostUrl());
    }

    protected String getApiUrl(int pageNumber) {
        return "https://imgur-apiv3.p.mashape.com/3/image/" + this.imageId;
    }

    private boolean imageIdIsAlbum() {
        try {
            if (new ObjectMapper().readTree(GalleryProducer.fetchUrl(ImgurAlbumProducer.API_ALBUM_URL + this.imageId, ImgurProducer.API_CREDENTIALS, ImgurProducer.MASHAPE_AUTH)).get("status").asInt() != WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    protected GalleryImage parseImgurImage(JsonElement element) {
        GalleryImage image = super.parseImgurImage(element);
        String link = element.getAsJsonObject().get("link").getAsString();
        return image;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        if (this.fetched) {
            return galleryImages;
        }
        this.fetched = true;
        if (imageIdIsAlbum()) {
            setSilentRedirectUrl(ImgurAlbumFragment.BASE_URL + this.imageId);
            return null;
        }
        galleryImages.add(parseImgurImage(JSON_PARSER.parse(fetchImgurUrl(pageNumber)).getAsJsonObject().get("data")));
        return galleryImages;
    }
}
