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

public class iq
implements Parcelable.Creator<ih.g> {
    static void a(ih.g g2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = g2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, g2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, g2.isPrimary());
        }
        if (set.contains(3)) {
            b.a(parcel, 3, g2.getValue(), true);
        }
        b.F(parcel, n2);
    }

    public ih.g aV(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        String string2 = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    hashSet.add(1);
                    continue block5;
                }
                case 2: {
                    bl2 = a.c(parcel, n4);
                    hashSet.add(2);
                    continue block5;
                }
                case 3: 
            }
            string2 = a.n(parcel, n4);
            hashSet.add(3);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ih.g(hashSet, n3, bl2, string2);
    }

    public ih.g[] bY(int n2) {
        return new ih.g[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aV(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bY(n2);
    }
}

