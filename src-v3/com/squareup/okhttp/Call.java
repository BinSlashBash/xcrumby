package com.squareup.okhttp;

import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import java.io.IOException;
import java.net.ProtocolException;
import okio.BufferedSource;

public final class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    private final Dispatcher dispatcher;
    HttpEngine engine;
    private boolean executed;
    private int redirectionCount;
    private Request request;

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        private AsyncCall(Callback responseCallback) {
            super("OkHttp %s", r5.request.urlString());
            this.responseCallback = responseCallback;
        }

        String host() {
            return Call.this.request.url().getHost();
        }

        Request request() {
            return Call.this.request;
        }

        Object tag() {
            return Call.this.request.tag();
        }

        Call get() {
            return Call.this;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void execute() {
            /*
            r7 = this;
            r2 = 0;
            r3 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r1 = r3.getResponse();	 Catch:{ IOException -> 0x0038 }
            r3 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r3 = r3.canceled;	 Catch:{ IOException -> 0x0038 }
            if (r3 == 0) goto L_0x002a;
        L_0x000d:
            r2 = 1;
            r3 = r7.responseCallback;	 Catch:{ IOException -> 0x0038 }
            r4 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r4 = r4.request;	 Catch:{ IOException -> 0x0038 }
            r5 = new java.io.IOException;	 Catch:{ IOException -> 0x0038 }
            r6 = "Canceled";
            r5.<init>(r6);	 Catch:{ IOException -> 0x0038 }
            r3.onFailure(r4, r5);	 Catch:{ IOException -> 0x0038 }
        L_0x0020:
            r3 = com.squareup.okhttp.Call.this;
            r3 = r3.dispatcher;
            r3.finished(r7);
        L_0x0029:
            return;
        L_0x002a:
            r2 = 1;
            r3 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r3 = r3.engine;	 Catch:{ IOException -> 0x0038 }
            r3.releaseConnection();	 Catch:{ IOException -> 0x0038 }
            r3 = r7.responseCallback;	 Catch:{ IOException -> 0x0038 }
            r3.onResponse(r1);	 Catch:{ IOException -> 0x0038 }
            goto L_0x0020;
        L_0x0038:
            r0 = move-exception;
            if (r2 == 0) goto L_0x004c;
        L_0x003b:
            r3 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0041 }
            r3.<init>(r0);	 Catch:{ all -> 0x0041 }
            throw r3;	 Catch:{ all -> 0x0041 }
        L_0x0041:
            r3 = move-exception;
            r4 = com.squareup.okhttp.Call.this;
            r4 = r4.dispatcher;
            r4.finished(r7);
            throw r3;
        L_0x004c:
            r3 = r7.responseCallback;	 Catch:{ all -> 0x0041 }
            r4 = com.squareup.okhttp.Call.this;	 Catch:{ all -> 0x0041 }
            r4 = r4.request;	 Catch:{ all -> 0x0041 }
            r3.onFailure(r4, r0);	 Catch:{ all -> 0x0041 }
            r3 = com.squareup.okhttp.Call.this;
            r3 = r3.dispatcher;
            r3.finished(r7);
            goto L_0x0029;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Call.AsyncCall.execute():void");
        }
    }

    private static class RealResponseBody extends ResponseBody {
        private final Response response;
        private final BufferedSource source;

        RealResponseBody(Response response, BufferedSource source) {
            this.response = response;
            this.source = source;
        }

        public MediaType contentType() {
            String contentType = this.response.header("Content-Type");
            return contentType != null ? MediaType.parse(contentType) : null;
        }

        public long contentLength() {
            return OkHeaders.contentLength(this.response);
        }

        public BufferedSource source() {
            return this.source;
        }
    }

    Call(OkHttpClient client, Dispatcher dispatcher, Request request) {
        this.client = client;
        this.dispatcher = dispatcher;
        this.request = request;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        Response result = getResponse();
        this.engine.releaseConnection();
        if (result != null) {
            return result;
        }
        throw new IOException("Canceled");
    }

    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.dispatcher.enqueue(new AsyncCall(responseCallback, null));
    }

    public void cancel() {
        this.canceled = true;
        if (this.engine != null) {
            this.engine.disconnect();
        }
    }

    private Response getResponse() throws IOException {
        RequestBody body = this.request.body();
        RetryableSink requestBodyOut = null;
        if (body != null) {
            Builder requestBuilder = this.request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                requestBuilder.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                requestBuilder.header("Content-Length", Long.toString(contentLength));
                requestBuilder.removeHeader("Transfer-Encoding");
            } else {
                requestBuilder.header("Transfer-Encoding", "chunked");
                requestBuilder.removeHeader("Content-Length");
            }
            this.request = requestBuilder.build();
        } else if (HttpMethod.hasRequestBody(this.request.method())) {
            requestBodyOut = Util.emptySink();
        }
        this.engine = new HttpEngine(this.client, this.request, false, null, null, requestBodyOut, null);
        while (!this.canceled) {
            try {
                this.engine.sendRequest();
                if (this.request.body() != null) {
                    this.request.body().writeTo(this.engine.getBufferedRequestBody());
                }
                this.engine.readResponse();
                Response response = this.engine.getResponse();
                Request followUp = this.engine.followUpRequest();
                if (followUp == null) {
                    this.engine.releaseConnection();
                    return response.newBuilder().body(new RealResponseBody(response, this.engine.getResponseBody())).build();
                }
                if (this.engine.getResponse().isRedirect()) {
                    int i = this.redirectionCount + 1;
                    this.redirectionCount = i;
                    if (i > 20) {
                        throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                    }
                }
                if (!this.engine.sameConnection(followUp.url())) {
                    this.engine.releaseConnection();
                }
                Connection connection = this.engine.close();
                this.request = followUp;
                this.engine = new HttpEngine(this.client, this.request, false, connection, null, null, response);
            } catch (IOException e) {
                HttpEngine retryEngine = this.engine.recover(e, null);
                if (retryEngine != null) {
                    this.engine = retryEngine;
                } else {
                    throw e;
                }
            }
        }
        return null;
    }
}
