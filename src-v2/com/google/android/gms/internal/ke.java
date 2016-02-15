/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.internal.kd;
import com.google.android.gms.wearable.d;

public class ke
extends b
implements d {
    public ke(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.mf();
    }

    @Override
    public String getId() {
        return this.getString("asset_id");
    }

    @Override
    public String mc() {
        return this.getString("asset_key");
    }

    public d mf() {
        return new kd(this);
    }
}

