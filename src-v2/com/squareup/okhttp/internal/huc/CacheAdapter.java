/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.huc.JavaApiConverter;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class CacheAdapter
implements InternalCache {
    private final ResponseCache delegate;

    public CacheAdapter(ResponseCache responseCache) {
        this.delegate = responseCache;
    }

    private CacheResponse getJavaCachedResponse(Request request) throws IOException {
        Map<String, List<String>> map = JavaApiConverter.extractJavaHeaders(request);
        return this.delegate.get(request.uri(), request.method(), map);
    }

    @Override
    public Response get(Request request) throws IOException {
        CacheResponse cacheResponse = this.getJavaCachedResponse(request);
        if (cacheResponse == null) {
            return null;
        }
        return JavaApiConverter.createOkResponse(request, cacheResponse);
    }

    public ResponseCache getDelegate() {
        return this.delegate;
    }

    @Override
    public CacheRequest put(Response object) throws IOException {
        URI uRI = object.request().uri();
        object = JavaApiConverter.createJavaUrlConnection((Response)object);
        return this.delegate.put(uRI, (URLConnection)object);
    }

    @Override
    public void remove(Request request) throws IOException {
    }

    @Override
    public void trackConditionalCacheHit() {
    }

    @Override
    public void trackResponse(CacheStrategy cacheStrategy) {
    }

    @Override
    public void update(Response response, Response response2) throws IOException {
    }
}

