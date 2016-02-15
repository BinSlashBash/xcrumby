package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class hp implements Creator<ho> {
    static void m1071a(ho hoVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, hoVar.getName(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, hoVar.xH);
        C0262b.m219a(parcel, 2, hoVar.ia(), i, false);
        C0262b.m222a(parcel, 3, hoVar.getAddress(), false);
        C0262b.m233b(parcel, 4, hoVar.ib(), false);
        C0262b.m222a(parcel, 5, hoVar.getPhoneNumber(), false);
        C0262b.m222a(parcel, 6, hoVar.ic(), false);
        C0262b.m211F(parcel, p);
    }

    public ho aH(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str2 = null;
        List list = null;
        String str3 = null;
        LatLng latLng = null;
        String str4 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    latLng = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    list = C0261a.m182c(parcel, n, hm.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str = C0261a.m195n(parcel, n);
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
            return new ho(i, str4, latLng, str3, list, str2, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ho[] bI(int i) {
        return new ho[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aH(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bI(x0);
    }
}
