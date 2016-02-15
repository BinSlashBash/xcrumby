package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {
    private final LeaderboardScoreBufferHeader LT;

    public LeaderboardScoreBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.LT = new LeaderboardScoreBufferHeader(dataHolder.getMetadata());
    }

    public LeaderboardScore get(int position) {
        return new LeaderboardScoreRef(this.BB, position);
    }

    public LeaderboardScoreBufferHeader hD() {
        return this.LT;
    }
}
