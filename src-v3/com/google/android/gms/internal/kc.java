package com.google.android.gms.internal;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.C1101a;
import com.google.android.gms.wearable.C1102c;

public final class kc extends C0251b implements C1101a {
    private final int LE;

    public kc(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.LE = i2;
    }

    public /* synthetic */ Object freeze() {
        return me();
    }

    public int getType() {
        return getInteger("event_type");
    }

    public C1102c lZ() {
        return new kg(this.BB, this.BD, this.LE);
    }

    public C1101a me() {
        return new kb(this);
    }
}
