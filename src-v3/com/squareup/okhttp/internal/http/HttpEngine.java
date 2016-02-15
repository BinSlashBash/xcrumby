package com.squareup.okhttp.internal.http;

import com.crumby.impl.device.DeviceFragment;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheStrategy.Factory;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
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
import uk.co.senab.photoview.IPhotoView;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY;
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
    long sentRequestMillis;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    /* renamed from: com.squareup.okhttp.internal.http.HttpEngine.1 */
    static class C11501 extends ResponseBody {
        C11501() {
        }

        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return 0;
        }

        public BufferedSource source() {
            return new Buffer();
        }
    }

    static {
        EMPTY_BODY = new C11501();
    }

    public HttpEngine(OkHttpClient client, Request request, boolean bufferRequestBody, Connection connection, RouteSelector routeSelector, RetryableSink requestBodyOut, Response priorResponse) {
        this.sentRequestMillis = -1;
        this.client = client;
        this.userRequest = request;
        this.bufferRequestBody = bufferRequestBody;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = requestBodyOut;
        this.priorResponse = priorResponse;
        if (connection != null) {
            Internal.instance.setOwner(connection, this);
            this.route = connection.getRoute();
            return;
        }
        this.route = null;
    }

    public void sendRequest() throws IOException {
        if (this.cacheStrategy == null) {
            if (this.transport != null) {
                throw new IllegalStateException();
            }
            Response cacheCandidate;
            Request request = networkRequest(this.userRequest);
            InternalCache responseCache = Internal.instance.internalCache(this.client);
            if (responseCache != null) {
                cacheCandidate = responseCache.get(request);
            } else {
                cacheCandidate = null;
            }
            this.cacheStrategy = new Factory(System.currentTimeMillis(), request, cacheCandidate).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (responseCache != null) {
                responseCache.trackResponse(this.cacheStrategy);
            }
            if (cacheCandidate != null && this.cacheResponse == null) {
                Util.closeQuietly(cacheCandidate.body());
            }
            if (this.networkRequest != null) {
                if (this.connection == null) {
                    connect(this.networkRequest);
                }
                if (Internal.instance.getOwner(this.connection) == this || Internal.instance.isSpdy(this.connection)) {
                    this.transport = Internal.instance.newTransport(this.connection, this);
                    if (hasRequestBody() && this.requestBodyOut == null) {
                        this.requestBodyOut = this.transport.createRequestBody(request);
                        return;
                    }
                    return;
                }
                throw new AssertionError();
            }
            if (this.connection != null) {
                Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
                this.connection = null;
            }
            if (this.cacheResponse != null) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
            } else {
                this.userResponse = new Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
            }
            if (this.userResponse.body() != null) {
                initContentStream(this.userResponse.body().source());
            }
        }
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private void connect(Request request) throws IOException {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        if (this.routeSelector == null) {
            String uriHost = request.url().getHost();
            if (uriHost == null || uriHost.length() == 0) {
                throw new UnknownHostException(request.url().toString());
            }
            SSLSocketFactory sslSocketFactory = null;
            HostnameVerifier hostnameVerifier = null;
            if (request.isHttps()) {
                sslSocketFactory = this.client.getSslSocketFactory();
                hostnameVerifier = this.client.getHostnameVerifier();
            }
            Address address = new Address(uriHost, Util.getEffectivePort(request.url()), this.client.getSocketFactory(), sslSocketFactory, hostnameVerifier, this.client.getAuthenticator(), this.client.getProxy(), this.client.getProtocols());
            this.routeSelector = new RouteSelector(address, request.uri(), this.client.getProxySelector(), this.client.getConnectionPool(), Dns.DEFAULT, Internal.instance.routeDatabase(this.client));
        }
        this.connection = this.routeSelector.next(request.method());
        Internal.instance.setOwner(this.connection, this);
        if (!Internal.instance.isConnected(this.connection)) {
            Internal.instance.connect(this.connection, this.client.getConnectTimeout(), this.client.getReadTimeout(), this.client.getWriteTimeout(), tunnelRequest(this.connection, request));
            if (Internal.instance.isSpdy(this.connection)) {
                Internal.instance.share(this.client.getConnectionPool(), this.connection);
            }
            Internal.instance.routeDatabase(this.client).connected(this.connection.getRoute());
        }
        Internal.instance.setTimeouts(this.connection, this.client.getReadTimeout(), this.client.getWriteTimeout());
        this.route = this.connection.getRoute();
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

    boolean hasRequestBody() {
        return HttpMethod.hasRequestBody(this.userRequest.method()) && !Util.emptySink().equals(this.requestBodyOut);
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink result = this.bufferedRequestBody;
        if (result != null) {
            return result;
        }
        BufferedSink buffer;
        Sink requestBody = getRequestBody();
        if (requestBody != null) {
            buffer = Okio.buffer(requestBody);
            this.bufferedRequestBody = buffer;
        } else {
            buffer = null;
        }
        return buffer;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public BufferedSource getResponseBody() {
        if (this.userResponse != null) {
            return this.responseBody;
        }
        throw new IllegalStateException();
    }

    public InputStream getResponseBodyBytes() {
        InputStream result = this.responseBodyBytes;
        if (result != null) {
            return result;
        }
        result = Okio.buffer(getResponseBody()).inputStream();
        this.responseBodyBytes = result;
        return result;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public HttpEngine recover(IOException e, Sink requestBodyOut) {
        if (!(this.routeSelector == null || this.connection == null)) {
            this.routeSelector.connectFailed(this.connection, e);
        }
        boolean canRetryRequestBody = requestBodyOut == null || (requestBodyOut instanceof RetryableSink);
        if ((this.routeSelector == null && this.connection == null) || ((this.routeSelector != null && !this.routeSelector.hasNext()) || !isRecoverable(e) || !canRetryRequestBody)) {
            return null;
        }
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, close(), this.routeSelector, (RetryableSink) requestBodyOut, this.priorResponse);
    }

    public HttpEngine recover(IOException e) {
        return recover(e, this.requestBodyOut);
    }

    private boolean isRecoverable(IOException e) {
        boolean sslFailure;
        if ((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) {
            sslFailure = true;
        } else {
            sslFailure = false;
        }
        return (sslFailure || (e instanceof ProtocolException)) ? false : true;
    }

    public Route getRoute() {
        return this.route;
    }

    private void maybeCache() throws IOException {
        InternalCache responseCache = Internal.instance.internalCache(this.client);
        if (responseCache != null) {
            if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
                this.storeRequest = responseCache.put(stripBody(this.userResponse));
            } else if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                try {
                    responseCache.remove(this.networkRequest);
                } catch (IOException e) {
                }
            }
        }
    }

    public void releaseConnection() throws IOException {
        if (!(this.transport == null || this.connection == null)) {
            this.transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public void disconnect() {
        if (this.transport != null) {
            try {
                this.transport.disconnect(this);
            } catch (IOException e) {
            }
        }
    }

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
        if (this.transport == null || this.connection == null || this.transport.canReuseConnection()) {
            if (!(this.connection == null || Internal.instance.clearOwner(this.connection))) {
                this.connection = null;
            }
            Connection result = this.connection;
            this.connection = null;
            return result;
        }
        Util.closeQuietly(this.connection.getSocket());
        this.connection = null;
        return null;
    }

    private void initContentStream(Source transferSource) throws IOException {
        this.responseTransferSource = transferSource;
        if (this.transparentGzip && "gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))) {
            this.userResponse = this.userResponse.newBuilder().removeHeader("Content-Encoding").removeHeader("Content-Length").build();
            this.responseBody = Okio.buffer(new GzipSource(transferSource));
            return;
        }
        this.responseBody = Okio.buffer(transferSource);
    }

    public boolean hasResponseBody() {
        if (this.userRequest.method().equals("HEAD")) {
            return false;
        }
        int responseCode = this.userResponse.code();
        if ((responseCode < 100 || responseCode >= IPhotoView.DEFAULT_ZOOM_DURATION) && responseCode != 204 && responseCode != 304) {
            return true;
        }
        if (OkHeaders.contentLength(this.networkResponse) != -1 || "chunked".equalsIgnoreCase(this.networkResponse.header("Transfer-Encoding"))) {
            return true;
        }
        return false;
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();
        if (request.header("Host") == null) {
            result.header("Host", hostHeader(request.url()));
        }
        if ((this.connection == null || this.connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            result.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            result.header("Accept-Encoding", "gzip");
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(result, cookieHandler.get(request.uri(), OkHeaders.toMultimap(result.build().headers(), null)));
        }
        return result.build();
    }

    public static String hostHeader(URL url) {
        return Util.getEffectivePort(url) != Util.getDefaultPort(url.getProtocol()) ? url.getHost() + ":" + url.getPort() : url.getHost();
    }

    public void readResponse() throws IOException {
        if (this.userResponse == null) {
            if (this.networkRequest == null && this.cacheResponse == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (this.networkRequest != null) {
                if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
                    this.bufferedRequestBody.flush();
                }
                if (this.sentRequestMillis == -1) {
                    if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                        this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.requestBodyOut).contentLength())).build();
                    }
                    this.transport.writeRequestHeaders(this.networkRequest);
                }
                if (this.requestBodyOut != null) {
                    if (this.bufferedRequestBody != null) {
                        this.bufferedRequestBody.close();
                    } else {
                        this.requestBodyOut.close();
                    }
                    if ((this.requestBodyOut instanceof RetryableSink) && !Util.emptySink().equals(this.requestBodyOut)) {
                        this.transport.writeRequestBody((RetryableSink) this.requestBodyOut);
                    }
                }
                this.transport.flushRequest();
                this.networkResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
                Internal.instance.setProtocol(this.connection, this.networkResponse.protocol());
                receiveHeaders(this.networkResponse.headers());
                if (this.cacheResponse != null) {
                    if (validate(this.cacheResponse, this.networkResponse)) {
                        this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), this.networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
                        this.transport.emptyTransferStream();
                        releaseConnection();
                        InternalCache responseCache = Internal.instance.internalCache(this.client);
                        responseCache.trackConditionalCacheHit();
                        responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                        if (this.cacheResponse.body() != null) {
                            initContentStream(this.cacheResponse.body().source());
                            return;
                        }
                        return;
                    }
                    Util.closeQuietly(this.cacheResponse.body());
                }
                this.userResponse = this.networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(this.networkResponse)).build();
                if (hasResponseBody()) {
                    maybeCache();
                    initContentStream(this.transport.getTransferStream(this.storeRequest));
                    return;
                }
                this.responseTransferSource = this.transport.getTransferStream(this.storeRequest);
                this.responseBody = Okio.buffer(this.responseTransferSource);
            }
        }
    }

    private static boolean validate(Response cached, Response network) {
        if (network.code() == 304) {
            return true;
        }
        Date lastModified = cached.headers().getDate("Last-Modified");
        if (lastModified != null) {
            Date networkLastModified = network.headers().getDate("Last-Modified");
            if (networkLastModified != null && networkLastModified.getTime() < lastModified.getTime()) {
                return true;
            }
        }
        return false;
    }

    private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
        int i;
        Headers.Builder result = new Headers.Builder();
        for (i = 0; i < cachedHeaders.size(); i++) {
            String fieldName = cachedHeaders.name(i);
            String value = cachedHeaders.value(i);
            if (!("Warning".equals(fieldName) && value.startsWith("1")) && (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null)) {
                result.add(fieldName, value);
            }
        }
        for (i = 0; i < networkHeaders.size(); i++) {
            fieldName = networkHeaders.name(i);
            if (OkHeaders.isEndToEnd(fieldName)) {
                result.add(fieldName, networkHeaders.value(i));
            }
        }
        return result.build();
    }

    private Request tunnelRequest(Connection connection, Request request) throws IOException {
        if (!connection.getRoute().requiresTunnel()) {
            return null;
        }
        String host = request.url().getHost();
        int port = Util.getEffectivePort(request.url());
        Request.Builder result = new Request.Builder().url(new URL("https", host, port, DeviceFragment.REGEX_BASE)).header("Host", port == Util.getDefaultPort("https") ? host : host + ":" + port).header("Proxy-Connection", "Keep-Alive");
        String userAgent = request.header("User-Agent");
        if (userAgent != null) {
            result.header("User-Agent", userAgent);
        }
        String proxyAuthorization = request.header("Proxy-Authorization");
        if (proxyAuthorization != null) {
            result.header("Proxy-Authorization", proxyAuthorization);
        }
        return result.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.squareup.okhttp.Request followUpRequest() throws java.io.IOException {
        /*
        r9 = this;
        r6 = 0;
        r7 = r9.userResponse;
        if (r7 != 0) goto L_0x000b;
    L_0x0005:
        r6 = new java.lang.IllegalStateException;
        r6.<init>();
        throw r6;
    L_0x000b:
        r7 = r9.getRoute();
        if (r7 == 0) goto L_0x0023;
    L_0x0011:
        r7 = r9.getRoute();
        r4 = r7.getProxy();
    L_0x0019:
        r7 = r9.userResponse;
        r2 = r7.code();
        switch(r2) {
            case 300: goto L_0x0063;
            case 301: goto L_0x0063;
            case 302: goto L_0x0063;
            case 303: goto L_0x0063;
            case 307: goto L_0x0047;
            case 401: goto L_0x003a;
            case 407: goto L_0x002a;
            default: goto L_0x0022;
        };
    L_0x0022:
        return r6;
    L_0x0023:
        r7 = r9.client;
        r4 = r7.getProxy();
        goto L_0x0019;
    L_0x002a:
        r6 = r4.type();
        r7 = java.net.Proxy.Type.HTTP;
        if (r6 == r7) goto L_0x003a;
    L_0x0032:
        r6 = new java.net.ProtocolException;
        r7 = "Received HTTP_PROXY_AUTH (407) code while not using proxy";
        r6.<init>(r7);
        throw r6;
    L_0x003a:
        r6 = r9.client;
        r6 = r6.getAuthenticator();
        r7 = r9.userResponse;
        r6 = com.squareup.okhttp.internal.http.OkHeaders.processAuthHeader(r6, r7, r4);
        goto L_0x0022;
    L_0x0047:
        r7 = r9.userRequest;
        r7 = r7.method();
        r8 = "GET";
        r7 = r7.equals(r8);
        if (r7 != 0) goto L_0x0063;
    L_0x0055:
        r7 = r9.userRequest;
        r7 = r7.method();
        r8 = "HEAD";
        r7 = r7.equals(r8);
        if (r7 == 0) goto L_0x0022;
    L_0x0063:
        r7 = r9.userResponse;
        r8 = "Location";
        r0 = r7.header(r8);
        if (r0 == 0) goto L_0x0022;
    L_0x006d:
        r5 = new java.net.URL;
        r7 = r9.userRequest;
        r7 = r7.url();
        r5.<init>(r7, r0);
        r7 = r5.getProtocol();
        r8 = "https";
        r7 = r7.equals(r8);
        if (r7 != 0) goto L_0x0090;
    L_0x0084:
        r7 = r5.getProtocol();
        r8 = "http";
        r7 = r7.equals(r8);
        if (r7 == 0) goto L_0x0022;
    L_0x0090:
        r7 = r5.getProtocol();
        r8 = r9.userRequest;
        r8 = r8.url();
        r8 = r8.getProtocol();
        r3 = r7.equals(r8);
        if (r3 != 0) goto L_0x00ac;
    L_0x00a4:
        r7 = r9.client;
        r7 = r7.getFollowSslRedirects();
        if (r7 == 0) goto L_0x0022;
    L_0x00ac:
        r7 = r9.userRequest;
        r1 = r7.newBuilder();
        r7 = r9.userRequest;
        r7 = r7.method();
        r7 = com.squareup.okhttp.internal.http.HttpMethod.hasRequestBody(r7);
        if (r7 == 0) goto L_0x00d2;
    L_0x00be:
        r7 = "GET";
        r1.method(r7, r6);
        r6 = "Transfer-Encoding";
        r1.removeHeader(r6);
        r6 = "Content-Length";
        r1.removeHeader(r6);
        r6 = "Content-Type";
        r1.removeHeader(r6);
    L_0x00d2:
        r6 = r9.sameConnection(r5);
        if (r6 != 0) goto L_0x00dd;
    L_0x00d8:
        r6 = "Authorization";
        r1.removeHeader(r6);
    L_0x00dd:
        r6 = r1.url(r5);
        r6 = r6.build();
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.HttpEngine.followUpRequest():com.squareup.okhttp.Request");
    }

    public boolean sameConnection(URL followUp) {
        URL url = this.userRequest.url();
        return url.getHost().equals(followUp.getHost()) && Util.getEffectivePort(url) == Util.getEffectivePort(followUp) && url.getProtocol().equals(followUp.getProtocol());
    }
}
