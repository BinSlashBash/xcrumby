package com.crumby.lib.fragment;

import android.view.View;
import com.crumby.lib.GalleryImage;

public interface GalleryClickHandler {
    void goToImage(View view, GalleryImage galleryImage, int i);
}
