package com.crumby.impl.konachan;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class GenericBooruImageProducer extends SingleGalleryProducer {
    private final String apiUrl;
    private final String baseUrl;
    private final String regexUrl;

    public GenericBooruImageProducer(String apiUrl, String baseUrl, String regexUrl) {
        this.regexUrl = regexUrl;
        this.apiUrl = apiUrl;
        this.baseUrl = baseUrl;
    }

    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
        GenericBooruProducer.convertArrayToGalleryImages(this.baseUrl, GalleryProducer.fetchUrl(this.apiUrl + "tags=id:" + GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl())), galleryImages);
    }
}
