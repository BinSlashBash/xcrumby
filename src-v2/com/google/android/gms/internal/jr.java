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
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.jp;
import com.google.android.gms.internal.ju;

public class jr
implements Parcelable.Creator<jo> {
    static void a(jo jo2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, jo2.getVersionCode());
        b.a(parcel, 2, jo2.label, false);
        b.a(parcel, 3, jo2.adg, n2, false);
        b.a(parcel, 4, jo2.type, false);
        b.a(parcel, 5, jo2.abJ, n2, false);
        b.F(parcel, n3);
    }

    public jo bt(Parcel parcel) {
        ju ju2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        jp jp2 = null;
        String string3 = null;
        block7 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block7;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block7;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block7;
                }
                case 3: {
                    jp2 = a.a(parcel, n4, jp.CREATOR);
                    continue block7;
                }
                case 4: {
                    string2 = a.n(parcel, n4);
                    continue block7;
                }
                case 5: 
            }
            ju2 = a.a(parcel, n4, ju.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new jo(n3, string3, jp2, string2, ju2);
    }

    public jo[] cH(int n2) {
        return new jo[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bt(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cH(n2);
    }
}

