package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class StreetViewPanoramaCameraCreator implements Creator<StreetViewPanoramaCamera> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1262a(StreetViewPanoramaCamera streetViewPanoramaCamera, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, streetViewPanoramaCamera.getVersionCode());
        C0262b.m214a(parcel, 2, streetViewPanoramaCamera.zoom);
        C0262b.m214a(parcel, 3, streetViewPanoramaCamera.tilt);
        C0262b.m214a(parcel, 4, streetViewPanoramaCamera.bearing);
        C0262b.m211F(parcel, p);
    }

    public StreetViewPanoramaCamera createFromParcel(Parcel parcel) {
        float f = 0.0f;
        int o = C0261a.m196o(parcel);
        float f2 = 0.0f;
        int i = 0;
        float f3 = 0.0f;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    f2 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    f3 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new StreetViewPanoramaCamera(i, f2, f3, f);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public StreetViewPanoramaCamera[] newArray(int size) {
        return new StreetViewPanoramaCamera[size];
    }
}
