package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.C0796d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.kc;

/* renamed from: com.google.android.gms.wearable.b */
public class C1423b extends C0796d<C1101a> implements Result {
    private final Status wJ;

    public C1423b(DataHolder dataHolder) {
        super(dataHolder);
        this.wJ = new Status(dataHolder.getStatusCode());
    }

    protected /* synthetic */ Object m3254c(int i, int i2) {
        return m3255g(i, i2);
    }

    protected C1101a m3255g(int i, int i2) {
        return new kc(this.BB, i, i2);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "path";
    }

    public Status getStatus() {
        return this.wJ;
    }
}
