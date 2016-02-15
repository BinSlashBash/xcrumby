/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpConnection {
    private static final String CRLF = "\r\n";
    private static final byte[] FINAL_CHUNK;
    private static final byte[] HEX_DIGITS;
    private static final int ON_IDLE_CLOSE = 2;
    private static final int ON_IDLE_HOLD = 0;
    private static final int ON_IDLE_POOL = 1;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private final Connection connection;
    private int onIdle = 0;
    private final ConnectionPool pool;
    private final BufferedSink sink;
    private final Socket socket;
    private final BufferedSource source;
    private int state = 0;

    static {
        HEX_DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        FINAL_CHUNK = new byte[]{48, 13, 10, 13, 10};
    }

    public HttpConnection(ConnectionPool connectionPool, Connection connection, Socket socket) throws IOException {
        this.pool = connectionPool;
        this.connection = connection;
        this.socket = socket;
        this.source = Okio.buffer(Okio.source(socket));
        this.sink = Okio.buffer(Okio.sink(socket));
    }

    public long bufferSize() {
        return this.source.buffer().size();
    }

    public void closeIfOwnedBy(Object object) throws IOException {
        Internal.instance.closeIfOwnedBy(this.connection, object);
    }

    public void closeOnIdle() throws IOException {
        this.onIdle = 2;
        if (this.state == 0) {
            this.state = 6;
            this.connection.getSocket().close();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean discard(Source source, int n2) {
        int n3;
        try {
            n3 = this.socket.getSoTimeout();
            this.socket.setSoTimeout(n2);
        }
        catch (IOException iOException) {
            return false;
        }
        boolean bl2 = Util.skipAll(source, n2);
        {
            catch (Throwable throwable) {
                this.socket.setSoTimeout(n3);
                throw throwable;
            }
        }
        this.socket.setSoTimeout(n3);
        return bl2;
    }

    public void emptyResponseBody() throws IOException {
        this.newFixedLengthSource(null, 0);
    }

    public void flush() throws IOException {
        this.sink.flush();
    }

    public boolean isClosed() {
        if (this.state == 6) {
            return true;
        }
        return false;
    }

    public boolean isReadable() {
        int n2;
        block8 : {
            n2 = this.socket.getSoTimeout();
            try {
                this.socket.setSoTimeout(1);
                boolean bl2 = this.source.exhausted();
                if (!bl2) break block8;
            }
            catch (Throwable var3_3) {
                try {
                    this.socket.setSoTimeout(n2);
                    throw var3_3;
                }
                catch (SocketTimeoutException var3_4) {
                    return true;
                }
                catch (IOException var3_5) {
                    return false;
                }
            }
            this.socket.setSoTimeout(n2);
            return false;
        }
        this.socket.setSoTimeout(n2);
        return true;
    }

    public Sink newChunkedSink() {
        if (this.state != 1) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 2;
        return new ChunkedSink();
    }

    public Source newChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 5;
        return new ChunkedSource(cacheRequest, httpEngine);
    }

    public Sink newFixedLengthSink(long l2) {
        if (this.state != 1) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 2;
        return new FixedLengthSink(l2);
    }

    public Source newFixedLengthSource(CacheRequest cacheRequest, long l2) throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 5;
        return new FixedLengthSource(cacheRequest, l2);
    }

    public Source newUnknownLengthSource(CacheRequest cacheRequest) throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 5;
        return new UnknownLengthSource(cacheRequest);
    }

    public void poolOnIdle() {
        this.onIdle = 1;
        if (this.state == 0) {
            this.onIdle = 0;
            Internal.instance.recycle(this.pool, this.connection);
        }
    }

    public void readHeaders(Headers.Builder builder) throws IOException {
        String string2;
        while ((string2 = this.source.readUtf8LineStrict()).length() != 0) {
            Internal.instance.addLine(builder, string2);
        }
    }

    public Response.Builder readResponse() throws IOException {
        Response.Builder builder;
        if (this.state != 1 && this.state != 3) {
            throw new IllegalStateException("state: " + this.state);
        }
        do {
            StatusLine statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
            builder = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
            Headers.Builder builder2 = new Headers.Builder();
            this.readHeaders(builder2);
            builder2.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
            builder.headers(builder2.build());
        } while (statusLine.code == 100);
        this.state = 4;
        return builder;
    }

    public void setTimeouts(int n2, int n3) {
        if (n2 != 0) {
            this.source.timeout().timeout(n2, TimeUnit.MILLISECONDS);
        }
        if (n3 != 0) {
            this.sink.timeout().timeout(n3, TimeUnit.MILLISECONDS);
        }
    }

    public void writeRequest(Headers headers, String string2) throws IOException {
        if (this.state != 0) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.sink.writeUtf8(string2).writeUtf8("\r\n");
        for (int i2 = 0; i2 < headers.size(); ++i2) {
            this.sink.writeUtf8(headers.name(i2)).writeUtf8(": ").writeUtf8(headers.value(i2)).writeUtf8("\r\n");
        }
        this.sink.writeUtf8("\r\n");
        this.state = 1;
    }

    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        if (this.state != 1) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 3;
        retryableSink.writeToSocket(this.sink);
    }

    private class AbstractSource {
        protected final OutputStream cacheBody;
        private final CacheRequest cacheRequest;
        protected boolean closed;

        /*
         * Enabled aggressive block sorting
         */
        AbstractSource(CacheRequest cacheRequest) throws IOException {
            Object.this = cacheRequest != null ? cacheRequest.getBody() : null;
            if (Object.this == null) {
                cacheRequest = null;
            }
            this.cacheBody = Object.this;
            this.cacheRequest = cacheRequest;
        }

        protected final void cacheWrite(Buffer buffer, long l2) throws IOException {
            if (this.cacheBody != null) {
                buffer.copyTo(this.cacheBody, buffer.size() - l2, l2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        protected final void endOfInput(boolean bl2) throws IOException {
            if (Object.this.state != 5) {
                throw new IllegalStateException("state: " + Object.this.state);
            }
            if (this.cacheRequest != null) {
                this.cacheBody.close();
            }
            Object.this.state = 0;
            if (bl2 && Object.this.onIdle == 1) {
                Object.this.onIdle = 0;
                Internal.instance.recycle(Object.this.pool, Object.this.connection);
                return;
            } else {
                if (Object.this.onIdle != 2) return;
                {
                    Object.this.state = 6;
                    Object.this.connection.getSocket().close();
                    return;
                }
            }
        }

        protected final void unexpectedEndOfInput() {
            if (this.cacheRequest != null) {
                this.cacheRequest.abort();
            }
            Util.closeQuietly(Object.this.connection.getSocket());
            Object.this.state = 6;
        }
    }

    private final class ChunkedSink
    implements Sink {
        private boolean closed;
        private final byte[] hex;

        private ChunkedSink() {
            this.hex = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 10};
        }

        private void writeHex(long l2) throws IOException {
            long l3;
            int n2;
            int n3 = 16;
            do {
                byte[] arrby = this.hex;
                n2 = n3 - 1;
                arrby[n2] = HEX_DIGITS[(int)(15 & l2)];
                l3 = l2 >>> 4;
                n3 = n2;
                l2 = l3;
            } while (l3 != 0);
            HttpConnection.this.sink.write(this.hex, n2, this.hex.length - n2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            synchronized (this) {
                block6 : {
                    boolean bl2 = this.closed;
                    if (!bl2) break block6;
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.closed = true;
                HttpConnection.this.sink.write(FINAL_CHUNK);
                HttpConnection.this.state = 3;
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void flush() throws IOException {
            synchronized (this) {
                block6 : {
                    boolean bl2 = this.closed;
                    if (!bl2) break block6;
                    do {
                        return;
                        break;
                    } while (true);
                }
                HttpConnection.this.sink.flush();
                return;
            }
        }

        @Override
        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        @Override
        public void write(Buffer buffer, long l2) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (l2 == 0) {
                return;
            }
            this.writeHex(l2);
            HttpConnection.this.sink.write(buffer, l2);
            HttpConnection.this.sink.writeUtf8("\r\n");
        }
    }

    private class ChunkedSource
    extends AbstractSource
    implements Source {
        private static final int NO_CHUNK_YET = -1;
        private int bytesRemainingInChunk;
        private boolean hasMoreChunks;
        private final HttpEngine httpEngine;

        ChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
            super(cacheRequest);
            this.bytesRemainingInChunk = -1;
            this.hasMoreChunks = true;
            this.httpEngine = httpEngine;
        }

        private void readChunkSize() throws IOException {
            block4 : {
                if (this.bytesRemainingInChunk != -1) {
                    HttpConnection.this.source.readUtf8LineStrict();
                }
                String string2 = HttpConnection.this.source.readUtf8LineStrict();
                int n2 = string2.indexOf(";");
                Object object = string2;
                if (n2 != -1) {
                    object = string2.substring(0, n2);
                }
                try {
                    this.bytesRemainingInChunk = Integer.parseInt(object.trim(), 16);
                    if (this.bytesRemainingInChunk != 0) break block4;
                }
                catch (NumberFormatException var3_2) {
                    throw new ProtocolException("Expected a hex chunk size but was " + (String)object);
                }
                this.hasMoreChunks = false;
                object = new Headers.Builder();
                HttpConnection.this.readHeaders((Headers.Builder)object);
                this.httpEngine.receiveHeaders(object.build());
                this.endOfInput(true);
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.hasMoreChunks && !HttpConnection.this.discard(this, 100)) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
        }

        @Override
        public long read(Buffer buffer, long l2) throws IOException {
            if (l2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + l2);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (!this.hasMoreChunks) {
                return -1;
            }
            if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
                this.readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1;
                }
            }
            if ((l2 = HttpConnection.this.source.read(buffer, Math.min(l2, (long)this.bytesRemainingInChunk))) == -1) {
                this.unexpectedEndOfInput();
                throw new IOException("unexpected end of stream");
            }
            this.bytesRemainingInChunk = (int)((long)this.bytesRemainingInChunk - l2);
            this.cacheWrite(buffer, l2);
            return l2;
        }

        @Override
        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }
    }

    private final class FixedLengthSink
    implements Sink {
        private long bytesRemaining;
        private boolean closed;

        private FixedLengthSink(long l2) {
            this.bytesRemaining = l2;
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.bytesRemaining > 0) {
                throw new ProtocolException("unexpected end of stream");
            }
            HttpConnection.this.state = 3;
        }

        @Override
        public void flush() throws IOException {
            if (this.closed) {
                return;
            }
            HttpConnection.this.sink.flush();
        }

        @Override
        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        @Override
        public void write(Buffer buffer, long l2) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0, l2);
            if (l2 > this.bytesRemaining) {
                throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + l2);
            }
            HttpConnection.this.sink.write(buffer, l2);
            this.bytesRemaining -= l2;
        }
    }

    private class FixedLengthSource
    extends AbstractSource
    implements Source {
        private long bytesRemaining;

        public FixedLengthSource(CacheRequest cacheRequest, long l2) throws IOException {
            super(cacheRequest);
            this.bytesRemaining = l2;
            if (this.bytesRemaining == 0) {
                this.endOfInput(true);
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.bytesRemaining != 0 && !HttpConnection.this.discard(this, 100)) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
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
            if (this.bytesRemaining == 0) {
                return -1;
            }
            long l3 = HttpConnection.this.source.read(buffer, Math.min(this.bytesRemaining, l2));
            if (l3 == -1) {
                this.unexpectedEndOfInput();
                throw new ProtocolException("unexpected end of stream");
            }
            this.bytesRemaining -= l3;
            this.cacheWrite(buffer, l3);
            l2 = l3;
            if (this.bytesRemaining != 0) return l2;
            this.endOfInput(true);
            return l3;
        }

        @Override
        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }
    }

    class UnknownLengthSource
    extends AbstractSource
    implements Source {
        private boolean inputExhausted;

        UnknownLengthSource(CacheRequest cacheRequest) throws IOException {
            super(cacheRequest);
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (!this.inputExhausted) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
        }

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
            l2 = HttpConnection.this.source.read(buffer, l2);
            if (l2 == -1) {
                this.inputExhausted = true;
                this.endOfInput(false);
                return -1;
            }
            this.cacheWrite(buffer, l2);
            return l2;
        }

        @Override
        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }
    }

}

