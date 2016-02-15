/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.plus.internal.PlusCommonExtras;

public class f
implements Parcelable.Creator<PlusCommonExtras> {
    static void a(PlusCommonExtras plusCommonExtras, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, plusCommonExtras.iN(), false);
        b.c(parcel, 1000, plusCommonExtras.getVersionCode());
        b.a(parcel, 2, plusCommonExtras.iO(), false);
        b.F(parcel, n2);
    }

    public PlusCommonExtras aJ(Parcel parcel) {
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
        return new PlusCommonExtras(n3, string3, string2);
    }

    public PlusCommonExtras[] bM(int n2) {
        return new PlusCommonExtras[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aJ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bM(n2);
    }
}

