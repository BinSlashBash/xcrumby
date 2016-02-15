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
import com.google.android.gms.internal.kk;

public class kl
implements Parcelable.Creator<kk> {
    static void a(kk kk2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, kk2.xH);
        b.a(parcel, 2, kk2.getId(), false);
        b.a(parcel, 3, kk2.getDisplayName(), false);
        b.F(parcel, n2);
    }

    public kk bz(Parcel parcel) {
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
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new kk(n3, string3, string2);
    }

    public kk[] cO(int n2) {
        return new kk[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bz(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cO(n2);
    }
}

