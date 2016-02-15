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
import com.google.android.gms.internal.ic;
import com.google.android.gms.internal.id;
import com.google.android.gms.internal.ie;
import java.util.HashSet;
import java.util.Set;

public class if
implements Parcelable.Creator<ie> {
    static void a(ie ie2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        Set<Integer> set = ie2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, ie2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, ie2.getId(), true);
        }
        if (set.contains(4)) {
            b.a(parcel, 4, ie2.jr(), n2, true);
        }
        if (set.contains(5)) {
            b.a(parcel, 5, ie2.getStartDate(), true);
        }
        if (set.contains(6)) {
            b.a(parcel, 6, ie2.js(), n2, true);
        }
        if (set.contains(7)) {
            b.a(parcel, 7, ie2.getType(), true);
        }
        b.F(parcel, n3);
    }

    public ie aM(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n3 = 0;
        ic ic2 = null;
        String string3 = null;
        ic ic3 = null;
        String string4 = null;
        block8 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block8;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    hashSet.add(1);
                    continue block8;
                }
                case 2: {
                    string4 = a.n(parcel, n4);
                    hashSet.add(2);
                    continue block8;
                }
                case 4: {
                    ic3 = (ic)a.a(parcel, n4, ic.CREATOR);
                    hashSet.add(4);
                    continue block8;
                }
                case 5: {
                    string3 = a.n(parcel, n4);
                    hashSet.add(5);
                    continue block8;
                }
                case 6: {
                    ic2 = (ic)a.a(parcel, n4, ic.CREATOR);
                    hashSet.add(6);
                    continue block8;
                }
                case 7: 
            }
            string2 = a.n(parcel, n4);
            hashSet.add(7);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ie(hashSet, n3, string4, ic3, string3, ic2, string2);
    }

    public ie[] bP(int n2) {
        return new ie[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aM(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bP(n2);
    }
}

