/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;
import java.util.List;

public class CachedProducer
extends SingleGalleryProducer {
    private boolean cached;
    private ArrayList<GalleryImage> images;

    public CachedProducer(String object) {
        object = new GalleryImage(null, (String)object, null);
        object.setLinksToGallery(true);
        object.setReload(true);
        this.images = new ArrayList();
        this.images.add((GalleryImage)object);
    }

    public CachedProducer(ArrayList<GalleryImage> arrayList) {
        this.images = arrayList;
        this.cached = true;
    }

    @Override
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) {
        arrayList.size();
    }

    @Override
    public boolean initialize() {
        boolean bl2 = false;
        if (super.initialize()) {
            this.addProducedImagesToCache(this.images);
            this.shareAndSetCurrentImageFocus(0);
            bl2 = true;
        }
        return bl2;
    }

    @Override
    protected void postInitialize() {
        if (this.cached) {
            this.addImagesToConsumers(this.images);
            this.notifyHandlerDataSetsChanged();
        }
    }
}

