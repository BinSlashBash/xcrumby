package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class CircleOptionsCreator implements Creator<CircleOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1254a(CircleOptions circleOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, circleOptions.getVersionCode());
        C0262b.m219a(parcel, 2, circleOptions.getCenter(), i, false);
        C0262b.m213a(parcel, 3, circleOptions.getRadius());
        C0262b.m214a(parcel, 4, circleOptions.getStrokeWidth());
        C0262b.m234c(parcel, 5, circleOptions.getStrokeColor());
        C0262b.m234c(parcel, 6, circleOptions.getFillColor());
        C0262b.m214a(parcel, 7, circleOptions.getZIndex());
        C0262b.m225a(parcel, 8, circleOptions.isVisible());
        C0262b.m211F(parcel, p);
    }

    public CircleOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int o = C0261a.m196o(parcel);
        LatLng latLng = null;
        double d = 0.0d;
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    latLng = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    d = C0261a.m192l(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    f2 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new CircleOptions(i3, latLng, d, f2, i2, i, f, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public CircleOptions[] newArray(int size) {
        return new CircleOptions[size];
    }
}
