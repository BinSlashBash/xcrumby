/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.ArrayList;

public abstract class SingleGalleryProducer
extends GalleryProducer {
    private boolean fetched;

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        if (!this.fetched) {
            this.fetchGalleryImagesOnce(arrayList);
        }
        this.fetched = true;
        return arrayList;
    }

    protected abstract void fetchGalleryImagesOnce(ArrayList<GalleryImage> var1) throws Exception;
}

