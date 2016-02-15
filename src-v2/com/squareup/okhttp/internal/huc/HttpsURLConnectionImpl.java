/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.huc.DelegatingHttpsURLConnection;
import com.squareup.okhttp.internal.huc.HttpURLConnectionImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public final class HttpsURLConnectionImpl
extends DelegatingHttpsURLConnection {
    private final HttpURLConnectionImpl delegate;

    public HttpsURLConnectionImpl(HttpURLConnectionImpl httpURLConnectionImpl) {
        super(httpURLConnectionImpl);
        this.delegate = httpURLConnectionImpl;
    }

    public HttpsURLConnectionImpl(URL uRL, OkHttpClient okHttpClient) {
        this(new HttpURLConnectionImpl(uRL, okHttpClient));
    }

    @Override
    public long getContentLengthLong() {
        return this.delegate.getContentLengthLong();
    }

    @Override
    public long getHeaderFieldLong(String string2, long l2) {
        return this.delegate.getHeaderFieldLong(string2, l2);
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return this.delegate.client.getHostnameVerifier();
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return this.delegate.client.getSslSocketFactory();
    }

    @Override
    protected Handshake handshake() {
        if (this.delegate.httpEngine == null) {
            throw new IllegalStateException("Connection has not yet been established");
        }
        if (this.delegate.httpEngine.hasResponse()) {
            return this.delegate.httpEngine.getResponse().handshake();
        }
        return this.delegate.handshake;
    }

    @Override
    public void setFixedLengthStreamingMode(long l2) {
        this.delegate.setFixedLengthStreamingMode(l2);
    }

    @Override
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.delegate.client.setHostnameVerifier(hostnameVerifier);
    }

    @Override
    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.delegate.client.setSslSocketFactory(sSLSocketFactory);
    }
}

