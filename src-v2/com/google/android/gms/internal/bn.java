/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bl;
import com.google.android.gms.internal.br;

public final class bn {
    public final bl nA;
    public final int nw;
    public final bi nx;
    public final br ny;
    public final String nz;

    public bn(int n2) {
        this(null, null, null, null, n2);
    }

    public bn(bi bi2, br br2, String string2, bl bl2, int n2) {
        this.nx = bi2;
        this.ny = br2;
        this.nz = string2;
        this.nA = bl2;
        this.nw = n2;
    }

    public static interface a {
        public void f(int var1);
    }

}

