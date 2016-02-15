package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.List;

public class PolylineOptionsCreator implements Creator<PolylineOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1261a(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, polylineOptions.getVersionCode());
        C0262b.m233b(parcel, 2, polylineOptions.getPoints(), false);
        C0262b.m214a(parcel, 3, polylineOptions.getWidth());
        C0262b.m234c(parcel, 4, polylineOptions.getColor());
        C0262b.m214a(parcel, 5, polylineOptions.getZIndex());
        C0262b.m225a(parcel, 6, polylineOptions.isVisible());
        C0262b.m225a(parcel, 7, polylineOptions.isGeodesic());
        C0262b.m211F(parcel, p);
    }

    public PolylineOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int o = C0261a.m196o(parcel);
        List list = null;
        boolean z2 = false;
        int i = 0;
        float f2 = 0.0f;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    list = C0261a.m182c(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    f2 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new PolylineOptions(i2, list, f2, i, f, z2, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public PolylineOptions[] newArray(int size) {
        return new PolylineOptions[size];
    }
}
