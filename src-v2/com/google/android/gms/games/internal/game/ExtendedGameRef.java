/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.internal.game.ExtendedGame;
import com.google.android.gms.games.internal.game.ExtendedGameEntity;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeRef;
import java.util.ArrayList;

public class ExtendedGameRef
extends b
implements ExtendedGame {
    private final GameRef LD;
    private final int LE;

    ExtendedGameRef(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LD = new GameRef(dataHolder, n2);
        this.LE = n3;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return ExtendedGameEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hg();
    }

    @Override
    public ArrayList<GameBadge> gW() {
        if (this.BB.getString("badge_title", this.BD, this.BB.G(this.BD)) == null) {
            return new ArrayList<GameBadge>(0);
        }
        ArrayList<GameBadge> arrayList = new ArrayList<GameBadge>(this.LE);
        for (int i2 = 0; i2 < this.LE; ++i2) {
            arrayList.add(new GameBadgeRef(this.BB, this.BD + i2));
        }
        return arrayList;
    }

    @Override
    public int gX() {
        return this.getInteger("availability");
    }

    @Override
    public boolean gY() {
        return this.getBoolean("owned");
    }

    @Override
    public int gZ() {
        return this.getInteger("achievement_unlocked_count");
    }

    @Override
    public Game getGame() {
        return this.LD;
    }

    @Override
    public long ha() {
        return this.getLong("last_played_server_time");
    }

    @Override
    public int hashCode() {
        return ExtendedGameEntity.a(this);
    }

    @Override
    public long hb() {
        return this.getLong("price_micros");
    }

    @Override
    public String hc() {
        return this.getString("formatted_price");
    }

    @Override
    public long hd() {
        return this.getLong("full_price_micros");
    }

    @Override
    public String he() {
        return this.getString("formatted_full_price");
    }

    public ExtendedGame hg() {
        return new ExtendedGameEntity(this);
    }

    public String toString() {
        return ExtendedGameEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((ExtendedGameEntity)this.hg()).writeToParcel(parcel, n2);
    }
}

