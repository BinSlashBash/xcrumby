/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ix;

public class iy
implements Parcelable.Creator<ix> {
    static void a(ix ix2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, ix2.getVersionCode());
        b.a(parcel, 2, ix2.act, false);
        b.a(parcel, 3, ix2.acu, false);
        b.F(parcel, n2);
    }

    public ix bm(Parcel parcel) {
        String[] arrstring = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        byte[][] arrby = null;
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
                    arrstring = a.z(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            arrby = a.r(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ix(n3, arrstring, arrby);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bm(parcel);
    }

    public ix[] cy(int n2) {
        return new ix[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cy(n2);
    }
}

