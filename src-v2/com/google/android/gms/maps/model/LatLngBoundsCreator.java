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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngCreator;

public class LatLngBoundsCreator
implements Parcelable.Creator<LatLngBounds> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LatLngBounds latLngBounds, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, latLngBounds.getVersionCode());
        b.a(parcel, 2, latLngBounds.southwest, n2, false);
        b.a(parcel, 3, latLngBounds.northeast, n2, false);
        b.F(parcel, n3);
    }

    public LatLngBounds createFromParcel(Parcel parcel) {
        LatLng latLng = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        LatLng latLng2 = null;
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
                    latLng2 = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            latLng = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new LatLngBounds(n3, latLng2, latLng);
    }

    public LatLngBounds[] newArray(int n2) {
        return new LatLngBounds[n2];
    }
}

