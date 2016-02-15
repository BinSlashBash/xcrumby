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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;

public class CameraPositionCreator
implements Parcelable.Creator<CameraPosition> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(CameraPosition cameraPosition, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, cameraPosition.getVersionCode());
        b.a(parcel, 2, cameraPosition.target, n2, false);
        b.a(parcel, 3, cameraPosition.zoom);
        b.a(parcel, 4, cameraPosition.tilt);
        b.a(parcel, 5, cameraPosition.bearing);
        b.F(parcel, n3);
    }

    public CameraPosition createFromParcel(Parcel parcel) {
        float f2 = 0.0f;
        int n2 = a.o(parcel);
        int n3 = 0;
        LatLng latLng = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        block7 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block7;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block7;
                }
                case 2: {
                    latLng = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
                    continue block7;
                }
                case 3: {
                    f4 = a.k(parcel, n4);
                    continue block7;
                }
                case 4: {
                    f3 = a.k(parcel, n4);
                    continue block7;
                }
                case 5: 
            }
            f2 = a.k(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CameraPosition(n3, latLng, f4, f3, f2);
    }

    public CameraPosition[] newArray(int n2) {
        return new CameraPosition[n2];
    }
}

