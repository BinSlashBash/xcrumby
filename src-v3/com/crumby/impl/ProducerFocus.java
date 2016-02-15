package com.crumby.impl;

import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;

public class ProducerFocus {
    int lastImageFocus;
    GalleryProducer producer;

    public ProducerFocus(String url) {
        this.producer = FragmentRouter.INSTANCE.getGalleryFragmentInstance(url).getProducer();
        this.lastImageFocus = -1;
    }

    public ProducerFocus(GalleryProducer producer, int currentImageFocus) {
        this.producer = producer;
        this.lastImageFocus = currentImageFocus;
    }

    public GalleryProducer getProducer() {
        return this.producer;
    }

    public boolean reselect() {
        boolean shouldReselect = (this.lastImageFocus == this.producer.getCurrentImageFocus() || this.producer.getCurrentImageFocus() == -1) ? false : true;
        this.lastImageFocus = this.producer.getCurrentImageFocus();
        return shouldReselect;
    }
}
