/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.widget.ImageView
 */
package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.Action;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoDrawable;
import com.squareup.picasso.Request;
import java.lang.ref.WeakReference;

class ImageViewAction
extends Action<ImageView> {
    Callback callback;

    ImageViewAction(Picasso picasso, ImageView imageView, Request request, boolean bl2, boolean bl3, int n2, Drawable drawable2, String string2, Callback callback) {
        super(picasso, imageView, request, bl2, bl3, n2, drawable2, string2);
        this.callback = callback;
    }

    @Override
    void cancel() {
        super.cancel();
        if (this.callback != null) {
            this.callback = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError((Object)String.format("Attempted to complete action with no result!\n%s", this));
        }
        ImageView imageView = (ImageView)this.target.get();
        if (imageView == null) {
            return;
        }
        Context context = this.picasso.context;
        boolean bl2 = this.picasso.indicatorsEnabled;
        PicassoDrawable.setBitmap(imageView, context, bitmap, loadedFrom, this.noFade, bl2);
        if (this.callback == null) return;
        this.callback.onSuccess();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void error(Exception exception) {
        ImageView imageView = (ImageView)this.target.get();
        if (imageView == null) {
            return;
        }
        if (this.errorResId != 0) {
            imageView.setImageResource(this.errorResId);
        } else if (this.errorDrawable != null) {
            imageView.setImageDrawable(this.errorDrawable);
        }
        if (this.callback == null) return;
        this.callback.onError(exception);
    }
}

