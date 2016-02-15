package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response.Builder;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

public final class HttpTransport implements Transport {
    private final HttpConnection httpConnection;
    private final HttpEngine httpEngine;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.httpEngine = httpEngine;
        this.httpConnection = httpConnection;
    }

    public Sink createRequestBody(Request request) throws IOException {
        long contentLength = OkHeaders.contentLength(request);
        if (this.httpEngine.bufferRequestBody) {
            if (contentLength > 2147483647L) {
                throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
            } else if (contentLength == -1) {
                return new RetryableSink();
            } else {
                writeRequestHeaders(request);
                return new RetryableSink((int) contentLength);
            }
        } else if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            writeRequestHeaders(request);
            return this.httpConnection.newChunkedSink();
        } else if (contentLength != -1) {
            writeRequestHeaders(request);
            return this.httpConnection.newFixedLengthSink(contentLength);
        } else {
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
    }

    public void flushRequest() throws IOException {
        this.httpConnection.flush();
    }

    public void writeRequestBody(RetryableSink requestBody) throws IOException {
        this.httpConnection.writeRequestBody(requestBody);
    }

    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        this.httpConnection.writeRequest(request.headers(), RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol()));
    }

    public Builder readResponseHeaders() throws IOException {
        return this.httpConnection.readResponse();
    }

    public void releaseConnectionOnIdle() throws IOException {
        if (canReuseConnection()) {
            this.httpConnection.poolOnIdle();
        } else {
            this.httpConnection.closeOnIdle();
        }
    }

    public boolean canReuseConnection() {
        if ("close".equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) || "close".equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) || this.httpConnection.isClosed()) {
            return false;
        }
        return true;
    }

    public void emptyTransferStream() throws IOException {
        this.httpConnection.emptyResponseBody();
    }

    public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
        if (!this.httpEngine.hasResponseBody()) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, 0);
        }
        if ("chunked".equalsIgnoreCase(this.httpEngine.getResponse().header("Transfer-Encoding"))) {
            return this.httpConnection.newChunkedSource(cacheRequest, this.httpEngine);
        }
        long contentLength = OkHeaders.contentLength(this.httpEngine.getResponse());
        if (contentLength != -1) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, contentLength);
        }
        return this.httpConnection.newUnknownLengthSource(cacheRequest);
    }

    public void disconnect(HttpEngine engine) throws IOException {
        this.httpConnection.closeIfOwnedBy(engine);
    }
}
