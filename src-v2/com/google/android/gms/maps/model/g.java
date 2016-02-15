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
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.List;

public class g {
    static void a(PolygonOptions polygonOptions, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, polygonOptions.getVersionCode());
        b.b(parcel, 2, polygonOptions.getPoints(), false);
        b.c(parcel, 3, polygonOptions.iF(), false);
        b.a(parcel, 4, polygonOptions.getStrokeWidth());
        b.c(parcel, 5, polygonOptions.getStrokeColor());
        b.c(parcel, 6, polygonOptions.getFillColor());
        b.a(parcel, 7, polygonOptions.getZIndex());
        b.a(parcel, 8, polygonOptions.isVisible());
        b.a(parcel, 9, polygonOptions.isGeodesic());
        b.F(parcel, n2);
    }
}

