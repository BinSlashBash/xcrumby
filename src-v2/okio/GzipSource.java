/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Segment;
import okio.Source;
import okio.Timeout;

public final class GzipSource
implements Source {
    private static final byte FCOMMENT = 4;
    private static final byte FEXTRA = 2;
    private static final byte FHCRC = 1;
    private static final byte FNAME = 3;
    private static final byte SECTION_BODY = 1;
    private static final byte SECTION_DONE = 3;
    private static final byte SECTION_HEADER = 0;
    private static final byte SECTION_TRAILER = 2;
    private final CRC32 crc = new CRC32();
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private int section = 0;
    private final BufferedSource source;

    public GzipSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.inflater = new Inflater(true);
        this.source = Okio.buffer(source);
        this.inflaterSource = new InflaterSource(this.source, this.inflater);
    }

    private void checkEqual(String string2, int n2, int n3) throws IOException {
        if (n3 != n2) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", string2, n3, n2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consumeHeader() throws IOException {
        long l2;
        this.source.require(10);
        byte by2 = this.source.buffer().getByte(3);
        boolean bl2 = (by2 >> 1 & 1) == 1;
        if (bl2) {
            this.updateCrc(this.source.buffer(), 0, 10);
        }
        this.checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8);
        if ((by2 >> 2 & 1) == 1) {
            this.source.require(2);
            if (bl2) {
                this.updateCrc(this.source.buffer(), 0, 2);
            }
            short s2 = this.source.buffer().readShortLe();
            this.source.require(s2);
            if (bl2) {
                this.updateCrc(this.source.buffer(), 0, s2);
            }
            this.source.skip(s2);
        }
        if ((by2 >> 3 & 1) == 1) {
            l2 = this.source.indexOf(0);
            if (l2 == -1) {
                throw new EOFException();
            }
            if (bl2) {
                this.updateCrc(this.source.buffer(), 0, 1 + l2);
            }
            this.source.skip(1 + l2);
        }
        if ((by2 >> 4 & 1) == 1) {
            l2 = this.source.indexOf(0);
            if (l2 == -1) {
                throw new EOFException();
            }
            if (bl2) {
                this.updateCrc(this.source.buffer(), 0, 1 + l2);
            }
            this.source.skip(1 + l2);
        }
        if (bl2) {
            this.checkEqual("FHCRC", this.source.readShortLe(), (short)this.crc.getValue());
            this.crc.reset();
        }
    }

    private void consumeTrailer() throws IOException {
        this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
        this.checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
    }

    private void updateCrc(Buffer object, long l2, long l3) {
        Object object2;
        int n2;
        long l4;
        object = object.head;
        do {
            object2 = object;
            l4 = l2;
            if (l2 < (long)(object.limit - object.pos)) break;
            l2 -= (long)(object.limit - object.pos);
            object = object.next;
        } while (true);
        for (long i2 = l3; i2 > 0; i2 -= (long)n2) {
            int n3 = (int)((long)object2.pos + l4);
            n2 = (int)Math.min((long)(object2.limit - n3), i2);
            this.crc.update(object2.data, n3, n2);
            l4 = 0;
            object2 = object2.next;
        }
    }

    @Override
    public void close() throws IOException {
        this.inflaterSource.close();
    }

    @Override
    public long read(Buffer buffer, long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (l2 == 0) {
            return 0;
        }
        if (this.section == 0) {
            this.consumeHeader();
            this.section = 1;
        }
        if (this.section == 1) {
            long l3 = buffer.size;
            if ((l2 = this.inflaterSource.read(buffer, l2)) != -1) {
                this.updateCrc(buffer, l3, l2);
                return l2;
            }
            this.section = 2;
        }
        if (this.section == 2) {
            this.consumeTrailer();
            this.section = 3;
            if (!this.source.exhausted()) {
                throw new IOException("gzip finished without exhausting source");
            }
        }
        return -1;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

