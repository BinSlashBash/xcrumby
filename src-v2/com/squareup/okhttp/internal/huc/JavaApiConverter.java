/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.huc.DelegatingHttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.BufferedSource;
import okio.Okio;

public final class JavaApiConverter {
    private JavaApiConverter() {
    }

    public static CacheResponse createJavaCacheResponse(final Response response) {
        final Headers headers = response.headers();
        final ResponseBody responseBody = response.body();
        if (response.request().isHttps()) {
            return new SecureCacheResponse(response.handshake(), headers, response, responseBody){
                final /* synthetic */ ResponseBody val$body;
                final /* synthetic */ Handshake val$handshake;
                final /* synthetic */ Headers val$headers;
                final /* synthetic */ Response val$response;

                @Override
                public InputStream getBody() throws IOException {
                    if (this.val$body == null) {
                        return null;
                    }
                    return this.val$body.byteStream();
                }

                @Override
                public String getCipherSuite() {
                    if (this.val$handshake != null) {
                        return this.val$handshake.cipherSuite();
                    }
                    return null;
                }

                @Override
                public Map<String, List<String>> getHeaders() throws IOException {
                    return OkHeaders.toMultimap(this.val$headers, StatusLine.get(this.val$response).toString());
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public List<Certificate> getLocalCertificateChain() {
                    if (this.val$handshake == null) {
                        return null;
                    }
                    List<Certificate> list = this.val$handshake.localCertificates();
                    if (list.size() <= 0) return null;
                    return list;
                }

                @Override
                public Principal getLocalPrincipal() {
                    if (this.val$handshake == null) {
                        return null;
                    }
                    return this.val$handshake.localPrincipal();
                }

                @Override
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    if (this.val$handshake == null) {
                        return null;
                    }
                    return this.val$handshake.peerPrincipal();
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public List<Certificate> getServerCertificateChain() throws SSLPeerUnverifiedException {
                    if (this.val$handshake == null) {
                        return null;
                    }
                    List<Certificate> list = this.val$handshake.peerCertificates();
                    if (list.size() <= 0) return null;
                    return list;
                }
            };
        }
        return new CacheResponse(){

            @Override
            public InputStream getBody() throws IOException {
                if (responseBody == null) {
                    return null;
                }
                return responseBody.byteStream();
            }

            @Override
            public Map<String, List<String>> getHeaders() throws IOException {
                return OkHeaders.toMultimap(headers, StatusLine.get(response).toString());
            }
        };
    }

    static HttpURLConnection createJavaUrlConnection(Response response) {
        if (response.request().isHttps()) {
            return new CacheHttpsURLConnection(new CacheHttpURLConnection(response));
        }
        return new CacheHttpURLConnection(response);
    }

    private static ResponseBody createOkBody(final Headers headers, InputStream inputStream) {
        return new ResponseBody(Okio.buffer(Okio.source(inputStream))){
            final /* synthetic */ BufferedSource val$source;

            @Override
            public long contentLength() {
                return OkHeaders.contentLength(headers);
            }

            @Override
            public MediaType contentType() {
                String string2 = headers.get("Content-Type");
                if (string2 == null) {
                    return null;
                }
                return MediaType.parse(string2);
            }

            @Override
            public BufferedSource source() {
                return this.val$source;
            }
        };
    }

    public static Request createOkRequest(URI object, String string2, Map<String, List<String>> map) {
        object = new Request.Builder().url(object.toString()).method(string2, null);
        if (map != null) {
            object.headers(JavaApiConverter.extractOkHeaders(map));
        }
        return object.build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Response createOkResponse(Request list, CacheResponse list2) throws IOException {
        List list3;
        Response.Builder builder = new Response.Builder();
        builder.request((Request)((Object)list));
        StatusLine statusLine = StatusLine.parse(JavaApiConverter.extractStatusLine((CacheResponse)((Object)list3)));
        builder.protocol(statusLine.protocol);
        builder.code(statusLine.code);
        builder.message(statusLine.message);
        Headers headers = JavaApiConverter.extractOkHeaders((CacheResponse)((Object)list3));
        builder.headers(headers);
        builder.body(JavaApiConverter.createOkBody(headers, list3.getBody()));
        if (list3 instanceof SecureCacheResponse) {
            void var0_4;
            List<Certificate> list4;
            SecureCacheResponse secureCacheResponse = (SecureCacheResponse)((Object)list3);
            try {
                List<Certificate> list5 = secureCacheResponse.getServerCertificateChain();
            }
            catch (SSLPeerUnverifiedException var0_5) {
                List list6 = Collections.emptyList();
            }
            list3 = list4 = secureCacheResponse.getLocalCertificateChain();
            if (list4 == null) {
                list3 = Collections.emptyList();
            }
            builder.handshake(Handshake.get(secureCacheResponse.getCipherSuite(), var0_4, list3));
        }
        return builder.build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Response createOkResponse(URI arrcertificate, URLConnection uRLConnection) throws IOException {
        Certificate[] arrcertificate2 = (Certificate[])uRLConnection;
        Response.Builder builder = new Response.Builder();
        builder.request(JavaApiConverter.createOkRequest((URI)arrcertificate, arrcertificate2.getRequestMethod(), null));
        arrcertificate = StatusLine.parse(JavaApiConverter.extractStatusLine((HttpURLConnection)arrcertificate2));
        builder.protocol(arrcertificate.protocol);
        builder.code(arrcertificate.code);
        builder.message(arrcertificate.message);
        arrcertificate = JavaApiConverter.extractOkResponseHeaders((HttpURLConnection)arrcertificate2);
        builder.headers((Headers)arrcertificate);
        builder.body(JavaApiConverter.createOkBody((Headers)arrcertificate, uRLConnection.getInputStream()));
        if (arrcertificate2 instanceof HttpsURLConnection) {
            uRLConnection = (HttpsURLConnection)arrcertificate2;
            try {
                arrcertificate = uRLConnection.getServerCertificates();
            }
            catch (SSLPeerUnverifiedException var0_1) {
                arrcertificate = null;
            }
            arrcertificate2 = uRLConnection.getLocalCertificates();
            builder.handshake(Handshake.get(uRLConnection.getCipherSuite(), JavaApiConverter.nullSafeImmutableList(arrcertificate), JavaApiConverter.nullSafeImmutableList(arrcertificate2)));
        }
        return builder.build();
    }

    static Map<String, List<String>> extractJavaHeaders(Request request) {
        return OkHeaders.toMultimap(request.headers(), null);
    }

    private static Headers extractOkHeaders(CacheResponse cacheResponse) throws IOException {
        return JavaApiConverter.extractOkHeaders(cacheResponse.getHeaders());
    }

    static Headers extractOkHeaders(Map<String, List<String>> object) {
        Headers.Builder builder = new Headers.Builder();
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            String string2 = (String)object2.getKey();
            if (string2 == null) continue;
            object2 = ((List)object2.getValue()).iterator();
            while (object2.hasNext()) {
                builder.add(string2, (String)object2.next());
            }
        }
        return builder.build();
    }

    private static Headers extractOkResponseHeaders(HttpURLConnection httpURLConnection) {
        return JavaApiConverter.extractOkHeaders(httpURLConnection.getHeaderFields());
    }

    private static String extractStatusLine(CacheResponse cacheResponse) throws IOException {
        return JavaApiConverter.extractStatusLine(cacheResponse.getHeaders());
    }

    private static String extractStatusLine(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getHeaderField(null);
    }

    static String extractStatusLine(Map<String, List<String>> object) {
        if ((object = object.get(null)) == null || object.size() == 0) {
            return null;
        }
        return (String)object.get(0);
    }

    private static <T> List<T> nullSafeImmutableList(T[] arrT) {
        if (arrT == null) {
            return Collections.emptyList();
        }
        return Util.immutableList(arrT);
    }

    private static RuntimeException throwRequestHeaderAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access request headers");
    }

    private static RuntimeException throwRequestModificationException() {
        throw new UnsupportedOperationException("ResponseCache cannot modify the request.");
    }

    private static RuntimeException throwRequestSslAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access SSL internals");
    }

    private static RuntimeException throwResponseBodyAccessException() {
        throw new UnsupportedOperationException("ResponseCache cannot access the response body.");
    }

    private static final class CacheHttpURLConnection
    extends HttpURLConnection {
        private final Request request;
        private final Response response;

        /*
         * Enabled aggressive block sorting
         */
        public CacheHttpURLConnection(Response response) {
            boolean bl2 = true;
            super(response.request().url());
            this.request = response.request();
            this.response = response;
            this.connected = true;
            if (response.body() != null) {
                bl2 = false;
            }
            this.doOutput = bl2;
            this.method = this.request.method();
        }

        @Override
        public void addRequestProperty(String string2, String string3) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void connect() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void disconnect() {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public boolean getAllowUserInteraction() {
            return false;
        }

        @Override
        public int getConnectTimeout() {
            return 0;
        }

        @Override
        public Object getContent() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public Object getContent(Class[] arrclass) throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public boolean getDefaultUseCaches() {
            return super.getDefaultUseCaches();
        }

        @Override
        public boolean getDoInput() {
            return true;
        }

        @Override
        public boolean getDoOutput() {
            if (this.request.body() != null) {
                return true;
            }
            return false;
        }

        @Override
        public InputStream getErrorStream() {
            return null;
        }

        @Override
        public String getHeaderField(int n2) {
            if (n2 < 0) {
                throw new IllegalArgumentException("Invalid header index: " + n2);
            }
            if (n2 == 0) {
                return StatusLine.get(this.response).toString();
            }
            return this.response.headers().value(n2 - 1);
        }

        @Override
        public String getHeaderField(String string2) {
            if (string2 == null) {
                return StatusLine.get(this.response).toString();
            }
            return this.response.headers().get(string2);
        }

        @Override
        public String getHeaderFieldKey(int n2) {
            if (n2 < 0) {
                throw new IllegalArgumentException("Invalid header index: " + n2);
            }
            if (n2 == 0) {
                return null;
            }
            return this.response.headers().name(n2 - 1);
        }

        @Override
        public Map<String, List<String>> getHeaderFields() {
            return OkHeaders.toMultimap(this.response.headers(), StatusLine.get(this.response).toString());
        }

        @Override
        public long getIfModifiedSince() {
            return 0;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            throw JavaApiConverter.throwResponseBodyAccessException();
        }

        @Override
        public boolean getInstanceFollowRedirects() {
            return super.getInstanceFollowRedirects();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public int getReadTimeout() {
            return 0;
        }

        @Override
        public String getRequestMethod() {
            return this.request.method();
        }

        @Override
        public Map<String, List<String>> getRequestProperties() {
            throw JavaApiConverter.throwRequestHeaderAccessException();
        }

        @Override
        public String getRequestProperty(String string2) {
            return this.request.header(string2);
        }

        @Override
        public int getResponseCode() throws IOException {
            return this.response.code();
        }

        @Override
        public String getResponseMessage() throws IOException {
            return this.response.message();
        }

        @Override
        public boolean getUseCaches() {
            return super.getUseCaches();
        }

        @Override
        public void setAllowUserInteraction(boolean bl2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setChunkedStreamingMode(int n2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setConnectTimeout(int n2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setDefaultUseCaches(boolean bl2) {
            super.setDefaultUseCaches(bl2);
        }

        @Override
        public void setDoInput(boolean bl2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setDoOutput(boolean bl2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setFixedLengthStreamingMode(int n2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setFixedLengthStreamingMode(long l2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setIfModifiedSince(long l2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setInstanceFollowRedirects(boolean bl2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setReadTimeout(int n2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setRequestMethod(String string2) throws ProtocolException {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setRequestProperty(String string2, String string3) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setUseCaches(boolean bl2) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    private static final class CacheHttpsURLConnection
    extends DelegatingHttpsURLConnection {
        private final CacheHttpURLConnection delegate;

        public CacheHttpsURLConnection(CacheHttpURLConnection cacheHttpURLConnection) {
            super(cacheHttpURLConnection);
            this.delegate = cacheHttpURLConnection;
        }

        @Override
        public long getContentLengthLong() {
            return this.delegate.getContentLengthLong();
        }

        @Override
        public long getHeaderFieldLong(String string2, long l2) {
            return this.delegate.getHeaderFieldLong(string2, l2);
        }

        @Override
        public HostnameVerifier getHostnameVerifier() {
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        @Override
        public SSLSocketFactory getSSLSocketFactory() {
            throw JavaApiConverter.throwRequestSslAccessException();
        }

        @Override
        protected Handshake handshake() {
            return this.delegate.response.handshake();
        }

        @Override
        public void setFixedLengthStreamingMode(long l2) {
            this.delegate.setFixedLengthStreamingMode(l2);
        }

        @Override
        public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            throw JavaApiConverter.throwRequestModificationException();
        }

        @Override
        public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
            throw JavaApiConverter.throwRequestModificationException();
        }
    }

}

