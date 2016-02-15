package com.crumby.impl.danbooru;

import android.net.Uri;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DanbooruPoolProducer extends GalleryParser {
    public static final String API_URL = "http://danbooru.donmai.us/pools.json?";

    public void postInitialize() {
        setStartPage(1);
    }

    public URL convertUrlToApi(String url) {
        Uri uri = Uri.parse(url.toString());
        String apiUrlString = API_URL;
        if (GalleryProducer.getQueryParameter(uri, url, "search[name_matches]") != null) {
            apiUrlString = apiUrlString + "&search[name_matches]=" + GalleryProducer.getQueryParameter(uri, url, "search[name_matches]");
        }
        try {
            return new URL(apiUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleDeferredThumbnail(GalleryImage image, String deferredThumbnailUrl) {
    }

    static GalleryImage getGalleryImage(JsonObject galleryImageJson) {
        GalleryImage galleryImage = new GalleryImage(null, "http://danbooru.donmai.us/pools/" + galleryImageJson.get("id").getAsString(), galleryImageJson.get("name").getAsString().replace("_", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        String description = galleryImageJson.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).getAsString();
        galleryImage.setThumbnailUrl(CrumbyProducer.getSnapshot(galleryImage.getLinkUrl()));
        galleryImage.setDescription(description);
        galleryImage.setLinksToGallery(true);
        galleryImage.setIcon(0);
        return galleryImage;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        JsonArray arr = new JsonParser().parse(GalleryProducer.fetchUrl(this.apiUrl.toString() + getSearchMark() + "&page=" + pageNumber)).getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            GalleryImage galleryImage = getGalleryImage(arr.get(i).getAsJsonObject());
            galleryImage.setPage(pageNumber);
            galleryImages.add(galleryImage);
        }
        return galleryImages;
    }
}
