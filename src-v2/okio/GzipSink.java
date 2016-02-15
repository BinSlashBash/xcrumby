/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.DeflaterSink;
import okio.Okio;
import okio.Segment;
import okio.Sink;
import okio.Timeout;
import okio.Util;

public final class GzipSink
implements Sink {
    private boolean closed;
    private final CRC32 crc = new CRC32();
    private final Deflater deflater;
    private final DeflaterSink deflaterSink;
    private final BufferedSink sink;

    public GzipSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.deflater = new Deflater(-1, true);
        this.sink = Okio.buffer(sink);
        this.deflaterSink = new DeflaterSink(this.sink, this.deflater);
        this.writeHeader();
    }

    private void updateCrc(Buffer object, long l2) {
        object = object.head;
        while (l2 > 0) {
            int n2 = (int)Math.min(l2, (long)(object.limit - object.pos));
            this.crc.update(object.data, object.pos, n2);
            l2 -= (long)n2;
            object = object.next;
        }
    }

    private void writeFooter() throws IOException {
        this.sink.writeIntLe((int)this.crc.getValue());
        this.sink.writeIntLe(this.deflater.getTotalIn());
    }

    private void writeHeader() {
        Buffer buffer = this.sink.buffer();
        buffer.writeShort(8075);
        buffer.writeByte(8);
        buffer.writeByte(0);
        buffer.writeInt(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
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
                    this.deflaterSink.finishDeflate();
                    this.writeFooter();
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

    @Override
    public void flush() throws IOException {
        this.deflaterSink.flush();
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    @Override
    public void write(Buffer buffer, long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (l2 == 0) {
            return;
        }
        this.updateCrc(buffer, l2);
        this.deflaterSink.write(buffer, l2);
    }
}

