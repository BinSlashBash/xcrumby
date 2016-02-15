package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
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
    private int onIdle;
    private final ConnectionPool pool;
    private final BufferedSink sink;
    private final Socket socket;
    private final BufferedSource source;
    private int state;

    private class AbstractSource {
        protected final OutputStream cacheBody;
        private final CacheRequest cacheRequest;
        protected boolean closed;

        AbstractSource(CacheRequest cacheRequest) throws IOException {
            OutputStream cacheBody = cacheRequest != null ? cacheRequest.getBody() : null;
            if (cacheBody == null) {
                cacheRequest = null;
            }
            this.cacheBody = cacheBody;
            this.cacheRequest = cacheRequest;
        }

        protected final void cacheWrite(Buffer source, long byteCount) throws IOException {
            if (this.cacheBody != null) {
                source.copyTo(this.cacheBody, source.size() - byteCount, byteCount);
            }
        }

        protected final void endOfInput(boolean recyclable) throws IOException {
            if (HttpConnection.this.state != HttpConnection.STATE_READING_RESPONSE_BODY) {
                throw new IllegalStateException("state: " + HttpConnection.this.state);
            }
            if (this.cacheRequest != null) {
                this.cacheBody.close();
            }
            HttpConnection.this.state = HttpConnection.STATE_IDLE;
            if (recyclable && HttpConnection.this.onIdle == HttpConnection.STATE_OPEN_REQUEST_BODY) {
                HttpConnection.this.onIdle = HttpConnection.STATE_IDLE;
                Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
            } else if (HttpConnection.this.onIdle == HttpConnection.STATE_WRITING_REQUEST_BODY) {
                HttpConnection.this.state = HttpConnection.STATE_CLOSED;
                HttpConnection.this.connection.getSocket().close();
            }
        }

        protected final void unexpectedEndOfInput() {
            if (this.cacheRequest != null) {
                this.cacheRequest.abort();
            }
            Util.closeQuietly(HttpConnection.this.connection.getSocket());
            HttpConnection.this.state = HttpConnection.STATE_CLOSED;
        }
    }

    private final class ChunkedSink implements Sink {
        private boolean closed;
        private final byte[] hex;

        private ChunkedSink() {
            this.hex = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 13, (byte) 10};
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (byteCount != 0) {
                writeHex(byteCount);
                HttpConnection.this.sink.write(source, byteCount);
                HttpConnection.this.sink.writeUtf8(HttpConnection.CRLF);
            }
        }

        public synchronized void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public synchronized void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                HttpConnection.this.sink.write(HttpConnection.FINAL_CHUNK);
                HttpConnection.this.state = HttpConnection.STATE_READ_RESPONSE_HEADERS;
            }
        }

        private void writeHex(long i) throws IOException {
            int cursor = 16;
            do {
                cursor--;
                this.hex[cursor] = HttpConnection.HEX_DIGITS[(int) (15 & i)];
                i >>>= HttpConnection.STATE_OPEN_RESPONSE_BODY;
            } while (i != 0);
            HttpConnection.this.sink.write(this.hex, cursor, this.hex.length - cursor);
        }
    }

    private class ChunkedSource extends AbstractSource implements Source {
        private static final int NO_CHUNK_YET = -1;
        private int bytesRemainingInChunk;
        private boolean hasMoreChunks;
        private final HttpEngine httpEngine;

        ChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
            super(cacheRequest);
            this.bytesRemainingInChunk = NO_CHUNK_YET;
            this.hasMoreChunks = true;
            this.httpEngine = httpEngine;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (!this.hasMoreChunks) {
                return -1;
            } else {
                if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == NO_CHUNK_YET) {
                    readChunkSize();
                    if (!this.hasMoreChunks) {
                        return -1;
                    }
                }
                long read = HttpConnection.this.source.read(sink, Math.min(byteCount, (long) this.bytesRemainingInChunk));
                if (read == -1) {
                    unexpectedEndOfInput();
                    throw new IOException("unexpected end of stream");
                }
                this.bytesRemainingInChunk = (int) (((long) this.bytesRemainingInChunk) - read);
                cacheWrite(sink, read);
                return read;
            }
        }

        private void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != NO_CHUNK_YET) {
                HttpConnection.this.source.readUtf8LineStrict();
            }
            String chunkSizeString = HttpConnection.this.source.readUtf8LineStrict();
            int index = chunkSizeString.indexOf(";");
            if (index != NO_CHUNK_YET) {
                chunkSizeString = chunkSizeString.substring(HttpConnection.STATE_IDLE, index);
            }
            try {
                this.bytesRemainingInChunk = Integer.parseInt(chunkSizeString.trim(), 16);
                if (this.bytesRemainingInChunk == 0) {
                    this.hasMoreChunks = false;
                    Builder trailersBuilder = new Builder();
                    HttpConnection.this.readHeaders(trailersBuilder);
                    this.httpEngine.receiveHeaders(trailersBuilder.build());
                    endOfInput(true);
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException("Expected a hex chunk size but was " + chunkSizeString);
            }
        }

        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (this.hasMoreChunks && !HttpConnection.this.discard(this, 100)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private final class FixedLengthSink implements Sink {
        private long bytesRemaining;
        private boolean closed;

        private FixedLengthSink(long bytesRemaining) {
            this.bytesRemaining = bytesRemaining;
        }

        public Timeout timeout() {
            return HttpConnection.this.sink.timeout();
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(source.size(), 0, byteCount);
            if (byteCount > this.bytesRemaining) {
                throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + byteCount);
            }
            HttpConnection.this.sink.write(source, byteCount);
            this.bytesRemaining -= byteCount;
        }

        public void flush() throws IOException {
            if (!this.closed) {
                HttpConnection.this.sink.flush();
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                if (this.bytesRemaining > 0) {
                    throw new ProtocolException("unexpected end of stream");
                }
                HttpConnection.this.state = HttpConnection.STATE_READ_RESPONSE_HEADERS;
            }
        }
    }

    private class FixedLengthSource extends AbstractSource implements Source {
        private long bytesRemaining;

        public FixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
            super(cacheRequest);
            this.bytesRemaining = length;
            if (this.bytesRemaining == 0) {
                endOfInput(true);
            }
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.bytesRemaining == 0) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, Math.min(this.bytesRemaining, byteCount));
                if (read == -1) {
                    unexpectedEndOfInput();
                    throw new ProtocolException("unexpected end of stream");
                }
                this.bytesRemaining -= read;
                cacheWrite(sink, read);
                if (this.bytesRemaining != 0) {
                    return read;
                }
                endOfInput(true);
                return read;
            }
        }

        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!(this.bytesRemaining == 0 || HttpConnection.this.discard(this, 100))) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    class UnknownLengthSource extends AbstractSource implements Source {
        private boolean inputExhausted;

        UnknownLengthSource(CacheRequest cacheRequest) throws IOException {
            super(cacheRequest);
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.inputExhausted) {
                return -1;
            } else {
                long read = HttpConnection.this.source.read(sink, byteCount);
                if (read == -1) {
                    this.inputExhausted = true;
                    endOfInput(false);
                    return -1;
                }
                cacheWrite(sink, read);
                return read;
            }
        }

        public Timeout timeout() {
            return HttpConnection.this.source.timeout();
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!this.inputExhausted) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    public HttpConnection(ConnectionPool pool, Connection connection, Socket socket) throws IOException {
        this.state = STATE_IDLE;
        this.onIdle = STATE_IDLE;
        this.pool = pool;
        this.connection = connection;
        this.socket = socket;
        this.source = Okio.buffer(Okio.source(socket));
        this.sink = Okio.buffer(Okio.sink(socket));
    }

    public void setTimeouts(int readTimeoutMillis, int writeTimeoutMillis) {
        if (readTimeoutMillis != 0) {
            this.source.timeout().timeout((long) readTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        if (writeTimeoutMillis != 0) {
            this.sink.timeout().timeout((long) writeTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void poolOnIdle() {
        this.onIdle = STATE_OPEN_REQUEST_BODY;
        if (this.state == 0) {
            this.onIdle = STATE_IDLE;
            Internal.instance.recycle(this.pool, this.connection);
        }
    }

    public void closeOnIdle() throws IOException {
        this.onIdle = STATE_WRITING_REQUEST_BODY;
        if (this.state == 0) {
            this.state = STATE_CLOSED;
            this.connection.getSocket().close();
        }
    }

    public boolean isClosed() {
        return this.state == STATE_CLOSED;
    }

    public void closeIfOwnedBy(Object owner) throws IOException {
        Internal.instance.closeIfOwnedBy(this.connection, owner);
    }

    public void flush() throws IOException {
        this.sink.flush();
    }

    public long bufferSize() {
        return this.source.buffer().size();
    }

    public boolean isReadable() {
        int readTimeout;
        try {
            readTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(STATE_OPEN_REQUEST_BODY);
            if (this.source.exhausted()) {
                this.socket.setSoTimeout(readTimeout);
                return false;
            }
            this.socket.setSoTimeout(readTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(readTimeout);
        }
    }

    public void writeRequest(Headers headers, String requestLine) throws IOException {
        if (this.state != 0) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.sink.writeUtf8(requestLine).writeUtf8(CRLF);
        for (int i = STATE_IDLE; i < headers.size(); i += STATE_OPEN_REQUEST_BODY) {
            this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8(CRLF);
        }
        this.sink.writeUtf8(CRLF);
        this.state = STATE_OPEN_REQUEST_BODY;
    }

    public Response.Builder readResponse() throws IOException {
        if (this.state == STATE_OPEN_REQUEST_BODY || this.state == STATE_READ_RESPONSE_HEADERS) {
            Response.Builder responseBuilder;
            StatusLine statusLine;
            do {
                statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
                responseBuilder = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message);
                Builder headersBuilder = new Builder();
                readHeaders(headersBuilder);
                headersBuilder.add(OkHeaders.SELECTED_PROTOCOL, statusLine.protocol.toString());
                responseBuilder.headers(headersBuilder.build());
            } while (statusLine.code == 100);
            this.state = STATE_OPEN_RESPONSE_BODY;
            return responseBuilder;
        }
        throw new IllegalStateException("state: " + this.state);
    }

    public void readHeaders(Builder builder) throws IOException {
        while (true) {
            String line = this.source.readUtf8LineStrict();
            if (line.length() != 0) {
                Internal.instance.addLine(builder, line);
            } else {
                return;
            }
        }
    }

    public boolean discard(Source in, int timeoutMillis) {
        int socketTimeout;
        try {
            socketTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(timeoutMillis);
            boolean skipAll = Util.skipAll(in, timeoutMillis);
            this.socket.setSoTimeout(socketTimeout);
            return skipAll;
        } catch (IOException e) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(socketTimeout);
        }
    }

    public Sink newChunkedSink() {
        if (this.state != STATE_OPEN_REQUEST_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_WRITING_REQUEST_BODY;
        return new ChunkedSink();
    }

    public Sink newFixedLengthSink(long contentLength) {
        if (this.state != STATE_OPEN_REQUEST_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_WRITING_REQUEST_BODY;
        return new FixedLengthSink(contentLength, null);
    }

    public void writeRequestBody(RetryableSink requestBody) throws IOException {
        if (this.state != STATE_OPEN_REQUEST_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_READ_RESPONSE_HEADERS;
        requestBody.writeToSocket(this.sink);
    }

    public Source newFixedLengthSource(CacheRequest cacheRequest, long length) throws IOException {
        if (this.state != STATE_OPEN_RESPONSE_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_READING_RESPONSE_BODY;
        return new FixedLengthSource(cacheRequest, length);
    }

    public void emptyResponseBody() throws IOException {
        newFixedLengthSource(null, 0);
    }

    public Source newChunkedSource(CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
        if (this.state != STATE_OPEN_RESPONSE_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_READING_RESPONSE_BODY;
        return new ChunkedSource(cacheRequest, httpEngine);
    }

    public Source newUnknownLengthSource(CacheRequest cacheRequest) throws IOException {
        if (this.state != STATE_OPEN_RESPONSE_BODY) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = STATE_READING_RESPONSE_BODY;
        return new UnknownLengthSource(cacheRequest);
    }

    static {
        HEX_DIGITS = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
        FINAL_CHUNK = new byte[]{(byte) 48, (byte) 13, (byte) 10, (byte) 13, (byte) 10};
    }
}
