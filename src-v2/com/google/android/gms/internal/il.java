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
import com.google.android.gms.internal.ih;
import java.util.HashSet;
import java.util.Set;

public class il
implements Parcelable.Creator<ih.b.a> {
    static void a(ih.b.a a2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = a2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, a2.getVersionCode());
        }
        if (set.contains(2)) {
            b.c(parcel, 2, a2.getLeftImageOffset());
        }
        if (set.contains(3)) {
            b.c(parcel, 3, a2.getTopImageOffset());
        }
        b.F(parcel, n2);
    }

    public ih.b.a aQ(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
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
                    n5 = a.g(parcel, n6);
                    hashSet.add(1);
                    continue block5;
                }
                case 2: {
                    n4 = a.g(parcel, n6);
                    hashSet.add(2);
                    continue block5;
                }
                case 3: 
            }
            n2 = a.g(parcel, n6);
            hashSet.add(3);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ih.b.a(hashSet, n5, n4, n2);
    }

    public ih.b.a[] bT(int n2) {
        return new ih.b.a[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aQ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bT(n2);
    }
}

