/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.wearable.a;
import com.google.android.gms.wearable.c;

public class kb
implements a {
    private int LF;
    private c adC;

    public kb(a a2) {
        this.LF = a2.getType();
        this.adC = (c)a2.lZ().freeze();
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.me();
    }

    @Override
    public int getType() {
        return this.LF;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public c lZ() {
        return this.adC;
    }

    public a me() {
        return this;
    }
}

