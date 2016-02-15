/*
 * Decompiled with CFR 0_110.
 */
package com.crumby;

import com.squareup.otto.Bus;

public enum BusProvider {
    BUS;
    
    private final Bus bus = new Bus();

    private BusProvider() {
    }

    public Bus get() {
        return this.bus;
    }
}

