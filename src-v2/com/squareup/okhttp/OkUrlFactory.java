/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.huc.CacheAdapter;
import com.squareup.okhttp.internal.huc.HttpURLConnectionImpl;
import com.squareup.okhttp.internal.huc.HttpsURLConnectionImpl;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ResponseCache;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public final class OkUrlFactory
implements URLStreamHandlerFactory,
Cloneable {
    private final OkHttpClient client;

    public OkUrlFactory(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public OkUrlFactory clone() {
        return new OkUrlFactory(this.client.clone());
    }

    @Override
    public URLStreamHandler createURLStreamHandler(final String string2) {
        if (!string2.equals("http") && !string2.equals("https")) {
            return null;
        }
        return new URLStreamHandler(){

            @Override
            protected int getDefaultPort() {
                if (string2.equals("http")) {
                    return 80;
                }
                if (string2.equals("https")) {
                    return 443;
                }
                throw new AssertionError();
            }

            @Override
            protected URLConnection openConnection(URL uRL) {
                return OkUrlFactory.this.open(uRL);
            }

            @Override
            protected URLConnection openConnection(URL uRL, Proxy proxy) {
                return OkUrlFactory.this.open(uRL, proxy);
            }
        };
    }

    ResponseCache getResponseCache() {
        InternalCache internalCache = this.client.internalCache();
        if (internalCache instanceof CacheAdapter) {
            return ((CacheAdapter)internalCache).getDelegate();
        }
        return null;
    }

    public HttpURLConnection open(URL uRL) {
        return this.open(uRL, this.client.getProxy());
    }

    HttpURLConnection open(URL uRL, Proxy proxy) {
        String string2 = uRL.getProtocol();
        OkHttpClient okHttpClient = this.client.copyWithDefaults();
        okHttpClient.setProxy(proxy);
        if (string2.equals("http")) {
            return new HttpURLConnectionImpl(uRL, okHttpClient);
        }
        if (string2.equals("https")) {
            return new HttpsURLConnectionImpl(uRL, okHttpClient);
        }
        throw new IllegalArgumentException("Unexpected protocol: " + string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    OkUrlFactory setResponseCache(ResponseCache object) {
        OkHttpClient okHttpClient = this.client;
        object = object != null ? new CacheAdapter((ResponseCache)object) : null;
        okHttpClient.setInternalCache((InternalCache)object);
        return this;
    }

}

