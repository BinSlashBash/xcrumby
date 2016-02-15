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
import com.google.android.gms.internal.gc;
import com.google.android.gms.internal.gd;
import java.util.ArrayList;

public class gf
implements Parcelable.Creator<gd.a> {
    static void a(gd.a a2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, a2.versionCode);
        b.a(parcel, 2, a2.className, false);
        b.b(parcel, 3, a2.El, false);
        b.F(parcel, n2);
    }

    public gd.a[] Y(int n2) {
        return new gd.a[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.w(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.Y(n2);
    }

    public gd.a w(Parcel parcel) {
        ArrayList arrayList = null;
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
            arrayList = a.c(parcel, n4, gd.b.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new gd.a(n3, string2, arrayList);
    }
}

