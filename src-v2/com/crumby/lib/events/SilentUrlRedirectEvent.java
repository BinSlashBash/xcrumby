/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.events;

import com.crumby.lib.GalleryImage;

public class SilentUrlRedirectEvent {
    public final GalleryImage keyImage;
    public final String silentRedirectUrl;

    public SilentUrlRedirectEvent(String string2, GalleryImage galleryImage) {
        this.silentRedirectUrl = string2;
        this.keyImage = galleryImage;
    }
}

