/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl;

import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;

public class ProducerFocus {
    int lastImageFocus;
    GalleryProducer producer;

    public ProducerFocus(GalleryProducer galleryProducer, int n2) {
        this.producer = galleryProducer;
        this.lastImageFocus = n2;
    }

    public ProducerFocus(String string2) {
        this.producer = FragmentRouter.INSTANCE.getGalleryFragmentInstance(string2).getProducer();
        this.lastImageFocus = -1;
    }

    public GalleryProducer getProducer() {
        return this.producer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean reselect() {
        boolean bl2 = this.lastImageFocus != this.producer.getCurrentImageFocus() && this.producer.getCurrentImageFocus() != -1;
        this.lastImageFocus = this.producer.getCurrentImageFocus();
        return bl2;
    }
}

