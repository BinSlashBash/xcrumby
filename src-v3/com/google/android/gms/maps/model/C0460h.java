package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.h */
public class C0460h {
    static void m1276a(PolylineOptions polylineOptions, Parcel parcel, int i) {
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
}
