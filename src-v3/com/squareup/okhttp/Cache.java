package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.DiskLruCache.Editor;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;
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

    private final class CacheRequestImpl extends CacheRequest {
        private OutputStream body;
        private OutputStream cacheOut;
        private boolean done;
        private final Editor editor;

        /* renamed from: com.squareup.okhttp.Cache.CacheRequestImpl.1 */
        class C05811 extends FilterOutputStream {
            final /* synthetic */ Editor val$editor;
            final /* synthetic */ Cache val$this$0;

            C05811(OutputStream x0, Cache cache, Editor editor) {
                this.val$this$0 = cache;
                this.val$editor = editor;
                super(x0);
            }

            public void close() throws IOException {
                synchronized (Cache.this) {
                    if (CacheRequestImpl.this.done) {
                        return;
                    }
                    CacheRequestImpl.this.done = true;
                    Cache.this.writeSuccessCount = Cache.this.writeSuccessCount + Cache.ENTRY_BODY;
                    super.close();
                    this.val$editor.commit();
                }
            }

            public void write(byte[] buffer, int offset, int length) throws IOException {
                this.out.write(buffer, offset, length);
            }
        }

        public CacheRequestImpl(Editor editor) throws IOException {
            this.editor = editor;
            this.cacheOut = editor.newOutputStream(Cache.ENTRY_BODY);
            this.body = new C05811(this.cacheOut, Cache.this, editor);
        }

        public void abort() {
            synchronized (Cache.this) {
                if (this.done) {
                    return;
                }
                this.done = true;
                Cache.this.writeAbortCount = Cache.this.writeAbortCount + Cache.ENTRY_BODY;
                Util.closeQuietly(this.cacheOut);
                try {
                    this.editor.abort();
                } catch (IOException e) {
                }
            }
        }

        public OutputStream getBody() throws IOException {
            return this.body;
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

        public Entry(InputStream in) throws IOException {
            try {
                int i;
                BufferedSource source = Okio.buffer(Okio.source(in));
                this.url = source.readUtf8LineStrict();
                this.requestMethod = source.readUtf8LineStrict();
                Builder varyHeadersBuilder = new Builder();
                int varyRequestHeaderLineCount = Cache.readInt(source);
                for (i = Cache.ENTRY_METADATA; i < varyRequestHeaderLineCount; i += Cache.ENTRY_BODY) {
                    varyHeadersBuilder.addLine(source.readUtf8LineStrict());
                }
                this.varyHeaders = varyHeadersBuilder.build();
                StatusLine statusLine = StatusLine.parse(source.readUtf8LineStrict());
                this.protocol = statusLine.protocol;
                this.code = statusLine.code;
                this.message = statusLine.message;
                Builder responseHeadersBuilder = new Builder();
                int responseHeaderLineCount = Cache.readInt(source);
                for (i = Cache.ENTRY_METADATA; i < responseHeaderLineCount; i += Cache.ENTRY_BODY) {
                    responseHeadersBuilder.addLine(source.readUtf8LineStrict());
                }
                this.responseHeaders = responseHeadersBuilder.build();
                if (isHttps()) {
                    String blank = source.readUtf8LineStrict();
                    if (blank.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + blank + "\"");
                    }
                    this.handshake = Handshake.get(source.readUtf8LineStrict(), readCertificateList(source), readCertificateList(source));
                } else {
                    this.handshake = null;
                }
                in.close();
            } catch (Throwable th) {
                in.close();
            }
        }

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

        public void writeTo(Editor editor) throws IOException {
            int i;
            Writer writer = new BufferedWriter(new OutputStreamWriter(editor.newOutputStream(Cache.ENTRY_METADATA), Util.UTF_8));
            writer.write(this.url);
            writer.write(10);
            writer.write(this.requestMethod);
            writer.write(10);
            writer.write(Integer.toString(this.varyHeaders.size()));
            writer.write(10);
            for (i = Cache.ENTRY_METADATA; i < this.varyHeaders.size(); i += Cache.ENTRY_BODY) {
                writer.write(this.varyHeaders.name(i));
                writer.write(": ");
                writer.write(this.varyHeaders.value(i));
                writer.write(10);
            }
            writer.write(new StatusLine(this.protocol, this.code, this.message).toString());
            writer.write(10);
            writer.write(Integer.toString(this.responseHeaders.size()));
            writer.write(10);
            for (i = Cache.ENTRY_METADATA; i < this.responseHeaders.size(); i += Cache.ENTRY_BODY) {
                writer.write(this.responseHeaders.name(i));
                writer.write(": ");
                writer.write(this.responseHeaders.value(i));
                writer.write(10);
            }
            if (isHttps()) {
                writer.write(10);
                writer.write(this.handshake.cipherSuite());
                writer.write(10);
                writeCertArray(writer, this.handshake.peerCertificates());
                writeCertArray(writer, this.handshake.localCertificates());
            }
            writer.close();
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        private List<Certificate> readCertificateList(BufferedSource source) throws IOException {
            int length = Cache.readInt(source);
            if (length == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                List<Certificate> result = new ArrayList(length);
                for (int i = Cache.ENTRY_METADATA; i < length; i += Cache.ENTRY_BODY) {
                    result.add(certificateFactory.generateCertificate(new ByteArrayInputStream(ByteString.decodeBase64(source.readUtf8LineStrict()).toByteArray())));
                }
                return result;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertArray(Writer writer, List<Certificate> certificates) throws IOException {
            try {
                writer.write(Integer.toString(certificates.size()));
                writer.write(10);
                int size = certificates.size();
                for (int i = Cache.ENTRY_METADATA; i < size; i += Cache.ENTRY_BODY) {
                    writer.write(ByteString.of(((Certificate) certificates.get(i)).getEncoded()).base64());
                    writer.write(10);
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public boolean matches(Request request, Response response) {
            return this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
        }

        public Response response(Request request, Snapshot snapshot) {
            String contentType = this.responseHeaders.get("Content-Type");
            String contentLength = this.responseHeaders.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.url).method(this.message, null).headers(this.varyHeaders).build()).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, contentType, contentLength)).handshake(this.handshake).build();
        }
    }

    /* renamed from: com.squareup.okhttp.Cache.1 */
    class C11451 implements InternalCache {
        C11451() {
        }

        public Response get(Request request) throws IOException {
            return Cache.this.get(request);
        }

        public CacheRequest put(Response response) throws IOException {
            return Cache.this.put(response);
        }

        public void remove(Request request) throws IOException {
            Cache.this.remove(request);
        }

        public void update(Response cached, Response network) throws IOException {
            Cache.this.update(cached, network);
        }

        public void trackConditionalCacheHit() {
            Cache.this.trackConditionalCacheHit();
        }

        public void trackResponse(CacheStrategy cacheStrategy) {
            Cache.this.trackResponse(cacheStrategy);
        }
    }

    private static class CacheResponseBody extends ResponseBody {
        private final BufferedSource bodySource;
        private final String contentLength;
        private final String contentType;
        private final Snapshot snapshot;

        /* renamed from: com.squareup.okhttp.Cache.CacheResponseBody.1 */
        class C14291 extends ForwardingSource {
            final /* synthetic */ Snapshot val$snapshot;

            C14291(Source x0, Snapshot snapshot) {
                this.val$snapshot = snapshot;
                super(x0);
            }

            public void close() throws IOException {
                this.val$snapshot.close();
                super.close();
            }
        }

        public CacheResponseBody(Snapshot snapshot, String contentType, String contentLength) {
            this.snapshot = snapshot;
            this.contentType = contentType;
            this.contentLength = contentLength;
            this.bodySource = Okio.buffer(new C14291(Okio.source(snapshot.getInputStream(Cache.ENTRY_BODY)), snapshot));
        }

        public MediaType contentType() {
            return this.contentType != null ? MediaType.parse(this.contentType) : null;
        }

        public long contentLength() {
            long j = -1;
            try {
                if (this.contentLength != null) {
                    j = Long.parseLong(this.contentLength);
                }
            } catch (NumberFormatException e) {
            }
            return j;
        }

        public BufferedSource source() {
            return this.bodySource;
        }
    }

    public Cache(File directory, long maxSize) throws IOException {
        this.internalCache = new C11451();
        this.cache = DiskLruCache.open(directory, VERSION, ENTRY_COUNT, maxSize);
    }

    private static String urlToKey(Request requst) {
        return Util.hash(requst.urlString());
    }

    Response get(Request request) {
        try {
            Closeable snapshot = this.cache.get(urlToKey(request));
            if (snapshot == null) {
                return null;
            }
            try {
                Entry entry = new Entry(snapshot.getInputStream(ENTRY_METADATA));
                Response response = entry.response(request, snapshot);
                if (entry.matches(request, response)) {
                    return response;
                }
                Util.closeQuietly(response.body());
                return null;
            } catch (IOException e) {
                Util.closeQuietly(snapshot);
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    private CacheRequest put(Response response) throws IOException {
        String requestMethod = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                remove(response.request());
                return null;
            } catch (IOException e) {
                return null;
            }
        } else if (!requestMethod.equals("GET") || OkHeaders.hasVaryAll(response)) {
            return null;
        } else {
            Entry entry = new Entry(response);
            try {
                Editor editor = this.cache.edit(urlToKey(response.request()));
                if (editor == null) {
                    return null;
                }
                entry.writeTo(editor);
                return new CacheRequestImpl(editor);
            } catch (IOException e2) {
                abortQuietly(null);
                return null;
            }
        }
    }

    private void remove(Request request) throws IOException {
        this.cache.remove(urlToKey(request));
    }

    private void update(Response cached, Response network) {
        Entry entry = new Entry(network);
        try {
            Editor editor = ((CacheResponseBody) cached.body()).snapshot.edit();
            if (editor != null) {
                entry.writeTo(editor);
                editor.commit();
            }
        } catch (IOException e) {
            abortQuietly(null);
        }
    }

    private void abortQuietly(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException e) {
            }
        }
    }

    public void delete() throws IOException {
        this.cache.delete();
    }

    public synchronized int getWriteAbortCount() {
        return this.writeAbortCount;
    }

    public synchronized int getWriteSuccessCount() {
        return this.writeSuccessCount;
    }

    public long getSize() {
        return this.cache.size();
    }

    public long getMaxSize() {
        return this.cache.getMaxSize();
    }

    public void flush() throws IOException {
        this.cache.flush();
    }

    public void close() throws IOException {
        this.cache.close();
    }

    public File getDirectory() {
        return this.cache.getDirectory();
    }

    public boolean isClosed() {
        return this.cache.isClosed();
    }

    private synchronized void trackResponse(CacheStrategy cacheStrategy) {
        this.requestCount += ENTRY_BODY;
        if (cacheStrategy.networkRequest != null) {
            this.networkCount += ENTRY_BODY;
        } else if (cacheStrategy.cacheResponse != null) {
            this.hitCount += ENTRY_BODY;
        }
    }

    private synchronized void trackConditionalCacheHit() {
        this.hitCount += ENTRY_BODY;
    }

    public synchronized int getNetworkCount() {
        return this.networkCount;
    }

    public synchronized int getHitCount() {
        return this.hitCount;
    }

    public synchronized int getRequestCount() {
        return this.requestCount;
    }

    private static int readInt(BufferedSource source) throws IOException {
        String line = source.readUtf8LineStrict();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IOException("Expected an integer but was \"" + line + "\"");
        }
    }
}
