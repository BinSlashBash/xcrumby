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
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPositionCreator;

public class GoogleMapOptionsCreator
implements Parcelable.Creator<GoogleMapOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

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

    public GoogleMapOptions createFromParcel(Parcel parcel) {
        byte by2 = 0;
        int n2 = a.o(parcel);
        CameraPosition cameraPosition = null;
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        byte by6 = 0;
        byte by7 = 0;
        int n3 = 0;
        byte by8 = 0;
        byte by9 = 0;
        int n4 = 0;
        block13 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block13;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block13;
                }
                case 2: {
                    by9 = a.e(parcel, n5);
                    continue block13;
                }
                case 3: {
                    by8 = a.e(parcel, n5);
                    continue block13;
                }
                case 4: {
                    n3 = a.g(parcel, n5);
                    continue block13;
                }
                case 5: {
                    cameraPosition = (CameraPosition)a.a(parcel, n5, CameraPosition.CREATOR);
                    continue block13;
                }
                case 6: {
                    by7 = a.e(parcel, n5);
                    continue block13;
                }
                case 7: {
                    by6 = a.e(parcel, n5);
                    continue block13;
                }
                case 8: {
                    by5 = a.e(parcel, n5);
                    continue block13;
                }
                case 9: {
                    by4 = a.e(parcel, n5);
                    continue block13;
                }
                case 10: {
                    by3 = a.e(parcel, n5);
                    continue block13;
                }
                case 11: 
            }
            by2 = a.e(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new GoogleMapOptions(n4, by9, by8, n3, cameraPosition, by7, by6, by5, by4, by3, by2);
    }

    public GoogleMapOptions[] newArray(int n2) {
        return new GoogleMapOptions[n2];
    }
}

