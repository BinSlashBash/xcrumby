/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardRef;

public final class LeaderboardBuffer
extends d<Leaderboard> {
    public LeaderboardBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.getEntry(n2, n3);
    }

    protected Leaderboard getEntry(int n2, int n3) {
        return new LeaderboardRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_leaderboard_id";
    }
}

