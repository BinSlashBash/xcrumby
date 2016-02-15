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
import com.google.android.gms.internal.hg;
import com.google.android.gms.internal.hh;
import com.google.android.gms.internal.hk;

public class hl
implements Parcelable.Creator<hk> {
    static void a(hk hk2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, hk2.xH);
        b.a(parcel, 2, hk2.hZ(), n2, false);
        b.a(parcel, 3, hk2.getInterval());
        b.c(parcel, 4, hk2.getPriority());
        b.F(parcel, n3);
    }

    public hk aF(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        hg hg2 = null;
        long l2 = hk.OF;
        int n4 = 102;
        block6 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block6;
                }
                case 1000: {
                    n3 = a.g(parcel, n5);
                    continue block6;
                }
                case 2: {
                    hg2 = (hg)a.a(parcel, n5, hg.CREATOR);
                    continue block6;
                }
                case 3: {
                    l2 = a.i(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            n4 = a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new hk(n3, hg2, l2, n4);
    }

    public hk[] bG(int n2) {
        return new hk[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aF(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bG(n2);
    }
}

