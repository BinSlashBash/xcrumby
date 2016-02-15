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
import com.google.android.gms.internal.fv;
import com.google.android.gms.internal.fx;
import com.google.android.gms.internal.fy;

public class fw
implements Parcelable.Creator<fv> {
    static void a(fv fv2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, fv2.getVersionCode());
        b.a(parcel, 2, fv2.eT(), n2, false);
        b.F(parcel, n3);
    }

    public fv[] S(int n2) {
        return new fv[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.q(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.S(n2);
    }

    public fv q(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        fx fx2 = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            fx2 = (fx)a.a(parcel, n4, fx.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new fv(n3, fx2);
    }
}

