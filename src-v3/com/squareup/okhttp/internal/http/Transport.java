package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response.Builder;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

public interface Transport {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    boolean canReuseConnection();

    Sink createRequestBody(Request request) throws IOException;

    void disconnect(HttpEngine httpEngine) throws IOException;

    void emptyTransferStream() throws IOException;

    void flushRequest() throws IOException;

    Source getTransferStream(CacheRequest cacheRequest) throws IOException;

    Builder readResponseHeaders() throws IOException;

    void releaseConnectionOnIdle() throws IOException;

    void writeRequestBody(RetryableSink retryableSink) throws IOException;

    void writeRequestHeaders(Request request) throws IOException;
}
