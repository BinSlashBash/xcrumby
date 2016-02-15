/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationEntity;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantRef;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;

public final class InvitationRef
extends b
implements Invitation {
    private final Game LS;
    private final ParticipantRef MA;
    private final ArrayList<Participant> Mx;

    InvitationRef(DataHolder parcelable, int n2, int n3) {
        super((DataHolder)parcelable, n2);
        this.LS = new GameRef((DataHolder)parcelable, n2);
        this.Mx = new ArrayList(n3);
        String string2 = this.getString("external_inviter_id");
        parcelable = null;
        for (n2 = 0; n2 < n3; ++n2) {
            ParticipantRef participantRef = new ParticipantRef(this.BB, this.BD + n2);
            if (participantRef.getParticipantId().equals(string2)) {
                parcelable = participantRef;
            }
            this.Mx.add(participantRef);
        }
        this.MA = (ParticipantRef)((Object)fq.b(parcelable, (Object)"Must have a valid inviter!"));
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return InvitationEntity.a(this, object);
    }

    @Override
    public Invitation freeze() {
        return new InvitationEntity(this);
    }

    @Override
    public int getAvailableAutoMatchSlots() {
        if (!this.getBoolean("has_automatch_criteria")) {
            return 0;
        }
        return this.getInteger("automatch_max_players");
    }

    @Override
    public long getCreationTimestamp() {
        return Math.max(this.getLong("creation_timestamp"), this.getLong("last_modified_timestamp"));
    }

    @Override
    public Game getGame() {
        return this.LS;
    }

    @Override
    public String getInvitationId() {
        return this.getString("external_invitation_id");
    }

    @Override
    public int getInvitationType() {
        return this.getInteger("type");
    }

    @Override
    public Participant getInviter() {
        return this.MA;
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        return this.Mx;
    }

    @Override
    public int getVariant() {
        return this.getInteger("variant");
    }

    @Override
    public int hashCode() {
        return InvitationEntity.a(this);
    }

    public String toString() {
        return InvitationEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((InvitationEntity)this.freeze()).writeToParcel(parcel, n2);
    }
}

