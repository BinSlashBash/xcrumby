/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ig;
import com.google.android.gms.plus.model.moments.Moment;

public final class MomentBuffer
extends DataBuffer<Moment> {
    public MomentBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    public Moment get(int n2) {
        return new ig(this.BB, n2);
    }
}

