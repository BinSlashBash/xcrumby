/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.location.LocationRequest;

public class LocationRequestCreator
implements Parcelable.Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LocationRequest locationRequest, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, locationRequest.mPriority);
        b.c(parcel, 1000, locationRequest.getVersionCode());
        b.a(parcel, 2, locationRequest.Oc);
        b.a(parcel, 3, locationRequest.Od);
        b.a(parcel, 4, locationRequest.Oe);
        b.a(parcel, 5, locationRequest.NV);
        b.c(parcel, 6, locationRequest.Of);
        b.a(parcel, 7, locationRequest.Og);
        b.F(parcel, n2);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        int n3 = 102;
        long l2 = 3600000;
        long l3 = 600000;
        long l4 = Long.MAX_VALUE;
        int n4 = Integer.MAX_VALUE;
        float f2 = 0.0f;
        int n5 = 0;
        block10 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block10;
                }
                case 1: {
                    n3 = a.g(parcel, n6);
                    continue block10;
                }
                case 1000: {
                    n5 = a.g(parcel, n6);
                    continue block10;
                }
                case 2: {
                    l2 = a.i(parcel, n6);
                    continue block10;
                }
                case 3: {
                    l3 = a.i(parcel, n6);
                    continue block10;
                }
                case 4: {
                    bl2 = a.c(parcel, n6);
                    continue block10;
                }
                case 5: {
                    l4 = a.i(parcel, n6);
                    continue block10;
                }
                case 6: {
                    n4 = a.g(parcel, n6);
                    continue block10;
                }
                case 7: 
            }
            f2 = a.k(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new LocationRequest(n5, n3, l2, l3, bl2, l4, n4, f2);
    }

    public LocationRequest[] newArray(int n2) {
        return new LocationRequest[n2];
    }
}

