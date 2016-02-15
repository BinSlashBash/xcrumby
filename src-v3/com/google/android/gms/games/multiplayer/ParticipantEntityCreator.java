package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.location.GeofenceStatusCodes;

public class ParticipantEntityCreator implements Creator<ParticipantEntity> {
    static void m576a(ParticipantEntity participantEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, participantEntity.getParticipantId(), false);
        C0262b.m222a(parcel, 2, participantEntity.getDisplayName(), false);
        C0262b.m219a(parcel, 3, participantEntity.getIconImageUri(), i, false);
        C0262b.m219a(parcel, 4, participantEntity.getHiResImageUri(), i, false);
        C0262b.m234c(parcel, 5, participantEntity.getStatus());
        C0262b.m222a(parcel, 6, participantEntity.gi(), false);
        C0262b.m225a(parcel, 7, participantEntity.isConnectedToRoom());
        C0262b.m219a(parcel, 8, participantEntity.getPlayer(), i, false);
        C0262b.m234c(parcel, 9, participantEntity.getCapabilities());
        C0262b.m219a(parcel, 10, participantEntity.getResult(), i, false);
        C0262b.m222a(parcel, 11, participantEntity.getIconImageUrl(), false);
        C0262b.m222a(parcel, 12, participantEntity.getHiResImageUrl(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, participantEntity.getVersionCode());
        C0262b.m211F(parcel, p);
    }

    public ParticipantEntity av(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        Uri uri = null;
        Uri uri2 = null;
        int i2 = 0;
        String str3 = null;
        boolean z = false;
        PlayerEntity playerEntity = null;
        int i3 = 0;
        ParticipantResult participantResult = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    uri = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    uri2 = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    playerEntity = (PlayerEntity) C0261a.m176a(parcel, n, PlayerEntity.CREATOR);
                    break;
                case Std.STD_CHARSET /*9*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    participantResult = (ParticipantResult) C0261a.m176a(parcel, n, ParticipantResult.CREATOR);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ParticipantEntity(i, str, str2, uri, uri2, i2, str3, z, playerEntity, i3, participantResult, str4, str5);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ParticipantEntity[] bo(int i) {
        return new ParticipantEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return av(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bo(x0);
    }
}
