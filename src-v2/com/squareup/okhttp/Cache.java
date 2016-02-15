/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CacheRequest;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public final class Cache {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    final InternalCache internalCache;
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    public Cache(File file, long l2) throws IOException {
        this.internalCache = new InternalCache(){

            @Override
            public Response get(Request request) throws IOException {
                return Cache.this.get(request);
            }

            @Override
            public CacheRequest put(Response response) throws IOException {
                return Cache.this.put(response);
            }

            @Override
            public void remove(Request request) throws IOException {
                Cache.this.remove(request);
            }

            @Override
            public void trackConditionalCacheHit() {
                Cache.this.trackConditionalCacheHit();
            }

            @Override
            public void trackResponse(CacheStrategy cacheStrategy) {
                Cache.this.trackResponse(cacheStrategy);
            }

            @Override
            public void update(Response response, Response response2) throws IOException {
                Cache.this.update(response, response2);
            }
        };
        this.cache = DiskLruCache.open(file, 201105, 2, l2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void abortQuietly(DiskLruCache.Editor editor) {
        if (editor == null) return;
        try {
            editor.abort();
            return;
        }
        catch (IOException var1_2) {
            return;
        }
    }

    static /* synthetic */ int access$708(Cache cache) {
        int n2 = cache.writeSuccessCount;
        cache.writeSuccessCount = n2 + 1;
        return n2;
    }

    static /* synthetic */ int access$808(Cache cache) {
        int n2 = cache.writeAbortCount;
        cache.writeAbortCount = n2 + 1;
        return n2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private CacheRequest put(Response object) throws IOException {
        Object object2 = object.request().method();
        if (HttpMethod.invalidatesCache(object.request().method())) {
            this.remove(object.request());
            return null;
        }
        if (!object2.equals("GET")) return null;
        if (OkHeaders.hasVaryAll((Response)object)) return null;
        Entry entry = new Entry((Response)object);
        object2 = null;
        try {
            if ((object = this.cache.edit(Cache.urlToKey(object.request()))) == null) return null;
            object2 = object;
            entry.writeTo((DiskLruCache.Editor)object);
            object2 = object;
            return new CacheRequestImpl((DiskLruCache.Editor)object);
        }
        catch (IOException iOException) {
            this.abortQuietly((DiskLruCache.Editor)object2);
            return null;
        }
        catch (IOException iOException2) {
            return null;
        }
    }

    private static int readInt(BufferedSource object) throws IOException {
        object = object.readUtf8LineStrict();
        try {
            int n2 = Integer.parseInt((String)object);
            return n2;
        }
        catch (NumberFormatException var2_2) {
            throw new IOException("Expected an integer but was \"" + (String)object + "\"");
        }
    }

    private void remove(Request request) throws IOException {
        this.cache.remove(Cache.urlToKey(request));
    }

    private void trackConditionalCacheHit() {
        synchronized (this) {
            ++this.hitCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void trackResponse(CacheStrategy cacheStrategy) {
        synchronized (this) {
            ++this.requestCount;
            if (cacheStrategy.networkRequest != null) {
                ++this.networkCount;
            } else if (cacheStrategy.cacheResponse != null) {
                ++this.hitCount;
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void update(Response object, Response object2) {
        Entry entry = new Entry((Response)object2);
        object2 = ((CacheResponseBody)object.body()).snapshot;
        object = null;
        try {
            object2 = object2.edit();
            if (object2 == null) return;
            object = object2;
        }
        catch (IOException var2_3) {
            this.abortQuietly((DiskLruCache.Editor)object);
            return;
        }
        entry.writeTo((DiskLruCache.Editor)object2);
        object = object2;
        object2.commit();
    }

    private static String urlToKey(Request request) {
        return Util.hash(request.urlString());
    }

    public void close() throws IOException {
        this.cache.close();
    }

    public void delete() throws IOException {
        this.cache.delete();
    }

    public void flush() throws IOException {
        this.cache.flush();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Response get(Request request) {
        Response response;
        Object object = Cache.urlToKey(request);
        try {
            object = this.cache.get((String)object);
            if (object == null) {
                return null;
            }
        }
        catch (IOException var1_2) {
            return null;
        }
        try {
            Entry entry = new Entry(object.getInputStream(0));
            object = response = entry.response(request, (DiskLruCache.Snapshot)object);
            if (entry.matches(request, response)) return object;
        }
        catch (IOException var1_3) {
            Util.closeQuietly((Closeable)object);
            return null;
        }
        Util.closeQuietly(response.body());
        return null;
    }

    public File getDirectory() {
        return this.cache.getDirectory();
    }

    public int getHitCount() {
        synchronized (this) {
            int n2 = this.hitCount;
            return n2;
        }
    }

    public long getMaxSize() {
        return this.cache.getMaxSize();
    }

    public int getNetworkCount() {
        synchronized (this) {
            int n2 = this.networkCount;
            return n2;
        }
    }

    public int getRequestCount() {
        synchronized (this) {
            int n2 = this.requestCount;
            return n2;
        }
    }

    public long getSize() {
        return this.cache.size();
    }

    public int getWriteAbortCount() {
        synchronized (this) {
            int n2 = this.writeAbortCount;
            return n2;
        }
    }

    public int getWriteSuccessCount() {
        synchronized (this) {
            int n2 = this.writeSuccessCount;
            return n2;
        }
    }

    public boolean isClosed() {
        return this.cache.isClosed();
    }

    private final class CacheRequestImpl
    extends CacheRequest {
        private OutputStream body;
        private OutputStream cacheOut;
        private boolean done;
        private final DiskLruCache.Editor editor;

        public CacheRequestImpl(DiskLruCache.Editor editor) throws IOException {
            this.editor = editor;
            this.cacheOut = editor.newOutputStream(1);
            this.body = new FilterOutputStream(this.cacheOut, Cache.this, editor){
                final /* synthetic */ DiskLruCache.Editor val$editor;
                final /* synthetic */ Cache val$this$0;

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void close() throws IOException {
                    Cache cache = Cache.this;
                    synchronized (cache) {
                        if (CacheRequestImpl.this.done) {
                            return;
                        }
                        CacheRequestImpl.this.done = true;
                        Cache.access$708(Cache.this);
                    }
                    super.close();
                    this.val$editor.commit();
                }

                @Override
                public void write(byte[] arrby, int n2, int n3) throws IOException {
                    this.out.write(arrby, n2, n3);
                }
            };
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void abort() {
            Cache cache = Cache.this;
            synchronized (cache) {
                if (this.done) {
                    return;
                }
                this.done = true;
                Cache.access$808(Cache.this);
            }
            Util.closeQuietly(this.cacheOut);
            try {
                this.editor.abort();
                return;
            }
            catch (IOException var1_2) {
                return;
            }
        }

        @Override
        public OutputStream getBody() throws IOException {
            return this.body;
        }

    }

    private static class CacheResponseBody
    extends ResponseBody {
        private final BufferedSource bodySource;
        private final String contentLength;
        private final String contentType;
        private final DiskLruCache.Snapshot snapshot;

        public CacheResponseBody(final DiskLruCache.Snapshot snapshot, String string2, String string3) {
            this.snapshot = snapshot;
            this.contentType = string2;
            this.contentLength = string3;
            this.bodySource = Okio.buffer(new ForwardingSource(Okio.source(snapshot.getInputStream(1))){

                @Override
                public void close() throws IOException {
                    snapshot.close();
                    super.close();
                }
            });
        }

        @Override
        public long contentLength() {
            long l2 = -1;
            try {
                if (this.contentLength != null) {
                    l2 = Long.parseLong(this.contentLength);
                }
                return l2;
            }
            catch (NumberFormatException var3_2) {
                return -1;
            }
        }

        @Override
        public MediaType contentType() {
            if (this.contentType != null) {
                return MediaType.parse(this.contentType);
            }
            return null;
        }

        @Override
        public BufferedSource source() {
            return this.bodySource;
        }

    }

    private static final class Entry {
        private final int code;
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final String url;
        private final Headers varyHeaders;

        public Entry(Response response) {
            this.url = response.request().urlString();
            this.varyHeaders = OkHeaders.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public Entry(InputStream var1_1) throws IOException {
            super();
            var4_2 = Okio.buffer(Okio.source(var1_1));
            this.url = var4_2.readUtf8LineStrict();
            this.requestMethod = var4_2.readUtf8LineStrict();
            var5_4 = new Headers.Builder();
            var3_5 = Cache.access$900(var4_2);
            for (var2_6 = 0; var2_6 < var3_5; ++var2_6) {
                var5_4.addLine(var4_2.readUtf8LineStrict());
            }
            this.varyHeaders = var5_4.build();
            var5_4 = StatusLine.parse(var4_2.readUtf8LineStrict());
            this.protocol = var5_4.protocol;
            this.code = var5_4.code;
            this.message = var5_4.message;
            var5_4 = new Headers.Builder();
            var3_5 = Cache.access$900(var4_2);
            for (var2_6 = 0; var2_6 < var3_5; ++var2_6) {
                var5_4.addLine(var4_2.readUtf8LineStrict());
            }
            ** try [egrp 2[TRYBLOCK] [4 : 167->233)] { 
lbl25: // 1 sources:
            this.responseHeaders = var5_4.build();
            if (this.isHttps()) {
                var5_4 = var4_2.readUtf8LineStrict();
                if (var5_4.length() > 0) {
                    throw new IOException("expected \"\" but was \"" + (String)var5_4 + "\"");
                }
                this.handshake = Handshake.get(var4_2.readUtf8LineStrict(), this.readCertificateList(var4_2), this.readCertificateList(var4_2));
                return;
            }
            this.handshake = null;
            return;
lbl34: // 1 sources:
            finally {
                var1_1.close();
            }
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private List<Certificate> readCertificateList(BufferedSource bufferedSource) throws IOException {
            CertificateFactory certificateFactory;
            int n2;
            ArrayList<Certificate> arrayList;
            int n3 = Cache.readInt(bufferedSource);
            if (n3 == -1) {
                return Collections.emptyList();
            }
            try {
                certificateFactory = CertificateFactory.getInstance("X.509");
                arrayList = new ArrayList<Certificate>(n3);
                n2 = 0;
            }
            catch (CertificateException var1_2) {
                throw new IOException(var1_2.getMessage());
            }
            do {
                List<Certificate> list = arrayList;
                if (n2 >= n3) return list;
                arrayList.add(certificateFactory.generateCertificate(new ByteArrayInputStream(ByteString.decodeBase64(bufferedSource.readUtf8LineStrict()).toByteArray())));
                ++n2;
                continue;
                break;
            } while (true);
        }

        private void writeCertArray(Writer writer, List<Certificate> list) throws IOException {
            writer.write(Integer.toString(list.size()));
            writer.write(10);
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                try {
                    writer.write(ByteString.of(list.get(i2).getEncoded()).base64());
                    writer.write(10);
                    continue;
                }
                catch (CertificateEncodingException var1_2) {
                    throw new IOException(var1_2.getMessage());
                }
            }
        }

        public boolean matches(Request request, Response response) {
            if (this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request)) {
                return true;
            }
            return false;
        }

        public Response response(Request object, DiskLruCache.Snapshot snapshot) {
            object = this.responseHeaders.get("Content-Type");
            String string2 = this.responseHeaders.get("Content-Length");
            Request request = new Request.Builder().url(this.url).method(this.message, null).headers(this.varyHeaders).build();
            return new Response.Builder().request(request).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, (String)object, string2)).handshake(this.handshake).build();
        }

        public void writeTo(DiskLruCache.Editor object) throws IOException {
            int n2;
            object = new BufferedWriter(new OutputStreamWriter(object.newOutputStream(0), Util.UTF_8));
            object.write(this.url);
            object.write(10);
            object.write(this.requestMethod);
            object.write(10);
            object.write(Integer.toString(this.varyHeaders.size()));
            object.write(10);
            for (n2 = 0; n2 < this.varyHeaders.size(); ++n2) {
                object.write(this.varyHeaders.name(n2));
                object.write(": ");
                object.write(this.varyHeaders.value(n2));
                object.write(10);
            }
            object.write(new StatusLine(this.protocol, this.code, this.message).toString());
            object.write(10);
            object.write(Integer.toString(this.responseHeaders.size()));
            object.write(10);
            for (n2 = 0; n2 < this.responseHeaders.size(); ++n2) {
                object.write(this.responseHeaders.name(n2));
                object.write(": ");
                object.write(this.responseHeaders.value(n2));
                object.write(10);
            }
            if (this.isHttps()) {
                object.write(10);
                object.write(this.handshake.cipherSuite());
                object.write(10);
                this.writeCertArray((Writer)object, this.handshake.peerCertificates());
                this.writeCertArray((Writer)object, this.handshake.localCertificates());
            }
            object.close();
        }
    }

}

