package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.ArrayList;
import java.util.List;

public class PolygonOptionsCreator implements Creator<PolygonOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1260a(PolygonOptions polygonOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, polygonOptions.getVersionCode());
        C0262b.m233b(parcel, 2, polygonOptions.getPoints(), false);
        C0262b.m235c(parcel, 3, polygonOptions.iF(), false);
        C0262b.m214a(parcel, 4, polygonOptions.getStrokeWidth());
        C0262b.m234c(parcel, 5, polygonOptions.getStrokeColor());
        C0262b.m234c(parcel, 6, polygonOptions.getFillColor());
        C0262b.m214a(parcel, 7, polygonOptions.getZIndex());
        C0262b.m225a(parcel, 8, polygonOptions.isVisible());
        C0262b.m225a(parcel, 9, polygonOptions.isGeodesic());
        C0262b.m211F(parcel, p);
    }

    public PolygonOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int o = C0261a.m196o(parcel);
        List list = null;
        List arrayList = new ArrayList();
        boolean z2 = false;
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
                    list = C0261a.m182c(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    C0261a.m179a(parcel, n, arrayList, getClass().getClassLoader());
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
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new PolygonOptions(i3, list, arrayList, f2, i2, i, f, z2, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public PolygonOptions[] newArray(int size) {
        return new PolygonOptions[size];
    }
}
