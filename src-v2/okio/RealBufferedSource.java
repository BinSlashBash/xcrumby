/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

final class RealBufferedSource
implements BufferedSource {
    public final Buffer buffer;
    private boolean closed;
    public final Source source;

    public RealBufferedSource(Source source) {
        this(source, new Buffer());
    }

    public RealBufferedSource(Source source, Buffer buffer) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.buffer = buffer;
        this.source = source;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        this.buffer.clear();
    }

    @Override
    public boolean exhausted() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.exhausted() && this.source.read(this.buffer, 2048) == -1) {
            return true;
        }
        return false;
    }

    @Override
    public long indexOf(byte by2) throws IOException {
        long l2;
        block2 : {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            l2 = 0;
            do {
                long l3;
                l2 = l3 = this.buffer.indexOf(by2, l2);
                if (l3 != -1) break block2;
                l2 = this.buffer.size;
            } while (this.source.read(this.buffer, 2048) != -1);
            l2 = -1;
        }
        return l2;
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                return (int)Math.min(RealBufferedSource.this.buffer.size, Integer.MAX_VALUE);
            }

            @Override
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            @Override
            public int read() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.readByte() & 255;
            }

            @Override
            public int read(byte[] arrby, int n2, int n3) throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                Util.checkOffsetAndCount(arrby.length, n2, n3);
                if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.read(arrby, n2, n3);
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }
        };
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n2, int n3) throws IOException {
        Util.checkOffsetAndCount(arrby.length, n2, n3);
        if (this.buffer.size == 0 && this.source.read(this.buffer, 2048) == -1) {
            return -1;
        }
        n3 = (int)Math.min((long)n3, this.buffer.size);
        return this.buffer.read(arrby, n2, n3);
    }

    @Override
    public long read(Buffer buffer, long l2) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size == 0 && this.source.read(this.buffer, 2048) == -1) {
            return -1;
        }
        l2 = Math.min(l2, this.buffer.size);
        return this.buffer.read(buffer, l2);
    }

    @Override
    public long readAll(Sink sink) throws IOException {
        long l2;
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long l3 = 0;
        while (this.source.read(this.buffer, 2048) != -1) {
            l2 = this.buffer.completeSegmentByteCount();
            if (l2 <= 0) continue;
            l3 += l2;
            sink.write(this.buffer, l2);
        }
        l2 = l3;
        if (this.buffer.size() > 0) {
            l2 = l3 + this.buffer.size();
            sink.write(this.buffer, this.buffer.size());
        }
        return l2;
    }

    @Override
    public byte readByte() throws IOException {
        this.require(1);
        return this.buffer.readByte();
    }

    @Override
    public byte[] readByteArray() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteArray();
    }

    @Override
    public byte[] readByteArray(long l2) throws IOException {
        this.require(l2);
        return this.buffer.readByteArray(l2);
    }

    @Override
    public ByteString readByteString() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteString();
    }

    @Override
    public ByteString readByteString(long l2) throws IOException {
        this.require(l2);
        return this.buffer.readByteString(l2);
    }

    @Override
    public void readFully(Buffer buffer, long l2) throws IOException {
        try {
            this.require(l2);
        }
        catch (EOFException var4_3) {
            buffer.writeAll(this.buffer);
            throw var4_3;
        }
        this.buffer.readFully(buffer, l2);
    }

    @Override
    public void readFully(byte[] arrby) throws IOException {
        try {
            this.require(arrby.length);
        }
        catch (EOFException var4_2) {
            int n2 = 0;
            while (this.buffer.size > 0) {
                int n3 = this.buffer.read(arrby, n2, (int)this.buffer.size - n2);
                if (n3 == -1) {
                    throw new AssertionError();
                }
                n2 += n3;
            }
            throw var4_2;
        }
        this.buffer.readFully(arrby);
    }

    @Override
    public int readInt() throws IOException {
        this.require(4);
        return this.buffer.readInt();
    }

    @Override
    public int readIntLe() throws IOException {
        this.require(4);
        return this.buffer.readIntLe();
    }

    @Override
    public long readLong() throws IOException {
        this.require(8);
        return this.buffer.readLong();
    }

    @Override
    public long readLongLe() throws IOException {
        this.require(8);
        return this.buffer.readLongLe();
    }

    @Override
    public short readShort() throws IOException {
        this.require(2);
        return this.buffer.readShort();
    }

    @Override
    public short readShortLe() throws IOException {
        this.require(2);
        return this.buffer.readShortLe();
    }

    @Override
    public String readString(long l2, Charset charset) throws IOException {
        this.require(l2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        return this.buffer.readString(l2, charset);
    }

    @Override
    public String readString(Charset charset) throws IOException {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.buffer.writeAll(this.source);
        return this.buffer.readString(charset);
    }

    @Override
    public String readUtf8() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readUtf8();
    }

    @Override
    public String readUtf8(long l2) throws IOException {
        this.require(l2);
        return this.buffer.readUtf8(l2);
    }

    @Override
    public String readUtf8Line() throws IOException {
        long l2 = this.indexOf(10);
        if (l2 == -1) {
            if (this.buffer.size != 0) {
                return this.readUtf8(this.buffer.size);
            }
            return null;
        }
        return this.buffer.readUtf8Line(l2);
    }

    @Override
    public String readUtf8LineStrict() throws IOException {
        long l2 = this.indexOf(10);
        if (l2 == -1) {
            throw new EOFException();
        }
        return this.buffer.readUtf8Line(l2);
    }

    @Override
    public void require(long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        while (this.buffer.size < l2) {
            if (this.source.read(this.buffer, 2048) != -1) continue;
            throw new EOFException();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void skip(long var1_1) throws IOException {
        if (!this.closed) ** GOTO lbl6
        throw new IllegalStateException("closed");
lbl-1000: // 1 sources:
        {
            var3_2 = Math.min(var1_1, this.buffer.size());
            this.buffer.skip(var3_2);
            var1_1 -= var3_2;
lbl6: // 2 sources:
            if (var1_1 <= 0) return;
            ** while (this.buffer.size != 0 || this.source.read((Buffer)this.buffer, (long)2048) != -1)
        }
lbl8: // 1 sources:
        throw new EOFException();
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        return "buffer(" + this.source + ")";
    }

}

