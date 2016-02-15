/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntityCreator;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

public final class PlayerEntity
extends GamesDowngradeableSafeParcel
implements Player {
    public static final Parcelable.Creator<PlayerEntity> CREATOR = new PlayerEntityCreatorCompat();
    private final String HA;
    private final Uri HF;
    private final Uri HG;
    private final String HQ;
    private final String HR;
    private final String Ie;
    private final long If;
    private final int Ig;
    private final long Ih;
    private final int xH;

    PlayerEntity(int n2, String string2, String string3, Uri uri, Uri uri2, long l2, int n3, long l3, String string4, String string5) {
        this.xH = n2;
        this.Ie = string2;
        this.HA = string3;
        this.HF = uri;
        this.HQ = string4;
        this.HG = uri2;
        this.HR = string5;
        this.If = l2;
        this.Ig = n3;
        this.Ih = l3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public PlayerEntity(Player player) {
        this.xH = 4;
        this.Ie = player.getPlayerId();
        this.HA = player.getDisplayName();
        this.HF = player.getIconImageUri();
        this.HQ = player.getIconImageUrl();
        this.HG = player.getHiResImageUri();
        this.HR = player.getHiResImageUrl();
        this.If = player.getRetrievedTimestamp();
        this.Ig = player.gh();
        this.Ih = player.getLastPlayedWithTimestamp();
        fb.d(this.Ie);
        fb.d(this.HA);
        boolean bl2 = this.If > 0;
        fb.x(bl2);
    }

    static int a(Player player) {
        return fo.hashCode(new Object[]{player.getPlayerId(), player.getDisplayName(), player.getIconImageUri(), player.getHiResImageUri(), player.getRetrievedTimestamp()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(Player player, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Player)) {
            return false;
        }
        boolean bl3 = bl2;
        if (player == object) return bl3;
        if (!fo.equal((object = (Player)object).getPlayerId(), player.getPlayerId())) return false;
        if (!fo.equal(object.getDisplayName(), player.getDisplayName())) return false;
        if (!fo.equal((Object)object.getIconImageUri(), (Object)player.getIconImageUri())) return false;
        if (!fo.equal((Object)object.getHiResImageUri(), (Object)player.getHiResImageUri())) return false;
        bl3 = bl2;
        if (fo.equal(object.getRetrievedTimestamp(), player.getRetrievedTimestamp())) return bl3;
        return false;
    }

    static String b(Player player) {
        return fo.e(player).a("PlayerId", player.getPlayerId()).a("DisplayName", player.getDisplayName()).a("IconImageUri", (Object)player.getIconImageUri()).a("IconImageUrl", player.getIconImageUrl()).a("HiResImageUri", (Object)player.getHiResImageUri()).a("HiResImageUrl", player.getHiResImageUrl()).a("RetrievedTimestamp", player.getRetrievedTimestamp()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return PlayerEntity.a(this, object);
    }

    @Override
    public Player freeze() {
        return this;
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
    public Uri getHiResImageUri() {
        return this.HG;
    }

    @Override
    public String getHiResImageUrl() {
        return this.HR;
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
    public long getLastPlayedWithTimestamp() {
        return this.Ih;
    }

    @Override
    public String getPlayerId() {
        return this.Ie;
    }

    @Override
    public long getRetrievedTimestamp() {
        return this.If;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override
    public int gh() {
        return this.Ig;
    }

    @Override
    public boolean hasHiResImage() {
        if (this.getHiResImageUri() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasIconImage() {
        if (this.getIconImageUri() != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return PlayerEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return PlayerEntity.b(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        Object var4_3 = null;
        if (!this.eK()) {
            PlayerEntityCreator.a(this, parcel, n2);
            return;
        }
        parcel.writeString(this.Ie);
        parcel.writeString(this.HA);
        String string2 = this.HF == null ? null : this.HF.toString();
        parcel.writeString(string2);
        string2 = this.HG == null ? var4_3 : this.HG.toString();
        parcel.writeString(string2);
        parcel.writeLong(this.If);
    }

    static final class PlayerEntityCreatorCompat
    extends PlayerEntityCreator {
        PlayerEntityCreatorCompat() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public PlayerEntity ao(Parcel parcel) {
            if (PlayerEntity.c(PlayerEntity.eJ())) return super.ao(parcel);
            if (PlayerEntity.al(PlayerEntity.class.getCanonicalName())) {
                return super.ao(parcel);
            }
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            string4 = string4 == null ? null : Uri.parse((String)string4);
            if (string5 == null) {
                string5 = null;
                return new PlayerEntity(4, string2, string3, (Uri)string4, (Uri)string5, parcel.readLong(), -1, -1, null, null);
            }
            string5 = Uri.parse((String)string5);
            return new PlayerEntity(4, string2, string3, (Uri)string4, (Uri)string5, parcel.readLong(), -1, -1, null, null);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.ao(parcel);
        }
    }

}

