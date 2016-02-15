/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.adapter;

import com.crumby.lib.GalleryImage;

public class ImageWrapper {
    GalleryImage image;

    public ImageWrapper() {
    }

    public ImageWrapper(GalleryImage galleryImage) {
        this.image = galleryImage;
    }

    public GalleryImage getImage() {
        return this.image;
    }

    protected void setImage(GalleryImage galleryImage) {
        this.image = galleryImage;
    }
}

