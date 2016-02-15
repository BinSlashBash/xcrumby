package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;

public final class PlayerRef extends C0251b implements Player {
    private final PlayerColumnNames Ii;

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

        public PlayerColumnNames(String prefix) {
            if (TextUtils.isEmpty(prefix)) {
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
            this.Ij = prefix + "external_player_id";
            this.Ik = prefix + "profile_name";
            this.Il = prefix + "profile_icon_image_uri";
            this.Im = prefix + "profile_icon_image_url";
            this.In = prefix + "profile_hi_res_image_uri";
            this.Io = prefix + "profile_hi_res_image_url";
            this.Ip = prefix + "last_updated";
            this.Iq = prefix + "is_in_circles";
            this.Ir = prefix + "played_with_timestamp";
        }
    }

    public PlayerRef(DataHolder holder, int dataRow) {
        this(holder, dataRow, null);
    }

    public PlayerRef(DataHolder holder, int dataRow, String prefix) {
        super(holder, dataRow);
        this.Ii = new PlayerColumnNames(prefix);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return PlayerEntity.m3297a(this, obj);
    }

    public Player freeze() {
        return new PlayerEntity(this);
    }

    public String getDisplayName() {
        return getString(this.Ii.Ik);
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        m142a(this.Ii.Ik, dataOut);
    }

    public Uri getHiResImageUri() {
        return ah(this.Ii.In);
    }

    public String getHiResImageUrl() {
        return getString(this.Ii.Io);
    }

    public Uri getIconImageUri() {
        return ah(this.Ii.Il);
    }

    public String getIconImageUrl() {
        return getString(this.Ii.Im);
    }

    public long getLastPlayedWithTimestamp() {
        return !hasColumn(this.Ii.Ir) ? -1 : getLong(this.Ii.Ir);
    }

    public String getPlayerId() {
        return getString(this.Ii.Ij);
    }

    public long getRetrievedTimestamp() {
        return getLong(this.Ii.Ip);
    }

    public int gh() {
        return getInteger(this.Ii.Iq);
    }

    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public int hashCode() {
        return PlayerEntity.m3296a(this);
    }

    public String toString() {
        return PlayerEntity.m3298b((Player) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((PlayerEntity) freeze()).writeToParcel(dest, flags);
    }
}
