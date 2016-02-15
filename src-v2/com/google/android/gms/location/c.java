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
import com.google.android.gms.location.b;

public class c
implements Parcelable.Creator<b> {
    static void a(b b2, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, b2.Oh);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, b2.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, b2.Oi);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, b2.Oj);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n2);
    }

    public b aB(Parcel parcel) {
        int n2 = 1;
        int n3 = a.o(parcel);
        int n4 = 0;
        long l2 = 0;
        int n5 = 1;
        block6 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block6;
                }
                case 1000: {
                    n4 = a.g(parcel, n6);
                    continue block6;
                }
                case 2: {
                    n2 = a.g(parcel, n6);
                    continue block6;
                }
                case 3: 
            }
            l2 = a.i(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new b(n4, n5, n2, l2);
    }

    public b[] bA(int n2) {
        return new b[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aB(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bA(n2);
    }
}

