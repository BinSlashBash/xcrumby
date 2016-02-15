/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.internal.kb;
import com.google.android.gms.internal.kg;
import com.google.android.gms.wearable.a;
import com.google.android.gms.wearable.c;

public final class kc
extends b
implements a {
    private final int LE;

    public kc(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LE = n3;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.me();
    }

    @Override
    public int getType() {
        return this.getInteger("event_type");
    }

    @Override
    public c lZ() {
        return new kg(this.BB, this.BD, this.LE);
    }

    public a me() {
        return new kb(this);
    }
}

