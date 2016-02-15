/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.RouteSelector;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Sink;

public final class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    private final Dispatcher dispatcher;
    HttpEngine engine;
    private boolean executed;
    private int redirectionCount;
    private Request request;

    Call(OkHttpClient okHttpClient, Dispatcher dispatcher, Request request) {
        this.client = okHttpClient;
        this.dispatcher = dispatcher;
        this.request = request;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Response getResponse() throws IOException {
        var5_1 = this.request.body();
        var4_2 = null;
        if (var5_1 != null) {
            var6_4 = this.request.newBuilder();
            var7_5 = var5_1.contentType();
            if (var7_5 != null) {
                var6_4.header("Content-Type", var7_5.toString());
            }
            if ((var2_6 = var5_1.contentLength()) != -1) {
                var6_4.header("Content-Length", Long.toString(var2_6));
                var6_4.removeHeader("Transfer-Encoding");
            } else {
                var6_4.header("Transfer-Encoding", "chunked");
                var6_4.removeHeader("Content-Length");
            }
            this.request = var6_4.build();
        } else if (HttpMethod.hasRequestBody(this.request.method())) {
            var4_2 = Util.emptySink();
        }
        this.engine = new HttpEngine(this.client, this.request, false, null, null, (RetryableSink)var4_2, null);
        do {
            if (this.canceled) {
                return null;
            }
            try {
                this.engine.sendRequest();
                if (this.request.body() != null) {
                    var4_2 = this.engine.getBufferedRequestBody();
                    this.request.body().writeTo((BufferedSink)var4_2);
                }
                this.engine.readResponse();
                var4_2 = this.engine.getResponse();
            }
            catch (IOException var4_3) {
                var5_1 = this.engine.recover(var4_3, null);
                if (var5_1 == null) throw var4_3;
                this.engine = var5_1;
            }
            var5_1 = this.engine.followUpRequest();
            if (var5_1 == null) {
                this.engine.releaseConnection();
                return var4_2.newBuilder().body(new RealResponseBody((Response)var4_2, this.engine.getResponseBody())).build();
            }
            ** GOTO lbl39
            continue;
lbl39: // 1 sources:
            if (this.engine.getResponse().isRedirect()) {
                this.redirectionCount = var1_7 = this.redirectionCount + 1;
                if (var1_7 > 20) {
                    throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                }
            }
            if (!this.engine.sameConnection(var5_1.url())) {
                this.engine.releaseConnection();
            }
            var6_4 = this.engine.close();
            this.request = var5_1;
            this.engine = new HttpEngine(this.client, this.request, false, (Connection)var6_4, null, null, (Response)var4_2);
        } while (true);
    }

    public void cancel() {
        this.canceled = true;
        if (this.engine != null) {
            this.engine.disconnect();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.dispatcher.enqueue(new AsyncCall(callback));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        Response response = this.getResponse();
        this.engine.releaseConnection();
        if (response == null) {
            throw new IOException("Canceled");
        }
        return response;
    }

    final class AsyncCall
    extends NamedRunnable {
        private final Callback responseCallback;

        private AsyncCall(Callback callback) {
            super("OkHttp %s", Call.this.request.urlString());
            this.responseCallback = callback;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void execute() {
            IOException iOException;
            Response response;
            boolean bl2;
            boolean bl3;
            block8 : {
                bl2 = bl3 = false;
                response = Call.this.getResponse();
                bl2 = bl3;
                if (!Call.this.canceled) break block8;
                bl2 = true;
                this.responseCallback.onFailure(Call.this.request, new IOException("Canceled"));
                return;
            }
            bl2 = bl3 = true;
            try {
                try {
                    Call.this.engine.releaseConnection();
                    bl2 = bl3;
                    this.responseCallback.onResponse(response);
                    return;
                }
                catch (IOException iOException) {
                    if (bl2) {
                        throw new RuntimeException(iOException);
                    }
                }
            }
            catch (Throwable var3_5) {
                throw var3_5;
            }
            finally {
                Call.this.dispatcher.finished(this);
            }
            this.responseCallback.onFailure(Call.this.request, iOException);
        }

        Call get() {
            return Call.this;
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
    }

    private static class RealResponseBody
    extends ResponseBody {
        private final Response response;
        private final BufferedSource source;

        RealResponseBody(Response response, BufferedSource bufferedSource) {
            this.response = response;
            this.source = bufferedSource;
        }

        @Override
        public long contentLength() {
            return OkHeaders.contentLength(this.response);
        }

        @Override
        public MediaType contentType() {
            String string2 = this.response.header("Content-Type");
            if (string2 != null) {
                return MediaType.parse(string2);
            }
            return null;
        }

        @Override
        public BufferedSource source() {
            return this.source;
        }
    }

}

