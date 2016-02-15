package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.b */
public class C0454b {
    static void m1270a(CircleOptions circleOptions, Parcel parcel, int i) {
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
}
