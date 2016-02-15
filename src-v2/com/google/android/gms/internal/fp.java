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
import com.google.android.gms.internal.fc;
import java.util.ArrayList;
import java.util.List;

public class fp
implements Parcelable.Creator<fc.a> {
    static void a(fc.a a2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, a2.getAccountName(), false);
        b.c(parcel, 1000, a2.getVersionCode());
        b.a(parcel, 2, a2.eE(), false);
        b.c(parcel, 3, a2.eD());
        b.a(parcel, 4, a2.eG(), false);
        b.F(parcel, n2);
    }

    public fc.a[] Q(int n2) {
        return new fc.a[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.m(parcel);
    }

    public fc.a m(Parcel parcel) {
        int n2 = 0;
        String string2 = null;
        int n3 = a.o(parcel);
        ArrayList<String> arrayList = null;
        String string3 = null;
        int n4 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block7;
                }
                case 1: {
                    string3 = a.n(parcel, n5);
                    continue block7;
                }
                case 1000: {
                    n4 = a.g(parcel, n5);
                    continue block7;
                }
                case 2: {
                    arrayList = a.A(parcel, n5);
                    continue block7;
                }
                case 3: {
                    n2 = a.g(parcel, n5);
                    continue block7;
                }
                case 4: 
            }
            string2 = a.n(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new fc.a(n4, string3, arrayList, n2, string2);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.Q(n2);
    }
}

