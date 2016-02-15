/*
 * Decompiled with CFR 0_110.
 */
package okio;

import okio.SegmentPool;

final class Segment {
    static final int SIZE = 2048;
    final byte[] data = new byte[2048];
    int limit;
    Segment next;
    int pos;
    Segment prev;

    Segment() {
    }

    public void compact() {
        if (this.prev == this) {
            throw new IllegalStateException();
        }
        if (this.prev.limit - this.prev.pos + (this.limit - this.pos) > 2048) {
            return;
        }
        this.writeTo(this.prev, this.limit - this.pos);
        this.pop();
        SegmentPool.INSTANCE.recycle(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Segment pop() {
        Segment segment = this.next != this ? this.next : null;
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
        return segment;
    }

    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
        return segment;
    }

    public Segment split(int n2) {
        int n3 = this.limit - this.pos - n2;
        if (n2 <= 0 || n3 <= 0) {
            throw new IllegalArgumentException();
        }
        if (n2 < n3) {
            Segment segment = SegmentPool.INSTANCE.take();
            System.arraycopy(this.data, this.pos, segment.data, segment.pos, n2);
            this.pos += n2;
            segment.limit += n2;
            this.prev.push(segment);
            return segment;
        }
        Segment segment = SegmentPool.INSTANCE.take();
        System.arraycopy(this.data, this.pos + n2, segment.data, segment.pos, n3);
        this.limit -= n3;
        segment.limit += n3;
        this.push(segment);
        return this;
    }

    public void writeTo(Segment segment, int n2) {
        if (segment.limit - segment.pos + n2 > 2048) {
            throw new IllegalArgumentException();
        }
        if (segment.limit + n2 > 2048) {
            System.arraycopy(segment.data, segment.pos, segment.data, 0, segment.limit - segment.pos);
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        System.arraycopy(this.data, this.pos, segment.data, segment.limit, n2);
        segment.limit += n2;
        this.pos += n2;
    }
}

