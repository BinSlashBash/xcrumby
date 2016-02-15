/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 */
package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Action;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Target;

final class TargetAction
extends Action<Target> {
    TargetAction(Picasso picasso, Target target, Request request, boolean bl2, int n2, Drawable drawable2, String string2) {
        super(picasso, target, request, bl2, false, n2, drawable2, string2);
    }

    @Override
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError((Object)String.format("Attempted to complete action with no result!\n%s", this));
        }
        Target target = (Target)this.getTarget();
        if (target != null) {
            target.onBitmapLoaded(bitmap, loadedFrom);
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    void error(Exception object) {
        object = (Target)this.getTarget();
        if (object == null) return;
        if (this.errorResId != 0) {
            object.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
            return;
        }
        object.onBitmapFailed(this.errorDrawable);
    }
}

