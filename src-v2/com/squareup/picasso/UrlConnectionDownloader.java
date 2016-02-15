/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.net.http.HttpResponseCache
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Build;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionDownloader
implements Downloader {
    static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    static volatile Object cache;
    private static final Object lock;
    private final Context context;

    static {
        lock = new Object();
    }

    public UrlConnectionDownloader(Context context) {
        this.context = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void installCacheIfNeeded(Context context) {
        block5 : {
            if (cache != null) return;
            try {
                Object object = lock;
                // MONITORENTER : object
                if (cache != null) break block5;
            }
            catch (IOException var0_1) {
                // empty catch block
            }
            cache = ResponseCacheIcs.install(context);
        }
        // MONITOREXIT : object
        return;
    }

    @Override
    public Downloader.Response load(Uri object, boolean bl2) throws IOException {
        int n2;
        if (Build.VERSION.SDK_INT >= 14) {
            UrlConnectionDownloader.installCacheIfNeeded(this.context);
        }
        object = this.openConnection((Uri)object);
        object.setUseCaches(true);
        if (bl2) {
            object.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
        }
        if ((n2 = object.getResponseCode()) >= 300) {
            object.disconnect();
            throw new Downloader.ResponseException("" + n2 + " " + object.getResponseMessage());
        }
        long l2 = object.getHeaderFieldInt("Content-Length", -1);
        bl2 = Utils.parseResponseSourceHeader(object.getHeaderField("X-Android-Response-Source"));
        return new Downloader.Response(object.getInputStream(), bl2, l2);
    }

    protected HttpURLConnection openConnection(Uri object) throws IOException {
        object = (HttpURLConnection)new URL(object.toString()).openConnection();
        object.setConnectTimeout(15000);
        object.setReadTimeout(20000);
        return object;
    }

    private static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        static Object install(Context context) throws IOException {
            HttpResponseCache httpResponseCache;
            File file = Utils.createDefaultCacheDir(context);
            context = httpResponseCache = HttpResponseCache.getInstalled();
            if (httpResponseCache == null) {
                context = HttpResponseCache.install((File)file, (long)Utils.calculateDiskCacheSize(file));
            }
            return context;
        }
    }

}

