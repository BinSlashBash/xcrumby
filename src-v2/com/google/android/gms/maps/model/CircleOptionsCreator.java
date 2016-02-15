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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;

public class CircleOptionsCreator
implements Parcelable.Creator<CircleOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(CircleOptions circleOptions, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, circleOptions.getVersionCode());
        b.a(parcel, 2, circleOptions.getCenter(), n2, false);
        b.a(parcel, 3, circleOptions.getRadius());
        b.a(parcel, 4, circleOptions.getStrokeWidth());
        b.c(parcel, 5, circleOptions.getStrokeColor());
        b.c(parcel, 6, circleOptions.getFillColor());
        b.a(parcel, 7, circleOptions.getZIndex());
        b.a(parcel, 8, circleOptions.isVisible());
        b.F(parcel, n3);
    }

    public CircleOptions createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        boolean bl2 = false;
        int n2 = a.o(parcel);
        LatLng latLng = null;
        double d2 = 0.0;
        int n3 = 0;
        int n4 = 0;
        float f3 = 0.0f;
        int n5 = 0;
        block10 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block10;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block10;
                }
                case 2: {
                    latLng = (LatLng)a.a(parcel, n6, LatLng.CREATOR);
                    continue block10;
                }
                case 3: {
                    d2 = a.l(parcel, n6);
                    continue block10;
                }
                case 4: {
                    f3 = a.k(parcel, n6);
                    continue block10;
                }
                case 5: {
                    n4 = a.g(parcel, n6);
                    continue block10;
                }
                case 6: {
                    n3 = a.g(parcel, n6);
                    continue block10;
                }
                case 7: {
                    f2 = a.k(parcel, n6);
                    continue block10;
                }
                case 8: 
            }
            bl2 = a.c(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CircleOptions(n5, latLng, d2, f3, n4, n3, f2, bl2);
    }

    public CircleOptions[] newArray(int n2) {
        return new CircleOptions[n2];
    }
}

