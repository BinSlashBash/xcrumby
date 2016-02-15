package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.g */
public class C0459g {
    static void m1275a(PolygonOptions polygonOptions, Parcel parcel, int i) {
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
}
