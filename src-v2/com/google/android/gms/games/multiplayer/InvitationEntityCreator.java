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
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.InvitationEntity;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

public class InvitationEntityCreator
implements Parcelable.Creator<InvitationEntity> {
    static void a(InvitationEntity invitationEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, invitationEntity.getGame(), n2, false);
        b.c(parcel, 1000, invitationEntity.getVersionCode());
        b.a(parcel, 2, invitationEntity.getInvitationId(), false);
        b.a(parcel, 3, invitationEntity.getCreationTimestamp());
        b.c(parcel, 4, invitationEntity.getInvitationType());
        b.a(parcel, 5, invitationEntity.getInviter(), n2, false);
        b.b(parcel, 6, invitationEntity.getParticipants(), false);
        b.c(parcel, 7, invitationEntity.getVariant());
        b.c(parcel, 8, invitationEntity.getAvailableAutoMatchSlots());
        b.F(parcel, n3);
    }

    public InvitationEntity au(Parcel parcel) {
        ArrayList<ParticipantEntity> arrayList = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        long l2 = 0;
        int n4 = 0;
        ParticipantEntity participantEntity = null;
        int n5 = 0;
        String string2 = null;
        GameEntity gameEntity = null;
        int n6 = 0;
        block11 : while (parcel.dataPosition() < n3) {
            int n7 = a.n(parcel);
            switch (a.R(n7)) {
                default: {
                    a.b(parcel, n7);
                    continue block11;
                }
                case 1: {
                    gameEntity = a.a(parcel, n7, GameEntity.CREATOR);
                    continue block11;
                }
                case 1000: {
                    n6 = a.g(parcel, n7);
                    continue block11;
                }
                case 2: {
                    string2 = a.n(parcel, n7);
                    continue block11;
                }
                case 3: {
                    l2 = a.i(parcel, n7);
                    continue block11;
                }
                case 4: {
                    n5 = a.g(parcel, n7);
                    continue block11;
                }
                case 5: {
                    participantEntity = a.a(parcel, n7, ParticipantEntity.CREATOR);
                    continue block11;
                }
                case 6: {
                    arrayList = a.c(parcel, n7, ParticipantEntity.CREATOR);
                    continue block11;
                }
                case 7: {
                    n4 = a.g(parcel, n7);
                    continue block11;
                }
                case 8: 
            }
            n2 = a.g(parcel, n7);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new InvitationEntity(n6, gameEntity, string2, l2, n5, participantEntity, arrayList, n4, n2);
    }

    public InvitationEntity[] bn(int n2) {
        return new InvitationEntity[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.au(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bn(n2);
    }
}

