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
import com.google.android.gms.internal.gd;
import com.google.android.gms.internal.ge;
import com.google.android.gms.internal.gg;

public class gh
implements Parcelable.Creator<gg> {
    static void a(gg gg2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, gg2.getVersionCode());
        b.a(parcel, 2, gg2.fq(), false);
        b.a(parcel, 3, gg2.fr(), n2, false);
        b.F(parcel, n3);
    }

    public gg[] Z(int n2) {
        return new gg[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.x(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.Z(n2);
    }

    public gg x(Parcel parcel) {
        gd gd2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        Parcel parcel2 = null;
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
                    parcel2 = a.B(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            gd2 = (gd)a.a(parcel, n4, gd.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new gg(n3, parcel2, gd2);
    }
}

