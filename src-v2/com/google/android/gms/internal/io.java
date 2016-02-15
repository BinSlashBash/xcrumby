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

public class io
implements Parcelable.Creator<ih.d> {
    static void a(ih.d d2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = d2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, d2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, d2.getFamilyName(), true);
        }
        if (set.contains(3)) {
            b.a(parcel, 3, d2.getFormatted(), true);
        }
        if (set.contains(4)) {
            b.a(parcel, 4, d2.getGivenName(), true);
        }
        if (set.contains(5)) {
            b.a(parcel, 5, d2.getHonorificPrefix(), true);
        }
        if (set.contains(6)) {
            b.a(parcel, 6, d2.getHonorificSuffix(), true);
        }
        if (set.contains(7)) {
            b.a(parcel, 7, d2.getMiddleName(), true);
        }
        b.F(parcel, n2);
    }

    public ih.d aT(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n3 = 0;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        block9 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block9;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    hashSet.add(1);
                    continue block9;
                }
                case 2: {
                    string7 = a.n(parcel, n4);
                    hashSet.add(2);
                    continue block9;
                }
                case 3: {
                    string6 = a.n(parcel, n4);
                    hashSet.add(3);
                    continue block9;
                }
                case 4: {
                    string5 = a.n(parcel, n4);
                    hashSet.add(4);
                    continue block9;
                }
                case 5: {
                    string4 = a.n(parcel, n4);
                    hashSet.add(5);
                    continue block9;
                }
                case 6: {
                    string3 = a.n(parcel, n4);
                    hashSet.add(6);
                    continue block9;
                }
                case 7: 
            }
            string2 = a.n(parcel, n4);
            hashSet.add(7);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ih.d(hashSet, n3, string7, string6, string5, string4, string3, string2);
    }

    public ih.d[] bW(int n2) {
        return new ih.d[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aT(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bW(n2);
    }
}

