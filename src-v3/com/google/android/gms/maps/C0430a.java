package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.a */
public class C0430a {
    static void m1226a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, googleMapOptions.getVersionCode());
        C0262b.m212a(parcel, 2, googleMapOptions.ig());
        C0262b.m212a(parcel, 3, googleMapOptions.ih());
        C0262b.m234c(parcel, 4, googleMapOptions.getMapType());
        C0262b.m219a(parcel, 5, googleMapOptions.getCamera(), i, false);
        C0262b.m212a(parcel, 6, googleMapOptions.ii());
        C0262b.m212a(parcel, 7, googleMapOptions.ij());
        C0262b.m212a(parcel, 8, googleMapOptions.ik());
        C0262b.m212a(parcel, 9, googleMapOptions.il());
        C0262b.m212a(parcel, 10, googleMapOptions.im());
        C0262b.m212a(parcel, 11, googleMapOptions.in());
        C0262b.m211F(parcel, p);
    }
}
