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

public class in
implements Parcelable.Creator<ih.c> {
    static void a(ih.c c2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = c2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, c2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, c2.getUrl(), true);
        }
        b.F(parcel, n2);
    }

    public ih.c aS(Parcel parcel) {
        int n2 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
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
                    n3 = a.g(parcel, n4);
                    hashSet.add(1);
                    continue block4;
                }
                case 2: 
            }
            string2 = a.n(parcel, n4);
            hashSet.add(2);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ih.c(hashSet, n3, string2);
    }

    public ih.c[] bV(int n2) {
        return new ih.c[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aS(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bV(n2);
    }
}

