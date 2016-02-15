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
import com.squareup.picasso.Action;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

class GetAction
extends Action<Void> {
    GetAction(Picasso picasso, Request request, boolean bl2, String string2) {
        super(picasso, null, request, bl2, false, 0, null, string2);
    }

    @Override
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
    }

    @Override
    public void error(Exception exception) {
    }
}

