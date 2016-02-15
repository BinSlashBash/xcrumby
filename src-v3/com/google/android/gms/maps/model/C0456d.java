package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.d */
public class C0456d {
    static void m1272a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, latLngBounds.getVersionCode());
        C0262b.m219a(parcel, 2, latLngBounds.southwest, i, false);
        C0262b.m219a(parcel, 3, latLngBounds.northeast, i, false);
        C0262b.m211F(parcel, p);
    }
}
