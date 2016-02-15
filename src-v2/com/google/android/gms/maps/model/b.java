/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class b {
    static void a(CircleOptions circleOptions, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, circleOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, circleOptions.getCenter(), n2, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, circleOptions.getRadius());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, circleOptions.getStrokeWidth());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, circleOptions.getStrokeColor());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, circleOptions.getFillColor());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, circleOptions.getZIndex());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, circleOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n3);
    }
}

