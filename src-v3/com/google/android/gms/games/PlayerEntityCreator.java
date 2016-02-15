package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

public class PlayerEntityCreator implements Creator<PlayerEntity> {
    static void m362a(PlayerEntity playerEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, playerEntity.getPlayerId(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, playerEntity.getVersionCode());
        C0262b.m222a(parcel, 2, playerEntity.getDisplayName(), false);
        C0262b.m219a(parcel, 3, playerEntity.getIconImageUri(), i, false);
        C0262b.m219a(parcel, 4, playerEntity.getHiResImageUri(), i, false);
        C0262b.m215a(parcel, 5, playerEntity.getRetrievedTimestamp());
        C0262b.m234c(parcel, 6, playerEntity.gh());
        C0262b.m215a(parcel, 7, playerEntity.getLastPlayedWithTimestamp());
        C0262b.m222a(parcel, 8, playerEntity.getIconImageUrl(), false);
        C0262b.m222a(parcel, 9, playerEntity.getHiResImageUrl(), false);
        C0262b.m211F(parcel, p);
    }

    public PlayerEntity[] aT(int i) {
        return new PlayerEntity[i];
    }

    public PlayerEntity ao(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        Uri uri = null;
        Uri uri2 = null;
        long j = 0;
        int i2 = 0;
        long j2 = 0;
        String str3 = null;
        String str4 = null;
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
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str4 = C0261a.m195n(parcel, n);
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
            return new PlayerEntity(i, str, str2, uri, uri2, j, i2, j2, str3, str4);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ao(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aT(x0);
    }
}
