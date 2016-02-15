/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crumby.lib.events;

import android.os.Bundle;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class UrlChangeEvent {
    public Bundle bundle;
    public boolean clearPrevious;
    public int position;
    public GalleryProducer producer;
    public boolean silent;
    public String url;

    public UrlChangeEvent(String string2) {
        this.url = string2;
    }

    public UrlChangeEvent(String string2, Bundle bundle) {
        this.url = string2;
        this.bundle = bundle;
    }

    public UrlChangeEvent(String string2, GalleryProducer galleryProducer) {
        this.url = string2;
        this.producer = galleryProducer;
    }

    public UrlChangeEvent(String string2, boolean bl2) {
        this.url = string2;
        this.clearPrevious = bl2;
    }
}

