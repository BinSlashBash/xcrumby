package okio;

final class Segment {
    static final int SIZE = 2048;
    final byte[] data;
    int limit;
    Segment next;
    int pos;
    Segment prev;

    Segment() {
        this.data = new byte[SIZE];
    }

    public Segment pop() {
        Segment result;
        if (this.next != this) {
            result = this.next;
        } else {
            result = null;
        }
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
        return result;
    }

    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
        return segment;
    }

    public Segment split(int byteCount) {
        int aSize = byteCount;
        int bSize = (this.limit - this.pos) - byteCount;
        if (aSize <= 0 || bSize <= 0) {
            throw new IllegalArgumentException();
        } else if (aSize < bSize) {
            Segment before = SegmentPool.INSTANCE.take();
            System.arraycopy(this.data, this.pos, before.data, before.pos, aSize);
            this.pos += aSize;
            before.limit += aSize;
            this.prev.push(before);
            return before;
        } else {
            Segment after = SegmentPool.INSTANCE.take();
            System.arraycopy(this.data, this.pos + aSize, after.data, after.pos, bSize);
            this.limit -= bSize;
            after.limit += bSize;
            push(after);
            return this;
        }
    }

    public void compact() {
        if (this.prev == this) {
            throw new IllegalStateException();
        } else if ((this.prev.limit - this.prev.pos) + (this.limit - this.pos) <= SIZE) {
            writeTo(this.prev, this.limit - this.pos);
            pop();
            SegmentPool.INSTANCE.recycle(this);
        }
    }

    public void writeTo(Segment sink, int byteCount) {
        if ((sink.limit - sink.pos) + byteCount > SIZE) {
            throw new IllegalArgumentException();
        }
        if (sink.limit + byteCount > SIZE) {
            System.arraycopy(sink.data, sink.pos, sink.data, 0, sink.limit - sink.pos);
            sink.limit -= sink.pos;
            sink.pos = 0;
        }
        System.arraycopy(this.data, this.pos, sink.data, sink.limit, byteCount);
        sink.limit += byteCount;
        this.pos += byteCount;
    }
}
