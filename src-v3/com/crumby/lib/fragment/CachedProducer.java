package com.crumby.lib.fragment;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class CachedProducer extends SingleGalleryProducer {
    private boolean cached;
    private ArrayList<GalleryImage> images;

    public CachedProducer(ArrayList<GalleryImage> images) {
        this.images = images;
        this.cached = true;
    }

    public CachedProducer(String urlString) {
        GalleryImage defaultImage = new GalleryImage(null, urlString, null);
        defaultImage.setLinksToGallery(true);
        defaultImage.setReload(true);
        this.images = new ArrayList();
        this.images.add(defaultImage);
    }

    public boolean initialize() {
        if (!super.initialize()) {
            return false;
        }
        addProducedImagesToCache(this.images);
        shareAndSetCurrentImageFocus(0);
        return true;
    }

    protected void postInitialize() {
        if (this.cached) {
            addImagesToConsumers(this.images);
            notifyHandlerDataSetsChanged();
        }
    }

    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) {
        galleryImages.size();
    }
}
