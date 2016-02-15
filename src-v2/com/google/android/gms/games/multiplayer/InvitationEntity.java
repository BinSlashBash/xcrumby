/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationEntityCreator;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collection;

public final class InvitationEntity
extends GamesDowngradeableSafeParcel
implements Invitation {
    public static final Parcelable.Creator<InvitationEntity> CREATOR = new InvitationEntityCreatorCompat();
    private final String IV;
    private final GameEntity Lt;
    private final long Mu;
    private final int Mv;
    private final ParticipantEntity Mw;
    private final ArrayList<ParticipantEntity> Mx;
    private final int My;
    private final int Mz;
    private final int xH;

    InvitationEntity(int n2, GameEntity gameEntity, String string2, long l2, int n3, ParticipantEntity participantEntity, ArrayList<ParticipantEntity> arrayList, int n4, int n5) {
        this.xH = n2;
        this.Lt = gameEntity;
        this.IV = string2;
        this.Mu = l2;
        this.Mv = n3;
        this.Mw = participantEntity;
        this.Mx = arrayList;
        this.My = n4;
        this.Mz = n5;
    }

    InvitationEntity(Invitation parcelable) {
        this.xH = 2;
        this.Lt = new GameEntity(parcelable.getGame());
        this.IV = parcelable.getInvitationId();
        this.Mu = parcelable.getCreationTimestamp();
        this.Mv = parcelable.getInvitationType();
        this.My = parcelable.getVariant();
        this.Mz = parcelable.getAvailableAutoMatchSlots();
        String string2 = parcelable.getInviter().getParticipantId();
        Participant participant = null;
        ArrayList arrayList = parcelable.getParticipants();
        int n2 = arrayList.size();
        this.Mx = new ArrayList(n2);
        parcelable = participant;
        for (int i2 = 0; i2 < n2; ++i2) {
            participant = (Participant)arrayList.get(i2);
            if (participant.getParticipantId().equals(string2)) {
                parcelable = participant;
            }
            this.Mx.add((ParticipantEntity)participant.freeze());
        }
        fq.b(parcelable, (Object)"Must have a valid inviter!");
        this.Mw = (ParticipantEntity)parcelable.freeze();
    }

    static int a(Invitation invitation) {
        return fo.hashCode(invitation.getGame(), invitation.getInvitationId(), invitation.getCreationTimestamp(), invitation.getInvitationType(), invitation.getInviter(), invitation.getParticipants(), invitation.getVariant(), invitation.getAvailableAutoMatchSlots());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(Invitation invitation, Object object) {
        boolean bl2 = true;
        if (!(object instanceof Invitation)) {
            return false;
        }
        boolean bl3 = bl2;
        if (invitation == object) return bl3;
        if (!fo.equal((object = (Invitation)object).getGame(), invitation.getGame())) return false;
        if (!fo.equal(object.getInvitationId(), invitation.getInvitationId())) return false;
        if (!fo.equal(object.getCreationTimestamp(), invitation.getCreationTimestamp())) return false;
        if (!fo.equal(object.getInvitationType(), invitation.getInvitationType())) return false;
        if (!fo.equal(object.getInviter(), invitation.getInviter())) return false;
        if (!fo.equal(object.getParticipants(), invitation.getParticipants())) return false;
        if (!fo.equal(object.getVariant(), invitation.getVariant())) return false;
        bl3 = bl2;
        if (fo.equal(object.getAvailableAutoMatchSlots(), invitation.getAvailableAutoMatchSlots())) return bl3;
        return false;
    }

    static String b(Invitation invitation) {
        return fo.e(invitation).a("Game", invitation.getGame()).a("InvitationId", invitation.getInvitationId()).a("CreationTimestamp", invitation.getCreationTimestamp()).a("InvitationType", invitation.getInvitationType()).a("Inviter", invitation.getInviter()).a("Participants", invitation.getParticipants()).a("Variant", invitation.getVariant()).a("AvailableAutoMatchSlots", invitation.getAvailableAutoMatchSlots()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return InvitationEntity.a(this, object);
    }

    @Override
    public Invitation freeze() {
        return this;
    }

    @Override
    public int getAvailableAutoMatchSlots() {
        return this.Mz;
    }

    @Override
    public long getCreationTimestamp() {
        return this.Mu;
    }

    @Override
    public Game getGame() {
        return this.Lt;
    }

    @Override
    public String getInvitationId() {
        return this.IV;
    }

    @Override
    public int getInvitationType() {
        return this.Mv;
    }

    @Override
    public Participant getInviter() {
        return this.Mw;
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        return new ArrayList<Participant>(this.Mx);
    }

    @Override
    public int getVariant() {
        return this.My;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return InvitationEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return InvitationEntity.b(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeToParcel(Parcel parcel, int n2) {
        if (!this.eK()) {
            InvitationEntityCreator.a(this, parcel, n2);
            return;
        }
        this.Lt.writeToParcel(parcel, n2);
        parcel.writeString(this.IV);
        parcel.writeLong(this.Mu);
        parcel.writeInt(this.Mv);
        this.Mw.writeToParcel(parcel, n2);
        int n3 = this.Mx.size();
        parcel.writeInt(n3);
        int n4 = 0;
        while (n4 < n3) {
            this.Mx.get(n4).writeToParcel(parcel, n2);
            ++n4;
        }
    }

    static final class InvitationEntityCreatorCompat
    extends InvitationEntityCreator {
        InvitationEntityCreatorCompat() {
        }

        @Override
        public InvitationEntity au(Parcel parcel) {
            if (InvitationEntity.c(InvitationEntity.eJ()) || InvitationEntity.al(InvitationEntity.class.getCanonicalName())) {
                return super.au(parcel);
            }
            GameEntity gameEntity = (GameEntity)GameEntity.CREATOR.createFromParcel(parcel);
            String string2 = parcel.readString();
            long l2 = parcel.readLong();
            int n2 = parcel.readInt();
            ParticipantEntity participantEntity = (ParticipantEntity)ParticipantEntity.CREATOR.createFromParcel(parcel);
            int n3 = parcel.readInt();
            ArrayList<ParticipantEntity> arrayList = new ArrayList<ParticipantEntity>(n3);
            for (int i2 = 0; i2 < n3; ++i2) {
                arrayList.add((ParticipantEntity)ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new InvitationEntity(2, gameEntity, string2, l2, n2, participantEntity, arrayList, -1, 0);
        }

        @Override
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.au(parcel);
        }
    }

}

