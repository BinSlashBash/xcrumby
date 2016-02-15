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
import com.google.android.gms.internal.fz;
import java.util.ArrayList;

public class fy
implements Parcelable.Creator<fx> {
    static void a(fx fx2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, fx2.getVersionCode());
        b.b(parcel, 2, fx2.eV(), false);
        b.F(parcel, n2);
    }

    public fx[] T(int n2) {
        return new fx[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.r(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.T(n2);
    }

    public fx r(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = null;
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
            arrayList = a.c(parcel, n4, fx.a.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new fx(n3, arrayList);
    }
}

