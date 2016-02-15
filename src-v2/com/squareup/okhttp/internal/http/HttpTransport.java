/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RequestLine;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.Transport;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.Proxy;
import okio.Sink;
import okio.Source;

public final class HttpTransport
implements Transport {
    private final HttpConnection httpConnection;
    private final HttpEngine httpEngine;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.httpEngine = httpEngine;
        this.httpConnection = httpConnection;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean canReuseConnection() {
        if ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) {
            return false;
        }
        return true;
    }

    @Override
    public Sink createRequestBody(Request request) throws IOException {
        long l2 = OkHeaders.contentLength(request);
        if (this.httpEngine.bufferRequestBody) {
            if (l2 > Integer.MAX_VALUE) {
                throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
            }
            if (l2 != -1) {
                this.writeRequestHeaders(request);
                return new RetryableSink((int)l2);
            }
            return new RetryableSink();
        }
        if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            this.writeRequestHeaders(request);
            return this.httpConnection.newChunkedSink();
        }
        if (l2 != -1) {
            this.writeRequestHeaders(request);
            return this.httpConnection.newFixedLengthSink(l2);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    @Override
    public void disconnect(HttpEngine httpEngine) throws IOException {
        this.httpConnection.closeIfOwnedBy(httpEngine);
    }

    @Override
    public void emptyTransferStream() throws IOException {
        this.httpConnection.emptyResponseBody();
    }

    @Override
    public void flushRequest() throws IOException {
        this.httpConnection.flush();
    }

    @Override
    public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
        if (!this.httpEngine.hasResponseBody()) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, 0);
        }
        if ("chunked".equalsIgnoreCase(this.httpEngine.getResponse().header("Transfer-Encoding"))) {
            return this.httpConnection.newChunkedSource(cacheRequest, this.httpEngine);
        }
        long l2 = OkHeaders.contentLength(this.httpEngine.getResponse());
        if (l2 != -1) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, l2);
        }
        return this.httpConnection.newUnknownLengthSource(cacheRequest);
    }

    @Override
    public Response.Builder readResponseHeaders() throws IOException {
        return this.httpConnection.readResponse();
    }

    @Override
    public void releaseConnectionOnIdle() throws IOException {
        if (this.canReuseConnection()) {
            this.httpConnection.poolOnIdle();
            return;
        }
        this.httpConnection.closeOnIdle();
    }

    @Override
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        this.httpConnection.writeRequestBody(retryableSink);
    }

    @Override
    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        String string2 = RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol());
        this.httpConnection.writeRequest(request.headers(), string2);
    }
}

