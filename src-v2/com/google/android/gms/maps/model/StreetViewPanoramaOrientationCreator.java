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
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public class StreetViewPanoramaOrientationCreator
implements Parcelable.Creator<StreetViewPanoramaOrientation> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaOrientation streetViewPanoramaOrientation, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, streetViewPanoramaOrientation.getVersionCode());
        b.a(parcel, 2, streetViewPanoramaOrientation.tilt);
        b.a(parcel, 3, streetViewPanoramaOrientation.bearing);
        b.F(parcel, n2);
    }

    public StreetViewPanoramaOrientation createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        int n2 = a.o(parcel);
        int n3 = 0;
        float f3 = 0.0f;
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
                    f3 = a.k(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            f2 = a.k(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new StreetViewPanoramaOrientation(n3, f3, f2);
    }

    public StreetViewPanoramaOrientation[] newArray(int n2) {
        return new StreetViewPanoramaOrientation[n2];
    }
}

