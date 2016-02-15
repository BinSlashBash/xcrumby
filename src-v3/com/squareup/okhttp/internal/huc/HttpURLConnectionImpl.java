package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import oauth.signpost.OAuth;
import okio.BufferedSink;

public class HttpURLConnectionImpl extends HttpURLConnection {
    final OkHttpClient client;
    private long fixedContentLength;
    Handshake handshake;
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private int redirectionCount;
    private Builder requestHeaders;
    private Headers responseHeaders;
    private Route route;

    public HttpURLConnectionImpl(URL url, OkHttpClient client) {
        super(url);
        this.requestHeaders = new Builder();
        this.fixedContentLength = -1;
        this.client = client;
    }

    public final void connect() throws IOException {
        initHttpEngine();
        do {
        } while (!execute(false));
    }

    public final void disconnect() {
        if (this.httpEngine != null) {
            this.httpEngine.disconnect();
        }
    }

    public final InputStream getErrorStream() {
        InputStream inputStream = null;
        try {
            HttpEngine response = getResponse();
            if (response.hasResponseBody() && response.getResponse().code() >= 400) {
                inputStream = response.getResponseBodyBytes();
            }
        } catch (IOException e) {
        }
        return inputStream;
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = getResponse().getResponse();
            this.responseHeaders = response.headers().newBuilder().add(Platform.get().getPrefix() + "-Response-Source", responseSourceHeader(response)).build();
        }
        return this.responseHeaders;
    }

    private static String responseSourceHeader(Response response) {
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            return "CACHE " + response.code();
        } else if (response.cacheResponse() == null) {
            return "NETWORK " + response.code();
        } else {
            return "CONDITIONAL_CACHE " + response.networkResponse().code();
        }
    }

    public final String getHeaderField(int position) {
        try {
            return getHeaders().value(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderField(String fieldName) {
        if (fieldName != null) {
            return getHeaders().get(fieldName);
        }
        try {
            return StatusLine.get(getResponse().getResponse()).toString();
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderFieldKey(int position) {
        try {
            return getHeaders().name(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final Map<String, List<String>> getHeaderFields() {
        try {
            return OkHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse().getResponse()).toString());
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    public final Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return OkHeaders.toMultimap(this.requestHeaders.build(), null);
        }
        throw new IllegalStateException("Cannot access request header fields after connection is set");
    }

    public final InputStream getInputStream() throws IOException {
        if (this.doInput) {
            HttpEngine response = getResponse();
            if (getResponseCode() >= 400) {
                throw new FileNotFoundException(this.url.toString());
            }
            InputStream result = response.getResponseBodyBytes();
            if (result != null) {
                return result;
            }
            throw new ProtocolException("No response body exists; responseCode=" + getResponseCode());
        }
        throw new ProtocolException("This protocol does not support input");
    }

    public final OutputStream getOutputStream() throws IOException {
        connect();
        BufferedSink sink = this.httpEngine.getBufferedRequestBody();
        if (sink == null) {
            throw new ProtocolException("method does not support a request body: " + this.method);
        } else if (!this.httpEngine.hasResponse()) {
            return sink.outputStream();
        } else {
            throw new ProtocolException("cannot write request body after response has been read");
        }
    }

    public final Permission getPermission() throws IOException {
        String hostName = getURL().getHost();
        int hostPort = Util.getEffectivePort(getURL());
        if (usingProxy()) {
            InetSocketAddress proxyAddress = (InetSocketAddress) this.client.getProxy().address();
            hostName = proxyAddress.getHostName();
            hostPort = proxyAddress.getPort();
        }
        return new SocketPermission(hostName + ":" + hostPort, "connect, resolve");
    }

    public final String getRequestProperty(String field) {
        if (field == null) {
            return null;
        }
        return this.requestHeaders.get(field);
    }

    public void setConnectTimeout(int timeoutMillis) {
        this.client.setConnectTimeout((long) timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public int getConnectTimeout() {
        return this.client.getConnectTimeout();
    }

    public void setReadTimeout(int timeoutMillis) {
        this.client.setReadTimeout((long) timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public int getReadTimeout() {
        return this.client.getReadTimeout();
    }

    private void initHttpEngine() throws IOException {
        RetryableSink requestBody = null;
        if (this.httpEngineFailure != null) {
            throw this.httpEngineFailure;
        } else if (this.httpEngine == null) {
            this.connected = true;
            try {
                if (this.doOutput) {
                    if (this.method.equals("GET")) {
                        this.method = "POST";
                    } else if (!HttpMethod.hasRequestBody(this.method)) {
                        throw new ProtocolException(this.method + " does not support writing");
                    }
                }
                if (this.doOutput && this.fixedContentLength == 0) {
                    requestBody = Util.emptySink();
                }
                this.httpEngine = newHttpEngine(this.method, null, requestBody, null);
            } catch (IOException e) {
                this.httpEngineFailure = e;
                throw e;
            }
        }
    }

    private HttpEngine newHttpEngine(String method, Connection connection, RetryableSink requestBody, Response priorResponse) {
        Request.Builder builder = new Request.Builder().url(getURL()).method(method, null);
        Headers headers = this.requestHeaders.build();
        for (int i = 0; i < headers.size(); i++) {
            builder.addHeader(headers.name(i), headers.value(i));
        }
        boolean bufferRequestBody = false;
        if (HttpMethod.hasRequestBody(method)) {
            if (this.fixedContentLength != -1) {
                builder.header("Content-Length", Long.toString(this.fixedContentLength));
            } else if (this.chunkLength > 0) {
                builder.header("Transfer-Encoding", "chunked");
            } else {
                bufferRequestBody = true;
            }
            if (headers.get("Content-Type") == null) {
                builder.header("Content-Type", OAuth.FORM_ENCODED);
            }
        }
        if (headers.get("User-Agent") == null) {
            builder.header("User-Agent", defaultUserAgent());
        }
        Request request = builder.build();
        OkHttpClient engineClient = this.client;
        if (!(Internal.instance.internalCache(engineClient) == null || getUseCaches())) {
            engineClient = this.client.clone().setCache(null);
        }
        return new HttpEngine(engineClient, request, bufferRequestBody, connection, null, requestBody, priorResponse);
    }

    private String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : "Java" + System.getProperty("java.version");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.internal.http.HttpEngine getResponse() throws java.io.IOException {
        /*
        r7 = this;
        r7.initHttpEngine();
        r4 = r7.httpEngine;
        r4 = r4.hasResponse();
        if (r4 == 0) goto L_0x000e;
    L_0x000b:
        r4 = r7.httpEngine;
    L_0x000d:
        return r4;
    L_0x000e:
        r4 = 1;
        r4 = r7.execute(r4);
        if (r4 == 0) goto L_0x000e;
    L_0x0015:
        r4 = r7.httpEngine;
        r3 = r4.getResponse();
        r4 = r7.httpEngine;
        r1 = r4.followUpRequest();
        if (r1 != 0) goto L_0x002b;
    L_0x0023:
        r4 = r7.httpEngine;
        r4.releaseConnection();
        r4 = r7.httpEngine;
        goto L_0x000d;
    L_0x002b:
        r4 = r3.isRedirect();
        if (r4 == 0) goto L_0x0056;
    L_0x0031:
        r4 = r7.redirectionCount;
        r4 = r4 + 1;
        r7.redirectionCount = r4;
        r5 = 20;
        if (r4 <= r5) goto L_0x0056;
    L_0x003b:
        r4 = new java.net.ProtocolException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Too many redirects: ";
        r5 = r5.append(r6);
        r6 = r7.redirectionCount;
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0056:
        r4 = r1.url();
        r7.url = r4;
        r4 = r1.headers();
        r4 = r4.newBuilder();
        r7.requestHeaders = r4;
        r4 = r7.httpEngine;
        r2 = r4.getRequestBody();
        r4 = r1.method();
        r5 = r7.method;
        r4 = r4.equals(r5);
        if (r4 != 0) goto L_0x0079;
    L_0x0078:
        r2 = 0;
    L_0x0079:
        if (r2 == 0) goto L_0x0089;
    L_0x007b:
        r4 = r2 instanceof com.squareup.okhttp.internal.http.RetryableSink;
        if (r4 != 0) goto L_0x0089;
    L_0x007f:
        r4 = new java.net.HttpRetryException;
        r5 = "Cannot retry streamed HTTP body";
        r6 = r7.responseCode;
        r4.<init>(r5, r6);
        throw r4;
    L_0x0089:
        r4 = r7.httpEngine;
        r5 = r1.url();
        r4 = r4.sameConnection(r5);
        if (r4 != 0) goto L_0x009a;
    L_0x0095:
        r4 = r7.httpEngine;
        r4.releaseConnection();
    L_0x009a:
        r4 = r7.httpEngine;
        r0 = r4.close();
        r4 = r1.method();
        r2 = (com.squareup.okhttp.internal.http.RetryableSink) r2;
        r4 = r7.newHttpEngine(r4, r0, r2, r3);
        r7.httpEngine = r4;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.huc.HttpURLConnectionImpl.getResponse():com.squareup.okhttp.internal.http.HttpEngine");
    }

    private boolean execute(boolean readResponse) throws IOException {
        try {
            this.httpEngine.sendRequest();
            this.route = this.httpEngine.getRoute();
            this.handshake = this.httpEngine.getConnection() != null ? this.httpEngine.getConnection().getHandshake() : null;
            if (readResponse) {
                this.httpEngine.readResponse();
            }
            return true;
        } catch (IOException e) {
            HttpEngine retryEngine = this.httpEngine.recover(e);
            if (retryEngine != null) {
                this.httpEngine = retryEngine;
                return false;
            }
            this.httpEngineFailure = e;
            throw e;
        }
    }

    public final boolean usingProxy() {
        Proxy proxy = this.route != null ? this.route.getProxy() : this.client.getProxy();
        return (proxy == null || proxy.type() == Type.DIRECT) ? false : true;
    }

    public String getResponseMessage() throws IOException {
        return getResponse().getResponse().message();
    }

    public final int getResponseCode() throws IOException {
        return getResponse().getResponse().code();
    }

    public final void setRequestProperty(String field, String newValue) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else if (newValue == null) {
            Platform.get().logW("Ignoring header " + field + " because its value was null.");
        } else if ("X-Android-Transports".equals(field) || "X-Android-Protocols".equals(field)) {
            setProtocols(newValue, false);
        } else {
            this.requestHeaders.set(field, newValue);
        }
    }

    public void setIfModifiedSince(long newValue) {
        super.setIfModifiedSince(newValue);
        if (this.ifModifiedSince != 0) {
            this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
        } else {
            this.requestHeaders.removeAll("If-Modified-Since");
        }
    }

    public final void addRequestProperty(String field, String value) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else if (value == null) {
            Platform.get().logW("Ignoring header " + field + " because its value was null.");
        } else if ("X-Android-Transports".equals(field) || "X-Android-Protocols".equals(field)) {
            setProtocols(value, true);
        } else {
            this.requestHeaders.add(field, value);
        }
    }

    private void setProtocols(String protocolsString, boolean append) {
        List<Protocol> protocolsList = new ArrayList();
        if (append) {
            protocolsList.addAll(this.client.getProtocols());
        }
        String[] arr$ = protocolsString.split(",", -1);
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            try {
                protocolsList.add(Protocol.get(arr$[i$]));
                i$++;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        this.client.setProtocols(protocolsList);
    }

    public void setRequestMethod(String method) throws ProtocolException {
        if (HttpMethod.METHODS.contains(method)) {
            this.method = method;
            return;
        }
        throw new ProtocolException("Expected one of " + HttpMethod.METHODS + " but was " + method);
    }

    public void setFixedLengthStreamingMode(int contentLength) {
        setFixedLengthStreamingMode((long) contentLength);
    }

    public void setFixedLengthStreamingMode(long contentLength) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        } else if (this.chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        } else if (contentLength < 0) {
            throw new IllegalArgumentException("contentLength < 0");
        } else {
            this.fixedContentLength = contentLength;
            this.fixedContentLength = (int) Math.min(contentLength, 2147483647L);
        }
    }
}
