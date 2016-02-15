package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;

public class InvitationEntityCreator implements Creator<InvitationEntity> {
    static void m575a(InvitationEntity invitationEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m219a(parcel, 1, invitationEntity.getGame(), i, false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, invitationEntity.getVersionCode());
        C0262b.m222a(parcel, 2, invitationEntity.getInvitationId(), false);
        C0262b.m215a(parcel, 3, invitationEntity.getCreationTimestamp());
        C0262b.m234c(parcel, 4, invitationEntity.getInvitationType());
        C0262b.m219a(parcel, 5, invitationEntity.getInviter(), i, false);
        C0262b.m233b(parcel, 6, invitationEntity.getParticipants(), false);
        C0262b.m234c(parcel, 7, invitationEntity.getVariant());
        C0262b.m234c(parcel, 8, invitationEntity.getAvailableAutoMatchSlots());
        C0262b.m211F(parcel, p);
    }

    public InvitationEntity au(Parcel parcel) {
        ArrayList arrayList = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        long j = 0;
        int i2 = 0;
        ParticipantEntity participantEntity = null;
        int i3 = 0;
        String str = null;
        GameEntity gameEntity = null;
        int i4 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    gameEntity = (GameEntity) C0261a.m176a(parcel, n, GameEntity.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    participantEntity = (ParticipantEntity) C0261a.m176a(parcel, n, ParticipantEntity.CREATOR);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    arrayList = C0261a.m182c(parcel, n, ParticipantEntity.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new InvitationEntity(i4, gameEntity, str, j, i3, participantEntity, arrayList, i2, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public InvitationEntity[] bn(int i) {
        return new InvitationEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return au(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bn(x0);
    }
}
