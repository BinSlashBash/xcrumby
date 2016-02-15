/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.appstate;

import com.google.android.gms.appstate.AppState;
import com.google.android.gms.appstate.b;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class AppStateBuffer
extends DataBuffer<AppState> {
    public AppStateBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    public AppState get(int n2) {
        return new b(this.BB, n2);
    }
}

