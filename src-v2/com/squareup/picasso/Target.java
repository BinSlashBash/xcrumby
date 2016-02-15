/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 */
package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso;

public interface Target {
    public void onBitmapFailed(Drawable var1);

    public void onBitmapLoaded(Bitmap var1, Picasso.LoadedFrom var2);

    public void onPrepareLoad(Drawable var1);
}

