package com.crumby.impl.danbooru;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class DanbooruImageProducer extends DanbooruGalleryProducer {
    private boolean done;

    public void postIntialize() {
        this.apiUrl = convertUrlToApi(getHostUrl());
    }

    protected GalleryImage getGalleryImage(JsonObject galleryImageObj) {
        GalleryImage galleryImage = super.getGalleryImage(galleryImageObj);
        galleryImage.setLinkUrl(null);
        galleryImage.setImageUrl(deriveUrl(galleryImageObj.get("file_url"), UnsupportedUrlFragment.DISPLAY_NAME));
        galleryImage.setId(galleryImageObj.get("id").getAsString());
        return galleryImage;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        return fetchSingularImage();
    }

    protected boolean fetchMetadata() throws IOException {
        return false;
    }

    public String getHostUrl() {
        return DanbooruImageFragment.BASE_URL + GalleryViewerFragment.matchIdFromUrl(DanbooruImageFragment.REGEX_URL, super.getHostUrl());
    }

    private ArrayList<GalleryImage> fetchSingularImage() throws IOException {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        if (!this.done) {
            this.done = true;
            galleryImages.add(getGalleryImage(JSON_PARSER.parse(new JsonReader(new StringReader(GalleryProducer.fetchUrl(getHostUrl() + ".json")))).getAsJsonObject()));
        }
        return galleryImages;
    }
}
