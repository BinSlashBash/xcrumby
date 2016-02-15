/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 *  android.os.Parcel
 *  android.text.TextUtils
 */
package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;

public final class PlayerRef
extends b
implements Player {
    private final PlayerColumnNames Ii;

    public PlayerRef(DataHolder dataHolder, int n2) {
        this(dataHolder, n2, null);
    }

    public PlayerRef(DataHolder dataHolder, int n2, String string2) {
        super(dataHolder, n2);
        this.Ii = new PlayerColumnNames(string2);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return PlayerEntity.a(this, object);
    }

    @Override
    public Player freeze() {
        return new PlayerEntity(this);
    }

    @Override
    public String getDisplayName() {
        return this.getString(this.Ii.Ik);
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        this.a(this.Ii.Ik, charArrayBuffer);
    }

    @Override
    public Uri getHiResImageUri() {
        return this.ah(this.Ii.In);
    }

    @Override
    public String getHiResImageUrl() {
        return this.getString(this.Ii.Io);
    }

    @Override
    public Uri getIconImageUri() {
        return this.ah(this.Ii.Il);
    }

    @Override
    public String getIconImageUrl() {
        return this.getString(this.Ii.Im);
    }

    @Override
    public long getLastPlayedWithTimestamp() {
        if (!this.hasColumn(this.Ii.Ir)) {
            return -1;
        }
        return this.getLong(this.Ii.Ir);
    }

    @Override
    public String getPlayerId() {
        return this.getString(this.Ii.Ij);
    }

    @Override
    public long getRetrievedTimestamp() {
        return this.getLong(this.Ii.Ip);
    }

    @Override
    public int gh() {
        return this.getInteger(this.Ii.Iq);
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

    @Override
    public int hashCode() {
        return PlayerEntity.a(this);
    }

    public String toString() {
        return PlayerEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((PlayerEntity)this.freeze()).writeToParcel(parcel, n2);
    }

    private static final class PlayerColumnNames {
        public final String Ij;
        public final String Ik;
        public final String Il;
        public final String Im;
        public final String In;
        public final String Io;
        public final String Ip;
        public final String Iq;
        public final String Ir;

        public PlayerColumnNames(String string2) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                this.Ij = "external_player_id";
                this.Ik = "profile_name";
                this.Il = "profile_icon_image_uri";
                this.Im = "profile_icon_image_url";
                this.In = "profile_hi_res_image_uri";
                this.Io = "profile_hi_res_image_url";
                this.Ip = "last_updated";
                this.Iq = "is_in_circles";
                this.Ir = "played_with_timestamp";
                return;
            }
            this.Ij = string2 + "external_player_id";
            this.Ik = string2 + "profile_name";
            this.Il = string2 + "profile_icon_image_uri";
            this.Im = string2 + "profile_icon_image_url";
            this.In = string2 + "profile_hi_res_image_uri";
            this.Io = string2 + "profile_hi_res_image_url";
            this.Ip = string2 + "last_updated";
            this.Iq = string2 + "is_in_circles";
            this.Ir = string2 + "played_with_timestamp";
        }
    }

}

