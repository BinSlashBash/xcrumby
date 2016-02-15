/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestRef;

public final class GameRequestBuffer
extends d<GameRequest> {
    public GameRequestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.getEntry(n2, n3);
    }

    protected GameRequest getEntry(int n2, int n3) {
        return new GameRequestRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_request_id";
    }
}

