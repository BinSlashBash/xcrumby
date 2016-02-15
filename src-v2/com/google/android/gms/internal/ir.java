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

public class ir
implements Parcelable.Creator<ih.h> {
    static void a(ih.h h2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        Set<Integer> set = h2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, h2.getVersionCode());
        }
        if (set.contains(3)) {
            b.c(parcel, 3, h2.jN());
        }
        if (set.contains(4)) {
            b.a(parcel, 4, h2.getValue(), true);
        }
        if (set.contains(5)) {
            b.a(parcel, 5, h2.getLabel(), true);
        }
        if (set.contains(6)) {
            b.c(parcel, 6, h2.getType());
        }
        b.F(parcel, n2);
    }

    public ih.h aW(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n4 = 0;
        String string3 = null;
        int n5 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block7;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    hashSet.add(1);
                    continue block7;
                }
                case 3: {
                    n2 = a.g(parcel, n6);
                    hashSet.add(3);
                    continue block7;
                }
                case 4: {
                    string2 = a.n(parcel, n6);
                    hashSet.add(4);
                    continue block7;
                }
                case 5: {
                    string3 = a.n(parcel, n6);
                    hashSet.add(5);
                    continue block7;
                }
                case 6: 
            }
            n4 = a.g(parcel, n6);
            hashSet.add(6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ih.h(hashSet, n5, string3, n4, string2, n2);
    }

    public ih.h[] bZ(int n2) {
        return new ih.h[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aW(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bZ(n2);
    }
}

