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
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntityCreator;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

public final class GameEntity
extends GamesDowngradeableSafeParcel
implements Game {
    public static final Parcelable.Creator<GameEntity> CREATOR = new GameEntityCreatorCompat();
    private final String HA;
    private final String HB;
    private final String HC;
    private final String HD;
    private final String HE;
    private final Uri HF;
    private final Uri HG;
    private final Uri HH;
    private final boolean HI;
    private final boolean HJ;
    private final String HK;
    private final int HL;
    private final int HM;
    private final int HN;
    private final boolean HO;
    private final boolean HP;
    private final String HQ;
    private final String HR;
    private final String HS;
    private final boolean HT;
    private final boolean HU;
    private final int xH;
    private final String xI;

    GameEntity(int n2, String string2, String string3, String string4, String string5, String string6, String string7, Uri uri, Uri uri2, Uri uri3, boolean bl2, boolean bl3, String string8, int n3, int n4, int n5, boolean bl4, boolean bl5, String string9, String string10, String string11, boolean bl6, boolean bl7) {
        this.xH = n2;
        this.xI = string2;
        this.HA = string3;
        this.HB = string4;
        this.HC = string5;
        this.HD = string6;
        this.HE = string7;
        this.HF = uri;
        this.HQ = string9;
        this.HG = uri2;
        this.HR = string10;
        this.HH = uri3;
        this.HS = string11;
        this.HI = bl2;
        this.HJ = bl3;
        this.HK = string8;
        this.HL = n3;
        this.HM = n4;
        this.HN = n5;
        this.HO = bl4;
        this.HP = bl5;
        this.HT = bl6;
        this.HU = bl7;
    }

    public GameEntity(Game game) {
        this.xH = 3;
        this.xI = game.getApplicationId();
        this.HB = game.getPrimaryCategory();
        this.HC = game.getSecondaryCategory();
        this.HD = game.getDescription();
        this.HE = game.getDeveloperName();
        this.HA = game.getDisplayName();
        this.HF = game.getIconImageUri();
        this.HQ = game.getIconImageUrl();
        this.HG = game.getHiResImageUri();
        this.HR = game.getHiResImageUrl();
        this.HH = game.getFeaturedImageUri();
        this.HS = game.getFeaturedImageUrl();
        this.HI = game.gb();
        this.HJ = game.gd();
        this.HK = game.ge();
        this.HL = game.gf();
        this.HM = game.getAchievementTotalCount();
        this.HN = game.getLeaderboardCount();
        this.HO = game.isRealTimeMultiplayerEnabled();
        this.HP = game.isTurnBasedMultiplayerEnabled();
        this.HT = game.isMuted();
        this.HU = game.gc();
    }

    static int a(Game game) {
        return fo.hashCode(new Object[]{game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), game.gb(), game.gd(), game.ge(), game.gf(), game.getAchievementTotalCount(), game.getLeaderboardCount(), game.isRealTimeMultiplayerEnabled(), game.isTurnBasedMultiplayerEnabled(), game.isMuted(), game.gc()});
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean a(Game game, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Game)) {
            return false;
        }
        boolean bl3 = bl2;
        if (game == object) return bl3;
        if (!fo.equal((object = (Game)object).getApplicationId(), game.getApplicationId())) return false;
        if (!fo.equal(object.getDisplayName(), game.getDisplayName())) return false;
        if (!fo.equal(object.getPrimaryCategory(), game.getPrimaryCategory())) return false;
        if (!fo.equal(object.getSecondaryCategory(), game.getSecondaryCategory())) return false;
        if (!fo.equal(object.getDescription(), game.getDescription())) return false;
        if (!fo.equal(object.getDeveloperName(), game.getDeveloperName())) return false;
        if (!fo.equal((Object)object.getIconImageUri(), (Object)game.getIconImageUri())) return false;
        if (!fo.equal((Object)object.getHiResImageUri(), (Object)game.getHiResImageUri())) return false;
        if (!fo.equal((Object)object.getFeaturedImageUri(), (Object)game.getFeaturedImageUri())) return false;
        if (!fo.equal(object.gb(), game.gb())) return false;
        if (!fo.equal(object.gd(), game.gd())) return false;
        if (!fo.equal(object.ge(), game.ge())) return false;
        if (!fo.equal(object.gf(), game.gf())) return false;
        if (!fo.equal(object.getAchievementTotalCount(), game.getAchievementTotalCount())) return false;
        if (!fo.equal(object.getLeaderboardCount(), game.getLeaderboardCount())) return false;
        if (!fo.equal(object.isRealTimeMultiplayerEnabled(), game.isRealTimeMultiplayerEnabled())) return false;
        boolean bl4 = object.isTurnBasedMultiplayerEnabled();
        boolean bl5 = game.isTurnBasedMultiplayerEnabled() && fo.equal(object.isMuted(), game.isMuted()) && fo.equal(object.gc(), game.gc());
        bl3 = bl2;
        if (!fo.equal(bl4, bl5)) return false;
        return bl3;
    }

    static String b(Game game) {
        return fo.e(game).a("ApplicationId", game.getApplicationId()).a("DisplayName", game.getDisplayName()).a("PrimaryCategory", game.getPrimaryCategory()).a("SecondaryCategory", game.getSecondaryCategory()).a("Description", game.getDescription()).a("DeveloperName", game.getDeveloperName()).a("IconImageUri", (Object)game.getIconImageUri()).a("IconImageUrl", game.getIconImageUrl()).a("HiResImageUri", (Object)game.getHiResImageUri()).a("HiResImageUrl", game.getHiResImageUrl()).a("FeaturedImageUri", (Object)game.getFeaturedImageUri()).a("FeaturedImageUrl", game.getFeaturedImageUrl()).a("PlayEnabledGame", game.gb()).a("InstanceInstalled", game.gd()).a("InstancePackageName", game.ge()).a("AchievementTotalCount", game.getAchievementTotalCount()).a("LeaderboardCount", game.getLeaderboardCount()).a("RealTimeMultiplayerEnabled", game.isRealTimeMultiplayerEnabled()).a("TurnBasedMultiplayerEnabled", game.isTurnBasedMultiplayerEnabled()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return GameEntity.a(this, object);
    }

    @Override
    public Game freeze() {
        return this;
    }

    @Override
    public boolean gb() {
        return this.HI;
    }

    @Override
    public boolean gc() {
        return this.HU;
    }

    @Override
    public boolean gd() {
        return this.HJ;
    }

    @Override
    public String ge() {
        return this.HK;
    }

    @Override
    public int getAchievementTotalCount() {
        return this.HM;
    }

    @Override
    public String getApplicationId() {
        return this.xI;
    }

    @Override
    public String getDescription() {
        return this.HD;
    }

    @Override
    public void getDescription(CharArrayBuffer charArrayBuffer) {
        gm.b(this.HD, charArrayBuffer);
    }

    @Override
    public String getDeveloperName() {
        return this.HE;
    }

    @Override
    public void getDeveloperName(CharArrayBuffer charArrayBuffer) {
        gm.b(this.HE, charArrayBuffer);
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
    public Uri getFeaturedImageUri() {
        return this.HH;
    }

    @Override
    public String getFeaturedImageUrl() {
        return this.HS;
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
    public int getLeaderboardCount() {
        return this.HN;
    }

    @Override
    public String getPrimaryCategory() {
        return this.HB;
    }

    @Override
    public String getSecondaryCategory() {
        return this.HC;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override
    public int gf() {
        return this.HL;
    }

    public int hashCode() {
        return GameEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public boolean isMuted() {
        return this.HT;
    }

    @Override
    public boolean isRealTimeMultiplayerEnabled() {
        return this.HO;
    }

    @Override
    public boolean isTurnBasedMultiplayerEnabled() {
        return this.HP;
    }

    public String toString() {
        return GameEntity.b(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        int n3 = 1;
        Object var5_4 = null;
        if (!this.eK()) {
            GameEntityCreator.a(this, parcel, n2);
            return;
        }
        parcel.writeString(this.xI);
        parcel.writeString(this.HA);
        parcel.writeString(this.HB);
        parcel.writeString(this.HC);
        parcel.writeString(this.HD);
        parcel.writeString(this.HE);
        String string2 = this.HF == null ? null : this.HF.toString();
        parcel.writeString(string2);
        string2 = this.HG == null ? null : this.HG.toString();
        parcel.writeString(string2);
        string2 = this.HH == null ? var5_4 : this.HH.toString();
        parcel.writeString(string2);
        n2 = this.HI ? 1 : 0;
        parcel.writeInt(n2);
        n2 = this.HJ ? n3 : 0;
        parcel.writeInt(n2);
        parcel.writeString(this.HK);
        parcel.writeInt(this.HL);
        parcel.writeInt(this.HM);
        parcel.writeInt(this.HN);
    }

    static final class GameEntityCreatorCompat
    extends GameEntityCreator {
        GameEntityCreatorCompat() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public GameEntity an(Parcel parcel) {
            boolean bl2;
            if (GameEntity.c(GameEntity.eJ())) return super.an(parcel);
            if (GameEntity.al(GameEntity.class.getCanonicalName())) {
                return super.an(parcel);
            }
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            String string6 = parcel.readString();
            String string7 = parcel.readString();
            String string8 = parcel.readString();
            string8 = string8 == null ? null : Uri.parse((String)string8);
            String string9 = parcel.readString();
            string9 = string9 == null ? null : Uri.parse((String)string9);
            String string10 = parcel.readString();
            string10 = string10 == null ? null : Uri.parse((String)string10);
            boolean bl3 = parcel.readInt() > 0;
            if (parcel.readInt() > 0) {
                bl2 = true;
                return new GameEntity(3, string2, string3, string4, string5, string6, string7, (Uri)string8, (Uri)string9, (Uri)string10, bl3, bl2, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false, false, null, null, null, false, false);
            }
            bl2 = false;
            return new GameEntity(3, string2, string3, string4, string5, string6, string7, (Uri)string8, (Uri)string9, (Uri)string10, bl3, bl2, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false, false, null, null, null, false, false);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.an(parcel);
        }
    }

}

