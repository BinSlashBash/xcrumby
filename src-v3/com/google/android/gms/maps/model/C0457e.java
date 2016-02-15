package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.e */
public class C0457e {
    static void m1273a(LatLng latLng, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, latLng.getVersionCode());
        C0262b.m213a(parcel, 2, latLng.latitude);
        C0262b.m213a(parcel, 3, latLng.longitude);
        C0262b.m211F(parcel, p);
    }
}
