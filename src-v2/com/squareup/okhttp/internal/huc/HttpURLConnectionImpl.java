/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.RouteSelector;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Sink;

public class HttpURLConnectionImpl
extends HttpURLConnection {
    final OkHttpClient client;
    private long fixedContentLength = -1;
    Handshake handshake;
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private int redirectionCount;
    private Headers.Builder requestHeaders = new Headers.Builder();
    private Headers responseHeaders;
    private Route route;

    public HttpURLConnectionImpl(URL uRL, OkHttpClient okHttpClient) {
        super(uRL);
        this.client = okHttpClient;
    }

    private String defaultUserAgent() {
        String string2 = System.getProperty("http.agent");
        if (string2 != null) {
            return string2;
        }
        return "Java" + System.getProperty("java.version");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean execute(boolean bl2) throws IOException {
        try {
            this.httpEngine.sendRequest();
            this.route = this.httpEngine.getRoute();
            Handshake handshake = this.httpEngine.getConnection() != null ? this.httpEngine.getConnection().getHandshake() : null;
            this.handshake = handshake;
            if (bl2) {
                this.httpEngine.readResponse();
            }
            return true;
        }
        catch (IOException var2_3) {
            HttpEngine httpEngine = this.httpEngine.recover(var2_3);
            if (httpEngine != null) {
                this.httpEngine = httpEngine;
                return false;
            }
            this.httpEngineFailure = var2_3;
            throw var2_3;
        }
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = this.getResponse().getResponse();
            this.responseHeaders = response.headers().newBuilder().add(Platform.get().getPrefix() + "-Response-Source", HttpURLConnectionImpl.responseSourceHeader(response)).build();
        }
        return this.responseHeaders;
    }

    private HttpEngine getResponse() throws IOException {
        this.initHttpEngine();
        if (this.httpEngine.hasResponse()) {
            return this.httpEngine;
        }
        do {
            if (!this.execute(true)) {
                continue;
            }
            Response response = this.httpEngine.getResponse();
            Request request = this.httpEngine.followUpRequest();
            if (request == null) {
                this.httpEngine.releaseConnection();
                return this.httpEngine;
            }
            if (response.isRedirect()) {
                int n2;
                this.redirectionCount = n2 = this.redirectionCount + 1;
                if (n2 > 20) {
                    throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                }
            }
            this.url = request.url();
            this.requestHeaders = request.headers().newBuilder();
            Sink sink = this.httpEngine.getRequestBody();
            if (!request.method().equals(this.method)) {
                sink = null;
            }
            if (sink != null && !(sink instanceof RetryableSink)) {
                throw new HttpRetryException("Cannot retry streamed HTTP body", this.responseCode);
            }
            if (!this.httpEngine.sameConnection(request.url())) {
                this.httpEngine.releaseConnection();
            }
            Connection connection = this.httpEngine.close();
            this.httpEngine = this.newHttpEngine(request.method(), connection, (RetryableSink)sink, response);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initHttpEngine() throws IOException {
        RetryableSink retryableSink = null;
        if (this.httpEngineFailure != null) {
            throw this.httpEngineFailure;
        }
        if (this.httpEngine != null) {
            return;
        }
        this.connected = true;
        try {
            if (this.doOutput) {
                if (this.method.equals("GET")) {
                    this.method = "POST";
                } else if (!HttpMethod.hasRequestBody(this.method)) {
                    throw new ProtocolException(this.method + " does not support writing");
                }
            }
            RetryableSink retryableSink2 = retryableSink;
            if (this.doOutput) {
                retryableSink2 = retryableSink;
                if (this.fixedContentLength == 0) {
                    retryableSink2 = Util.emptySink();
                }
            }
            this.httpEngine = this.newHttpEngine(this.method, null, retryableSink2, null);
            return;
        }
        catch (IOException var1_3) {
            this.httpEngineFailure = var1_3;
            throw var1_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private HttpEngine newHttpEngine(String object, Connection connection, RetryableSink retryableSink, Response response) {
        Object object2 = new Request.Builder().url(this.getURL()).method((String)object, null);
        Object object3 = this.requestHeaders.build();
        for (int i2 = 0; i2 < object3.size(); ++i2) {
            object2.addHeader(object3.name(i2), object3.value(i2));
        }
        boolean bl2 = false;
        boolean bl3 = false;
        if (HttpMethod.hasRequestBody((String)object)) {
            if (this.fixedContentLength != -1) {
                object2.header("Content-Length", Long.toString(this.fixedContentLength));
            } else if (this.chunkLength > 0) {
                object2.header("Transfer-Encoding", "chunked");
            } else {
                bl3 = true;
            }
            bl2 = bl3;
            if (object3.get("Content-Type") == null) {
                object2.header("Content-Type", "application/x-www-form-urlencoded");
                bl2 = bl3;
            }
        }
        if (object3.get("User-Agent") == null) {
            object2.header("User-Agent", this.defaultUserAgent());
        }
        object3 = object2.build();
        object = object2 = this.client;
        if (Internal.instance.internalCache((OkHttpClient)object2) != null) {
            object = object2;
            if (!this.getUseCaches()) {
                object = this.client.clone().setCache(null);
            }
        }
        return new HttpEngine((OkHttpClient)object, (Request)object3, bl2, connection, null, retryableSink, response);
    }

    private static String responseSourceHeader(Response response) {
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            return "CACHE " + response.code();
        }
        if (response.cacheResponse() == null) {
            return "NETWORK " + response.code();
        }
        return "CONDITIONAL_CACHE " + response.networkResponse().code();
    }

    private void setProtocols(String arrstring, boolean bl2) {
        ArrayList<Protocol> arrayList = new ArrayList<Protocol>();
        if (bl2) {
            arrayList.addAll(this.client.getProtocols());
        }
        for (String string2 : arrstring.split(",", -1)) {
            try {
                arrayList.add(Protocol.get(string2));
                continue;
            }
            catch (IOException var1_2) {
                throw new IllegalStateException(var1_2);
            }
        }
        this.client.setProtocols(arrayList);
    }

    @Override
    public final void addRequestProperty(String string2, String string3) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        }
        if (string2 == null) {
            throw new NullPointerException("field == null");
        }
        if (string3 == null) {
            Platform.get().logW("Ignoring header " + string2 + " because its value was null.");
            return;
        }
        if ("X-Android-Transports".equals(string2) || "X-Android-Protocols".equals(string2)) {
            this.setProtocols(string3, true);
            return;
        }
        this.requestHeaders.add(string2, string3);
    }

    @Override
    public final void connect() throws IOException {
        this.initHttpEngine();
        while (!this.execute(false)) {
        }
    }

    @Override
    public final void disconnect() {
        if (this.httpEngine == null) {
            return;
        }
        this.httpEngine.disconnect();
    }

    @Override
    public int getConnectTimeout() {
        return this.client.getConnectTimeout();
    }

    @Override
    public final InputStream getErrorStream() {
        InputStream inputStream;
        block4 : {
            HttpEngine httpEngine;
            InputStream inputStream2 = null;
            try {
                httpEngine = this.getResponse();
                inputStream = inputStream2;
            }
            catch (IOException var1_4) {
                return null;
            }
            if (!httpEngine.hasResponseBody()) break block4;
            inputStream = inputStream2;
            if (httpEngine.getResponse().code() < 400) break block4;
            inputStream = httpEngine.getResponseBodyBytes();
        }
        return inputStream;
    }

    @Override
    public final String getHeaderField(int n2) {
        try {
            String string2 = this.getHeaders().value(n2);
            return string2;
        }
        catch (IOException var2_3) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final String getHeaderField(String string2) {
        if (string2 != null) return this.getHeaders().get(string2);
        try {
            return StatusLine.get(this.getResponse().getResponse()).toString();
        }
        catch (IOException var1_2) {
            return null;
        }
    }

    @Override
    public final String getHeaderFieldKey(int n2) {
        try {
            String string2 = this.getHeaders().name(n2);
            return string2;
        }
        catch (IOException var2_3) {
            return null;
        }
    }

    @Override
    public final Map<String, List<String>> getHeaderFields() {
        try {
            Map<String, List<String>> map = OkHeaders.toMultimap(this.getHeaders(), StatusLine.get(this.getResponse().getResponse()).toString());
            return map;
        }
        catch (IOException var1_2) {
            return Collections.emptyMap();
        }
    }

    @Override
    public final InputStream getInputStream() throws IOException {
        if (!this.doInput) {
            throw new ProtocolException("This protocol does not support input");
        }
        Object object = this.getResponse();
        if (this.getResponseCode() >= 400) {
            throw new FileNotFoundException(this.url.toString());
        }
        if ((object = object.getResponseBodyBytes()) == null) {
            throw new ProtocolException("No response body exists; responseCode=" + this.getResponseCode());
        }
        return object;
    }

    @Override
    public final OutputStream getOutputStream() throws IOException {
        this.connect();
        BufferedSink bufferedSink = this.httpEngine.getBufferedRequestBody();
        if (bufferedSink == null) {
            throw new ProtocolException("method does not support a request body: " + this.method);
        }
        if (this.httpEngine.hasResponse()) {
            throw new ProtocolException("cannot write request body after response has been read");
        }
        return bufferedSink.outputStream();
    }

    @Override
    public final Permission getPermission() throws IOException {
        String string2 = this.getURL().getHost();
        int n2 = Util.getEffectivePort(this.getURL());
        if (this.usingProxy()) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)this.client.getProxy().address();
            string2 = inetSocketAddress.getHostName();
            n2 = inetSocketAddress.getPort();
        }
        return new SocketPermission(string2 + ":" + n2, "connect, resolve");
    }

    @Override
    public int getReadTimeout() {
        return this.client.getReadTimeout();
    }

    @Override
    public final Map<String, List<String>> getRequestProperties() {
        if (this.connected) {
            throw new IllegalStateException("Cannot access request header fields after connection is set");
        }
        return OkHeaders.toMultimap(this.requestHeaders.build(), null);
    }

    @Override
    public final String getRequestProperty(String string2) {
        if (string2 == null) {
            return null;
        }
        return this.requestHeaders.get(string2);
    }

    @Override
    public final int getResponseCode() throws IOException {
        return this.getResponse().getResponse().code();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return this.getResponse().getResponse().message();
    }

    @Override
    public void setConnectTimeout(int n2) {
        this.client.setConnectTimeout(n2, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setFixedLengthStreamingMode(int n2) {
        this.setFixedLengthStreamingMode((long)n2);
    }

    @Override
    public void setFixedLengthStreamingMode(long l2) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        }
        if (l2 < 0) {
            throw new IllegalArgumentException("contentLength < 0");
        }
        this.fixedContentLength = l2;
        this.fixedContentLength = (int)Math.min(l2, Integer.MAX_VALUE);
    }

    @Override
    public void setIfModifiedSince(long l2) {
        super.setIfModifiedSince(l2);
        if (this.ifModifiedSince != 0) {
            this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
            return;
        }
        this.requestHeaders.removeAll("If-Modified-Since");
    }

    @Override
    public void setReadTimeout(int n2) {
        this.client.setReadTimeout(n2, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setRequestMethod(String string2) throws ProtocolException {
        if (!HttpMethod.METHODS.contains(string2)) {
            throw new ProtocolException("Expected one of " + HttpMethod.METHODS + " but was " + string2);
        }
        this.method = string2;
    }

    @Override
    public final void setRequestProperty(String string2, String string3) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        }
        if (string2 == null) {
            throw new NullPointerException("field == null");
        }
        if (string3 == null) {
            Platform.get().logW("Ignoring header " + string2 + " because its value was null.");
            return;
        }
        if ("X-Android-Transports".equals(string2) || "X-Android-Protocols".equals(string2)) {
            this.setProtocols(string3, false);
            return;
        }
        this.requestHeaders.set(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final boolean usingProxy() {
        Proxy proxy = this.route != null ? this.route.getProxy() : this.client.getProxy();
        if (proxy != null && proxy.type() != Proxy.Type.DIRECT) {
            return true;
        }
        return false;
    }
}

