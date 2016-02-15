/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RequestLine;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyTransport
implements Transport {
    private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS;
    private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS;
    private final HttpEngine httpEngine;
    private final SpdyConnection spdyConnection;
    private SpdyStream stream;

    static {
        SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding"));
        HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade"));
    }

    public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
        this.httpEngine = httpEngine;
        this.spdyConnection = spdyConnection;
    }

    private static boolean isProhibitedHeader(Protocol protocol, ByteString byteString) {
        if (protocol == Protocol.SPDY_3) {
            return SPDY_3_PROHIBITED_HEADERS.contains(byteString);
        }
        if (protocol == Protocol.HTTP_2) {
            return HTTP_2_PROHIBITED_HEADERS.contains(byteString);
        }
        throw new AssertionError((Object)protocol);
    }

    private static String joinOnNull(String string2, String string3) {
        return string2 + '\u0000' + string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Response.Builder readNameValueBlock(List<Header> object, Protocol protocol) throws IOException {
        String string2 = null;
        String string3 = "HTTP/1.1";
        Headers.Builder builder = new Headers.Builder();
        builder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
        int n2 = 0;
        do {
            int n3;
            String string4;
            ByteString byteString;
            if (n2 < object.size()) {
                byteString = ((Header)object.get((int)n2)).name;
                string4 = ((Header)object.get((int)n2)).value.utf8();
                n3 = 0;
            } else {
                if (string2 == null) {
                    throw new ProtocolException("Expected ':status' header not present");
                }
                if (string3 == null) {
                    throw new ProtocolException("Expected ':version' header not present");
                }
                object = StatusLine.parse(string3 + " " + string2);
                return new Response.Builder().protocol(object.protocol).code(object.code).message(object.message).headers(builder.build());
            }
            while (n3 < string4.length()) {
                String string5;
                String string6;
                int n4;
                int n5 = n4 = string4.indexOf(0, n3);
                if (n4 == -1) {
                    n5 = string4.length();
                }
                String string7 = string4.substring(n3, n5);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    string5 = string7;
                    string6 = string3;
                } else if (byteString.equals(Header.VERSION)) {
                    string6 = string7;
                    string5 = string2;
                } else {
                    string5 = string2;
                    string6 = string3;
                    if (!SpdyTransport.isProhibitedHeader(protocol, byteString)) {
                        builder.add(byteString.utf8(), string7);
                        string5 = string2;
                        string6 = string3;
                    }
                }
                n3 = n5 + 1;
                string2 = string5;
                string3 = string6;
            }
            ++n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static List<Header> writeNameValueBlock(Request var0, Protocol var1_1, String var2_2) {
        var5_3 = var0.headers();
        var6_4 = new ArrayList<Header>(var5_3.size() + 10);
        var6_4.add(new Header(Header.TARGET_METHOD, var0.method()));
        var6_4.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(var0.url())));
        var7_5 = HttpEngine.hostHeader(var0.url());
        if (Protocol.SPDY_3 == var1_1) {
            var6_4.add(new Header(Header.VERSION, (String)var2_2));
            var6_4.add(new Header(Header.TARGET_HOST, var7_5));
        } else {
            if (Protocol.HTTP_2 != var1_1) throw new AssertionError();
            var6_4.add(new Header(Header.TARGET_AUTHORITY, var7_5));
        }
        var6_4.add(new Header(Header.TARGET_SCHEME, var0.url().getProtocol()));
        var0 = new LinkedHashSet<E>();
        var3_6 = 0;
        block0 : do {
            if (var3_6 >= var5_3.size()) return var6_4;
            var2_2 = ByteString.encodeUtf8(var5_3.name(var3_6).toLowerCase(Locale.US));
            var7_5 = var5_3.value(var3_6);
            if (SpdyTransport.isProhibitedHeader(var1_1, (ByteString)var2_2) || var2_2.equals(Header.TARGET_METHOD) || var2_2.equals(Header.TARGET_PATH) || var2_2.equals(Header.TARGET_SCHEME) || var2_2.equals(Header.TARGET_AUTHORITY) || var2_2.equals(Header.TARGET_HOST) || var2_2.equals(Header.VERSION)) ** GOTO lbl-1000
            if (!var0.add(var2_2)) ** GOTO lbl23
            var6_4.add(new Header((ByteString)var2_2, var7_5));
            ** GOTO lbl-1000
lbl23: // 1 sources:
            var4_7 = 0;
            do {
                if (var4_7 >= var6_4.size()) lbl-1000: // 4 sources:
                {
                    do {
                        ++var3_6;
                        continue block0;
                        break;
                    } while (true);
                }
                if (var6_4.get((int)var4_7).name.equals(var2_2)) {
                    var6_4.set(var4_7, new Header((ByteString)var2_2, SpdyTransport.joinOnNull(var6_4.get((int)var4_7).value.utf8(), var7_5)));
                    ** continue;
                }
                ++var4_7;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public boolean canReuseConnection() {
        return true;
    }

    @Override
    public Sink createRequestBody(Request request) throws IOException {
        this.writeRequestHeaders(request);
        return this.stream.getSink();
    }

    @Override
    public void disconnect(HttpEngine httpEngine) throws IOException {
        this.stream.close(ErrorCode.CANCEL);
    }

    @Override
    public void emptyTransferStream() {
    }

    @Override
    public void flushRequest() throws IOException {
        this.stream.getSink().close();
    }

    @Override
    public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
        return new SpdySource(this.stream, cacheRequest);
    }

    @Override
    public Response.Builder readResponseHeaders() throws IOException {
        return SpdyTransport.readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
    }

    @Override
    public void releaseConnectionOnIdle() {
    }

    @Override
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRequestHeaders(Request request) throws IOException {
        if (this.stream != null) {
            return;
        }
        this.httpEngine.writingRequestHeaders();
        boolean bl2 = this.httpEngine.hasRequestBody();
        String string2 = RequestLine.version(this.httpEngine.getConnection().getProtocol());
        this.stream = this.spdyConnection.newStream(SpdyTransport.writeNameValueBlock(request, this.spdyConnection.getProtocol(), string2), bl2, true);
        this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
    }

    private static class SpdySource
    implements Source {
        private final OutputStream cacheBody;
        private final CacheRequest cacheRequest;
        private boolean closed;
        private boolean inputExhausted;
        private final Source source;
        private final SpdyStream stream;

        /*
         * Enabled aggressive block sorting
         */
        SpdySource(SpdyStream object, CacheRequest cacheRequest) throws IOException {
            this.stream = object;
            this.source = object.getSource();
            object = cacheRequest != null ? cacheRequest.getBody() : null;
            if (object == null) {
                cacheRequest = null;
            }
            this.cacheBody = object;
            this.cacheRequest = cacheRequest;
        }

        private boolean discardStream() {
            long l2 = this.stream.readTimeout().timeoutNanos();
            this.stream.readTimeout().timeout(100, TimeUnit.MILLISECONDS);
            try {
                Util.skipAll(this, 100);
                return true;
            }
            catch (IOException var3_2) {
                return false;
            }
            finally {
                this.stream.readTimeout().timeout(l2, TimeUnit.NANOSECONDS);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (!this.inputExhausted && this.cacheBody != null) {
                this.discardStream();
            }
            this.closed = true;
            if (this.inputExhausted) return;
            this.stream.closeLater(ErrorCode.CANCEL);
            if (this.cacheRequest == null) return;
            this.cacheRequest.abort();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long read(Buffer buffer, long l2) throws IOException {
            if (l2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + l2);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (this.inputExhausted) {
                return -1;
            }
            long l3 = this.source.read(buffer, l2);
            if (l3 == -1) {
                this.inputExhausted = true;
                if (this.cacheRequest == null) return -1;
                this.cacheBody.close();
                return -1;
            }
            l2 = l3;
            if (this.cacheBody == null) return l2;
            buffer.copyTo(this.cacheBody, buffer.size() - l3, l3);
            return l3;
        }

        @Override
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

}

