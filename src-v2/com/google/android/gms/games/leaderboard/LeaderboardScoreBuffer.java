/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.games.leaderboard;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBufferHeader;
import com.google.android.gms.games.leaderboard.LeaderboardScoreRef;

public final class LeaderboardScoreBuffer
extends DataBuffer<LeaderboardScore> {
    private final LeaderboardScoreBufferHeader LT;

    public LeaderboardScoreBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.LT = new LeaderboardScoreBufferHeader(dataHolder.getMetadata());
    }

    @Override
    public LeaderboardScore get(int n2) {
        return new LeaderboardScoreRef(this.BB, n2);
    }

    public LeaderboardScoreBufferHeader hD() {
        return this.LT;
    }
}

