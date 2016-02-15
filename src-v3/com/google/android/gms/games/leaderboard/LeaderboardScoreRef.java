package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

public final class LeaderboardScoreRef extends C0251b implements LeaderboardScore {
    private final PlayerRef Mg;

    LeaderboardScoreRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
        this.Mg = new PlayerRef(holder, dataRow);
    }

    public boolean equals(Object obj) {
        return LeaderboardScoreEntity.m2886a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return hF();
    }

    public String getDisplayRank() {
        return getString("display_rank");
    }

    public void getDisplayRank(CharArrayBuffer dataOut) {
        m142a("display_rank", dataOut);
    }

    public String getDisplayScore() {
        return getString("display_score");
    }

    public void getDisplayScore(CharArrayBuffer dataOut) {
        m142a("display_score", dataOut);
    }

    public long getRank() {
        return getLong("rank");
    }

    public long getRawScore() {
        return getLong("raw_score");
    }

    public Player getScoreHolder() {
        return ai("external_player_id") ? null : this.Mg;
    }

    public String getScoreHolderDisplayName() {
        return ai("external_player_id") ? getString("default_display_name") : this.Mg.getDisplayName();
    }

    public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
        if (ai("external_player_id")) {
            m142a("default_display_name", dataOut);
        } else {
            this.Mg.getDisplayName(dataOut);
        }
    }

    public Uri getScoreHolderHiResImageUri() {
        return ai("external_player_id") ? null : this.Mg.getHiResImageUri();
    }

    public String getScoreHolderHiResImageUrl() {
        return ai("external_player_id") ? null : this.Mg.getHiResImageUrl();
    }

    public Uri getScoreHolderIconImageUri() {
        return ai("external_player_id") ? ah("default_display_image_uri") : this.Mg.getIconImageUri();
    }

    public String getScoreHolderIconImageUrl() {
        return ai("external_player_id") ? getString("default_display_image_url") : this.Mg.getIconImageUrl();
    }

    public String getScoreTag() {
        return getString("score_tag");
    }

    public long getTimestampMillis() {
        return getLong("achieved_timestamp");
    }

    public LeaderboardScore hF() {
        return new LeaderboardScoreEntity(this);
    }

    public int hashCode() {
        return LeaderboardScoreEntity.m2885a(this);
    }

    public String toString() {
        return LeaderboardScoreEntity.m2887b(this);
    }
}
