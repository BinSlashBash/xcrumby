/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.internal.kc;
import com.google.android.gms.wearable.a;

public class b
extends d<a>
implements Result {
    private final Status wJ;

    public b(DataHolder dataHolder) {
        super(dataHolder);
        this.wJ = new Status(dataHolder.getStatusCode());
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.g(n2, n3);
    }

    protected a g(int n2, int n3) {
        return new kc(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "path";
    }

    @Override
    public Status getStatus() {
        return this.wJ;
    }
}

