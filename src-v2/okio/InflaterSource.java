/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Segment;
import okio.Source;
import okio.Timeout;

public final class InflaterSource
implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.source = bufferedSource;
        this.inflater = inflater;
    }

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    private void releaseInflatedBytes() throws IOException {
        if (this.bufferBytesHeldByInflater == 0) {
            return;
        }
        int n2 = this.bufferBytesHeldByInflater - this.inflater.getRemaining();
        this.bufferBytesHeldByInflater -= n2;
        this.source.skip(n2);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    @Override
    public long read(Buffer buffer, long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (l2 == 0) {
            return 0;
        }
        do {
            boolean bl2;
            block9 : {
                bl2 = this.refill();
                Segment segment = buffer.writableSegment(1);
                int n2 = this.inflater.inflate(segment.data, segment.limit, 2048 - segment.limit);
                if (n2 <= 0) break block9;
                segment.limit += n2;
                buffer.size += (long)n2;
                return n2;
            }
            if (!this.inflater.finished() && !this.inflater.needsDictionary()) continue;
            this.releaseInflatedBytes();
            return -1;
            if (!bl2) continue;
            break;
        } while (true);
        try {
            throw new EOFException("source exhausted prematurely");
        }
        catch (DataFormatException var1_2) {
            throw new IOException(var1_2);
        }
    }

    public boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        this.releaseInflatedBytes();
        if (this.inflater.getRemaining() != 0) {
            throw new IllegalStateException("?");
        }
        if (this.source.exhausted()) {
            return true;
        }
        Segment segment = this.source.buffer().head;
        this.bufferBytesHeldByInflater = segment.limit - segment.pos;
        this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
        return false;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

