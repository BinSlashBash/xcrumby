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
package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntityCreator;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

public final class ParticipantEntity
extends GamesDowngradeableSafeParcel
implements Participant {
    public static final Parcelable.Creator<ParticipantEntity> CREATOR = new ParticipantEntityCreatorCompat();
    private final String HA;
    private final Uri HF;
    private final Uri HG;
    private final String HQ;
    private final String HR;
    private final String Is;
    private final String Jg;
    private final PlayerEntity LH;
    private final int MB;
    private final boolean MC;
    private final int MD;
    private final ParticipantResult ME;
    private final int xH;

    ParticipantEntity(int n2, String string2, String string3, Uri uri, Uri uri2, int n3, String string4, boolean bl2, PlayerEntity playerEntity, int n4, ParticipantResult participantResult, String string5, String string6) {
        this.xH = n2;
        this.Jg = string2;
        this.HA = string3;
        this.HF = uri;
        this.HG = uri2;
        this.MB = n3;
        this.Is = string4;
        this.MC = bl2;
        this.LH = playerEntity;
        this.MD = n4;
        this.ME = participantResult;
        this.HQ = string5;
        this.HR = string6;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ParticipantEntity(Participant participant) {
        this.xH = 3;
        this.Jg = participant.getParticipantId();
        this.HA = participant.getDisplayName();
        this.HF = participant.getIconImageUri();
        this.HG = participant.getHiResImageUri();
        this.MB = participant.getStatus();
        this.Is = participant.gi();
        this.MC = participant.isConnectedToRoom();
        Player player = participant.getPlayer();
        player = player == null ? null : new PlayerEntity(player);
        this.LH = player;
        this.MD = participant.getCapabilities();
        this.ME = participant.getResult();
        this.HQ = participant.getIconImageUrl();
        this.HR = participant.getHiResImageUrl();
    }

    static int a(Participant participant) {
        return fo.hashCode(new Object[]{participant.getPlayer(), participant.getStatus(), participant.gi(), participant.isConnectedToRoom(), participant.getDisplayName(), participant.getIconImageUri(), participant.getHiResImageUri(), participant.getCapabilities(), participant.getResult(), participant.getParticipantId()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(Participant participant, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Participant)) {
            return false;
        }
        boolean bl3 = bl2;
        if (participant == object) return bl3;
        if (!fo.equal((object = (Participant)object).getPlayer(), participant.getPlayer())) return false;
        if (!fo.equal(object.getStatus(), participant.getStatus())) return false;
        if (!fo.equal(object.gi(), participant.gi())) return false;
        if (!fo.equal(object.isConnectedToRoom(), participant.isConnectedToRoom())) return false;
        if (!fo.equal(object.getDisplayName(), participant.getDisplayName())) return false;
        if (!fo.equal((Object)object.getIconImageUri(), (Object)participant.getIconImageUri())) return false;
        if (!fo.equal((Object)object.getHiResImageUri(), (Object)participant.getHiResImageUri())) return false;
        if (!fo.equal(object.getCapabilities(), participant.getCapabilities())) return false;
        if (!fo.equal(object.getResult(), participant.getResult())) return false;
        bl3 = bl2;
        if (fo.equal(object.getParticipantId(), participant.getParticipantId())) return bl3;
        return false;
    }

    static String b(Participant participant) {
        return fo.e(participant).a("ParticipantId", participant.getParticipantId()).a("Player", participant.getPlayer()).a("Status", participant.getStatus()).a("ClientAddress", participant.gi()).a("ConnectedToRoom", participant.isConnectedToRoom()).a("DisplayName", participant.getDisplayName()).a("IconImage", (Object)participant.getIconImageUri()).a("IconImageUrl", participant.getIconImageUrl()).a("HiResImage", (Object)participant.getHiResImageUri()).a("HiResImageUrl", participant.getHiResImageUrl()).a("Capabilities", participant.getCapabilities()).a("Result", participant.getResult()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return ParticipantEntity.a(this, object);
    }

    @Override
    public Participant freeze() {
        return this;
    }

    @Override
    public int getCapabilities() {
        return this.MD;
    }

    @Override
    public String getDisplayName() {
        if (this.LH == null) {
            return this.HA;
        }
        return this.LH.getDisplayName();
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        if (this.LH == null) {
            gm.b(this.HA, charArrayBuffer);
            return;
        }
        this.LH.getDisplayName(charArrayBuffer);
    }

    @Override
    public Uri getHiResImageUri() {
        if (this.LH == null) {
            return this.HG;
        }
        return this.LH.getHiResImageUri();
    }

    @Override
    public String getHiResImageUrl() {
        if (this.LH == null) {
            return this.HR;
        }
        return this.LH.getHiResImageUrl();
    }

    @Override
    public Uri getIconImageUri() {
        if (this.LH == null) {
            return this.HF;
        }
        return this.LH.getIconImageUri();
    }

    @Override
    public String getIconImageUrl() {
        if (this.LH == null) {
            return this.HQ;
        }
        return this.LH.getIconImageUrl();
    }

    @Override
    public String getParticipantId() {
        return this.Jg;
    }

    @Override
    public Player getPlayer() {
        return this.LH;
    }

    @Override
    public ParticipantResult getResult() {
        return this.ME;
    }

    @Override
    public int getStatus() {
        return this.MB;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override
    public String gi() {
        return this.Is;
    }

    public int hashCode() {
        return ParticipantEntity.a(this);
    }

    @Override
    public boolean isConnectedToRoom() {
        return this.MC;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return ParticipantEntity.b(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        Object var6_3 = null;
        int n3 = 0;
        if (!this.eK()) {
            ParticipantEntityCreator.a(this, parcel, n2);
            return;
        } else {
            parcel.writeString(this.Jg);
            parcel.writeString(this.HA);
            String string2 = this.HF == null ? null : this.HF.toString();
            parcel.writeString(string2);
            string2 = this.HG == null ? var6_3 : this.HG.toString();
            parcel.writeString(string2);
            parcel.writeInt(this.MB);
            parcel.writeString(this.Is);
            int n4 = this.MC ? 1 : 0;
            parcel.writeInt(n4);
            n4 = this.LH == null ? n3 : 1;
            parcel.writeInt(n4);
            if (this.LH == null) return;
            {
                this.LH.writeToParcel(parcel, n2);
                return;
            }
        }
    }

    static final class ParticipantEntityCreatorCompat
    extends ParticipantEntityCreator {
        ParticipantEntityCreatorCompat() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public ParticipantEntity av(Parcel object) {
            boolean bl2 = true;
            if (ParticipantEntity.c(ParticipantEntity.eJ())) return super.av((Parcel)object);
            if (ParticipantEntity.al(ParticipantEntity.class.getCanonicalName())) {
                return super.av((Parcel)object);
            }
            String string2 = object.readString();
            String string3 = object.readString();
            String string4 = object.readString();
            string4 = string4 == null ? null : Uri.parse((String)string4);
            String string5 = object.readString();
            string5 = string5 == null ? null : Uri.parse((String)string5);
            int n2 = object.readInt();
            String string6 = object.readString();
            boolean bl3 = object.readInt() > 0;
            if (object.readInt() <= 0) {
                bl2 = false;
            }
            if (bl2) {
                object = (PlayerEntity)PlayerEntity.CREATOR.createFromParcel((Parcel)object);
                return new ParticipantEntity(3, string2, string3, (Uri)string4, (Uri)string5, n2, string6, bl3, (PlayerEntity)object, 7, null, null, null);
            }
            object = null;
            return new ParticipantEntity(3, string2, string3, (Uri)string4, (Uri)string5, n2, string6, bl3, (PlayerEntity)object, 7, null, null, null);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.av(parcel);
        }
    }

}

