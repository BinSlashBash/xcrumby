/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;

public class a {
    static void a(GoogleMapOptions googleMapOptions, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, googleMapOptions.getVersionCode());
        b.a(parcel, 2, googleMapOptions.ig());
        b.a(parcel, 3, googleMapOptions.ih());
        b.c(parcel, 4, googleMapOptions.getMapType());
        b.a(parcel, 5, googleMapOptions.getCamera(), n2, false);
        b.a(parcel, 6, googleMapOptions.ii());
        b.a(parcel, 7, googleMapOptions.ij());
        b.a(parcel, 8, googleMapOptions.ik());
        b.a(parcel, 9, googleMapOptions.il());
        b.a(parcel, 10, googleMapOptions.im());
        b.a(parcel, 11, googleMapOptions.in());
        b.F(parcel, n3);
    }
}

