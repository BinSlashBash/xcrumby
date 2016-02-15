/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RetryableSink;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

public interface Transport {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    public boolean canReuseConnection();

    public Sink createRequestBody(Request var1) throws IOException;

    public void disconnect(HttpEngine var1) throws IOException;

    public void emptyTransferStream() throws IOException;

    public void flushRequest() throws IOException;

    public Source getTransferStream(CacheRequest var1) throws IOException;

    public Response.Builder readResponseHeaders() throws IOException;

    public void releaseConnectionOnIdle() throws IOException;

    public void writeRequestBody(RetryableSink var1) throws IOException;

    public void writeRequestHeaders(Request var1) throws IOException;
}

