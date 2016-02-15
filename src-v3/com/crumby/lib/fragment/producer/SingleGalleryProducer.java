package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.util.ArrayList;

public abstract class SingleGalleryProducer extends GalleryProducer {
    private boolean fetched;

    protected abstract void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception;

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        if (!this.fetched) {
            fetchGalleryImagesOnce(galleryImages);
        }
        this.fetched = true;
        return galleryImages;
    }
}
