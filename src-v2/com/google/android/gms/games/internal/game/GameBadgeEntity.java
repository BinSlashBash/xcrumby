/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeEntityCreator;
import com.google.android.gms.internal.fo;

public final class GameBadgeEntity
extends GamesDowngradeableSafeParcel
implements GameBadge {
    public static final GameBadgeEntityCreator CREATOR = new GameBadgeEntityCreatorCompat();
    private String EB;
    private String HD;
    private Uri HF;
    private int LF;
    private final int xH;

    GameBadgeEntity(int n2, int n3, String string2, String string3, Uri uri) {
        this.xH = n2;
        this.LF = n3;
        this.EB = string2;
        this.HD = string3;
        this.HF = uri;
    }

    public GameBadgeEntity(GameBadge gameBadge) {
        this.xH = 1;
        this.LF = gameBadge.getType();
        this.EB = gameBadge.getTitle();
        this.HD = gameBadge.getDescription();
        this.HF = gameBadge.getIconImageUri();
    }

    static int a(GameBadge gameBadge) {
        return fo.hashCode(new Object[]{gameBadge.getType(), gameBadge.getTitle(), gameBadge.getDescription(), gameBadge.getIconImageUri()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(GameBadge gameBadge, Object object) {
        boolean bl2 = true;
        if (!(object instanceof GameBadge)) {
            return false;
        }
        boolean bl3 = bl2;
        if (gameBadge == object) return bl3;
        if (!fo.equal((object = (GameBadge)object).getType(), gameBadge.getTitle())) return false;
        bl3 = bl2;
        if (fo.equal(object.getDescription(), (Object)gameBadge.getIconImageUri())) return bl3;
        return false;
    }

    static String b(GameBadge gameBadge) {
        return fo.e(gameBadge).a("Type", gameBadge.getType()).a("Title", gameBadge.getTitle()).a("Description", gameBadge.getDescription()).a("IconImageUri", (Object)gameBadge.getIconImageUri()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return GameBadgeEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hh();
    }

    @Override
    public String getDescription() {
        return this.HD;
    }

    @Override
    public Uri getIconImageUri() {
        return this.HF;
    }

    @Override
    public String getTitle() {
        return this.EB;
    }

    @Override
    public int getType() {
        return this.LF;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return GameBadgeEntity.a(this);
    }

    public GameBadge hh() {
        return this;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return GameBadgeEntity.b(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        if (!this.eK()) {
            GameBadgeEntityCreator.a(this, parcel, n2);
            return;
        }
        parcel.writeInt(this.LF);
        parcel.writeString(this.EB);
        parcel.writeString(this.HD);
        String string2 = this.HF == null ? null : this.HF.toString();
        parcel.writeString(string2);
    }

    static final class GameBadgeEntityCreatorCompat
    extends GameBadgeEntityCreator {
        GameBadgeEntityCreatorCompat() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public GameBadgeEntity ar(Parcel object) {
            if (GameBadgeEntity.c(GameBadgeEntity.eJ()) || GameBadgeEntity.al(GameBadgeEntity.class.getCanonicalName())) {
                return super.ar((Parcel)object);
            }
            int n2 = object.readInt();
            String string2 = object.readString();
            String string3 = object.readString();
            if ((object = object.readString()) == null) {
                object = null;
                do {
                    return new GameBadgeEntity(1, n2, string2, string3, (Uri)object);
                    break;
                } while (true);
            }
            object = Uri.parse((String)object);
            return new GameBadgeEntity(1, n2, string2, string3, (Uri)object);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.ar(parcel);
        }
    }

}

