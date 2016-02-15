package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class LatLngBoundsCreator implements Creator<LatLngBounds> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1257a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, latLngBounds.getVersionCode());
        C0262b.m219a(parcel, 2, latLngBounds.southwest, i, false);
        C0262b.m219a(parcel, 3, latLngBounds.northeast, i, false);
        C0262b.m211F(parcel, p);
    }

    public LatLngBounds createFromParcel(Parcel parcel) {
        LatLng latLng = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        LatLng latLng2 = null;
        while (parcel.dataPosition() < o) {
            int g;
            LatLng latLng3;
            int n = C0261a.m194n(parcel);
            LatLng latLng4;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    latLng4 = latLng;
                    latLng = latLng2;
                    g = C0261a.m187g(parcel, n);
                    latLng3 = latLng4;
                    break;
                case Std.STD_URL /*2*/:
                    g = i;
                    latLng4 = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    latLng3 = latLng;
                    latLng = latLng4;
                    break;
                case Std.STD_URI /*3*/:
                    latLng3 = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    latLng = latLng2;
                    g = i;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    latLng3 = latLng;
                    latLng = latLng2;
                    g = i;
                    break;
            }
            i = g;
            latLng2 = latLng;
            latLng = latLng3;
        }
        if (parcel.dataPosition() == o) {
            return new LatLngBounds(i, latLng2, latLng);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public LatLngBounds[] newArray(int size) {
        return new LatLngBounds[size];
    }
}
