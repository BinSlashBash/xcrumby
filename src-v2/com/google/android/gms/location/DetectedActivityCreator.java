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
import com.google.android.gms.location.DetectedActivity;

public class DetectedActivityCreator
implements Parcelable.Creator<DetectedActivity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(DetectedActivity detectedActivity, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, detectedActivity.NS);
        b.c(parcel, 1000, detectedActivity.getVersionCode());
        b.c(parcel, 2, detectedActivity.NT);
        b.F(parcel, n2);
    }

    public DetectedActivity createFromParcel(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        int n4 = 0;
        int n5 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block5;
                }
                case 1: {
                    n4 = a.g(parcel, n6);
                    continue block5;
                }
                case 1000: {
                    n5 = a.g(parcel, n6);
                    continue block5;
                }
                case 2: 
            }
            n2 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new DetectedActivity(n5, n4, n2);
    }

    public DetectedActivity[] newArray(int n2) {
        return new DetectedActivity[n2];
    }
}

