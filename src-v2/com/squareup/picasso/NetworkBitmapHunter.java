/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.net.NetworkInfo
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.NetworkInfo;
import android.net.Uri;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MarkableInputStream;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import com.squareup.picasso.Utils;
import java.io.IOException;
import java.io.InputStream;

class NetworkBitmapHunter
extends BitmapHunter {
    static final int DEFAULT_RETRY_COUNT = 2;
    private static final int MARKER = 524288;
    private final Downloader downloader;
    int retryCount;

    public NetworkBitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        super(picasso, dispatcher, cache, stats, action);
        this.downloader = downloader;
        this.retryCount = 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Bitmap decodeStream(InputStream object, Request request) throws IOException {
        MarkableInputStream markableInputStream = new MarkableInputStream((InputStream)object);
        long l2 = markableInputStream.savePosition(524288);
        object = NetworkBitmapHunter.createBitmapOptions(request);
        boolean bl2 = NetworkBitmapHunter.requiresInSampleSize((BitmapFactory.Options)object);
        boolean bl3 = Utils.isWebPFile(markableInputStream);
        markableInputStream.reset(l2);
        if (bl3) {
            markableInputStream = (MarkableInputStream)Utils.toByteArray(markableInputStream);
            if (!bl2) return BitmapFactory.decodeByteArray((byte[])markableInputStream, (int)0, (int)markableInputStream.length, (BitmapFactory.Options)object);
            BitmapFactory.decodeByteArray((byte[])markableInputStream, (int)0, (int)markableInputStream.length, (BitmapFactory.Options)object);
            NetworkBitmapHunter.calculateInSampleSize(request.targetWidth, request.targetHeight, (BitmapFactory.Options)object);
            return BitmapFactory.decodeByteArray((byte[])markableInputStream, (int)0, (int)markableInputStream.length, (BitmapFactory.Options)object);
        }
        if (bl2) {
            BitmapFactory.decodeStream((InputStream)markableInputStream, (Rect)null, (BitmapFactory.Options)object);
            NetworkBitmapHunter.calculateInSampleSize(request.targetWidth, request.targetHeight, (BitmapFactory.Options)object);
            markableInputStream.reset(l2);
        }
        object = request = BitmapFactory.decodeStream((InputStream)markableInputStream, (Rect)null, (BitmapFactory.Options)object);
        if (request != null) return object;
        throw new IOException("Failed to decode stream.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    Bitmap decode(Request request) throws IOException {
        Bitmap bitmap;
        boolean bl2 = this.retryCount == 0;
        Downloader.Response response = this.downloader.load(request.uri, bl2);
        if (response == null) {
            return null;
        }
        Object object = response.cached ? Picasso.LoadedFrom.DISK : Picasso.LoadedFrom.NETWORK;
        this.loadedFrom = object;
        object = bitmap = response.getBitmap();
        if (bitmap != null) return object;
        object = response.getInputStream();
        if (object == null) {
            return null;
        }
        if (response.getContentLength() == 0) {
            Utils.closeQuietly((InputStream)object);
            throw new IOException("Received response with 0 content-length header.");
        }
        if (this.loadedFrom == Picasso.LoadedFrom.NETWORK && response.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(response.getContentLength());
        }
        try {
            request = this.decodeStream((InputStream)object, request);
            return request;
        }
        finally {
            Utils.closeQuietly((InputStream)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    boolean shouldRetry(boolean bl2, NetworkInfo networkInfo) {
        if (this.retryCount <= 0) return false;
        boolean bl3 = true;
        if (!bl3) {
            return false;
        }
        --this.retryCount;
        if (networkInfo == null) return true;
        if (!networkInfo.isConnected()) return false;
        return true;
    }

    @Override
    boolean supportsReplay() {
        return true;
    }
}

