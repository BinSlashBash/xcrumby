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
import com.google.android.gms.internal.il;
import com.google.android.gms.internal.im;
import java.util.HashSet;
import java.util.Set;

public class ik
implements Parcelable.Creator<ih.b> {
    static void a(ih.b b2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        Set<Integer> set = b2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, b2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, b2.jE(), n2, true);
        }
        if (set.contains(3)) {
            b.a(parcel, 3, b2.jF(), n2, true);
        }
        if (set.contains(4)) {
            b.c(parcel, 4, b2.getLayout());
        }
        b.F(parcel, n3);
    }

    public ih.b aP(Parcel parcel) {
        ih.b.b b2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        ih.b.a a2 = null;
        int n4 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    hashSet.add(1);
                    continue block6;
                }
                case 2: {
                    a2 = (ih.b.a)a.a(parcel, n5, ih.b.a.CREATOR);
                    hashSet.add(2);
                    continue block6;
                }
                case 3: {
                    b2 = (ih.b.b)a.a(parcel, n5, ih.b.b.CREATOR);
                    hashSet.add(3);
                    continue block6;
                }
                case 4: 
            }
            n2 = a.g(parcel, n5);
            hashSet.add(4);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ih.b(hashSet, n4, a2, b2, n2);
    }

    public ih.b[] bS(int n2) {
        return new ih.b[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aP(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bS(n2);
    }
}

