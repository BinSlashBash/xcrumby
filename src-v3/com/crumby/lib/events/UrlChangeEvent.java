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

    public UrlChangeEvent(String url) {
        this.url = url;
    }

    public UrlChangeEvent(String url, Bundle bundle) {
        this.url = url;
        this.bundle = bundle;
    }

    public UrlChangeEvent(String url, GalleryProducer producer) {
        this.url = url;
        this.producer = producer;
    }

    public UrlChangeEvent(String baseUrl, boolean clearPrevious) {
        this.url = baseUrl;
        this.clearPrevious = clearPrevious;
    }
}
