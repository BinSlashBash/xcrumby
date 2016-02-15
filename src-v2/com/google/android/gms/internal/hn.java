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
import com.google.android.gms.internal.hm;

public class hn
implements Parcelable.Creator<hm> {
    static void a(hm hm2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, hm2.Rd, false);
        b.c(parcel, 1000, hm2.xH);
        b.F(parcel, n2);
    }

    public hm aG(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    string2 = a.n(parcel, n4);
                    continue block4;
                }
                case 1000: 
            }
            n3 = a.g(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new hm(n3, string2);
    }

    public hm[] bH(int n2) {
        return new hm[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aG(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bH(n2);
    }
}

