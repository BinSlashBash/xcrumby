package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;

public final class LeaderboardVariantRef extends C0251b implements LeaderboardVariant {
    LeaderboardVariantRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    public boolean equals(Object obj) {
        return LeaderboardVariantEntity.m2889a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return hJ();
    }

    public int getCollection() {
        return getInteger("collection");
    }

    public String getDisplayPlayerRank() {
        return getString("player_display_rank");
    }

    public String getDisplayPlayerScore() {
        return getString("player_display_score");
    }

    public long getNumScores() {
        return ai("total_scores") ? -1 : getLong("total_scores");
    }

    public long getPlayerRank() {
        return ai("player_rank") ? -1 : getLong("player_rank");
    }

    public String getPlayerScoreTag() {
        return getString("player_score_tag");
    }

    public long getRawPlayerScore() {
        return ai("player_raw_score") ? -1 : getLong("player_raw_score");
    }

    public int getTimeSpan() {
        return getInteger("timespan");
    }

    public String hG() {
        return getString("top_page_token_next");
    }

    public String hH() {
        return getString("window_page_token_prev");
    }

    public String hI() {
        return getString("window_page_token_next");
    }

    public LeaderboardVariant hJ() {
        return new LeaderboardVariantEntity(this);
    }

    public boolean hasPlayerInfo() {
        return !ai("player_raw_score");
    }

    public int hashCode() {
        return LeaderboardVariantEntity.m2888a(this);
    }

    public String toString() {
        return LeaderboardVariantEntity.m2890b(this);
    }
}
