package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.List;

public class hh implements Creator<hg> {
    static void m1067a(hg hgVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m233b(parcel, 1, hgVar.OA, false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, hgVar.xH);
        C0262b.m222a(parcel, 2, hgVar.hW(), false);
        C0262b.m225a(parcel, 3, hgVar.hX());
        C0262b.m211F(parcel, p);
    }

    public hg aD(Parcel parcel) {
        String str = null;
        boolean z = false;
        int o = C0261a.m196o(parcel);
        List list = null;
        int i = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    list = C0261a.m182c(parcel, n, hm.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    z = C0261a.m183c(parcel, n);
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
            return new hg(i, list, str, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public hg[] bE(int i) {
        return new hg[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aD(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bE(x0);
    }
}
