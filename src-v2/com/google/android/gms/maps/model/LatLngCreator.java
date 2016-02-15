/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.LatLng;

public class LatLngCreator
implements Parcelable.Creator<LatLng> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LatLng latLng, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, latLng.getVersionCode());
        b.a(parcel, 2, latLng.latitude);
        b.a(parcel, 3, latLng.longitude);
        b.F(parcel, n2);
    }

    public LatLng createFromParcel(Parcel parcel) {
        double d2 = 0.0;
        int n2 = a.o(parcel);
        int n3 = 0;
        double d3 = 0.0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 2: {
                    d3 = a.l(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            d2 = a.l(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new LatLng(n3, d3, d2);
    }

    public LatLng[] newArray(int n2) {
        return new LatLng[n2];
    }
}

