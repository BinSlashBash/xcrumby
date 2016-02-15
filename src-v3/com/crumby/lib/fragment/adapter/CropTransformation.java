package com.crumby.lib.fragment.adapter;

import android.graphics.Bitmap;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Transformation;

public class CropTransformation implements Transformation {
    private GalleryImage image;
    private int index;

    public CropTransformation(GalleryImage image, int index) {
        this.image = image;
        this.index = index;
    }

    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = Bitmap.createBitmap(source, this.image.getOffset(), 0, this.image.getWidth(), this.image.getHeight());
        if (bitmap != source) {
            source.recycle();
        }
        return bitmap;
    }

    public String key() {
        return "crop" + this.index;
    }
}
