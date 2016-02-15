/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.gm;

public final class LeaderboardScoreEntity
implements LeaderboardScore {
    private final long LU;
    private final String LV;
    private final String LW;
    private final long LX;
    private final long LY;
    private final String LZ;
    private final Uri Ma;
    private final Uri Mb;
    private final PlayerEntity Mc;
    private final String Md;
    private final String Me;
    private final String Mf;

    /*
     * Enabled aggressive block sorting
     */
    public LeaderboardScoreEntity(LeaderboardScore leaderboardScore) {
        this.LU = leaderboardScore.getRank();
        this.LV = fq.f(leaderboardScore.getDisplayRank());
        this.LW = fq.f(leaderboardScore.getDisplayScore());
        this.LX = leaderboardScore.getRawScore();
        this.LY = leaderboardScore.getTimestampMillis();
        this.LZ = leaderboardScore.getScoreHolderDisplayName();
        this.Ma = leaderboardScore.getScoreHolderIconImageUri();
        this.Mb = leaderboardScore.getScoreHolderHiResImageUri();
        Player player = leaderboardScore.getScoreHolder();
        player = player == null ? null : (PlayerEntity)player.freeze();
        this.Mc = player;
        this.Md = leaderboardScore.getScoreTag();
        this.Me = leaderboardScore.getScoreHolderIconImageUrl();
        this.Mf = leaderboardScore.getScoreHolderHiResImageUrl();
    }

    static int a(LeaderboardScore leaderboardScore) {
        return fo.hashCode(new Object[]{leaderboardScore.getRank(), leaderboardScore.getDisplayRank(), leaderboardScore.getRawScore(), leaderboardScore.getDisplayScore(), leaderboardScore.getTimestampMillis(), leaderboardScore.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolder()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(LeaderboardScore leaderboardScore, Object object) {
        boolean bl2 = true;
        if (!(object instanceof LeaderboardScore)) {
            return false;
        }
        boolean bl3 = bl2;
        if (leaderboardScore == object) return bl3;
        if (!fo.equal((object = (LeaderboardScore)object).getRank(), leaderboardScore.getRank())) return false;
        if (!fo.equal(object.getDisplayRank(), leaderboardScore.getDisplayRank())) return false;
        if (!fo.equal(object.getRawScore(), leaderboardScore.getRawScore())) return false;
        if (!fo.equal(object.getDisplayScore(), leaderboardScore.getDisplayScore())) return false;
        if (!fo.equal(object.getTimestampMillis(), leaderboardScore.getTimestampMillis())) return false;
        if (!fo.equal(object.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderDisplayName())) return false;
        if (!fo.equal((Object)object.getScoreHolderIconImageUri(), (Object)leaderboardScore.getScoreHolderIconImageUri())) return false;
        if (!fo.equal((Object)object.getScoreHolderHiResImageUri(), (Object)leaderboardScore.getScoreHolderHiResImageUri())) return false;
        if (!fo.equal(object.getScoreHolder(), leaderboardScore.getScoreHolder())) return false;
        bl3 = bl2;
        if (fo.equal(object.getScoreTag(), leaderboardScore.getScoreTag())) return bl3;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String b(LeaderboardScore leaderboardScore) {
        Player player;
        fo.a a2 = fo.e(leaderboardScore).a("Rank", leaderboardScore.getRank()).a("DisplayRank", leaderboardScore.getDisplayRank()).a("Score", leaderboardScore.getRawScore()).a("DisplayScore", leaderboardScore.getDisplayScore()).a("Timestamp", leaderboardScore.getTimestampMillis()).a("DisplayName", leaderboardScore.getScoreHolderDisplayName()).a("IconImageUri", (Object)leaderboardScore.getScoreHolderIconImageUri()).a("IconImageUrl", leaderboardScore.getScoreHolderIconImageUrl()).a("HiResImageUri", (Object)leaderboardScore.getScoreHolderHiResImageUri()).a("HiResImageUrl", leaderboardScore.getScoreHolderHiResImageUrl());
        if (leaderboardScore.getScoreHolder() == null) {
            player = null;
            do {
                return a2.a("Player", player).a("ScoreTag", leaderboardScore.getScoreTag()).toString();
                break;
            } while (true);
        }
        player = leaderboardScore.getScoreHolder();
        return a2.a("Player", player).a("ScoreTag", leaderboardScore.getScoreTag()).toString();
    }

    public boolean equals(Object object) {
        return LeaderboardScoreEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hF();
    }

    @Override
    public String getDisplayRank() {
        return this.LV;
    }

    @Override
    public void getDisplayRank(CharArrayBuffer charArrayBuffer) {
        gm.b(this.LV, charArrayBuffer);
    }

    @Override
    public String getDisplayScore() {
        return this.LW;
    }

    @Override
    public void getDisplayScore(CharArrayBuffer charArrayBuffer) {
        gm.b(this.LW, charArrayBuffer);
    }

    @Override
    public long getRank() {
        return this.LU;
    }

    @Override
    public long getRawScore() {
        return this.LX;
    }

    @Override
    public Player getScoreHolder() {
        return this.Mc;
    }

    @Override
    public String getScoreHolderDisplayName() {
        if (this.Mc == null) {
            return this.LZ;
        }
        return this.Mc.getDisplayName();
    }

    @Override
    public void getScoreHolderDisplayName(CharArrayBuffer charArrayBuffer) {
        if (this.Mc == null) {
            gm.b(this.LZ, charArrayBuffer);
            return;
        }
        this.Mc.getDisplayName(charArrayBuffer);
    }

    @Override
    public Uri getScoreHolderHiResImageUri() {
        if (this.Mc == null) {
            return this.Mb;
        }
        return this.Mc.getHiResImageUri();
    }

    @Override
    public String getScoreHolderHiResImageUrl() {
        if (this.Mc == null) {
            return this.Mf;
        }
        return this.Mc.getHiResImageUrl();
    }

    @Override
    public Uri getScoreHolderIconImageUri() {
        if (this.Mc == null) {
            return this.Ma;
        }
        return this.Mc.getIconImageUri();
    }

    @Override
    public String getScoreHolderIconImageUrl() {
        if (this.Mc == null) {
            return this.Me;
        }
        return this.Mc.getIconImageUrl();
    }

    @Override
    public String getScoreTag() {
        return this.Md;
    }

    @Override
    public long getTimestampMillis() {
        return this.LY;
    }

    public LeaderboardScore hF() {
        return this;
    }

    public int hashCode() {
        return LeaderboardScoreEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return LeaderboardScoreEntity.b(this);
    }
}

