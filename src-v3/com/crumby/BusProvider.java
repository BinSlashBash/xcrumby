package com.crumby;

import com.squareup.otto.Bus;

public enum BusProvider {
    BUS;
    
    private final Bus bus;

    public Bus get() {
        return this.bus;
    }
}
