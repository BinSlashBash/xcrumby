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
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

public class PolylineOptionsCreator
implements Parcelable.Creator<PolylineOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

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

    public PolylineOptions createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        boolean bl2 = false;
        int n2 = a.o(parcel);
        ArrayList arrayList = null;
        boolean bl3 = false;
        int n3 = 0;
        float f3 = 0.0f;
        int n4 = 0;
        block9 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block9;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block9;
                }
                case 2: {
                    arrayList = a.c(parcel, n5, LatLng.CREATOR);
                    continue block9;
                }
                case 3: {
                    f3 = a.k(parcel, n5);
                    continue block9;
                }
                case 4: {
                    n3 = a.g(parcel, n5);
                    continue block9;
                }
                case 5: {
                    f2 = a.k(parcel, n5);
                    continue block9;
                }
                case 6: {
                    bl3 = a.c(parcel, n5);
                    continue block9;
                }
                case 7: 
            }
            bl2 = a.c(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new PolylineOptions(n4, arrayList, f3, n3, f2, bl3, bl2);
    }

    public PolylineOptions[] newArray(int n2) {
        return new PolylineOptions[n2];
    }
}

