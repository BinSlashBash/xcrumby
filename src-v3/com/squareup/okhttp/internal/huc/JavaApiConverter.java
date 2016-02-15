package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.BufferedSource;
import okio.Okio;

public final class JavaApiConverter {

    /* renamed from: com.squareup.okhttp.internal.huc.JavaApiConverter.1 */
    static class C05981 extends SecureCacheResponse {
        final /* synthetic */ ResponseBody val$body;
        final /* synthetic */ Handshake val$handshake;
        final /* synthetic */ Headers val$headers;
        final /* synthetic */ Response val$response;

        C05981(Handshake handshake, Headers headers, Response response, ResponseBody responseBody) {
            this.val$handshake = handshake;
            this.val$headers = headers;
            this.val$response = response;
            this.val$body = responseBody;
        }

        public String getCipherSuite() {
            return this.val$handshake != null ? this.val$handshake.cipherSuite() : null;
        }

        public List<Certificate> getLocalCertificateChain() {
            if (this.val$handshake == null) {
                return null;
            }
            List<Certificate> certificates = this.val$handshake.localCertificates();
            if (certificates.size() <= 0) {
                certificates = null;
            }
            return certificates;
        }

        public List<Certificate> getServerCertificateChain() throws SSLPeerUnverifiedException {
            if (this.val$handshake == null) {
                return null;
            }
            List<Certificate> certificates = this.val$handshake.peerCertificates();
            if (certificates.size() <= 0) {
                certificates = null;
            }
            return certificates;
        }

        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            if (this.val$handshake == null) {
                return null;
            }
            return this.val$handshake.peerPrincipal();
        }

        public Principal getLocalPrincipal() {
            if (this.val$handshake == null) {
                return null;
            }
            return this.val$handshake.localPrincipal();
        }

        public Map<String, List<String>> getHeaders() throws IOException {
            return OkHeaders.toMultimap(this.val$headers, StatusLine.get(this.val$response).toString());
        }

        public InputStream getBody() throws IOException {
            if (this.val$body == null) {
                return null;
            }
            return this.val$body.byteStream();
        }
    }

    /* renamed from: com.squareup.okhttp.internal.huc.JavaApiConverter.2 */
    static class C05992 extends CacheResponse {
        final /* synthetic */ ResponseBody val$body;
        final /* synthetic */ Headers val$headers;
        final /* synthetic */ Response val$response;

        C05992(Headers headers, Response response, ResponseBody responseBody) {
            this.val$headers = headers;
            this.val$response = response;
            this.val$body = responseBody;
        }

        public Map<String, List<String>> getHeaders() throws IOException {
            return OkHeaders.toMultimap(this.val$headers, StatusLine.get(this.val$response).toString());
        }

        public InputStream getBody() throws IOException {
            if (this.val$body == null) {
                return null;
            }
            return this.val$body.byteStream();
        }
    }

    private static final class CacheHttpURLConnection extends HttpURLConnection {
        private final Request request;
        private final Response response;

        public CacheHttpURLConnection(Response response) {
            boolean z = true;
            super(response.request().url());
            this.request = response.request();
            this.response = response;
            this.connected = true;
            if (response.body() != null) {
                z = false;
            }
            this.doOutput = z;
            this.method = this.request.method();
        }

        public void connect() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void disconnect() {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void setRequestProperty(String key, String value) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void addRequestProperty(String key, String value) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public String getRequestProperty(String key) {
            return this.request.header(key);
        }

        public Map<String, List<String>> getRequestProperties() {
            throw JavaApiConverter.throwRequestHeaderAccessException();
        }

        public void setFixedLengthStreamingMode(int contentLength) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void setFixedLengthStreamingMode(long contentLength) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void setChunkedStreamingMode(int chunklen) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void setInstanceFollowRedirects(boolean followRedirects) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public boolean getInstanceFollowRedirects() {
            return super.getInstanceFollowRedirects();
        }

        public void setRequestMethod(String method) throws ProtocolException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public String getRequestMethod() {
            return this.request.method();
        }

        public String getHeaderFieldKey(int position) {
            if (position < 0) {
                throw new IllegalArgumentException("Invalid header index: " + position);
            } else if (position == 0) {
                return null;
            } else {
                return this.response.headers().name(position - 1);
            }
        }

        public String getHeaderField(int position) {
            if (position < 0) {
                throw new IllegalArgumentException("Invalid header index: " + position);
            } else if (position == 0) {
                return StatusLine.get(this.response).toString();
            } else {
                return this.response.headers().value(position - 1);
            }
        }

        public String getHeaderField(String fieldName) {
            return fieldName == null ? StatusLine.get(this.response).toString() : this.response.headers().get(fieldName);
        }

        public Map<String, List<String>> getHeaderFields() {
            return OkHeaders.toMultimap(this.response.headers(), StatusLine.get(this.response).toString());
        }

        public int getResponseCode() throws IOException {
            return this.response.code();
        }

        public String getResponseMessage() throws IOException {
            return this.response.message();
        }

        public InputStream getErrorStream() {
            return null;
        }

        public boolean usingProxy() {
            return false;
        }

        public void setConnectTimeout(int timeout) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public int getConnectTimeout() {
            return 0;
        }

        public void setReadTimeout(int timeout) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public int getReadTimeout() {
            return 0;
        }

        public Object getContent() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        public Object getContent(Class[] classes) throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        public InputStream getInputStream() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        public OutputStream getOutputStream() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public void setDoInput(boolean doInput) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public boolean getDoInput() {
            return true;
        }

        public void setDoOutput(boolean doOutput) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public boolean getDoOutput() {
            return this.request.body() != null;
        }

        public void setAllowUserInteraction(boolean allowUserInteraction) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public boolean getAllowUserInteraction() {
            return false;
        }

        public void setUseCaches(boolean useCaches) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public boolean getUseCaches() {
            return super.getUseCaches();
        }

        public void setIfModifiedSince(long ifModifiedSince) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public long getIfModifiedSince() {
            return 0;
        }

        public boolean getDefaultUseCaches() {
            return super.getDefaultUseCaches();
        }

        public void setDefaultUseCaches(boolean defaultUseCaches) {
            super.setDefaultUseCaches(defaultUseCaches);
        }
    }

    /* renamed from: com.squareup.okhttp.internal.huc.JavaApiConverter.3 */
    static class C11513 extends ResponseBody {
        final /* synthetic */ Headers val$okHeaders;
        final /* synthetic */ BufferedSource val$source;

        C11513(Headers headers, BufferedSource bufferedSource) {
            this.val$okHeaders = headers;
            this.val$source = bufferedSource;
        }

        public MediaType contentType() {
            String contentTypeHeader = this.val$okHeaders.get("Content-Type");
            return contentTypeHeader == null ? null : MediaType.parse(contentTypeHeader);
        }

        public long contentLength() {
            return OkHeaders.contentLength(this.val$okHeaders);
        }

        public BufferedSource source() {
            return this.val$source;
        }
    }

    private static final class CacheHttpsURLConnection extends DelegatingHttpsURLConnection {
        private final CacheHttpURLConnection delegate;

        public CacheHttpsURLConnection(CacheHttpURLConnection delegate) {
            super(delegate);
            this.delegate = delegate;
        }

        protected Handshake handshake() {
            return this.delegate.response.handshake();
        }

        public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public HostnameVerifier getHostnameVerifier() {
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        public void setSSLSocketFactory(SSLSocketFactory socketFactory) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        public SSLSocketFactory getSSLSocketFactory() {
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        public long getContentLengthLong() {
            return this.delegate.getContentLengthLong();
        }

        public void setFixedLengthStreamingMode(long contentLength) {
            this.delegate.setFixedLengthStreamingMode(contentLength);
        }

        public long getHeaderFieldLong(String field, long defaultValue) {
            return this.delegate.getHeaderFieldLong(field, defaultValue);
        }
    }

    private JavaApiConverter() {
    }

    public static Response createOkResponse(URI uri, URLConnection urlConnection) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
        Builder okResponseBuilder = new Builder();
        okResponseBuilder.request(createOkRequest(uri, httpUrlConnection.getRequestMethod(), null));
        StatusLine statusLine = StatusLine.parse(extractStatusLine(httpUrlConnection));
        okResponseBuilder.protocol(statusLine.protocol);
        okResponseBuilder.code(statusLine.code);
        okResponseBuilder.message(statusLine.message);
        Headers okHeaders = extractOkResponseHeaders(httpUrlConnection);
        okResponseBuilder.headers(okHeaders);
        okResponseBuilder.body(createOkBody(okHeaders, urlConnection.getInputStream()));
        if (httpUrlConnection instanceof HttpsURLConnection) {
            Certificate[] peerCertificates;
            HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) httpUrlConnection;
            try {
                peerCertificates = httpsUrlConnection.getServerCertificates();
            } catch (SSLPeerUnverifiedException e) {
                peerCertificates = null;
            }
            okResponseBuilder.handshake(Handshake.get(httpsUrlConnection.getCipherSuite(), nullSafeImmutableList(peerCertificates), nullSafeImmutableList(httpsUrlConnection.getLocalCertificates())));
        }
        return okResponseBuilder.build();
    }

    static Response createOkResponse(Request request, CacheResponse javaResponse) throws IOException {
        Builder okResponseBuilder = new Builder();
        okResponseBuilder.request(request);
        StatusLine statusLine = StatusLine.parse(extractStatusLine(javaResponse));
        okResponseBuilder.protocol(statusLine.protocol);
        okResponseBuilder.code(statusLine.code);
        okResponseBuilder.message(statusLine.message);
        Headers okHeaders = extractOkHeaders(javaResponse);
        okResponseBuilder.headers(okHeaders);
        okResponseBuilder.body(createOkBody(okHeaders, javaResponse.getBody()));
        if (javaResponse instanceof SecureCacheResponse) {
            List<Certificate> peerCertificates;
            SecureCacheResponse javaSecureCacheResponse = (SecureCacheResponse) javaResponse;
            try {
                peerCertificates = javaSecureCacheResponse.getServerCertificateChain();
            } catch (SSLPeerUnverifiedException e) {
                peerCertificates = Collections.emptyList();
            }
            List<Certificate> localCertificates = javaSecureCacheResponse.getLocalCertificateChain();
            if (localCertificates == null) {
                localCertificates = Collections.emptyList();
            }
            okResponseBuilder.handshake(Handshake.get(javaSecureCacheResponse.getCipherSuite(), peerCertificates, localCertificates));
        }
        return okResponseBuilder.build();
    }

    public static Request createOkRequest(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
        Request.Builder builder = new Request.Builder().url(uri.toString()).method(requestMethod, null);
        if (requestHeaders != null) {
            builder.headers(extractOkHeaders((Map) requestHeaders));
        }
        return builder.build();
    }

    public static CacheResponse createJavaCacheResponse(Response response) {
        Headers headers = response.headers();
        ResponseBody body = response.body();
        return response.request().isHttps() ? new C05981(response.handshake(), headers, response, body) : new C05992(headers, response, body);
    }

    static HttpURLConnection createJavaUrlConnection(Response okResponse) {
        if (okResponse.request().isHttps()) {
            return new CacheHttpsURLConnection(new CacheHttpURLConnection(okResponse));
        }
        return new CacheHttpURLConnection(okResponse);
    }

    static Map<String, List<String>> extractJavaHeaders(Request request) {
        return OkHeaders.toMultimap(request.headers(), null);
    }

    private static Headers extractOkHeaders(CacheResponse javaResponse) throws IOException {
        return extractOkHeaders(javaResponse.getHeaders());
    }

    private static Headers extractOkResponseHeaders(HttpURLConnection httpUrlConnection) {
        return extractOkHeaders(httpUrlConnection.getHeaderFields());
    }

    static Headers extractOkHeaders(Map<String, List<String>> javaHeaders) {
        Headers.Builder okHeadersBuilder = new Headers.Builder();
        for (Entry<String, List<String>> javaHeader : javaHeaders.entrySet()) {
            String name = (String) javaHeader.getKey();
            if (name != null) {
                for (String value : (List) javaHeader.getValue()) {
                    okHeadersBuilder.add(name, value);
                }
            }
        }
        return okHeadersBuilder.build();
    }

    private static String extractStatusLine(HttpURLConnection httpUrlConnection) {
        return httpUrlConnection.getHeaderField(null);
    }

    private static String extractStatusLine(CacheResponse javaResponse) throws IOException {
        return extractStatusLine(javaResponse.getHeaders());
    }

    static String extractStatusLine(Map<String, List<String>> javaResponseHeaders) {
        List<String> values = (List) javaResponseHeaders.get(null);
        if (values == null || values.size() == 0) {
            return null;
        }
        return (String) values.get(0);
    }

    private static ResponseBody createOkBody(Headers okHeaders, InputStream body) {
        return new C11513(okHeaders, Okio.buffer(Okio.source(body)));
    }

    private static RuntimeException throwRequestModificationException() {
        throw new UnsupportedOperationException("ResponseCache cannot modify the request.");
    }

    private static RuntimeException throwRequestHeaderAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access request headers");
    }

    private static RuntimeException throwRequestSslAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access SSL internals");
    }

    private static RuntimeException throwResponseBodyAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access the response body.");
    }

    private static <T> List<T> nullSafeImmutableList(T[] elements) {
        return elements == null ? Collections.emptyList() : Util.immutableList((Object[]) elements);
    }
}
