/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.request.GameRequestSummary;
import com.google.android.gms.games.request.GameRequestSummaryRef;

public final class GameRequestSummaryBuffer
extends DataBuffer<GameRequestSummary> {
    public GameRequestSummary br(int n2) {
        return new GameRequestSummaryRef(this.BB, n2);
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.br(n2);
    }
}

