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
import com.google.android.gms.internal.gf;
import java.util.ArrayList;

public class ge
implements Parcelable.Creator<gd> {
    static void a(gd gd2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, gd2.getVersionCode());
        b.b(parcel, 2, gd2.fn(), false);
        b.a(parcel, 3, gd2.fo(), false);
        b.F(parcel, n2);
    }

    public gd[] X(int n2) {
        return new gd[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.v(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.X(n2);
    }

    public gd v(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = null;
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
                    arrayList = a.c(parcel, n4, gd.a.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new gd(n3, arrayList, string2);
    }
}

