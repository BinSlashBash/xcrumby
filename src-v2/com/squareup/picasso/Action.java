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
import com.squareup.picasso.Request;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

abstract class Action<T> {
    boolean cancelled;
    final Drawable errorDrawable;
    final int errorResId;
    final String key;
    final boolean noFade;
    final Picasso picasso;
    final Request request;
    final boolean skipCache;
    final WeakReference<T> target;
    boolean willReplay;

    Action(Picasso picasso, T t2, Request request, boolean bl2, boolean bl3, int n2, Drawable drawable2, String string2) {
        this.picasso = picasso;
        this.request = request;
        this.target = new RequestWeakReference<Object>(this, t2, picasso.referenceQueue);
        this.skipCache = bl2;
        this.noFade = bl3;
        this.errorResId = n2;
        this.errorDrawable = drawable2;
        this.key = string2;
    }

    void cancel() {
        this.cancelled = true;
    }

    abstract void complete(Bitmap var1, Picasso.LoadedFrom var2);

    abstract void error(Exception var1);

    String getKey() {
        return this.key;
    }

    Picasso getPicasso() {
        return this.picasso;
    }

    Request getRequest() {
        return this.request;
    }

    T getTarget() {
        return this.target.get();
    }

    boolean isCancelled() {
        return this.cancelled;
    }

    boolean willReplay() {
        return this.willReplay;
    }

    static class RequestWeakReference<T>
    extends WeakReference<T> {
        final Action action;

        public RequestWeakReference(Action action, T t2, ReferenceQueue<? super T> referenceQueue) {
            super(t2, referenceQueue);
            this.action = action;
        }
    }

}

