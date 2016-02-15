/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.internal.game.ExtendedGame;
import com.google.android.gms.games.internal.game.ExtendedGameEntityCreator;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeEntity;
import com.google.android.gms.games.internal.game.GameBadgeEntityCreator;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;
import java.util.Collection;

public final class ExtendedGameEntity
extends GamesDowngradeableSafeParcel
implements ExtendedGame {
    public static final ExtendedGameEntityCreator CREATOR = new ExtendedGameEntityCreatorCompat();
    private final long LA;
    private final String LB;
    private final ArrayList<GameBadgeEntity> LC;
    private final GameEntity Lt;
    private final int Lu;
    private final boolean Lv;
    private final int Lw;
    private final long Lx;
    private final long Ly;
    private final String Lz;
    private final int xH;

    ExtendedGameEntity(int n2, GameEntity gameEntity, int n3, boolean bl2, int n4, long l2, long l3, String string2, long l4, String string3, ArrayList<GameBadgeEntity> arrayList) {
        this.xH = n2;
        this.Lt = gameEntity;
        this.Lu = n3;
        this.Lv = bl2;
        this.Lw = n4;
        this.Lx = l2;
        this.Ly = l3;
        this.Lz = string2;
        this.LA = l4;
        this.LB = string3;
        this.LC = arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ExtendedGameEntity(ExtendedGame arrayList) {
        this.xH = 1;
        Game game = arrayList.getGame();
        game = game == null ? null : new GameEntity(game);
        this.Lt = game;
        this.Lu = arrayList.gX();
        this.Lv = arrayList.gY();
        this.Lw = arrayList.gZ();
        this.Lx = arrayList.ha();
        this.Ly = arrayList.hb();
        this.Lz = arrayList.hc();
        this.LA = arrayList.hd();
        this.LB = arrayList.he();
        arrayList = arrayList.gW();
        int n2 = arrayList.size();
        this.LC = new ArrayList(n2);
        int n3 = 0;
        while (n3 < n2) {
            this.LC.add((GameBadgeEntity)arrayList.get(n3).freeze());
            ++n3;
        }
    }

    static int a(ExtendedGame extendedGame) {
        return fo.hashCode(extendedGame.getGame(), extendedGame.gX(), extendedGame.gY(), extendedGame.gZ(), extendedGame.ha(), extendedGame.hb(), extendedGame.hc(), extendedGame.hd(), extendedGame.he());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(ExtendedGame extendedGame, Object object) {
        boolean bl2 = true;
        if (!(object instanceof ExtendedGame)) {
            return false;
        }
        boolean bl3 = bl2;
        if (extendedGame == object) return bl3;
        if (!fo.equal((object = (ExtendedGame)object).getGame(), extendedGame.getGame())) return false;
        if (!fo.equal(object.gX(), extendedGame.gX())) return false;
        if (!fo.equal(object.gY(), extendedGame.gY())) return false;
        if (!fo.equal(object.gZ(), extendedGame.gZ())) return false;
        if (!fo.equal(object.ha(), extendedGame.ha())) return false;
        if (!fo.equal(object.hb(), extendedGame.hb())) return false;
        if (!fo.equal(object.hc(), extendedGame.hc())) return false;
        if (!fo.equal(object.hd(), extendedGame.hd())) return false;
        bl3 = bl2;
        if (fo.equal(object.he(), extendedGame.he())) return bl3;
        return false;
    }

    static String b(ExtendedGame extendedGame) {
        return fo.e(extendedGame).a("Game", extendedGame.getGame()).a("Availability", extendedGame.gX()).a("Owned", extendedGame.gY()).a("AchievementUnlockedCount", extendedGame.gZ()).a("LastPlayedServerTimestamp", extendedGame.ha()).a("PriceMicros", extendedGame.hb()).a("FormattedPrice", extendedGame.hc()).a("FullPriceMicros", extendedGame.hd()).a("FormattedFullPrice", extendedGame.he()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return ExtendedGameEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hg();
    }

    @Override
    public ArrayList<GameBadge> gW() {
        return new ArrayList<GameBadge>(this.LC);
    }

    @Override
    public int gX() {
        return this.Lu;
    }

    @Override
    public boolean gY() {
        return this.Lv;
    }

    @Override
    public int gZ() {
        return this.Lw;
    }

    @Override
    public /* synthetic */ Game getGame() {
        return this.hf();
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override
    public long ha() {
        return this.Lx;
    }

    public int hashCode() {
        return ExtendedGameEntity.a(this);
    }

    @Override
    public long hb() {
        return this.Ly;
    }

    @Override
    public String hc() {
        return this.Lz;
    }

    @Override
    public long hd() {
        return this.LA;
    }

    @Override
    public String he() {
        return this.LB;
    }

    public GameEntity hf() {
        return this.Lt;
    }

    public ExtendedGame hg() {
        return this;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return ExtendedGameEntity.b(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        int n3 = 0;
        if (!this.eK()) {
            ExtendedGameEntityCreator.a(this, parcel, n2);
            return;
        } else {
            this.Lt.writeToParcel(parcel, n2);
            parcel.writeInt(this.Lu);
            int n4 = this.Lv ? 1 : 0;
            parcel.writeInt(n4);
            parcel.writeInt(this.Lw);
            parcel.writeLong(this.Lx);
            parcel.writeLong(this.Ly);
            parcel.writeString(this.Lz);
            parcel.writeLong(this.LA);
            parcel.writeString(this.LB);
            int n5 = this.LC.size();
            parcel.writeInt(n5);
            for (n4 = n3; n4 < n5; ++n4) {
                this.LC.get(n4).writeToParcel(parcel, n2);
            }
        }
    }

    static final class ExtendedGameEntityCreatorCompat
    extends ExtendedGameEntityCreator {
        ExtendedGameEntityCreatorCompat() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public ExtendedGameEntity aq(Parcel parcel) {
            if (ExtendedGameEntity.c(ExtendedGameEntity.eJ()) || ExtendedGameEntity.al(ExtendedGameEntity.class.getCanonicalName())) {
                return super.aq(parcel);
            }
            GameEntity gameEntity = (GameEntity)GameEntity.CREATOR.createFromParcel(parcel);
            int n2 = parcel.readInt();
            boolean bl2 = parcel.readInt() == 1;
            int n3 = parcel.readInt();
            long l2 = parcel.readLong();
            long l3 = parcel.readLong();
            String string2 = parcel.readString();
            long l4 = parcel.readLong();
            String string3 = parcel.readString();
            int n4 = parcel.readInt();
            ArrayList<GameBadgeEntity> arrayList = new ArrayList<GameBadgeEntity>(n4);
            int n5 = 0;
            while (n5 < n4) {
                arrayList.add(GameBadgeEntity.CREATOR.ar(parcel));
                ++n5;
            }
            return new ExtendedGameEntity(1, gameEntity, n2, bl2, n3, l2, l3, string2, l4, string3, arrayList);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.aq(parcel);
        }
    }

}

