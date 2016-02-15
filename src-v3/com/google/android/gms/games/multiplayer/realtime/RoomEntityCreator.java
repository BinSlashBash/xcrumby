package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;

public class RoomEntityCreator implements Creator<RoomEntity> {
    static void m578a(RoomEntity roomEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, roomEntity.getRoomId(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, roomEntity.getVersionCode());
        C0262b.m222a(parcel, 2, roomEntity.getCreatorId(), false);
        C0262b.m215a(parcel, 3, roomEntity.getCreationTimestamp());
        C0262b.m234c(parcel, 4, roomEntity.getStatus());
        C0262b.m222a(parcel, 5, roomEntity.getDescription(), false);
        C0262b.m234c(parcel, 6, roomEntity.getVariant());
        C0262b.m216a(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
        C0262b.m233b(parcel, 8, roomEntity.getParticipants(), false);
        C0262b.m234c(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
        C0262b.m211F(parcel, p);
    }

    public RoomEntity ax(Parcel parcel) {
        int i = 0;
        ArrayList arrayList = null;
        int o = C0261a.m196o(parcel);
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        String str2 = null;
        String str3 = null;
        int i4 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    arrayList = C0261a.m182c(parcel, n, ParticipantEntity.CREATOR);
                    break;
                case Std.STD_CHARSET /*9*/:
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
            return new RoomEntity(i4, str3, str2, j, i3, str, i2, bundle, arrayList, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public RoomEntity[] bq(int i) {
        return new RoomEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ax(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bq(x0);
    }
}
