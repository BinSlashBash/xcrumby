/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.CacheStrategy;
import java.io.IOException;
import java.net.CacheRequest;

public interface InternalCache {
    public Response get(Request var1) throws IOException;

    public CacheRequest put(Response var1) throws IOException;

    public void remove(Request var1) throws IOException;

    public void trackConditionalCacheHit();

    public void trackResponse(CacheStrategy var1);

    public void update(Response var1, Response var2) throws IOException;
}

