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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.LeaderboardVariantRef;
import java.util.ArrayList;

public final class LeaderboardRef
extends b
implements Leaderboard {
    private final int LE;
    private final Game LS;

    LeaderboardRef(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LE = n3;
        this.LS = new GameRef(dataHolder, n2);
    }

    @Override
    public boolean equals(Object object) {
        return LeaderboardEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hC();
    }

    @Override
    public String getDisplayName() {
        return this.getString("name");
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        this.a("name", charArrayBuffer);
    }

    @Override
    public Game getGame() {
        return this.LS;
    }

    @Override
    public Uri getIconImageUri() {
        return this.ah("board_icon_image_uri");
    }

    @Override
    public String getIconImageUrl() {
        return this.getString("board_icon_image_url");
    }

    @Override
    public String getLeaderboardId() {
        return this.getString("external_leaderboard_id");
    }

    @Override
    public int getScoreOrder() {
        return this.getInteger("score_order");
    }

    @Override
    public ArrayList<LeaderboardVariant> getVariants() {
        ArrayList<LeaderboardVariant> arrayList = new ArrayList<LeaderboardVariant>(this.LE);
        for (int i2 = 0; i2 < this.LE; ++i2) {
            arrayList.add(new LeaderboardVariantRef(this.BB, this.BD + i2));
        }
        return arrayList;
    }

    public Leaderboard hC() {
        return new LeaderboardEntity(this);
    }

    @Override
    public int hashCode() {
        return LeaderboardEntity.a(this);
    }

    public String toString() {
        return LeaderboardEntity.b(this);
    }
}

