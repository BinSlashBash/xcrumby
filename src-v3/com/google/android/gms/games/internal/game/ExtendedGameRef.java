package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;

public class ExtendedGameRef extends C0251b implements ExtendedGame {
    private final GameRef LD;
    private final int LE;

    ExtendedGameRef(DataHolder holder, int dataRow, int numChildren) {
        super(holder, dataRow);
        this.LD = new GameRef(holder, dataRow);
        this.LE = numChildren;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return ExtendedGameEntity.m3390a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return hg();
    }

    public ArrayList<GameBadge> gW() {
        int i = 0;
        if (this.BB.getString("badge_title", this.BD, this.BB.m1687G(this.BD)) == null) {
            return new ArrayList(0);
        }
        ArrayList<GameBadge> arrayList = new ArrayList(this.LE);
        while (i < this.LE) {
            arrayList.add(new GameBadgeRef(this.BB, this.BD + i));
            i++;
        }
        return arrayList;
    }

    public int gX() {
        return getInteger("availability");
    }

    public boolean gY() {
        return getBoolean("owned");
    }

    public int gZ() {
        return getInteger("achievement_unlocked_count");
    }

    public Game getGame() {
        return this.LD;
    }

    public long ha() {
        return getLong("last_played_server_time");
    }

    public int hashCode() {
        return ExtendedGameEntity.m3389a(this);
    }

    public long hb() {
        return getLong("price_micros");
    }

    public String hc() {
        return getString("formatted_price");
    }

    public long hd() {
        return getLong("full_price_micros");
    }

    public String he() {
        return getString("formatted_full_price");
    }

    public ExtendedGame hg() {
        return new ExtendedGameEntity(this);
    }

    public String toString() {
        return ExtendedGameEntity.m3391b((ExtendedGame) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((ExtendedGameEntity) hg()).writeToParcel(dest, flags);
    }
}
