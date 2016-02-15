/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Handler
 *  android.widget.ImageView
 *  android.widget.RemoteViews
 */
package com.squareup.picasso;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.DeferredRequestCreator;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.FetchAction;
import com.squareup.picasso.GetAction;
import com.squareup.picasso.ImageViewAction;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoDrawable;
import com.squareup.picasso.RemoteViewsAction;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import com.squareup.picasso.Target;
import com.squareup.picasso.TargetAction;
import com.squareup.picasso.Transformation;
import com.squareup.picasso.Utils;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCreator {
    private static int nextId = 0;
    private final Request.Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean skipMemoryCache;

    RequestCreator(Picasso picasso, Uri uri, int n2) {
        if (picasso.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso;
        this.data = new Request.Builder(uri, n2);
    }

    private Request createRequest(long l2) {
        Request request;
        int n2 = RequestCreator.getRequestId();
        Request request2 = this.data.build();
        request2.id = n2;
        request2.started = l2;
        boolean bl2 = this.picasso.loggingEnabled;
        if (bl2) {
            Utils.log("Main", "created", request2.plainId(), request2.toString());
        }
        if ((request = this.picasso.transformRequest(request2)) != request2) {
            request.id = n2;
            request.started = l2;
            if (bl2) {
                Utils.log("Main", "changed", request.logId(), "into " + request);
            }
        }
        return request;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getRequestId() {
        if (Utils.isMain()) {
            int n2 = nextId;
            nextId = n2 + 1;
            return n2;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicInteger atomicInteger = new AtomicInteger();
        Picasso.HANDLER.post(new Runnable(){

            @Override
            public void run() {
                atomicInteger.set(RequestCreator.getRequestId());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
            do {
                return atomicInteger.get();
                break;
            } while (true);
        }
        catch (InterruptedException var2_2) {
            Picasso.HANDLER.post(new Runnable(){

                @Override
                public void run() {
                    throw new RuntimeException(var2_2);
                }
            });
            return atomicInteger.get();
        }
    }

    private void performRemoteViewInto(RemoteViewsAction remoteViewsAction) {
        Bitmap bitmap;
        if (!this.skipMemoryCache && (bitmap = this.picasso.quickMemoryCacheCheck(remoteViewsAction.getKey())) != null) {
            remoteViewsAction.complete(bitmap, Picasso.LoadedFrom.MEMORY);
            return;
        }
        if (this.placeholderResId != 0) {
            remoteViewsAction.setImageResource(this.placeholderResId);
        }
        this.picasso.enqueueAndSubmit(remoteViewsAction);
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator config(Bitmap.Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator error(int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        }
        if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorResId = n2;
        return this;
    }

    public RequestCreator error(Drawable drawable2) {
        if (drawable2 == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        }
        if (this.errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorDrawable = drawable2;
        return this;
    }

    public void fetch() {
        long l2 = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        }
        if (this.data.hasImage()) {
            Object object = this.createRequest(l2);
            String string2 = Utils.createKey((Request)object, new StringBuilder());
            object = new FetchAction(this.picasso, (Request)object, this.skipMemoryCache, string2);
            this.picasso.submit((Action)object);
        }
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    public Bitmap get() throws IOException {
        long l2 = System.nanoTime();
        Utils.checkNotMain();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with get.");
        }
        if (!this.data.hasImage()) {
            return null;
        }
        Object object = this.createRequest(l2);
        String string2 = Utils.createKey((Request)object, new StringBuilder());
        object = new GetAction(this.picasso, (Request)object, this.skipMemoryCache, string2);
        return BitmapHunter.forRequest(this.picasso.context, this.picasso, this.picasso.dispatcher, this.picasso.cache, this.picasso.stats, (Action)object, this.picasso.dispatcher.downloader).hunt();
    }

    public void into(ImageView imageView) {
        this.into(imageView, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void into(ImageView object, Callback callback) {
        Bitmap bitmap;
        long l2 = System.nanoTime();
        Utils.checkMain();
        if (object == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (!this.data.hasImage()) {
            this.picasso.cancelRequest((ImageView)object);
            PicassoDrawable.setPlaceholder((ImageView)object, this.placeholderResId, this.placeholderDrawable);
            return;
        }
        if (this.deferred) {
            if (this.data.hasSize()) {
                throw new IllegalStateException("Fit cannot be used with resize.");
            }
            int n2 = object.getWidth();
            int n3 = object.getHeight();
            if (n2 == 0 || n3 == 0) {
                PicassoDrawable.setPlaceholder((ImageView)object, this.placeholderResId, this.placeholderDrawable);
                this.picasso.defer((ImageView)object, new DeferredRequestCreator(this, (ImageView)object, callback));
                return;
            }
            this.data.resize(n2, n3);
        }
        Request request = this.createRequest(l2);
        String string2 = Utils.createKey(request);
        if (!this.skipMemoryCache && (bitmap = this.picasso.quickMemoryCacheCheck(string2)) != null) {
            this.picasso.cancelRequest((ImageView)object);
            PicassoDrawable.setBitmap((ImageView)object, this.picasso.context, bitmap, Picasso.LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
            if (this.picasso.loggingEnabled) {
                Utils.log("Main", "completed", request.plainId(), "from " + (Object)((Object)Picasso.LoadedFrom.MEMORY));
            }
            if (callback == null) return;
            {
                callback.onSuccess();
                return;
            }
        }
        PicassoDrawable.setPlaceholder((ImageView)object, this.placeholderResId, this.placeholderDrawable);
        object = new ImageViewAction(this.picasso, (ImageView)object, request, this.skipMemoryCache, this.noFade, this.errorResId, this.errorDrawable, string2, callback);
        this.picasso.enqueueAndSubmit((Action)object);
    }

    public void into(RemoteViews remoteViews, int n2, int n3, Notification notification) {
        long l2 = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        }
        if (notification == null) {
            throw new IllegalArgumentException("Notification must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        }
        if (this.placeholderDrawable != null || this.errorDrawable != null) {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
        Request request = this.createRequest(l2);
        String string2 = Utils.createKey(request);
        this.performRemoteViewInto(new RemoteViewsAction.NotificationAction(this.picasso, request, remoteViews, n2, n3, notification, this.skipMemoryCache, this.errorResId, string2));
    }

    public void into(RemoteViews remoteViews, int n2, int[] arrn) {
        long l2 = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        }
        if (arrn == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        }
        if (this.placeholderDrawable != null || this.errorDrawable != null) {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
        Request request = this.createRequest(l2);
        String string2 = Utils.createKey(request);
        this.performRemoteViewInto(new RemoteViewsAction.AppWidgetAction(this.picasso, request, remoteViews, n2, arrn, this.skipMemoryCache, this.errorResId, string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void into(Target object) {
        Bitmap bitmap;
        long l2 = System.nanoTime();
        Utils.checkMain();
        if (object == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        }
        Drawable drawable2 = this.placeholderResId != 0 ? this.picasso.context.getResources().getDrawable(this.placeholderResId) : this.placeholderDrawable;
        if (!this.data.hasImage()) {
            this.picasso.cancelRequest((Target)object);
            object.onPrepareLoad(drawable2);
            return;
        }
        Request request = this.createRequest(l2);
        String string2 = Utils.createKey(request);
        if (!this.skipMemoryCache && (bitmap = this.picasso.quickMemoryCacheCheck(string2)) != null) {
            this.picasso.cancelRequest((Target)object);
            object.onBitmapLoaded(bitmap, Picasso.LoadedFrom.MEMORY);
            return;
        }
        object.onPrepareLoad(drawable2);
        object = new TargetAction(this.picasso, (Target)object, request, this.skipMemoryCache, this.errorResId, this.errorDrawable, string2);
        this.picasso.enqueueAndSubmit((Action)object);
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public RequestCreator placeholder(int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.placeholderResId = n2;
        return this;
    }

    public RequestCreator placeholder(Drawable drawable2) {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.placeholderDrawable = drawable2;
        return this;
    }

    public RequestCreator resize(int n2, int n3) {
        this.data.resize(n2, n3);
        return this;
    }

    public RequestCreator resizeDimen(int n2, int n3) {
        Resources resources = this.picasso.context.getResources();
        return this.resize(resources.getDimensionPixelSize(n2), resources.getDimensionPixelSize(n3));
    }

    public RequestCreator rotate(float f2) {
        this.data.rotate(f2);
        return this;
    }

    public RequestCreator rotate(float f2, float f3, float f4) {
        this.data.rotate(f2, f3, f4);
        return this;
    }

    public RequestCreator skipMemoryCache() {
        this.skipMemoryCache = true;
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

}

