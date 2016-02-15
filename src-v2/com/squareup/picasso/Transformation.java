/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Transformation {
    public String key();

    public Bitmap transform(Bitmap var1);
}

