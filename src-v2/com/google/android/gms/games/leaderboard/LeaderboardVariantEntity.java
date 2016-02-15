/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.leaderboard;

import com.google.android.gms.games.internal.constants.LeaderboardCollection;
import com.google.android.gms.games.internal.constants.TimeSpan;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.internal.fo;

public final class LeaderboardVariantEntity
implements LeaderboardVariant {
    private final int Mh;
    private final int Mi;
    private final boolean Mj;
    private final long Mk;
    private final String Ml;
    private final long Mm;
    private final String Mn;
    private final String Mo;
    private final long Mp;
    private final String Mq;
    private final String Mr;
    private final String Ms;

    public LeaderboardVariantEntity(LeaderboardVariant leaderboardVariant) {
        this.Mh = leaderboardVariant.getTimeSpan();
        this.Mi = leaderboardVariant.getCollection();
        this.Mj = leaderboardVariant.hasPlayerInfo();
        this.Mk = leaderboardVariant.getRawPlayerScore();
        this.Ml = leaderboardVariant.getDisplayPlayerScore();
        this.Mm = leaderboardVariant.getPlayerRank();
        this.Mn = leaderboardVariant.getDisplayPlayerRank();
        this.Mo = leaderboardVariant.getPlayerScoreTag();
        this.Mp = leaderboardVariant.getNumScores();
        this.Mq = leaderboardVariant.hG();
        this.Mr = leaderboardVariant.hH();
        this.Ms = leaderboardVariant.hI();
    }

    static int a(LeaderboardVariant leaderboardVariant) {
        return fo.hashCode(leaderboardVariant.getTimeSpan(), leaderboardVariant.getCollection(), leaderboardVariant.hasPlayerInfo(), leaderboardVariant.getRawPlayerScore(), leaderboardVariant.getDisplayPlayerScore(), leaderboardVariant.getPlayerRank(), leaderboardVariant.getDisplayPlayerRank(), leaderboardVariant.getNumScores(), leaderboardVariant.hG(), leaderboardVariant.hI(), leaderboardVariant.hH());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(LeaderboardVariant leaderboardVariant, Object object) {
        boolean bl2 = true;
        if (!(object instanceof LeaderboardVariant)) {
            return false;
        }
        boolean bl3 = bl2;
        if (leaderboardVariant == object) return bl3;
        if (!fo.equal((object = (LeaderboardVariant)object).getTimeSpan(), leaderboardVariant.getTimeSpan())) return false;
        if (!fo.equal(object.getCollection(), leaderboardVariant.getCollection())) return false;
        if (!fo.equal(object.hasPlayerInfo(), leaderboardVariant.hasPlayerInfo())) return false;
        if (!fo.equal(object.getRawPlayerScore(), leaderboardVariant.getRawPlayerScore())) return false;
        if (!fo.equal(object.getDisplayPlayerScore(), leaderboardVariant.getDisplayPlayerScore())) return false;
        if (!fo.equal(object.getPlayerRank(), leaderboardVariant.getPlayerRank())) return false;
        if (!fo.equal(object.getDisplayPlayerRank(), leaderboardVariant.getDisplayPlayerRank())) return false;
        if (!fo.equal(object.getNumScores(), leaderboardVariant.getNumScores())) return false;
        if (!fo.equal(object.hG(), leaderboardVariant.hG())) return false;
        if (!fo.equal(object.hI(), leaderboardVariant.hI())) return false;
        bl3 = bl2;
        if (fo.equal(object.hH(), leaderboardVariant.hH())) return bl3;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static String b(LeaderboardVariant leaderboardVariant) {
        fo.a a2 = fo.e(leaderboardVariant).a("TimeSpan", TimeSpan.bd(leaderboardVariant.getTimeSpan())).a("Collection", LeaderboardCollection.bd(leaderboardVariant.getCollection()));
        Object object = leaderboardVariant.hasPlayerInfo() ? Long.valueOf(leaderboardVariant.getRawPlayerScore()) : "none";
        a2 = a2.a("RawPlayerScore", object);
        object = leaderboardVariant.hasPlayerInfo() ? leaderboardVariant.getDisplayPlayerScore() : "none";
        a2 = a2.a("DisplayPlayerScore", object);
        object = leaderboardVariant.hasPlayerInfo() ? Long.valueOf(leaderboardVariant.getPlayerRank()) : "none";
        a2 = a2.a("PlayerRank", object);
        if (leaderboardVariant.hasPlayerInfo()) {
            object = leaderboardVariant.getDisplayPlayerRank();
            return a2.a("DisplayPlayerRank", object).a("NumScores", leaderboardVariant.getNumScores()).a("TopPageNextToken", leaderboardVariant.hG()).a("WindowPageNextToken", leaderboardVariant.hI()).a("WindowPagePrevToken", leaderboardVariant.hH()).toString();
        }
        object = "none";
        return a2.a("DisplayPlayerRank", object).a("NumScores", leaderboardVariant.getNumScores()).a("TopPageNextToken", leaderboardVariant.hG()).a("WindowPageNextToken", leaderboardVariant.hI()).a("WindowPagePrevToken", leaderboardVariant.hH()).toString();
    }

    public boolean equals(Object object) {
        return LeaderboardVariantEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hJ();
    }

    @Override
    public int getCollection() {
        return this.Mi;
    }

    @Override
    public String getDisplayPlayerRank() {
        return this.Mn;
    }

    @Override
    public String getDisplayPlayerScore() {
        return this.Ml;
    }

    @Override
    public long getNumScores() {
        return this.Mp;
    }

    @Override
    public long getPlayerRank() {
        return this.Mm;
    }

    @Override
    public String getPlayerScoreTag() {
        return this.Mo;
    }

    @Override
    public long getRawPlayerScore() {
        return this.Mk;
    }

    @Override
    public int getTimeSpan() {
        return this.Mh;
    }

    @Override
    public String hG() {
        return this.Mq;
    }

    @Override
    public String hH() {
        return this.Mr;
    }

    @Override
    public String hI() {
        return this.Ms;
    }

    public LeaderboardVariant hJ() {
        return this;
    }

    @Override
    public boolean hasPlayerInfo() {
        return this.Mj;
    }

    public int hashCode() {
        return LeaderboardVariantEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return LeaderboardVariantEntity.b(this);
    }
}

