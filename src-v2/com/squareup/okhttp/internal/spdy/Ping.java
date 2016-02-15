/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long received = -1;
    private long sent = -1;

    Ping() {
    }

    void cancel() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = this.sent - 1;
        this.latch.countDown();
    }

    void receive() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = System.nanoTime();
        this.latch.countDown();
    }

    public long roundTripTime() throws InterruptedException {
        this.latch.await();
        return this.received - this.sent;
    }

    public long roundTripTime(long l2, TimeUnit timeUnit) throws InterruptedException {
        if (this.latch.await(l2, timeUnit)) {
            return this.received - this.sent;
        }
        return -2;
    }

    void send() {
        if (this.sent != -1) {
            throw new IllegalStateException();
        }
        this.sent = System.nanoTime();
    }
}

