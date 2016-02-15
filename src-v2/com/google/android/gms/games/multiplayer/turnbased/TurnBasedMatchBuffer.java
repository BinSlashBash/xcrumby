/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer.turnbased;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchRef;

public final class TurnBasedMatchBuffer
extends d<TurnBasedMatch> {
    public TurnBasedMatchBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.getEntry(n2, n3);
    }

    protected TurnBasedMatch getEntry(int n2, int n3) {
        return new TurnBasedMatchRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}

