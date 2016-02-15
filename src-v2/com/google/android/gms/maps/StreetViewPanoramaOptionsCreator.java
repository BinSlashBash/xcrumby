/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaCameraCreator;

public class StreetViewPanoramaOptionsCreator
implements Parcelable.Creator<StreetViewPanoramaOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaOptions streetViewPanoramaOptions, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, streetViewPanoramaOptions.getVersionCode());
        b.a(parcel, 2, streetViewPanoramaOptions.getStreetViewPanoramaCamera(), n2, false);
        b.a(parcel, 3, streetViewPanoramaOptions.getPanoramaId(), false);
        b.a(parcel, 4, streetViewPanoramaOptions.getPosition(), n2, false);
        b.a(parcel, 5, streetViewPanoramaOptions.getRadius(), false);
        b.a(parcel, 6, streetViewPanoramaOptions.it());
        b.a(parcel, 7, streetViewPanoramaOptions.il());
        b.a(parcel, 8, streetViewPanoramaOptions.iu());
        b.a(parcel, 9, streetViewPanoramaOptions.iv());
        b.a(parcel, 10, streetViewPanoramaOptions.ih());
        b.F(parcel, n3);
    }

    public StreetViewPanoramaOptions createFromParcel(Parcel parcel) {
        Integer n2 = null;
        byte by2 = 0;
        int n3 = a.o(parcel);
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        byte by6 = 0;
        LatLng latLng = null;
        String string2 = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        int n4 = 0;
        block12 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block12;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block12;
                }
                case 2: {
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera)a.a(parcel, n5, StreetViewPanoramaCamera.CREATOR);
                    continue block12;
                }
                case 3: {
                    string2 = a.n(parcel, n5);
                    continue block12;
                }
                case 4: {
                    latLng = (LatLng)a.a(parcel, n5, LatLng.CREATOR);
                    continue block12;
                }
                case 5: {
                    n2 = a.h(parcel, n5);
                    continue block12;
                }
                case 6: {
                    by6 = a.e(parcel, n5);
                    continue block12;
                }
                case 7: {
                    by5 = a.e(parcel, n5);
                    continue block12;
                }
                case 8: {
                    by4 = a.e(parcel, n5);
                    continue block12;
                }
                case 9: {
                    by3 = a.e(parcel, n5);
                    continue block12;
                }
                case 10: 
            }
            by2 = a.e(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new StreetViewPanoramaOptions(n4, streetViewPanoramaCamera, string2, latLng, n2, by6, by5, by4, by3, by2);
    }

    public StreetViewPanoramaOptions[] newArray(int n2) {
        return new StreetViewPanoramaOptions[n2];
    }
}

