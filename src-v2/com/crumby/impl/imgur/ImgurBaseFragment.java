/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;

public abstract class ImgurBaseFragment
extends GalleryGridFragment {
    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }
}

