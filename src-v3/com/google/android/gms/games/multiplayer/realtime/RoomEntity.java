package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;
import java.util.ArrayList;

public final class RoomEntity extends GamesDowngradeableSafeParcel implements Room {
    public static final Creator<RoomEntity> CREATOR;
    private final String HD;
    private final String Ja;
    private final Bundle MO;
    private final String MS;
    private final int MT;
    private final int MU;
    private final long Mu;
    private final ArrayList<ParticipantEntity> Mx;
    private final int My;
    private final int xH;

    static final class RoomEntityCreatorCompat extends RoomEntityCreator {
        RoomEntityCreatorCompat() {
        }

        public RoomEntity ax(Parcel parcel) {
            if (GamesDowngradeableSafeParcel.m2875c(fe.eJ()) || fe.al(RoomEntity.class.getCanonicalName())) {
                return super.ax(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            String readString3 = parcel.readString();
            int readInt2 = parcel.readInt();
            Bundle readBundle = parcel.readBundle();
            int readInt3 = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt3);
            for (int i = 0; i < readInt3; i++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new RoomEntity(2, readString, readString2, readLong, readInt, readString3, readInt2, readBundle, arrayList, -1);
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return ax(x0);
        }
    }

    static {
        CREATOR = new RoomEntityCreatorCompat();
    }

    RoomEntity(int versionCode, String roomId, String creatorId, long creationTimestamp, int roomStatus, String description, int variant, Bundle autoMatchCriteria, ArrayList<ParticipantEntity> participants, int autoMatchWaitEstimateSeconds) {
        this.xH = versionCode;
        this.Ja = roomId;
        this.MS = creatorId;
        this.Mu = creationTimestamp;
        this.MT = roomStatus;
        this.HD = description;
        this.My = variant;
        this.MO = autoMatchCriteria;
        this.Mx = participants;
        this.MU = autoMatchWaitEstimateSeconds;
    }

    public RoomEntity(Room room) {
        this.xH = 2;
        this.Ja = room.getRoomId();
        this.MS = room.getCreatorId();
        this.Mu = room.getCreationTimestamp();
        this.MT = room.getStatus();
        this.HD = room.getDescription();
        this.My = room.getVariant();
        this.MO = room.getAutoMatchCriteria();
        ArrayList participants = room.getParticipants();
        int size = participants.size();
        this.Mx = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            this.Mx.add((ParticipantEntity) ((Participant) participants.get(i)).freeze());
        }
        this.MU = room.getAutoMatchWaitEstimateSeconds();
    }

    static int m3405a(Room room) {
        return fo.hashCode(room.getRoomId(), room.getCreatorId(), Long.valueOf(room.getCreationTimestamp()), Integer.valueOf(room.getStatus()), room.getDescription(), Integer.valueOf(room.getVariant()), room.getAutoMatchCriteria(), room.getParticipants(), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static int m3406a(Room room, String str) {
        ArrayList participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant.getStatus();
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in room " + room.getRoomId());
    }

    static boolean m3407a(Room room, Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        if (room == obj) {
            return true;
        }
        Room room2 = (Room) obj;
        return fo.equal(room2.getRoomId(), room.getRoomId()) && fo.equal(room2.getCreatorId(), room.getCreatorId()) && fo.equal(Long.valueOf(room2.getCreationTimestamp()), Long.valueOf(room.getCreationTimestamp())) && fo.equal(Integer.valueOf(room2.getStatus()), Integer.valueOf(room.getStatus())) && fo.equal(room2.getDescription(), room.getDescription()) && fo.equal(Integer.valueOf(room2.getVariant()), Integer.valueOf(room.getVariant())) && fo.equal(room2.getAutoMatchCriteria(), room.getAutoMatchCriteria()) && fo.equal(room2.getParticipants(), room.getParticipants()) && fo.equal(Integer.valueOf(room2.getAutoMatchWaitEstimateSeconds()), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static String m3408b(Room room) {
        return fo.m976e(room).m975a("RoomId", room.getRoomId()).m975a("CreatorId", room.getCreatorId()).m975a("CreationTimestamp", Long.valueOf(room.getCreationTimestamp())).m975a("RoomStatus", Integer.valueOf(room.getStatus())).m975a("Description", room.getDescription()).m975a("Variant", Integer.valueOf(room.getVariant())).m975a("AutoMatchCriteria", room.getAutoMatchCriteria()).m975a("Participants", room.getParticipants()).m975a("AutoMatchWaitEstimateSeconds", Integer.valueOf(room.getAutoMatchWaitEstimateSeconds())).toString();
    }

    static String m3409b(Room room, String str) {
        ArrayList participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            Player player = participant.getPlayer();
            if (player != null && player.getPlayerId().equals(str)) {
                return participant.getParticipantId();
            }
        }
        return null;
    }

    static Participant m3411c(Room room, String str) {
        ArrayList participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant;
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in match " + room.getRoomId());
    }

    static ArrayList<String> m3412c(Room room) {
        ArrayList participants = room.getParticipants();
        int size = participants.size();
        ArrayList<String> arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(((Participant) participants.get(i)).getParticipantId());
        }
        return arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m3407a((Room) this, obj);
    }

    public Room freeze() {
        return this;
    }

    public Bundle getAutoMatchCriteria() {
        return this.MO;
    }

    public int getAutoMatchWaitEstimateSeconds() {
        return this.MU;
    }

    public long getCreationTimestamp() {
        return this.Mu;
    }

    public String getCreatorId() {
        return this.MS;
    }

    public String getDescription() {
        return this.HD;
    }

    public void getDescription(CharArrayBuffer dataOut) {
        gm.m1035b(this.HD, dataOut);
    }

    public Participant getParticipant(String participantId) {
        return m3411c(this, participantId);
    }

    public String getParticipantId(String playerId) {
        return m3409b(this, playerId);
    }

    public ArrayList<String> getParticipantIds() {
        return m3412c(this);
    }

    public int getParticipantStatus(String participantId) {
        return m3406a((Room) this, participantId);
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList(this.Mx);
    }

    public String getRoomId() {
        return this.Ja;
    }

    public int getStatus() {
        return this.MT;
    }

    public int getVariant() {
        return this.My;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return m3405a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m3408b((Room) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (eK()) {
            dest.writeString(this.Ja);
            dest.writeString(this.MS);
            dest.writeLong(this.Mu);
            dest.writeInt(this.MT);
            dest.writeString(this.HD);
            dest.writeInt(this.My);
            dest.writeBundle(this.MO);
            int size = this.Mx.size();
            dest.writeInt(size);
            for (int i = 0; i < size; i++) {
                ((ParticipantEntity) this.Mx.get(i)).writeToParcel(dest, flags);
            }
            return;
        }
        RoomEntityCreator.m578a(this, dest, flags);
    }
}
