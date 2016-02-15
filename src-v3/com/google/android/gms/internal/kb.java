package com.google.android.gms.internal;

import com.google.android.gms.wearable.C1101a;
import com.google.android.gms.wearable.C1102c;

public class kb implements C1101a {
    private int LF;
    private C1102c adC;

    public kb(C1101a c1101a) {
        this.LF = c1101a.getType();
        this.adC = (C1102c) c1101a.lZ().freeze();
    }

    public /* synthetic */ Object freeze() {
        return me();
    }

    public int getType() {
        return this.LF;
    }

    public boolean isDataValid() {
        return true;
    }

    public C1102c lZ() {
        return this.adC;
    }

    public C1101a me() {
        return this;
    }
}
