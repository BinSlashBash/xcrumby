package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;

public final class ParticipantEntity extends GamesDowngradeableSafeParcel implements Participant {
    public static final Creator<ParticipantEntity> CREATOR;
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

    static final class ParticipantEntityCreatorCompat extends ParticipantEntityCreator {
        ParticipantEntityCreatorCompat() {
        }

        public ParticipantEntity av(Parcel parcel) {
            Object obj = 1;
            if (GamesDowngradeableSafeParcel.m2875c(fe.eJ()) || fe.al(ParticipantEntity.class.getCanonicalName())) {
                return super.av(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            Uri parse = readString3 == null ? null : Uri.parse(readString3);
            String readString4 = parcel.readString();
            Uri parse2 = readString4 == null ? null : Uri.parse(readString4);
            int readInt = parcel.readInt();
            String readString5 = parcel.readString();
            boolean z = parcel.readInt() > 0;
            if (parcel.readInt() <= 0) {
                obj = null;
            }
            return new ParticipantEntity(3, readString, readString2, parse, parse2, readInt, readString5, z, obj != null ? (PlayerEntity) PlayerEntity.CREATOR.createFromParcel(parcel) : null, 7, null, null, null);
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return av(x0);
        }
    }

    static {
        CREATOR = new ParticipantEntityCreatorCompat();
    }

    ParticipantEntity(int versionCode, String participantId, String displayName, Uri iconImageUri, Uri hiResImageUri, int status, String clientAddress, boolean connectedToRoom, PlayerEntity player, int capabilities, ParticipantResult result, String iconImageUrl, String hiResImageUrl) {
        this.xH = versionCode;
        this.Jg = participantId;
        this.HA = displayName;
        this.HF = iconImageUri;
        this.HG = hiResImageUri;
        this.MB = status;
        this.Is = clientAddress;
        this.MC = connectedToRoom;
        this.LH = player;
        this.MD = capabilities;
        this.ME = result;
        this.HQ = iconImageUrl;
        this.HR = hiResImageUrl;
    }

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
        this.LH = player == null ? null : new PlayerEntity(player);
        this.MD = participant.getCapabilities();
        this.ME = participant.getResult();
        this.HQ = participant.getIconImageUrl();
        this.HR = participant.getHiResImageUrl();
    }

    static int m3401a(Participant participant) {
        return fo.hashCode(participant.getPlayer(), Integer.valueOf(participant.getStatus()), participant.gi(), Boolean.valueOf(participant.isConnectedToRoom()), participant.getDisplayName(), participant.getIconImageUri(), participant.getHiResImageUri(), Integer.valueOf(participant.getCapabilities()), participant.getResult(), participant.getParticipantId());
    }

    static boolean m3402a(Participant participant, Object obj) {
        if (!(obj instanceof Participant)) {
            return false;
        }
        if (participant == obj) {
            return true;
        }
        Participant participant2 = (Participant) obj;
        return fo.equal(participant2.getPlayer(), participant.getPlayer()) && fo.equal(Integer.valueOf(participant2.getStatus()), Integer.valueOf(participant.getStatus())) && fo.equal(participant2.gi(), participant.gi()) && fo.equal(Boolean.valueOf(participant2.isConnectedToRoom()), Boolean.valueOf(participant.isConnectedToRoom())) && fo.equal(participant2.getDisplayName(), participant.getDisplayName()) && fo.equal(participant2.getIconImageUri(), participant.getIconImageUri()) && fo.equal(participant2.getHiResImageUri(), participant.getHiResImageUri()) && fo.equal(Integer.valueOf(participant2.getCapabilities()), Integer.valueOf(participant.getCapabilities())) && fo.equal(participant2.getResult(), participant.getResult()) && fo.equal(participant2.getParticipantId(), participant.getParticipantId());
    }

    static String m3403b(Participant participant) {
        return fo.m976e(participant).m975a("ParticipantId", participant.getParticipantId()).m975a("Player", participant.getPlayer()).m975a("Status", Integer.valueOf(participant.getStatus())).m975a("ClientAddress", participant.gi()).m975a("ConnectedToRoom", Boolean.valueOf(participant.isConnectedToRoom())).m975a("DisplayName", participant.getDisplayName()).m975a("IconImage", participant.getIconImageUri()).m975a("IconImageUrl", participant.getIconImageUrl()).m975a("HiResImage", participant.getHiResImageUri()).m975a("HiResImageUrl", participant.getHiResImageUrl()).m975a("Capabilities", Integer.valueOf(participant.getCapabilities())).m975a("Result", participant.getResult()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m3402a(this, obj);
    }

    public Participant freeze() {
        return this;
    }

    public int getCapabilities() {
        return this.MD;
    }

    public String getDisplayName() {
        return this.LH == null ? this.HA : this.LH.getDisplayName();
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        if (this.LH == null) {
            gm.m1035b(this.HA, dataOut);
        } else {
            this.LH.getDisplayName(dataOut);
        }
    }

    public Uri getHiResImageUri() {
        return this.LH == null ? this.HG : this.LH.getHiResImageUri();
    }

    public String getHiResImageUrl() {
        return this.LH == null ? this.HR : this.LH.getHiResImageUrl();
    }

    public Uri getIconImageUri() {
        return this.LH == null ? this.HF : this.LH.getIconImageUri();
    }

    public String getIconImageUrl() {
        return this.LH == null ? this.HQ : this.LH.getIconImageUrl();
    }

    public String getParticipantId() {
        return this.Jg;
    }

    public Player getPlayer() {
        return this.LH;
    }

    public ParticipantResult getResult() {
        return this.ME;
    }

    public int getStatus() {
        return this.MB;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String gi() {
        return this.Is;
    }

    public int hashCode() {
        return m3401a(this);
    }

    public boolean isConnectedToRoom() {
        return this.MC;
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m3403b((Participant) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        String str = null;
        int i = 0;
        if (eK()) {
            dest.writeString(this.Jg);
            dest.writeString(this.HA);
            dest.writeString(this.HF == null ? null : this.HF.toString());
            if (this.HG != null) {
                str = this.HG.toString();
            }
            dest.writeString(str);
            dest.writeInt(this.MB);
            dest.writeString(this.Is);
            dest.writeInt(this.MC ? 1 : 0);
            if (this.LH != null) {
                i = 1;
            }
            dest.writeInt(i);
            if (this.LH != null) {
                this.LH.writeToParcel(dest, flags);
                return;
            }
            return;
        }
        ParticipantEntityCreator.m576a(this, dest, flags);
    }
}
