/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 */
package okio;

import java.io.IOException;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Segment;
import okio.SegmentPool;
import okio.Sink;
import okio.Timeout;
import okio.Util;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class DeflaterSink
implements Sink {
    private boolean closed;
    private final Deflater deflater;
    private final BufferedSink sink;

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.sink = bufferedSink;
        this.deflater = deflater;
    }

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    /*
     * Enabled aggressive block sorting
     */
    @IgnoreJRERequirement
    private void deflate(boolean bl2) throws IOException {
        Buffer buffer = this.sink.buffer();
        do {
            Segment segment = buffer.writableSegment(1);
            int n2 = bl2 ? this.deflater.deflate(segment.data, segment.limit, 2048 - segment.limit, 2) : this.deflater.deflate(segment.data, segment.limit, 2048 - segment.limit);
            if (n2 > 0) {
                segment.limit += n2;
                buffer.size += (long)n2;
                this.sink.emitCompleteSegments();
                continue;
            }
            if (this.deflater.needsInput()) break;
        } while (true);
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
        block9 : {
            Throwable throwable2;
            block8 : {
                if (this.closed) {
                    return;
                }
                throwable = null;
                try {
                    this.finishDeflate();
                }
                catch (Throwable var2_2) {}
                try {
                    this.deflater.end();
                    throwable2 = throwable;
                }
                catch (Throwable var3_4) {
                    throwable2 = throwable;
                    if (throwable != null) break block8;
                    throwable2 = var3_4;
                }
            }
            try {
                this.sink.close();
                throwable = throwable2;
            }
            catch (Throwable var3_5) {
                throwable = throwable2;
                if (throwable2 != null) break block9;
                throwable = var3_5;
            }
        }
        this.closed = true;
        if (throwable == null) return;
        Util.sneakyRethrow(throwable);
    }

    void finishDeflate() throws IOException {
        this.deflater.finish();
        this.deflate(false);
    }

    @Override
    public void flush() throws IOException {
        this.deflate(true);
        this.sink.flush();
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "DeflaterSink(" + this.sink + ")";
    }

    @Override
    public void write(Buffer buffer, long l2) throws IOException {
        Util.checkOffsetAndCount(buffer.size, 0, l2);
        while (l2 > 0) {
            Segment segment = buffer.head;
            int n2 = (int)Math.min(l2, (long)(segment.limit - segment.pos));
            this.deflater.setInput(segment.data, segment.pos, n2);
            this.deflate(false);
            buffer.size -= (long)n2;
            segment.pos += n2;
            if (segment.pos == segment.limit) {
                buffer.head = segment.pop();
                SegmentPool.INSTANCE.recycle(segment);
            }
            l2 -= (long)n2;
        }
    }
}

