/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.TileOverlayOptions;

public class j {
    static void a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, tileOverlayOptions.getVersionCode());
        b.a(parcel, 2, tileOverlayOptions.iG(), false);
        b.a(parcel, 3, tileOverlayOptions.isVisible());
        b.a(parcel, 4, tileOverlayOptions.getZIndex());
        b.F(parcel, n2);
    }
}

