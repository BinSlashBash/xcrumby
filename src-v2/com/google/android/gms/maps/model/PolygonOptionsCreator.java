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
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;
import java.util.List;

public class PolygonOptionsCreator
implements Parcelable.Creator<PolygonOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

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

    public PolygonOptions createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        boolean bl2 = false;
        int n2 = a.o(parcel);
        ArrayList<LatLng> arrayList = null;
        ArrayList arrayList2 = new ArrayList();
        boolean bl3 = false;
        int n3 = 0;
        int n4 = 0;
        float f3 = 0.0f;
        int n5 = 0;
        block11 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block11;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block11;
                }
                case 2: {
                    arrayList = a.c(parcel, n6, LatLng.CREATOR);
                    continue block11;
                }
                case 3: {
                    a.a(parcel, n6, arrayList2, this.getClass().getClassLoader());
                    continue block11;
                }
                case 4: {
                    f3 = a.k(parcel, n6);
                    continue block11;
                }
                case 5: {
                    n4 = a.g(parcel, n6);
                    continue block11;
                }
                case 6: {
                    n3 = a.g(parcel, n6);
                    continue block11;
                }
                case 7: {
                    f2 = a.k(parcel, n6);
                    continue block11;
                }
                case 8: {
                    bl3 = a.c(parcel, n6);
                    continue block11;
                }
                case 9: 
            }
            bl2 = a.c(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new PolygonOptions(n5, arrayList, arrayList2, f3, n4, n3, f2, bl3, bl2);
    }

    public PolygonOptions[] newArray(int n2) {
        return new PolygonOptions[n2];
    }
}

