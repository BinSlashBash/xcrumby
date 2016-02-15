/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

final class RealBufferedSink
implements BufferedSink {
    public final Buffer buffer;
    private boolean closed;
    public final Sink sink;

    public RealBufferedSink(Sink sink) {
        this(sink, new Buffer());
    }

    public RealBufferedSink(Sink sink, Buffer buffer) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.buffer = buffer;
        this.sink = sink;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        Throwable throwable;
        block7 : {
            Throwable throwable2;
            if (this.closed) {
                return;
            }
            throwable2 = throwable = null;
            try {
                if (this.buffer.size > 0) {
                    this.sink.write(this.buffer, this.buffer.size);
                    throwable2 = throwable;
                }
            }
            catch (Throwable var1_3) {}
            try {
                this.sink.close();
                throwable = throwable2;
            }
            catch (Throwable var3_4) {
                throwable = throwable2;
                if (throwable2 != null) break block7;
                throwable = var3_4;
            }
        }
        this.closed = true;
        if (throwable == null) return;
        Util.sneakyRethrow(throwable);
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long l2 = this.buffer.completeSegmentByteCount();
        if (l2 > 0) {
            this.sink.write(this.buffer, l2);
        }
        return this;
    }

    @Override
    public void flush() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size > 0) {
            this.sink.write(this.buffer, this.buffer.size);
        }
        this.sink.flush();
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void close() throws IOException {
                RealBufferedSink.this.close();
            }

            @Override
            public void flush() throws IOException {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.flush();
                }
            }

            public String toString() {
                return RealBufferedSink.this + ".outputStream()";
            }

            @Override
            public void write(int n2) throws IOException {
                if (RealBufferedSink.this.closed) {
                    throw new IOException("closed");
                }
                RealBufferedSink.this.buffer.writeByte((byte)n2);
                RealBufferedSink.this.emitCompleteSegments();
            }

            @Override
            public void write(byte[] arrby, int n2, int n3) throws IOException {
                if (RealBufferedSink.this.closed) {
                    throw new IOException("closed");
                }
                RealBufferedSink.this.buffer.write(arrby, n2, n3);
                RealBufferedSink.this.emitCompleteSegments();
            }
        };
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(byteString);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(byte[] arrby) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(arrby);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink write(byte[] arrby, int n2, int n3) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(arrby, n2, n3);
        return this.emitCompleteSegments();
    }

    @Override
    public void write(Buffer buffer, long l2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(buffer, l2);
        this.emitCompleteSegments();
    }

    @Override
    public long writeAll(Source source) throws IOException {
        long l2;
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long l3 = 0;
        while ((l2 = source.read(this.buffer, 2048)) != -1) {
            l3 += l2;
            this.emitCompleteSegments();
        }
        return l3;
    }

    @Override
    public BufferedSink writeByte(int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeByte(n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeInt(int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeInt(n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeIntLe(int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeIntLe(n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeLong(long l2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeLong(l2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeLongLe(long l2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeLongLe(l2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeShort(int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShort(n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeShortLe(int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShortLe(n2);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeString(String string2, Charset charset) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeString(string2, charset);
        return this.emitCompleteSegments();
    }

    @Override
    public BufferedSink writeUtf8(String string2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(string2);
        return this.emitCompleteSegments();
    }

}

