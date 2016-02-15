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
import com.google.android.gms.internal.jp;

public class jq
implements Parcelable.Creator<jp> {
    static void a(jp jp2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, jp2.getVersionCode());
        b.c(parcel, 2, jp2.adh);
        b.a(parcel, 3, jp2.adi, false);
        b.a(parcel, 4, jp2.adj);
        b.a(parcel, 5, jp2.adk, false);
        b.a(parcel, 6, jp2.adl);
        b.c(parcel, 7, jp2.adm);
        b.F(parcel, n2);
    }

    public jp bs(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        double d2 = 0.0;
        long l2 = 0;
        int n4 = -1;
        String string3 = null;
        int n5 = 0;
        block9 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block9;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block9;
                }
                case 2: {
                    n2 = a.g(parcel, n6);
                    continue block9;
                }
                case 3: {
                    string3 = a.n(parcel, n6);
                    continue block9;
                }
                case 4: {
                    d2 = a.l(parcel, n6);
                    continue block9;
                }
                case 5: {
                    string2 = a.n(parcel, n6);
                    continue block9;
                }
                case 6: {
                    l2 = a.i(parcel, n6);
                    continue block9;
                }
                case 7: 
            }
            n4 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new jp(n5, n2, string3, d2, string2, l2, n4);
    }

    public jp[] cG(int n2) {
        return new jp[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bs(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cG(n2);
    }
}

