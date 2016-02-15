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
import com.google.android.gms.internal.iv;

public class iw
implements Parcelable.Creator<iv> {
    static void a(iv iv2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, iv2.getVersionCode());
        b.a(parcel, 2, iv2.acs, false);
        b.F(parcel, n2);
    }

    public iv bl(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        Object object = null;
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
            object = a.t(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new iv(n3, (int[])object);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bl(parcel);
    }

    public iv[] cx(int n2) {
        return new iv[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cx(n2);
    }
}

