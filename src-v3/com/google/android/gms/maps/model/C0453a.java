package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.a */
public class C0453a {
    static void m1269a(CameraPosition cameraPosition, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, cameraPosition.getVersionCode());
        C0262b.m219a(parcel, 2, cameraPosition.target, i, false);
        C0262b.m214a(parcel, 3, cameraPosition.zoom);
        C0262b.m214a(parcel, 4, cameraPosition.tilt);
        C0262b.m214a(parcel, 5, cameraPosition.bearing);
        C0262b.m211F(parcel, p);
    }
}
