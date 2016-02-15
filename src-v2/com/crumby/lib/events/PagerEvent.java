/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class PagerEvent {
    public GalleryImage image;
    public GalleryProducer producer;

    public PagerEvent(GalleryImage galleryImage, GalleryProducer galleryProducer) {
        this.image = galleryImage;
        this.producer = galleryProducer;
    }
}

