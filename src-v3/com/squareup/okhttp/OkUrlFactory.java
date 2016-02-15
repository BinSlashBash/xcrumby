package com.squareup.okhttp;

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

public final class OkUrlFactory implements URLStreamHandlerFactory, Cloneable {
    private final OkHttpClient client;

    /* renamed from: com.squareup.okhttp.OkUrlFactory.1 */
    class C05861 extends URLStreamHandler {
        final /* synthetic */ String val$protocol;

        C05861(String str) {
            this.val$protocol = str;
        }

        protected URLConnection openConnection(URL url) {
            return OkUrlFactory.this.open(url);
        }

        protected URLConnection openConnection(URL url, Proxy proxy) {
            return OkUrlFactory.this.open(url, proxy);
        }

        protected int getDefaultPort() {
            if (this.val$protocol.equals("http")) {
                return 80;
            }
            if (this.val$protocol.equals("https")) {
                return 443;
            }
            throw new AssertionError();
        }
    }

    public OkUrlFactory(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient client() {
        return this.client;
    }

    OkUrlFactory setResponseCache(ResponseCache responseCache) {
        this.client.setInternalCache(responseCache != null ? new CacheAdapter(responseCache) : null);
        return this;
    }

    ResponseCache getResponseCache() {
        InternalCache cache = this.client.internalCache();
        return cache instanceof CacheAdapter ? ((CacheAdapter) cache).getDelegate() : null;
    }

    public OkUrlFactory clone() {
        return new OkUrlFactory(this.client.clone());
    }

    public HttpURLConnection open(URL url) {
        return open(url, this.client.getProxy());
    }

    HttpURLConnection open(URL url, Proxy proxy) {
        String protocol = url.getProtocol();
        OkHttpClient copy = this.client.copyWithDefaults();
        copy.setProxy(proxy);
        if (protocol.equals("http")) {
            return new HttpURLConnectionImpl(url, copy);
        }
        if (protocol.equals("https")) {
            return new HttpsURLConnectionImpl(url, copy);
        }
        throw new IllegalArgumentException("Unexpected protocol: " + protocol);
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("http") || protocol.equals("https")) {
            return new C05861(protocol);
        }
        return null;
    }
}
