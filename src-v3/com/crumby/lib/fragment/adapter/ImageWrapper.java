package com.crumby.lib.fragment.adapter;

import com.crumby.lib.GalleryImage;

public class ImageWrapper {
    GalleryImage image;

    public ImageWrapper(GalleryImage image) {
        this.image = image;
    }

    public GalleryImage getImage() {
        return this.image;
    }

    protected void setImage(GalleryImage image) {
        this.image = image;
    }
}
