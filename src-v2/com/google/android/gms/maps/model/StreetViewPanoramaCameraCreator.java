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
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class StreetViewPanoramaCameraCreator
implements Parcelable.Creator<StreetViewPanoramaCamera> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaCamera streetViewPanoramaCamera, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, streetViewPanoramaCamera.getVersionCode());
        b.a(parcel, 2, streetViewPanoramaCamera.zoom);
        b.a(parcel, 3, streetViewPanoramaCamera.tilt);
        b.a(parcel, 4, streetViewPanoramaCamera.bearing);
        b.F(parcel, n2);
    }

    public StreetViewPanoramaCamera createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        int n2 = a.o(parcel);
        float f3 = 0.0f;
        int n3 = 0;
        float f4 = 0.0f;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    f3 = a.k(parcel, n4);
                    continue block6;
                }
                case 3: {
                    f4 = a.k(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            f2 = a.k(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new StreetViewPanoramaCamera(n3, f3, f4, f2);
    }

    public StreetViewPanoramaCamera[] newArray(int n2) {
        return new StreetViewPanoramaCamera[n2];
    }
}

