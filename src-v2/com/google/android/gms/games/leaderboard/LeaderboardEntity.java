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
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.LeaderboardVariantEntity;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;
import java.util.ArrayList;
import java.util.Collection;

public final class LeaderboardEntity
implements Leaderboard {
    private final String HA;
    private final Uri HF;
    private final String HQ;
    private final String LP;
    private final int LQ;
    private final ArrayList<LeaderboardVariantEntity> LR;
    private final Game LS;

    /*
     * Enabled aggressive block sorting
     */
    public LeaderboardEntity(Leaderboard arrayList) {
        this.LP = arrayList.getLeaderboardId();
        this.HA = arrayList.getDisplayName();
        this.HF = arrayList.getIconImageUri();
        this.HQ = arrayList.getIconImageUrl();
        this.LQ = arrayList.getScoreOrder();
        Game game = arrayList.getGame();
        game = game == null ? null : new GameEntity(game);
        this.LS = game;
        arrayList = arrayList.getVariants();
        int n2 = arrayList.size();
        this.LR = new ArrayList(n2);
        int n3 = 0;
        while (n3 < n2) {
            this.LR.add((LeaderboardVariantEntity)arrayList.get(n3).freeze());
            ++n3;
        }
    }

    static int a(Leaderboard leaderboard) {
        return fo.hashCode(new Object[]{leaderboard.getLeaderboardId(), leaderboard.getDisplayName(), leaderboard.getIconImageUri(), leaderboard.getScoreOrder(), leaderboard.getVariants()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(Leaderboard leaderboard, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Leaderboard)) {
            return false;
        }
        boolean bl3 = bl2;
        if (leaderboard == object) return bl3;
        if (!fo.equal((object = (Leaderboard)object).getLeaderboardId(), leaderboard.getLeaderboardId())) return false;
        if (!fo.equal(object.getDisplayName(), leaderboard.getDisplayName())) return false;
        if (!fo.equal((Object)object.getIconImageUri(), (Object)leaderboard.getIconImageUri())) return false;
        if (!fo.equal(object.getScoreOrder(), leaderboard.getScoreOrder())) return false;
        bl3 = bl2;
        if (fo.equal(object.getVariants(), leaderboard.getVariants())) return bl3;
        return false;
    }

    static String b(Leaderboard leaderboard) {
        return fo.e(leaderboard).a("LeaderboardId", leaderboard.getLeaderboardId()).a("DisplayName", leaderboard.getDisplayName()).a("IconImageUri", (Object)leaderboard.getIconImageUri()).a("IconImageUrl", leaderboard.getIconImageUrl()).a("ScoreOrder", leaderboard.getScoreOrder()).a("Variants", leaderboard.getVariants()).toString();
    }

    public boolean equals(Object object) {
        return LeaderboardEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hC();
    }

    @Override
    public String getDisplayName() {
        return this.HA;
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        gm.b(this.HA, charArrayBuffer);
    }

    @Override
    public Game getGame() {
        return this.LS;
    }

    @Override
    public Uri getIconImageUri() {
        return this.HF;
    }

    @Override
    public String getIconImageUrl() {
        return this.HQ;
    }

    @Override
    public String getLeaderboardId() {
        return this.LP;
    }

    @Override
    public int getScoreOrder() {
        return this.LQ;
    }

    @Override
    public ArrayList<LeaderboardVariant> getVariants() {
        return new ArrayList<LeaderboardVariant>(this.LR);
    }

    public Leaderboard hC() {
        return this;
    }

    public int hashCode() {
        return LeaderboardEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return LeaderboardEntity.b(this);
    }
}

