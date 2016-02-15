/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.crumby.lib.fragment.adapter;

import android.graphics.Bitmap;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Transformation;

public class CropTransformation
implements Transformation {
    private GalleryImage image;
    private int index;

    public CropTransformation(GalleryImage galleryImage, int n2) {
        this.image = galleryImage;
        this.index = n2;
    }

    @Override
    public String key() {
        return "crop" + this.index;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        Bitmap bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)this.image.getOffset(), (int)0, (int)this.image.getWidth(), (int)this.image.getHeight());
        if (bitmap2 != bitmap) {
            bitmap.recycle();
        }
        return bitmap2;
    }
}

