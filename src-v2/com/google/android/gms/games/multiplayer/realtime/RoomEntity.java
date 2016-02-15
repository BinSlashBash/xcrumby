/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomEntityCreator;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;
import java.util.ArrayList;
import java.util.Collection;

public final class RoomEntity
extends GamesDowngradeableSafeParcel
implements Room {
    public static final Parcelable.Creator<RoomEntity> CREATOR = new RoomEntityCreatorCompat();
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

    RoomEntity(int n2, String string2, String string3, long l2, int n3, String string4, int n4, Bundle bundle, ArrayList<ParticipantEntity> arrayList, int n5) {
        this.xH = n2;
        this.Ja = string2;
        this.MS = string3;
        this.Mu = l2;
        this.MT = n3;
        this.HD = string4;
        this.My = n4;
        this.MO = bundle;
        this.Mx = arrayList;
        this.MU = n5;
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
        ArrayList arrayList = room.getParticipants();
        int n2 = arrayList.size();
        this.Mx = new ArrayList(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            this.Mx.add((ParticipantEntity)((Participant)arrayList.get(i2)).freeze());
        }
        this.MU = room.getAutoMatchWaitEstimateSeconds();
    }

    static int a(Room room) {
        return fo.hashCode(new Object[]{room.getRoomId(), room.getCreatorId(), room.getCreationTimestamp(), room.getStatus(), room.getDescription(), room.getVariant(), room.getAutoMatchCriteria(), room.getParticipants(), room.getAutoMatchWaitEstimateSeconds()});
    }

    static int a(Room room, String string2) {
        ArrayList arrayList = room.getParticipants();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)arrayList.get(i2);
            if (!participant.getParticipantId().equals(string2)) continue;
            return participant.getStatus();
        }
        throw new IllegalStateException("Participant " + string2 + " is not in room " + room.getRoomId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(Room room, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Room)) {
            return false;
        }
        boolean bl3 = bl2;
        if (room == object) return bl3;
        if (!fo.equal((object = (Room)object).getRoomId(), room.getRoomId())) return false;
        if (!fo.equal(object.getCreatorId(), room.getCreatorId())) return false;
        if (!fo.equal(object.getCreationTimestamp(), room.getCreationTimestamp())) return false;
        if (!fo.equal(object.getStatus(), room.getStatus())) return false;
        if (!fo.equal(object.getDescription(), room.getDescription())) return false;
        if (!fo.equal(object.getVariant(), room.getVariant())) return false;
        if (!fo.equal((Object)object.getAutoMatchCriteria(), (Object)room.getAutoMatchCriteria())) return false;
        if (!fo.equal(object.getParticipants(), room.getParticipants())) return false;
        bl3 = bl2;
        if (fo.equal(object.getAutoMatchWaitEstimateSeconds(), room.getAutoMatchWaitEstimateSeconds())) return bl3;
        return false;
    }

    static String b(Room room) {
        return fo.e(room).a("RoomId", room.getRoomId()).a("CreatorId", room.getCreatorId()).a("CreationTimestamp", room.getCreationTimestamp()).a("RoomStatus", room.getStatus()).a("Description", room.getDescription()).a("Variant", room.getVariant()).a("AutoMatchCriteria", (Object)room.getAutoMatchCriteria()).a("Participants", room.getParticipants()).a("AutoMatchWaitEstimateSeconds", room.getAutoMatchWaitEstimateSeconds()).toString();
    }

    static String b(Room object, String string2) {
        object = object.getParticipants();
        int n2 = object.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)object.get(i2);
            Player player = participant.getPlayer();
            if (player == null || !player.getPlayerId().equals(string2)) continue;
            return participant.getParticipantId();
        }
        return null;
    }

    static Participant c(Room room, String string2) {
        ArrayList arrayList = room.getParticipants();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)arrayList.get(i2);
            if (!participant.getParticipantId().equals(string2)) continue;
            return participant;
        }
        throw new IllegalStateException("Participant " + string2 + " is not in match " + room.getRoomId());
    }

    static ArrayList<String> c(Room object) {
        object = object.getParticipants();
        int n2 = object.size();
        ArrayList<String> arrayList = new ArrayList<String>(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(((Participant)object.get(i2)).getParticipantId());
        }
        return arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return RoomEntity.a((Room)this, object);
    }

    @Override
    public Room freeze() {
        return this;
    }

    @Override
    public Bundle getAutoMatchCriteria() {
        return this.MO;
    }

    @Override
    public int getAutoMatchWaitEstimateSeconds() {
        return this.MU;
    }

    @Override
    public long getCreationTimestamp() {
        return this.Mu;
    }

    @Override
    public String getCreatorId() {
        return this.MS;
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
    public Participant getParticipant(String string2) {
        return RoomEntity.c(this, string2);
    }

    @Override
    public String getParticipantId(String string2) {
        return RoomEntity.b(this, string2);
    }

    @Override
    public ArrayList<String> getParticipantIds() {
        return RoomEntity.c(this);
    }

    @Override
    public int getParticipantStatus(String string2) {
        return RoomEntity.a((Room)this, string2);
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        return new ArrayList<Participant>(this.Mx);
    }

    @Override
    public String getRoomId() {
        return this.Ja;
    }

    @Override
    public int getStatus() {
        return this.MT;
    }

    @Override
    public int getVariant() {
        return this.My;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return RoomEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return RoomEntity.b(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeToParcel(Parcel parcel, int n2) {
        if (!this.eK()) {
            RoomEntityCreator.a(this, parcel, n2);
            return;
        }
        parcel.writeString(this.Ja);
        parcel.writeString(this.MS);
        parcel.writeLong(this.Mu);
        parcel.writeInt(this.MT);
        parcel.writeString(this.HD);
        parcel.writeInt(this.My);
        parcel.writeBundle(this.MO);
        int n3 = this.Mx.size();
        parcel.writeInt(n3);
        int n4 = 0;
        while (n4 < n3) {
            this.Mx.get(n4).writeToParcel(parcel, n2);
            ++n4;
        }
    }

    static final class RoomEntityCreatorCompat
    extends RoomEntityCreator {
        RoomEntityCreatorCompat() {
        }

        @Override
        public RoomEntity ax(Parcel parcel) {
            if (RoomEntity.c(RoomEntity.eJ()) || RoomEntity.al(RoomEntity.class.getCanonicalName())) {
                return super.ax(parcel);
            }
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            long l2 = parcel.readLong();
            int n2 = parcel.readInt();
            String string4 = parcel.readString();
            int n3 = parcel.readInt();
            Bundle bundle = parcel.readBundle();
            int n4 = parcel.readInt();
            ArrayList<ParticipantEntity> arrayList = new ArrayList<ParticipantEntity>(n4);
            for (int i2 = 0; i2 < n4; ++i2) {
                arrayList.add((ParticipantEntity)ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new RoomEntity(2, string2, string3, l2, n2, string4, n3, bundle, arrayList, -1);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.ax(parcel);
        }
    }

}

