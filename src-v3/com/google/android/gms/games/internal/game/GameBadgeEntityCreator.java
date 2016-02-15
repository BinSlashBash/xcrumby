package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

public class GameBadgeEntityCreator implements Creator<GameBadgeEntity> {
    static void m569a(GameBadgeEntity gameBadgeEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, gameBadgeEntity.getType());
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, gameBadgeEntity.getVersionCode());
        C0262b.m222a(parcel, 2, gameBadgeEntity.getTitle(), false);
        C0262b.m222a(parcel, 3, gameBadgeEntity.getDescription(), false);
        C0262b.m219a(parcel, 4, gameBadgeEntity.getIconImageUri(), i, false);
        C0262b.m211F(parcel, p);
    }

    public GameBadgeEntity ar(Parcel parcel) {
        int i = 0;
        Uri uri = null;
        int o = C0261a.m196o(parcel);
        String str = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    uri = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new GameBadgeEntity(i2, i, str2, str, uri);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public GameBadgeEntity[] bg(int i) {
        return new GameBadgeEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ar(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bg(x0);
    }
}
