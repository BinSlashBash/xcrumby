package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class PagerEvent {
    public GalleryImage image;
    public GalleryProducer producer;

    public PagerEvent(GalleryImage image, GalleryProducer producer) {
        this.image = image;
        this.producer = producer;
    }
}
