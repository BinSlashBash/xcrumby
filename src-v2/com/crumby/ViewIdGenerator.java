/*
 * Decompiled with CFR 0_110.
 */
package com.crumby;

import java.util.concurrent.atomic.AtomicInteger;

public enum ViewIdGenerator {
    INSTANCE;
    
    private final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private ViewIdGenerator() {
    }

    public int generateViewId() {
        int n2;
        int n3;
        do {
            int n4;
            n2 = this.sNextGeneratedId.get();
            n3 = n4 = n2 + 1;
            if (n4 <= 16777215) continue;
            n3 = 1;
        } while (!this.sNextGeneratedId.compareAndSet(n2, n3));
        return n2;
    }
}

