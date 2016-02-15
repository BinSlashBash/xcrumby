package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.gm;

public final class LeaderboardScoreEntity implements LeaderboardScore {
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

    public LeaderboardScoreEntity(LeaderboardScore score) {
        this.LU = score.getRank();
        this.LV = (String) fq.m985f(score.getDisplayRank());
        this.LW = (String) fq.m985f(score.getDisplayScore());
        this.LX = score.getRawScore();
        this.LY = score.getTimestampMillis();
        this.LZ = score.getScoreHolderDisplayName();
        this.Ma = score.getScoreHolderIconImageUri();
        this.Mb = score.getScoreHolderHiResImageUri();
        Player scoreHolder = score.getScoreHolder();
        this.Mc = scoreHolder == null ? null : (PlayerEntity) scoreHolder.freeze();
        this.Md = score.getScoreTag();
        this.Me = score.getScoreHolderIconImageUrl();
        this.Mf = score.getScoreHolderHiResImageUrl();
    }

    static int m2885a(LeaderboardScore leaderboardScore) {
        return fo.hashCode(Long.valueOf(leaderboardScore.getRank()), leaderboardScore.getDisplayRank(), Long.valueOf(leaderboardScore.getRawScore()), leaderboardScore.getDisplayScore(), Long.valueOf(leaderboardScore.getTimestampMillis()), leaderboardScore.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolder());
    }

    static boolean m2886a(LeaderboardScore leaderboardScore, Object obj) {
        if (!(obj instanceof LeaderboardScore)) {
            return false;
        }
        if (leaderboardScore == obj) {
            return true;
        }
        LeaderboardScore leaderboardScore2 = (LeaderboardScore) obj;
        return fo.equal(Long.valueOf(leaderboardScore2.getRank()), Long.valueOf(leaderboardScore.getRank())) && fo.equal(leaderboardScore2.getDisplayRank(), leaderboardScore.getDisplayRank()) && fo.equal(Long.valueOf(leaderboardScore2.getRawScore()), Long.valueOf(leaderboardScore.getRawScore())) && fo.equal(leaderboardScore2.getDisplayScore(), leaderboardScore.getDisplayScore()) && fo.equal(Long.valueOf(leaderboardScore2.getTimestampMillis()), Long.valueOf(leaderboardScore.getTimestampMillis())) && fo.equal(leaderboardScore2.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderDisplayName()) && fo.equal(leaderboardScore2.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderIconImageUri()) && fo.equal(leaderboardScore2.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolderHiResImageUri()) && fo.equal(leaderboardScore2.getScoreHolder(), leaderboardScore.getScoreHolder()) && fo.equal(leaderboardScore2.getScoreTag(), leaderboardScore.getScoreTag());
    }

    static String m2887b(LeaderboardScore leaderboardScore) {
        return fo.m976e(leaderboardScore).m975a("Rank", Long.valueOf(leaderboardScore.getRank())).m975a("DisplayRank", leaderboardScore.getDisplayRank()).m975a("Score", Long.valueOf(leaderboardScore.getRawScore())).m975a("DisplayScore", leaderboardScore.getDisplayScore()).m975a("Timestamp", Long.valueOf(leaderboardScore.getTimestampMillis())).m975a("DisplayName", leaderboardScore.getScoreHolderDisplayName()).m975a("IconImageUri", leaderboardScore.getScoreHolderIconImageUri()).m975a("IconImageUrl", leaderboardScore.getScoreHolderIconImageUrl()).m975a("HiResImageUri", leaderboardScore.getScoreHolderHiResImageUri()).m975a("HiResImageUrl", leaderboardScore.getScoreHolderHiResImageUrl()).m975a("Player", leaderboardScore.getScoreHolder() == null ? null : leaderboardScore.getScoreHolder()).m975a("ScoreTag", leaderboardScore.getScoreTag()).toString();
    }

    public boolean equals(Object obj) {
        return m2886a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return hF();
    }

    public String getDisplayRank() {
        return this.LV;
    }

    public void getDisplayRank(CharArrayBuffer dataOut) {
        gm.m1035b(this.LV, dataOut);
    }

    public String getDisplayScore() {
        return this.LW;
    }

    public void getDisplayScore(CharArrayBuffer dataOut) {
        gm.m1035b(this.LW, dataOut);
    }

    public long getRank() {
        return this.LU;
    }

    public long getRawScore() {
        return this.LX;
    }

    public Player getScoreHolder() {
        return this.Mc;
    }

    public String getScoreHolderDisplayName() {
        return this.Mc == null ? this.LZ : this.Mc.getDisplayName();
    }

    public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
        if (this.Mc == null) {
            gm.m1035b(this.LZ, dataOut);
        } else {
            this.Mc.getDisplayName(dataOut);
        }
    }

    public Uri getScoreHolderHiResImageUri() {
        return this.Mc == null ? this.Mb : this.Mc.getHiResImageUri();
    }

    public String getScoreHolderHiResImageUrl() {
        return this.Mc == null ? this.Mf : this.Mc.getHiResImageUrl();
    }

    public Uri getScoreHolderIconImageUri() {
        return this.Mc == null ? this.Ma : this.Mc.getIconImageUri();
    }

    public String getScoreHolderIconImageUrl() {
        return this.Mc == null ? this.Me : this.Mc.getIconImageUrl();
    }

    public String getScoreTag() {
        return this.Md;
    }

    public long getTimestampMillis() {
        return this.LY;
    }

    public LeaderboardScore hF() {
        return this;
    }

    public int hashCode() {
        return m2885a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m2887b(this);
    }
}
