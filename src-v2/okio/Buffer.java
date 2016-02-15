/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Segment;
import okio.SegmentPool;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

public final class Buffer
implements BufferedSource,
BufferedSink,
Cloneable {
    Segment head;
    long size;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void readFrom(InputStream var1_1, long var2_2, boolean var4_3) throws IOException {
        if (var1_1 != null) ** GOTO lbl6
        throw new IllegalArgumentException("in == null");
lbl-1000: // 1 sources:
        {
            var6_5.limit += var5_4;
            this.size += (long)var5_4;
            var2_2 -= (long)var5_4;
lbl6: // 2 sources:
            if (var2_2 <= 0) {
                if (var4_3 == false) return;
            }
            var6_5 = this.writableSegment(1);
            var5_4 = (int)Math.min(var2_2, (long)(2048 - var6_5.limit));
            ** while ((var5_4 = var1_1.read((byte[])var6_5.data, (int)var6_5.limit, (int)var5_4)) != -1)
        }
lbl11: // 1 sources:
        if (var4_3 == false) throw new EOFException();
    }

    @Override
    public Buffer buffer() {
        return this;
    }

    public void clear() {
        try {
            this.skip(this.size);
            return;
        }
        catch (EOFException var1_1) {
            throw new AssertionError(var1_1);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.size != 0) {
            buffer.write(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            Segment segment = this.head.next;
            while (segment != this.head) {
                buffer.write(segment.data, segment.pos, segment.limit - segment.pos);
                segment = segment.next;
            }
        }
        return buffer;
    }

    @Override
    public void close() {
    }

    public long completeSegmentByteCount() {
        long l2 = this.size;
        if (l2 == 0) {
            return 0;
        }
        Segment segment = this.head.prev;
        long l3 = l2;
        if (segment.limit < 2048) {
            l3 = l2 - (long)(segment.limit - segment.pos);
        }
        return l3;
    }

    public Buffer copyTo(OutputStream outputStream) throws IOException {
        return this.copyTo(outputStream, 0, this.size);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Buffer copyTo(OutputStream outputStream, long l2, long l3) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, l2, l3);
        if (l3 != 0) {
            Segment segment;
            long l4;
            int n2;
            Segment segment2 = this.head;
            do {
                segment = segment2;
                l4 = l2;
                if (l2 < (long)(segment2.limit - segment2.pos)) break;
                l2 -= (long)(segment2.limit - segment2.pos);
                segment2 = segment2.next;
            } while (true);
            for (long i2 = l3; i2 > 0; i2 -= (long)n2) {
                int n3 = (int)((long)segment.pos + l4);
                n2 = (int)Math.min((long)(segment.limit - n3), i2);
                outputStream.write(segment.data, n3, n2);
                l4 = 0;
                segment = segment.next;
            }
        }
        return this;
    }

    @Override
    public Buffer emitCompleteSegments() {
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Buffer)) {
            return false;
        }
        object = (Buffer)object;
        if (this.size != object.size) {
            return false;
        }
        if (this.size == 0) {
            return true;
        }
        Segment segment = this.head;
        object = object.head;
        int n2 = segment.pos;
        int n3 = object.pos;
        long l2 = 0;
        while (l2 < this.size) {
            long l3 = Math.min(segment.limit - n2, object.limit - n3);
            int n4 = 0;
            int n5 = n2;
            n2 = n3;
            n3 = n5;
            n5 = n4;
            while ((long)n5 < l3) {
                if (segment.data[n3] != object.data[n2]) {
                    return false;
                }
                ++n5;
                ++n2;
                ++n3;
            }
            if (n3 == segment.limit) {
                segment = segment.next;
                n3 = segment.pos;
            }
            if (n2 == object.limit) {
                object = object.next;
                n5 = object.pos;
            } else {
                n5 = n2;
            }
            l2 += l3;
            n2 = n3;
            n3 = n5;
        }
        return true;
    }

    @Override
    public boolean exhausted() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void flush() {
    }

    public byte getByte(long l2) {
        Util.checkOffsetAndCount(this.size, l2, 1);
        Segment segment = this.head;
        int n2;
        while (l2 >= (long)(n2 = segment.limit - segment.pos)) {
            l2 -= (long)n2;
            segment = segment.next;
        }
        return segment.data[segment.pos + (int)l2];
    }

    public int hashCode() {
        Segment segment;
        int n2;
        Segment segment2 = this.head;
        if (segment2 == null) {
            return 0;
        }
        int n3 = 1;
        do {
            int n4 = segment2.pos;
            int n5 = segment2.limit;
            n2 = n3;
            for (n3 = n4; n3 < n5; ++n3) {
                n2 = n2 * 31 + segment2.data[n3];
            }
            segment = segment2.next;
            n3 = n2;
            segment2 = segment;
        } while (segment != this.head);
        return n2;
    }

    @Override
    public long indexOf(byte by2) {
        return this.indexOf(by2, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long indexOf(byte var1_1, long var2_2) {
        if (var2_2 < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        var9_3 = this.head;
        if (var9_3 == null) {
            return -1;
        }
        var5_4 = 0;
        block0 : while (var2_2 >= (long)(var4_5 = var9_3.limit - var9_3.pos)) {
            var2_2 -= (long)var4_5;
lbl9: // 2 sources:
            do {
                var5_4 += (long)var4_5;
                var9_3 = var10_7 = (Object)var9_3.next;
                if (var10_7 != this.head) continue block0;
                return -1;
                break;
            } while (true);
        }
        var10_7 = var9_3.data;
        var7_6 = var9_3.limit;
        for (var2_2 = (long)var9_3.pos + var2_2; var2_2 < var7_6; ++var2_2) {
            if (var10_7[(int)var2_2] != var1_1) continue;
            return var5_4 + var2_2 - (long)var9_3.pos;
        }
        var2_2 = 0;
        ** while (true)
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() {
                return (int)Math.min(Buffer.this.size, Integer.MAX_VALUE);
            }

            @Override
            public void close() {
            }

            @Override
            public int read() {
                if (Buffer.this.size > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            @Override
            public int read(byte[] arrby, int n2, int n3) {
                return Buffer.this.read(arrby, n2, n3);
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void close() {
            }

            @Override
            public void flush() {
            }

            public String toString() {
                return this + ".outputStream()";
            }

            @Override
            public void write(int n2) {
                Buffer.this.writeByte((byte)n2);
            }

            @Override
            public void write(byte[] arrby, int n2, int n3) {
                Buffer.this.write(arrby, n2, n3);
            }
        };
    }

    @Override
    public int read(byte[] arrby) {
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read(byte[] arrby, int n2, int n3) {
        Util.checkOffsetAndCount(arrby.length, n2, n3);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        n3 = Math.min(n3, segment.limit - segment.pos);
        System.arraycopy(segment.data, segment.pos, arrby, n2, n3);
        segment.pos += n3;
        this.size -= (long)n3;
        n2 = n3;
        if (segment.pos != segment.limit) return n2;
        this.head = segment.pop();
        SegmentPool.INSTANCE.recycle(segment);
        return n3;
    }

    @Override
    public long read(Buffer buffer, long l2) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        if (this.size == 0) {
            return -1;
        }
        long l3 = l2;
        if (l2 > this.size) {
            l3 = this.size;
        }
        buffer.write(this, l3);
        return l3;
    }

    @Override
    public long readAll(Sink sink) throws IOException {
        long l2 = this.size;
        if (l2 > 0) {
            sink.write(this, l2);
        }
        return l2;
    }

    @Override
    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int n2 = segment.pos;
        int n3 = segment.limit;
        byte[] arrby = segment.data;
        int n4 = n2 + 1;
        byte by2 = arrby[n2];
        --this.size;
        if (n4 == n3) {
            this.head = segment.pop();
            SegmentPool.INSTANCE.recycle(segment);
            return by2;
        }
        segment.pos = n4;
        return by2;
    }

    @Override
    public byte[] readByteArray() {
        try {
            byte[] arrby = this.readByteArray(this.size);
            return arrby;
        }
        catch (EOFException var1_2) {
            throw new AssertionError(var1_2);
        }
    }

    @Override
    public byte[] readByteArray(long l2) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, l2);
        if (l2 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + l2);
        }
        byte[] arrby = new byte[(int)l2];
        this.readFully(arrby);
        return arrby;
    }

    @Override
    public ByteString readByteString() {
        return new ByteString(this.readByteArray());
    }

    @Override
    public ByteString readByteString(long l2) throws EOFException {
        return new ByteString(this.readByteArray(l2));
    }

    public Buffer readFrom(InputStream inputStream) throws IOException {
        this.readFrom(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    public Buffer readFrom(InputStream inputStream, long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + l2);
        }
        this.readFrom(inputStream, l2, false);
        return this;
    }

    @Override
    public void readFully(Buffer buffer, long l2) throws EOFException {
        if (this.size < l2) {
            buffer.write(this, this.size);
            throw new EOFException();
        }
        buffer.write(this, l2);
    }

    @Override
    public void readFully(byte[] arrby) throws EOFException {
        int n2;
        for (int i2 = 0; i2 < arrby.length; i2 += n2) {
            n2 = this.read(arrby, i2, arrby.length - i2);
            if (n2 != -1) continue;
            throw new EOFException();
        }
    }

    @Override
    public int readInt() {
        if (this.size < 4) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int n2 = segment.limit;
        int n3 = segment.pos;
        if (n2 - n3 < 4) {
            return (this.readByte() & 255) << 24 | (this.readByte() & 255) << 16 | (this.readByte() & 255) << 8 | this.readByte() & 255;
        }
        byte[] arrby = segment.data;
        int n4 = n3 + 1;
        n3 = arrby[n3];
        int n5 = n4 + 1;
        n4 = arrby[n4];
        int n6 = n5 + 1;
        byte by2 = arrby[n5];
        n5 = n6 + 1;
        n3 = (n3 & 255) << 24 | (n4 & 255) << 16 | (by2 & 255) << 8 | arrby[n6] & 255;
        this.size -= 4;
        if (n5 == n2) {
            this.head = segment.pop();
            SegmentPool.INSTANCE.recycle(segment);
            return n3;
        }
        segment.pos = n5;
        return n3;
    }

    @Override
    public int readIntLe() {
        return Util.reverseBytesInt(this.readInt());
    }

    @Override
    public long readLong() {
        if (this.size < 8) {
            throw new IllegalStateException("size < 8: " + this.size);
        }
        Segment segment = this.head;
        int n2 = segment.limit;
        int n3 = segment.pos;
        if (n2 - n3 < 8) {
            return ((long)this.readInt() & 0xFFFFFFFFL) << 32 | (long)this.readInt() & 0xFFFFFFFFL;
        }
        byte[] arrby = segment.data;
        int n4 = n3 + 1;
        long l2 = arrby[n3];
        n3 = n4 + 1;
        long l3 = arrby[n4];
        n4 = n3 + 1;
        long l4 = arrby[n3];
        n3 = n4 + 1;
        long l5 = arrby[n4];
        n4 = n3 + 1;
        long l6 = arrby[n3];
        n3 = n4 + 1;
        long l7 = arrby[n4];
        n4 = n3 + 1;
        long l8 = arrby[n3];
        n3 = n4 + 1;
        l2 = (l2 & 255) << 56 | (l3 & 255) << 48 | (l4 & 255) << 40 | (l5 & 255) << 32 | (l6 & 255) << 24 | (l7 & 255) << 16 | (l8 & 255) << 8 | (long)arrby[n4] & 255;
        this.size -= 8;
        if (n3 == n2) {
            this.head = segment.pop();
            SegmentPool.INSTANCE.recycle(segment);
            return l2;
        }
        segment.pos = n3;
        return l2;
    }

    @Override
    public long readLongLe() {
        return Util.reverseBytesLong(this.readLong());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public short readShort() {
        if (this.size < 2) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int n2 = segment.limit;
        int n3 = segment.pos;
        if (n2 - n3 < 2) {
            return (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
        }
        byte[] arrby = segment.data;
        int n4 = n3 + 1;
        n3 = arrby[n3];
        int n5 = n4 + 1;
        n4 = arrby[n4];
        this.size -= 2;
        if (n5 == n2) {
            this.head = segment.pop();
            SegmentPool.INSTANCE.recycle(segment);
            do {
                return (short)((n3 & 255) << 8 | n4 & 255);
                break;
            } while (true);
        }
        segment.pos = n5;
        return (short)((n3 & 255) << 8 | n4 & 255);
    }

    @Override
    public short readShortLe() {
        return Util.reverseBytesShort(this.readShort());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String readString(long l2, Charset object) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, l2);
        if (object == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (l2 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + l2);
        }
        if (l2 == 0) {
            return "";
        }
        Segment segment = this.head;
        if ((long)segment.pos + l2 > (long)segment.limit) {
            return new String(this.readByteArray(l2), (Charset)object);
        }
        String string2 = new String(segment.data, segment.pos, (int)l2, (Charset)object);
        segment.pos = (int)((long)segment.pos + l2);
        this.size -= l2;
        object = string2;
        if (segment.pos != segment.limit) return object;
        this.head = segment.pop();
        SegmentPool.INSTANCE.recycle(segment);
        return string2;
    }

    @Override
    public String readString(Charset object) {
        try {
            object = this.readString(this.size, (Charset)object);
            return object;
        }
        catch (EOFException var1_2) {
            throw new AssertionError(var1_2);
        }
    }

    @Override
    public String readUtf8() {
        try {
            String string2 = this.readString(this.size, Util.UTF_8);
            return string2;
        }
        catch (EOFException var1_2) {
            throw new AssertionError(var1_2);
        }
    }

    @Override
    public String readUtf8(long l2) throws EOFException {
        return this.readString(l2, Util.UTF_8);
    }

    @Override
    public String readUtf8Line() throws EOFException {
        long l2 = this.indexOf(10);
        if (l2 == -1) {
            if (this.size != 0) {
                return this.readUtf8(this.size);
            }
            return null;
        }
        return this.readUtf8Line(l2);
    }

    String readUtf8Line(long l2) throws EOFException {
        if (l2 > 0 && this.getByte(l2 - 1) == 13) {
            String string2 = this.readUtf8(l2 - 1);
            this.skip(2);
            return string2;
        }
        String string3 = this.readUtf8(l2);
        this.skip(1);
        return string3;
    }

    @Override
    public String readUtf8LineStrict() throws EOFException {
        long l2 = this.indexOf(10);
        if (l2 == -1) {
            throw new EOFException();
        }
        return this.readUtf8Line(l2);
    }

    @Override
    public void require(long l2) throws EOFException {
        if (this.size < l2) {
            throw new EOFException();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(this.head.limit - this.head.pos);
        Segment segment = this.head.next;
        do {
            List<Integer> list = arrayList;
            if (segment == this.head) return list;
            arrayList.add(segment.limit - segment.pos);
            segment = segment.next;
        } while (true);
    }

    public long size() {
        return this.size;
    }

    @Override
    public void skip(long l2) throws EOFException {
        while (l2 > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int n2 = (int)Math.min(l2, (long)(this.head.limit - this.head.pos));
            this.size -= (long)n2;
            long l3 = l2 - (long)n2;
            Segment segment = this.head;
            segment.pos += n2;
            l2 = l3;
            if (this.head.pos != this.head.limit) continue;
            segment = this.head;
            this.head = segment.pop();
            SegmentPool.INSTANCE.recycle(segment);
            l2 = l3;
        }
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        if (this.size <= 16) {
            ByteString byteString = this.clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", this.size, byteString.hex());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            Object object = this.head.next;
            while (object != this.head) {
                messageDigest.update(object.data, object.pos, object.limit - object.pos);
                object = object.next;
            }
            object = String.format("Buffer[size=%s md5=%s]", this.size, ByteString.of(messageDigest.digest()).hex());
            return object;
        }
        catch (NoSuchAlgorithmException var1_3) {
            throw new AssertionError();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    Segment writableSegment(int n2) {
        Segment segment;
        if (n2 < 1 || n2 > 2048) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            Segment segment2 = this.head = SegmentPool.INSTANCE.take();
            Segment segment3 = this.head;
            segment3.prev = segment = this.head;
            segment2.next = segment;
            return segment;
        } else {
            Segment segment4;
            segment = segment4 = this.head.prev;
            if (segment4.limit + n2 <= 2048) return segment;
            return segment4.push(SegmentPool.INSTANCE.take());
        }
    }

    @Override
    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        return this.write(byteString.data, 0, byteString.data.length);
    }

    @Override
    public Buffer write(byte[] arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("source == null");
        }
        return this.write(arrby, 0, arrby.length);
    }

    @Override
    public Buffer write(byte[] arrby, int n2, int n3) {
        if (arrby == null) {
            throw new IllegalArgumentException("source == null");
        }
        Util.checkOffsetAndCount(arrby.length, n2, n3);
        int n4 = n2 + n3;
        while (n2 < n4) {
            Segment segment = this.writableSegment(1);
            int n5 = Math.min(n4 - n2, 2048 - segment.limit);
            System.arraycopy(arrby, n2, segment.data, segment.limit, n5);
            n2 += n5;
            segment.limit += n5;
        }
        this.size += (long)n3;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void write(Buffer buffer, long l2) {
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        }
        Util.checkOffsetAndCount(buffer.size, 0, l2);
        while (l2 > 0) {
            Segment segment;
            if (l2 < (long)(buffer.head.limit - buffer.head.pos)) {
                segment = this.head != null ? this.head.prev : null;
                if (segment != null && (long)(segment.limit - segment.pos) + l2 <= 2048) {
                    buffer.head.writeTo(segment, (int)l2);
                    buffer.size -= l2;
                    this.size += l2;
                    return;
                }
                buffer.head = buffer.head.split((int)l2);
            }
            segment = buffer.head;
            long l3 = segment.limit - segment.pos;
            buffer.head = segment.pop();
            if (this.head == null) {
                Segment segment2;
                segment = this.head = segment;
                Segment segment3 = this.head;
                segment3.prev = segment2 = this.head;
                segment.next = segment2;
            } else {
                this.head.prev.push(segment).compact();
            }
            buffer.size -= l3;
            this.size += l3;
            l2 -= l3;
        }
    }

    @Override
    public long writeAll(Source source) throws IOException {
        long l2;
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long l3 = 0;
        while ((l2 = source.read(this, 2048)) != -1) {
            l3 += l2;
        }
        return l3;
    }

    @Override
    public Buffer writeByte(int n2) {
        Segment segment = this.writableSegment(1);
        byte[] arrby = segment.data;
        int n3 = segment.limit;
        segment.limit = n3 + 1;
        arrby[n3] = (byte)n2;
        ++this.size;
        return this;
    }

    @Override
    public Buffer writeInt(int n2) {
        Segment segment = this.writableSegment(4);
        byte[] arrby = segment.data;
        int n3 = segment.limit;
        int n4 = n3 + 1;
        arrby[n3] = (byte)(n2 >>> 24 & 255);
        n3 = n4 + 1;
        arrby[n4] = (byte)(n2 >>> 16 & 255);
        n4 = n3 + 1;
        arrby[n3] = (byte)(n2 >>> 8 & 255);
        arrby[n4] = (byte)(n2 & 255);
        segment.limit = n4 + 1;
        this.size += 4;
        return this;
    }

    @Override
    public Buffer writeIntLe(int n2) {
        return this.writeInt(Util.reverseBytesInt(n2));
    }

    @Override
    public Buffer writeLong(long l2) {
        Segment segment = this.writableSegment(8);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(l2 >>> 56 & 255);
        n2 = n3 + 1;
        arrby[n3] = (byte)(l2 >>> 48 & 255);
        n3 = n2 + 1;
        arrby[n2] = (byte)(l2 >>> 40 & 255);
        n2 = n3 + 1;
        arrby[n3] = (byte)(l2 >>> 32 & 255);
        n3 = n2 + 1;
        arrby[n2] = (byte)(l2 >>> 24 & 255);
        n2 = n3 + 1;
        arrby[n3] = (byte)(l2 >>> 16 & 255);
        n3 = n2 + 1;
        arrby[n2] = (byte)(l2 >>> 8 & 255);
        arrby[n3] = (byte)(l2 & 255);
        segment.limit = n3 + 1;
        this.size += 8;
        return this;
    }

    @Override
    public Buffer writeLongLe(long l2) {
        return this.writeLong(Util.reverseBytesLong(l2));
    }

    @Override
    public Buffer writeShort(int n2) {
        Segment segment = this.writableSegment(2);
        byte[] arrby = segment.data;
        int n3 = segment.limit;
        int n4 = n3 + 1;
        arrby[n3] = (byte)(n2 >>> 8 & 255);
        arrby[n4] = (byte)(n2 & 255);
        segment.limit = n4 + 1;
        this.size += 2;
        return this;
    }

    @Override
    public Buffer writeShortLe(int n2) {
        return this.writeShort(Util.reverseBytesShort((short)n2));
    }

    @Override
    public Buffer writeString(String string2, Charset charset) {
        if (string2 == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        string2 = (String)string2.getBytes(charset);
        return this.write((byte[])string2, 0, string2.length);
    }

    public Buffer writeTo(OutputStream outputStream) throws IOException {
        return this.writeTo(outputStream, this.size);
    }

    public Buffer writeTo(OutputStream outputStream, long l2) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, 0, l2);
        Segment segment = this.head;
        do {
            Segment segment2 = segment;
            if (l2 <= 0) break;
            int n2 = (int)Math.min(l2, (long)(segment2.limit - segment2.pos));
            outputStream.write(segment2.data, segment2.pos, n2);
            segment2.pos += n2;
            this.size -= (long)n2;
            long l3 = l2 - (long)n2;
            segment = segment2;
            l2 = l3;
            if (segment2.pos != segment2.limit) continue;
            this.head = segment = segment2.pop();
            SegmentPool.INSTANCE.recycle(segment2);
            l2 = l3;
        } while (true);
        return this;
    }

    @Override
    public Buffer writeUtf8(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("string == null");
        }
        return this.writeString(string2, Util.UTF_8);
    }

}

