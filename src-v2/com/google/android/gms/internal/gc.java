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
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.gb;
import com.google.android.gms.internal.gd;

public class gc
implements Parcelable.Creator<gd.b> {
    static void a(gd.b b2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, b2.versionCode);
        b.a(parcel, 2, b2.eM, false);
        b.a(parcel, 3, b2.Em, n2, false);
        b.F(parcel, n3);
    }

    public gd.b[] W(int n2) {
        return new gd.b[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.u(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.W(n2);
    }

    public gd.b u(Parcel parcel) {
        ga.a a2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
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
                    string2 = a.n(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            a2 = (ga.a)a.a(parcel, n4, ga.a.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new gd.b(n3, string2, a2);
    }
}

