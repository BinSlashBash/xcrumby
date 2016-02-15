package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.k */
public class C0472k {
    static void m1291a(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, visibleRegion.getVersionCode());
        C0262b.m219a(parcel, 2, visibleRegion.nearLeft, i, false);
        C0262b.m219a(parcel, 3, visibleRegion.nearRight, i, false);
        C0262b.m219a(parcel, 4, visibleRegion.farLeft, i, false);
        C0262b.m219a(parcel, 5, visibleRegion.farRight, i, false);
        C0262b.m219a(parcel, 6, visibleRegion.latLngBounds, i, false);
        C0262b.m211F(parcel, p);
    }
}
