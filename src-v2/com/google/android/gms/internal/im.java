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

public class im
implements Parcelable.Creator<ih.b.b> {
    static void a(ih.b.b b2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = b2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, b2.getVersionCode());
        }
        if (set.contains(2)) {
            b.c(parcel, 2, b2.getHeight());
        }
        if (set.contains(3)) {
            b.a(parcel, 3, b2.getUrl(), true);
        }
        if (set.contains(4)) {
            b.c(parcel, 4, b2.getWidth());
        }
        b.F(parcel, n2);
    }

    public ih.b.b aR(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        String string2 = null;
        int n4 = 0;
        int n5 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    hashSet.add(1);
                    continue block6;
                }
                case 2: {
                    n4 = a.g(parcel, n6);
                    hashSet.add(2);
                    continue block6;
                }
                case 3: {
                    string2 = a.n(parcel, n6);
                    hashSet.add(3);
                    continue block6;
                }
                case 4: 
            }
            n2 = a.g(parcel, n6);
            hashSet.add(4);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ih.b.b(hashSet, n5, n4, string2, n2);
    }

    public ih.b.b[] bU(int n2) {
        return new ih.b.b[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aR(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bU(n2);
    }
}

