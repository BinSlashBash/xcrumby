package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.i */
public class C0461i {
    static void m1277a(Tile tile, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, tile.getVersionCode());
        C0262b.m234c(parcel, 2, tile.width);
        C0262b.m234c(parcel, 3, tile.height);
        C0262b.m226a(parcel, 4, tile.data, false);
        C0262b.m211F(parcel, p);
    }
}
