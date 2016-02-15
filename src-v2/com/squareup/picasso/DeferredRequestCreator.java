/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.widget.ImageView
 */
package com.squareup.picasso;

import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.RequestCreator;
import java.lang.ref.WeakReference;

class DeferredRequestCreator
implements ViewTreeObserver.OnPreDrawListener {
    Callback callback;
    final RequestCreator creator;
    final WeakReference<ImageView> target;

    DeferredRequestCreator(RequestCreator requestCreator, ImageView imageView, Callback callback) {
        this.creator = requestCreator;
        this.target = new WeakReference<ImageView>(imageView);
        this.callback = callback;
        imageView.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    void cancel() {
        this.callback = null;
        ImageView imageView = this.target.get();
        if (imageView == null || !(imageView = imageView.getViewTreeObserver()).isAlive()) {
            return;
        }
        imageView.removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onPreDraw() {
        ImageView imageView = this.target.get();
        if (imageView == null) {
            return true;
        }
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) return true;
        int n2 = imageView.getWidth();
        int n3 = imageView.getHeight();
        if (n2 <= 0) return true;
        if (n3 <= 0) return true;
        viewTreeObserver.removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
        this.creator.unfit().resize(n2, n3).into(imageView, this.callback);
        return true;
    }
}

