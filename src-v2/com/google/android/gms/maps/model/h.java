/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.List;

public class h {
    static void a(PolylineOptions polylineOptions, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, polylineOptions.getVersionCode());
        b.b(parcel, 2, polylineOptions.getPoints(), false);
        b.a(parcel, 3, polylineOptions.getWidth());
        b.c(parcel, 4, polylineOptions.getColor());
        b.a(parcel, 5, polylineOptions.getZIndex());
        b.a(parcel, 6, polylineOptions.isVisible());
        b.a(parcel, 7, polylineOptions.isGeodesic());
        b.F(parcel, n2);
    }
}

