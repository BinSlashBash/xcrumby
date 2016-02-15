/*
 * Decompiled with CFR 0_110.
 */
package okio;

import okio.Segment;

final class SegmentPool {
    static final SegmentPool INSTANCE = new SegmentPool();
    static final long MAX_SIZE = 65536;
    long byteCount;
    private Segment next;

    private SegmentPool() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        }
        synchronized (this) {
            if (this.byteCount + 2048 > 65536) {
                return;
            }
            this.byteCount += 2048;
            segment.next = this.next;
            segment.limit = 0;
            segment.pos = 0;
            this.next = segment;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Segment take() {
        synchronized (this) {
            if (this.next != null) {
                Segment segment = this.next;
                this.next = segment.next;
                segment.next = null;
                this.byteCount -= 2048;
                return segment;
            }
            return new Segment();
        }
    }
}

