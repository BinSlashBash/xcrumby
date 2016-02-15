package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;

public class ExtendedGameEntityCreator implements Creator<ExtendedGameEntity> {
    static void m568a(ExtendedGameEntity extendedGameEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m219a(parcel, 1, extendedGameEntity.hf(), i, false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, extendedGameEntity.getVersionCode());
        C0262b.m234c(parcel, 2, extendedGameEntity.gX());
        C0262b.m225a(parcel, 3, extendedGameEntity.gY());
        C0262b.m234c(parcel, 4, extendedGameEntity.gZ());
        C0262b.m215a(parcel, 5, extendedGameEntity.ha());
        C0262b.m215a(parcel, 6, extendedGameEntity.hb());
        C0262b.m222a(parcel, 7, extendedGameEntity.hc(), false);
        C0262b.m215a(parcel, 8, extendedGameEntity.hd());
        C0262b.m222a(parcel, 9, extendedGameEntity.he(), false);
        C0262b.m233b(parcel, 10, extendedGameEntity.gW(), false);
        C0262b.m211F(parcel, p);
    }

    public ExtendedGameEntity aq(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        GameEntity gameEntity = null;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        long j = 0;
        long j2 = 0;
        String str = null;
        long j3 = 0;
        String str2 = null;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    gameEntity = (GameEntity) C0261a.m176a(parcel, n, GameEntity.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    j3 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    arrayList = C0261a.m182c(parcel, n, GameBadgeEntity.CREATOR);
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
            return new ExtendedGameEntity(i, gameEntity, i2, z, i3, j, j2, str, j3, str2, arrayList);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ExtendedGameEntity[] be(int i) {
        return new ExtendedGameEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aq(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return be(x0);
    }
}
