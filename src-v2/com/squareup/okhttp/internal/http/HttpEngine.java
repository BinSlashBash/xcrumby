/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.RouteSelector;
import com.squareup.okhttp.internal.http.Transport;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody(){

        @Override
        public long contentLength() {
            return 0;
        }

        @Override
        public MediaType contentType() {
            return null;
        }

        @Override
        public BufferedSource source() {
            return new Buffer();
        }
    };
    public static final int MAX_REDIRECTS = 20;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    final OkHttpClient client;
    private Connection connection;
    private Request networkRequest;
    private Response networkResponse;
    private final Response priorResponse;
    private Sink requestBodyOut;
    private BufferedSource responseBody;
    private InputStream responseBodyBytes;
    private Source responseTransferSource;
    private Route route;
    private RouteSelector routeSelector;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean bl2, Connection connection, RouteSelector routeSelector, RetryableSink retryableSink, Response response) {
        this.client = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = bl2;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
        if (connection != null) {
            Internal.instance.setOwner(connection, this);
            this.route = connection.getRoute();
            return;
        }
        this.route = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Headers combine(Headers object, Headers headers) throws IOException {
        int n2;
        Headers.Builder builder = new Headers.Builder();
        for (n2 = 0; n2 < object.size(); ++n2) {
            String string2 = object.name(n2);
            String string3 = object.value(n2);
            if ("Warning".equals(string2) && string3.startsWith("1") || OkHeaders.isEndToEnd(string2) && headers.get(string2) != null) continue;
            builder.add(string2, string3);
        }
        n2 = 0;
        while (n2 < headers.size()) {
            object = headers.name(n2);
            if (OkHeaders.isEndToEnd((String)object)) {
                builder.add((String)object, headers.value(n2));
            }
            ++n2;
        }
        return builder.build();
    }

    private void connect(Request request) throws IOException {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        if (this.routeSelector == null) {
            String string2 = request.url().getHost();
            if (string2 == null || string2.length() == 0) {
                throw new UnknownHostException(request.url().toString());
            }
            SSLSocketFactory sSLSocketFactory = null;
            HostnameVerifier hostnameVerifier = null;
            if (request.isHttps()) {
                sSLSocketFactory = this.client.getSslSocketFactory();
                hostnameVerifier = this.client.getHostnameVerifier();
            }
            this.routeSelector = new RouteSelector(new Address(string2, Util.getEffectivePort(request.url()), this.client.getSocketFactory(), sSLSocketFactory, hostnameVerifier, this.client.getAuthenticator(), this.client.getProxy(), this.client.getProtocols()), request.uri(), this.client.getProxySelector(), this.client.getConnectionPool(), Dns.DEFAULT, Internal.instance.routeDatabase(this.client));
        }
        this.connection = this.routeSelector.next(request.method());
        Internal.instance.setOwner(this.connection, this);
        if (!Internal.instance.isConnected(this.connection)) {
            Internal.instance.connect(this.connection, this.client.getConnectTimeout(), this.client.getReadTimeout(), this.client.getWriteTimeout(), this.tunnelRequest(this.connection, request));
            if (Internal.instance.isSpdy(this.connection)) {
                Internal.instance.share(this.client.getConnectionPool(), this.connection);
            }
            Internal.instance.routeDatabase(this.client).connected(this.connection.getRoute());
        }
        Internal.instance.setTimeouts(this.connection, this.client.getReadTimeout(), this.client.getWriteTimeout());
        this.route = this.connection.getRoute();
    }

    public static String hostHeader(URL uRL) {
        if (Util.getEffectivePort(uRL) != Util.getDefaultPort(uRL.getProtocol())) {
            return uRL.getHost() + ":" + uRL.getPort();
        }
        return uRL.getHost();
    }

    private void initContentStream(Source source) throws IOException {
        this.responseTransferSource = source;
        if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
            this.userResponse = this.userResponse.newBuilder().removeHeader("Content-Encoding").removeHeader("Content-Length").build();
            this.responseBody = Okio.buffer(new GzipSource(source));
            return;
        }
        this.responseBody = Okio.buffer(source);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isRecoverable(IOException iOException) {
        boolean bl2 = iOException instanceof SSLHandshakeException && iOException.getCause() instanceof CertificateException;
        boolean bl3 = iOException instanceof ProtocolException;
        if (!bl2 && !bl3) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void maybeCache() throws IOException {
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        if (internalCache == null) {
            return;
        }
        if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            this.storeRequest = internalCache.put(HttpEngine.stripBody(this.userResponse));
            return;
        }
        if (!HttpMethod.invalidatesCache(this.networkRequest.method())) return;
        try {
            internalCache.remove(this.networkRequest);
            return;
        }
        catch (IOException var1_2) {
            return;
        }
    }

    private Request networkRequest(Request request) throws IOException {
        CookieHandler cookieHandler;
        Request.Builder builder = request.newBuilder();
        if (request.header("Host") == null) {
            builder.header("Host", HttpEngine.hostHeader(request.url()));
        }
        if ((this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            builder.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            builder.header("Accept-Encoding", "gzip");
        }
        if ((cookieHandler = this.client.getCookieHandler()) != null) {
            Map<String, List<String>> map = OkHeaders.toMultimap(builder.build().headers(), null);
            OkHeaders.addCookies(builder, cookieHandler.get(request.uri(), map));
        }
        return builder.build();
    }

    private static Response stripBody(Response response) {
        Response response2 = response;
        if (response != null) {
            response2 = response;
            if (response.body() != null) {
                response2 = response.newBuilder().body(null).build();
            }
        }
        return response2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Request tunnelRequest(Connection object, Request object2) throws IOException {
        if (!object.getRoute().requiresTunnel()) {
            return null;
        }
        String string2 = object2.url().getHost();
        int n2 = Util.getEffectivePort(object2.url());
        object = n2 == Util.getDefaultPort("https") ? string2 : string2 + ":" + n2;
        object = new Request.Builder().url(new URL("https", string2, n2, "/")).header("Host", (String)object).header("Proxy-Connection", "Keep-Alive");
        string2 = object2.header("User-Agent");
        if (string2 != null) {
            object.header("User-Agent", string2);
        }
        if ((object2 = object2.header("Proxy-Authorization")) != null) {
            object.header("Proxy-Authorization", (String)object2);
        }
        return object.build();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean validate(Response object, Response object2) {
        if (object2.code() == 304 || (object = object.headers().getDate("Last-Modified")) != null && (object2 = object2.headers().getDate("Last-Modified")) != null && object2.getTime() < object.getTime()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Connection close() {
        if (this.bufferedRequestBody != null) {
            Util.closeQuietly(this.bufferedRequestBody);
        } else if (this.requestBodyOut != null) {
            Util.closeQuietly(this.requestBodyOut);
        }
        if (this.responseBody == null) {
            if (this.connection != null) {
                Util.closeQuietly(this.connection.getSocket());
            }
            this.connection = null;
            return null;
        }
        Util.closeQuietly(this.responseBody);
        Util.closeQuietly(this.responseBodyBytes);
        if (this.transport != null && this.connection != null && !this.transport.canReuseConnection()) {
            Util.closeQuietly(this.connection.getSocket());
            this.connection = null;
            return null;
        }
        if (this.connection != null && !Internal.instance.clearOwner(this.connection)) {
            this.connection = null;
        }
        Connection connection = this.connection;
        this.connection = null;
        return connection;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void disconnect() {
        if (this.transport == null) return;
        try {
            this.transport.disconnect(this);
            return;
        }
        catch (IOException var1_1) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Request followUpRequest() throws IOException {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        Object object = this.getRoute() != null ? this.getRoute().getProxy() : this.client.getProxy();
        switch (this.userResponse.code()) {
            case 407: {
                if (object.type() == Proxy.Type.HTTP) return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, (Proxy)object);
                {
                    throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                }
            }
            case 401: {
                return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, (Proxy)object);
            }
            case 307: {
                if (this.userRequest.method().equals("GET") || this.userRequest.method().equals("HEAD")) break;
            }
            default: {
                return null;
            }
            case 300: 
            case 301: 
            case 302: 
            case 303: 
        }
        if ((object = this.userResponse.header("Location")) == null || !(object = new URL(this.userRequest.url(), (String)object)).getProtocol().equals("https") && !object.getProtocol().equals("http") || !object.getProtocol().equals(this.userRequest.url().getProtocol()) && !this.client.getFollowSslRedirects()) return null;
        {
            Request.Builder builder = this.userRequest.newBuilder();
            if (HttpMethod.hasRequestBody(this.userRequest.method())) {
                builder.method("GET", null);
                builder.removeHeader("Transfer-Encoding");
                builder.removeHeader("Content-Length");
                builder.removeHeader("Content-Type");
            }
            if (this.sameConnection((URL)object)) return builder.url((URL)object).build();
            {
                builder.removeHeader("Authorization");
            }
            return builder.url((URL)object).build();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public BufferedSink getBufferedRequestBody() {
        void var1_4;
        BufferedSink bufferedSink;
        BufferedSink bufferedSink2 = this.bufferedRequestBody;
        if (bufferedSink2 != null) {
            return bufferedSink2;
        }
        Sink sink = this.getRequestBody();
        if (sink == null) return var1_4;
        this.bufferedRequestBody = bufferedSink = Okio.buffer(sink);
        return var1_4;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy == null) {
            throw new IllegalStateException();
        }
        return this.requestBodyOut;
    }

    public Response getResponse() {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        return this.userResponse;
    }

    public BufferedSource getResponseBody() {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        return this.responseBody;
    }

    public InputStream getResponseBodyBytes() {
        InputStream inputStream = this.responseBodyBytes;
        if (inputStream != null) {
            return inputStream;
        }
        this.responseBodyBytes = inputStream = Okio.buffer(this.getResponseBody()).inputStream();
        return inputStream;
    }

    public Route getRoute() {
        return this.route;
    }

    boolean hasRequestBody() {
        if (HttpMethod.hasRequestBody(this.userRequest.method()) && !Util.emptySink().equals(this.requestBodyOut)) {
            return true;
        }
        return false;
    }

    public boolean hasResponse() {
        if (this.userResponse != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean hasResponseBody() {
        if (this.userRequest.method().equals("HEAD")) {
            return false;
        }
        int n2 = this.userResponse.code();
        if ((n2 < 100 || n2 >= 200) && n2 != 204 && n2 != 304) {
            return true;
        }
        if (OkHeaders.contentLength(this.networkResponse) != -1) return true;
        if (!"chunked".equalsIgnoreCase(this.networkResponse.header("Transfer-Encoding"))) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void readResponse() throws IOException {
        if (this.userResponse != null) {
            return;
        }
        if (this.networkRequest == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        }
        if (this.networkRequest == null) return;
        if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
            this.bufferedRequestBody.flush();
        }
        if (this.sentRequestMillis == -1) {
            if (OkHeaders.contentLength(this.networkRequest) == -1 && this.requestBodyOut instanceof RetryableSink) {
                long l2 = ((RetryableSink)this.requestBodyOut).contentLength();
                this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(l2)).build();
            }
            this.transport.writeRequestHeaders(this.networkRequest);
        }
        if (this.requestBodyOut != null) {
            if (this.bufferedRequestBody != null) {
                this.bufferedRequestBody.close();
            } else {
                this.requestBodyOut.close();
            }
            if (this.requestBodyOut instanceof RetryableSink && !Util.emptySink().equals(this.requestBodyOut)) {
                this.transport.writeRequestBody((RetryableSink)this.requestBodyOut);
            }
        }
        this.transport.flushRequest();
        this.networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        Internal.instance.setProtocol(this.connection, this.networkResponse.protocol());
        this.receiveHeaders(this.networkResponse.headers());
        if (this.cacheResponse != null) {
            if (HttpEngine.validate(this.cacheResponse, this.networkResponse)) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).headers(HttpEngine.combine(this.cacheResponse.headers(), this.networkResponse.headers())).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).networkResponse(HttpEngine.stripBody(this.networkResponse)).build();
                this.transport.emptyTransferStream();
                this.releaseConnection();
                InternalCache internalCache = Internal.instance.internalCache(this.client);
                internalCache.trackConditionalCacheHit();
                internalCache.update(this.cacheResponse, HttpEngine.stripBody(this.userResponse));
                if (this.cacheResponse.body() == null) return;
                this.initContentStream(this.cacheResponse.body().source());
                return;
            }
            Util.closeQuietly(this.cacheResponse.body());
        }
        this.userResponse = this.networkResponse.newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).networkResponse(HttpEngine.stripBody(this.networkResponse)).build();
        if (!this.hasResponseBody()) {
            this.responseTransferSource = this.transport.getTransferStream(this.storeRequest);
            this.responseBody = Okio.buffer(this.responseTransferSource);
            return;
        }
        this.maybeCache();
        this.initContentStream(this.transport.getTransferStream(this.storeRequest));
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public HttpEngine recover(IOException iOException) {
        return this.recover(iOException, this.requestBodyOut);
    }

    /*
     * Enabled aggressive block sorting
     */
    public HttpEngine recover(IOException object, Sink sink) {
        if (this.routeSelector != null && this.connection != null) {
            this.routeSelector.connectFailed(this.connection, (IOException)object);
        }
        boolean bl2 = sink == null || sink instanceof RetryableSink;
        if ((this.routeSelector != null || this.connection != null) && (this.routeSelector == null || this.routeSelector.hasNext()) && this.isRecoverable((IOException)object) && bl2) {
            object = this.close();
            return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, (Connection)object, this.routeSelector, (RetryableSink)sink, this.priorResponse);
        }
        return null;
    }

    public void releaseConnection() throws IOException {
        if (this.transport != null && this.connection != null) {
            this.transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public boolean sameConnection(URL uRL) {
        URL uRL2 = this.userRequest.url();
        if (uRL2.getHost().equals(uRL.getHost()) && Util.getEffectivePort(uRL2) == Util.getEffectivePort(uRL) && uRL2.getProtocol().equals(uRL.getProtocol())) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void sendRequest() throws IOException {
        if (this.cacheStrategy != null) return;
        if (this.transport != null) {
            throw new IllegalStateException();
        }
        Request request = this.networkRequest(this.userRequest);
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        Response response = internalCache != null ? internalCache.get(request) : null;
        this.cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), request, response).get();
        this.networkRequest = this.cacheStrategy.networkRequest;
        this.cacheResponse = this.cacheStrategy.cacheResponse;
        if (internalCache != null) {
            internalCache.trackResponse(this.cacheStrategy);
        }
        if (response != null && this.cacheResponse == null) {
            Util.closeQuietly(response.body());
        }
        if (this.networkRequest != null) {
            if (this.connection == null) {
                this.connect(this.networkRequest);
            }
            if (Internal.instance.getOwner(this.connection) != this && !Internal.instance.isSpdy(this.connection)) {
                throw new AssertionError();
            }
            this.transport = Internal.instance.newTransport(this.connection, this);
            if (!this.hasRequestBody() || this.requestBodyOut != null) return;
            {
                this.requestBodyOut = this.transport.createRequestBody(request);
                return;
            }
        }
        if (this.connection != null) {
            Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
            this.connection = null;
        }
        this.userResponse = this.cacheResponse != null ? this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).cacheResponse(HttpEngine.stripBody(this.cacheResponse)).build() : new Response.Builder().request(this.userRequest).priorResponse(HttpEngine.stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
        if (this.userResponse.body() == null) {
            return;
        }
        this.initContentStream(this.userResponse.body().source());
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

}

