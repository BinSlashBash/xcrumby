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
import com.google.android.gms.internal.hs;

public class ht
implements Parcelable.Creator<hs> {
    static void a(hs hs2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, hs2.Rl, false);
        b.c(parcel, 1000, hs2.versionCode);
        b.a(parcel, 2, hs2.Rm, false);
        b.F(parcel, n2);
    }

    public hs aI(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string3 = null;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1: {
                    string3 = a.n(parcel, n4);
                    continue block5;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 2: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new hs(n3, string3, string2);
    }

    public hs[] bJ(int n2) {
        return new hs[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aI(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bJ(n2);
    }
}

