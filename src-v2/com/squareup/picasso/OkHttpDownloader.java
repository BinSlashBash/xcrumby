/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpDownloader
implements Downloader {
    static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
    static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
    private final OkUrlFactory urlFactory;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(Context context, long l2) {
        this(Utils.createDefaultCacheDir(context), l2);
    }

    public OkHttpDownloader(OkHttpClient okHttpClient) {
        this.urlFactory = new OkUrlFactory(okHttpClient);
    }

    public OkHttpDownloader(File file) {
        this(file, Utils.calculateDiskCacheSize(file));
    }

    public OkHttpDownloader(File file, long l2) {
        this(new OkHttpClient());
        try {
            this.urlFactory.client().setCache(new Cache(file, l2));
            return;
        }
        catch (IOException var1_2) {
            return;
        }
    }

    protected OkHttpClient getClient() {
        return this.urlFactory.client();
    }

    @Override
    public Downloader.Response load(Uri object, boolean bl2) throws IOException {
        String string2;
        int n2;
        HttpURLConnection httpURLConnection = this.openConnection((Uri)object);
        httpURLConnection.setUseCaches(true);
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        if (bl2) {
            httpURLConnection.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
        }
        if ((n2 = httpURLConnection.getResponseCode()) >= 300) {
            httpURLConnection.disconnect();
            throw new Downloader.ResponseException("" + n2 + " " + httpURLConnection.getResponseMessage());
        }
        object = string2 = httpURLConnection.getHeaderField("OkHttp-Response-Source");
        if (string2 == null) {
            object = httpURLConnection.getHeaderField("X-Android-Response-Source");
        }
        long l2 = httpURLConnection.getHeaderFieldInt("Content-Length", -1);
        bl2 = Utils.parseResponseSourceHeader((String)object);
        return new Downloader.Response(httpURLConnection.getInputStream(), bl2, l2);
    }

    protected HttpURLConnection openConnection(Uri object) throws IOException {
        object = this.urlFactory.open(new URL(object.toString()));
        object.setConnectTimeout(15000);
        object.setReadTimeout(20000);
        return object;
    }
}

