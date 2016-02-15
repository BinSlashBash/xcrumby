package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.LocationRequest;

public class hl implements Creator<hk> {
    static void m1069a(hk hkVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, hkVar.xH);
        C0262b.m219a(parcel, 2, hkVar.hZ(), i, false);
        C0262b.m215a(parcel, 3, hkVar.getInterval());
        C0262b.m234c(parcel, 4, hkVar.getPriority());
        C0262b.m211F(parcel, p);
    }

    public hk aF(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        hg hgVar = null;
        long j = hk.OF;
        int i2 = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_URL /*2*/:
                    hgVar = (hg) C0261a.m176a(parcel, n, hg.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i2 = C0261a.m187g(parcel, n);
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
            return new hk(i, hgVar, j, i2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public hk[] bG(int i) {
        return new hk[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aF(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bG(x0);
    }
}
