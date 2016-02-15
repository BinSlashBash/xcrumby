package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;

public final class InvitationEntity extends GamesDowngradeableSafeParcel implements Invitation {
    public static final Creator<InvitationEntity> CREATOR;
    private final String IV;
    private final GameEntity Lt;
    private final long Mu;
    private final int Mv;
    private final ParticipantEntity Mw;
    private final ArrayList<ParticipantEntity> Mx;
    private final int My;
    private final int Mz;
    private final int xH;

    static final class InvitationEntityCreatorCompat extends InvitationEntityCreator {
        InvitationEntityCreatorCompat() {
        }

        public InvitationEntity au(Parcel parcel) {
            if (GamesDowngradeableSafeParcel.m2875c(fe.eJ()) || fe.al(InvitationEntity.class.getCanonicalName())) {
                return super.au(parcel);
            }
            GameEntity gameEntity = (GameEntity) GameEntity.CREATOR.createFromParcel(parcel);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            ParticipantEntity participantEntity = (ParticipantEntity) ParticipantEntity.CREATOR.createFromParcel(parcel);
            int readInt2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt2);
            for (int i = 0; i < readInt2; i++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new InvitationEntity(2, gameEntity, readString, readLong, readInt, participantEntity, arrayList, -1, 0);
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return au(x0);
        }
    }

    static {
        CREATOR = new InvitationEntityCreatorCompat();
    }

    InvitationEntity(int versionCode, GameEntity game, String invitationId, long creationTimestamp, int invitationType, ParticipantEntity inviter, ArrayList<ParticipantEntity> participants, int variant, int availableAutoMatchSlots) {
        this.xH = versionCode;
        this.Lt = game;
        this.IV = invitationId;
        this.Mu = creationTimestamp;
        this.Mv = invitationType;
        this.Mw = inviter;
        this.Mx = participants;
        this.My = variant;
        this.Mz = availableAutoMatchSlots;
    }

    InvitationEntity(Invitation invitation) {
        this.xH = 2;
        this.Lt = new GameEntity(invitation.getGame());
        this.IV = invitation.getInvitationId();
        this.Mu = invitation.getCreationTimestamp();
        this.Mv = invitation.getInvitationType();
        this.My = invitation.getVariant();
        this.Mz = invitation.getAvailableAutoMatchSlots();
        String participantId = invitation.getInviter().getParticipantId();
        Object obj = null;
        ArrayList participants = invitation.getParticipants();
        int size = participants.size();
        this.Mx = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            if (participant.getParticipantId().equals(participantId)) {
                obj = participant;
            }
            this.Mx.add((ParticipantEntity) participant.freeze());
        }
        fq.m982b(obj, (Object) "Must have a valid inviter!");
        this.Mw = (ParticipantEntity) obj.freeze();
    }

    static int m3397a(Invitation invitation) {
        return fo.hashCode(invitation.getGame(), invitation.getInvitationId(), Long.valueOf(invitation.getCreationTimestamp()), Integer.valueOf(invitation.getInvitationType()), invitation.getInviter(), invitation.getParticipants(), Integer.valueOf(invitation.getVariant()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static boolean m3398a(Invitation invitation, Object obj) {
        if (!(obj instanceof Invitation)) {
            return false;
        }
        if (invitation == obj) {
            return true;
        }
        Invitation invitation2 = (Invitation) obj;
        return fo.equal(invitation2.getGame(), invitation.getGame()) && fo.equal(invitation2.getInvitationId(), invitation.getInvitationId()) && fo.equal(Long.valueOf(invitation2.getCreationTimestamp()), Long.valueOf(invitation.getCreationTimestamp())) && fo.equal(Integer.valueOf(invitation2.getInvitationType()), Integer.valueOf(invitation.getInvitationType())) && fo.equal(invitation2.getInviter(), invitation.getInviter()) && fo.equal(invitation2.getParticipants(), invitation.getParticipants()) && fo.equal(Integer.valueOf(invitation2.getVariant()), Integer.valueOf(invitation.getVariant())) && fo.equal(Integer.valueOf(invitation2.getAvailableAutoMatchSlots()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static String m3399b(Invitation invitation) {
        return fo.m976e(invitation).m975a("Game", invitation.getGame()).m975a("InvitationId", invitation.getInvitationId()).m975a("CreationTimestamp", Long.valueOf(invitation.getCreationTimestamp())).m975a("InvitationType", Integer.valueOf(invitation.getInvitationType())).m975a("Inviter", invitation.getInviter()).m975a("Participants", invitation.getParticipants()).m975a("Variant", Integer.valueOf(invitation.getVariant())).m975a("AvailableAutoMatchSlots", Integer.valueOf(invitation.getAvailableAutoMatchSlots())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m3398a(this, obj);
    }

    public Invitation freeze() {
        return this;
    }

    public int getAvailableAutoMatchSlots() {
        return this.Mz;
    }

    public long getCreationTimestamp() {
        return this.Mu;
    }

    public Game getGame() {
        return this.Lt;
    }

    public String getInvitationId() {
        return this.IV;
    }

    public int getInvitationType() {
        return this.Mv;
    }

    public Participant getInviter() {
        return this.Mw;
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList(this.Mx);
    }

    public int getVariant() {
        return this.My;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return m3397a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m3399b((Invitation) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (eK()) {
            this.Lt.writeToParcel(dest, flags);
            dest.writeString(this.IV);
            dest.writeLong(this.Mu);
            dest.writeInt(this.Mv);
            this.Mw.writeToParcel(dest, flags);
            int size = this.Mx.size();
            dest.writeInt(size);
            for (int i = 0; i < size; i++) {
                ((ParticipantEntity) this.Mx.get(i)).writeToParcel(dest, flags);
            }
            return;
        }
        InvitationEntityCreator.m575a(this, dest, flags);
    }
}
