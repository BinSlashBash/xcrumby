/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.LeaderboardVariantEntity;

public final class LeaderboardVariantRef
extends b
implements LeaderboardVariant {
    LeaderboardVariantRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    @Override
    public boolean equals(Object object) {
        return LeaderboardVariantEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hJ();
    }

    @Override
    public int getCollection() {
        return this.getInteger("collection");
    }

    @Override
    public String getDisplayPlayerRank() {
        return this.getString("player_display_rank");
    }

    @Override
    public String getDisplayPlayerScore() {
        return this.getString("player_display_score");
    }

    @Override
    public long getNumScores() {
        if (this.ai("total_scores")) {
            return -1;
        }
        return this.getLong("total_scores");
    }

    @Override
    public long getPlayerRank() {
        if (this.ai("player_rank")) {
            return -1;
        }
        return this.getLong("player_rank");
    }

    @Override
    public String getPlayerScoreTag() {
        return this.getString("player_score_tag");
    }

    @Override
    public long getRawPlayerScore() {
        if (this.ai("player_raw_score")) {
            return -1;
        }
        return this.getLong("player_raw_score");
    }

    @Override
    public int getTimeSpan() {
        return this.getInteger("timespan");
    }

    @Override
    public String hG() {
        return this.getString("top_page_token_next");
    }

    @Override
    public String hH() {
        return this.getString("window_page_token_prev");
    }

    @Override
    public String hI() {
        return this.getString("window_page_token_next");
    }

    public LeaderboardVariant hJ() {
        return new LeaderboardVariantEntity(this);
    }

    @Override
    public boolean hasPlayerInfo() {
        if (!this.ai("player_raw_score")) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LeaderboardVariantEntity.a(this);
    }

    public String toString() {
        return LeaderboardVariantEntity.b(this);
    }
}

