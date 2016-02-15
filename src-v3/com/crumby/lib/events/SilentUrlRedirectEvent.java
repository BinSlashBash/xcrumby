package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;

public class SilentUrlRedirectEvent {
    public final GalleryImage keyImage;
    public final String silentRedirectUrl;

    public SilentUrlRedirectEvent(String silentRedirectUrl, GalleryImage hostImage) {
        this.silentRedirectUrl = silentRedirectUrl;
        this.keyImage = hostImage;
    }
}
