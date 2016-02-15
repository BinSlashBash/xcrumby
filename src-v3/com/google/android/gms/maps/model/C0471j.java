package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.j */
public class C0471j {
    static void m1290a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, tileOverlayOptions.getVersionCode());
        C0262b.m217a(parcel, 2, tileOverlayOptions.iG(), false);
        C0262b.m225a(parcel, 3, tileOverlayOptions.isVisible());
        C0262b.m214a(parcel, 4, tileOverlayOptions.getZIndex());
        C0262b.m211F(parcel, p);
    }
}
