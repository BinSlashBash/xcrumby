package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

public final class PlayerEntity extends GamesDowngradeableSafeParcel implements Player {
    public static final Creator<PlayerEntity> CREATOR;
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

    static final class PlayerEntityCreatorCompat extends PlayerEntityCreator {
        PlayerEntityCreatorCompat() {
        }

        public PlayerEntity ao(Parcel parcel) {
            if (GamesDowngradeableSafeParcel.m2875c(fe.eJ()) || fe.al(PlayerEntity.class.getCanonicalName())) {
                return super.ao(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            return new PlayerEntity(4, readString, readString2, readString3 == null ? null : Uri.parse(readString3), readString4 == null ? null : Uri.parse(readString4), parcel.readLong(), -1, -1, null, null);
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return ao(x0);
        }
    }

    static {
        CREATOR = new PlayerEntityCreatorCompat();
    }

    PlayerEntity(int versionCode, String playerId, String displayName, Uri iconImageUri, Uri hiResImageUri, long retrievedTimestamp, int isInCircles, long lastPlayedWithTimestamp, String iconImageUrl, String hiResImageUrl) {
        this.xH = versionCode;
        this.Ie = playerId;
        this.HA = displayName;
        this.HF = iconImageUri;
        this.HQ = iconImageUrl;
        this.HG = hiResImageUri;
        this.HR = hiResImageUrl;
        this.If = retrievedTimestamp;
        this.Ig = isInCircles;
        this.Ih = lastPlayedWithTimestamp;
    }

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
        fb.m920d(this.Ie);
        fb.m920d(this.HA);
        fb.m921x(this.If > 0);
    }

    static int m3296a(Player player) {
        return fo.hashCode(player.getPlayerId(), player.getDisplayName(), player.getIconImageUri(), player.getHiResImageUri(), Long.valueOf(player.getRetrievedTimestamp()));
    }

    static boolean m3297a(Player player, Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (player == obj) {
            return true;
        }
        Player player2 = (Player) obj;
        return fo.equal(player2.getPlayerId(), player.getPlayerId()) && fo.equal(player2.getDisplayName(), player.getDisplayName()) && fo.equal(player2.getIconImageUri(), player.getIconImageUri()) && fo.equal(player2.getHiResImageUri(), player.getHiResImageUri()) && fo.equal(Long.valueOf(player2.getRetrievedTimestamp()), Long.valueOf(player.getRetrievedTimestamp()));
    }

    static String m3298b(Player player) {
        return fo.m976e(player).m975a("PlayerId", player.getPlayerId()).m975a("DisplayName", player.getDisplayName()).m975a("IconImageUri", player.getIconImageUri()).m975a("IconImageUrl", player.getIconImageUrl()).m975a("HiResImageUri", player.getHiResImageUri()).m975a("HiResImageUrl", player.getHiResImageUrl()).m975a("RetrievedTimestamp", Long.valueOf(player.getRetrievedTimestamp())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m3297a(this, obj);
    }

    public Player freeze() {
        return this;
    }

    public String getDisplayName() {
        return this.HA;
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        gm.m1035b(this.HA, dataOut);
    }

    public Uri getHiResImageUri() {
        return this.HG;
    }

    public String getHiResImageUrl() {
        return this.HR;
    }

    public Uri getIconImageUri() {
        return this.HF;
    }

    public String getIconImageUrl() {
        return this.HQ;
    }

    public long getLastPlayedWithTimestamp() {
        return this.Ih;
    }

    public String getPlayerId() {
        return this.Ie;
    }

    public long getRetrievedTimestamp() {
        return this.If;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int gh() {
        return this.Ig;
    }

    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public int hashCode() {
        return m3296a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m3298b((Player) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        String str = null;
        if (eK()) {
            dest.writeString(this.Ie);
            dest.writeString(this.HA);
            dest.writeString(this.HF == null ? null : this.HF.toString());
            if (this.HG != null) {
                str = this.HG.toString();
            }
            dest.writeString(str);
            dest.writeLong(this.If);
            return;
        }
        PlayerEntityCreator.m362a(this, dest, flags);
    }
}
