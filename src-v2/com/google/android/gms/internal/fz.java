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
import com.google.android.gms.internal.fx;

public class fz
implements Parcelable.Creator<fx.a> {
    static void a(fx.a a2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, a2.versionCode);
        b.a(parcel, 2, a2.DW, false);
        b.c(parcel, 3, a2.DX);
        b.F(parcel, n2);
    }

    public fx.a[] U(int n2) {
        return new fx.a[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.s(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.U(n2);
    }

    public fx.a s(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        String string2 = null;
        int n4 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block5;
                }
                case 2: {
                    string2 = a.n(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            n2 = a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new fx.a(n4, string2, n2);
    }
}

