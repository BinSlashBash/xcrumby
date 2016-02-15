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
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;

public class jz
implements Parcelable.Creator<jy> {
    static void a(jy jy2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, jy2.getVersionCode());
        b.a(parcel, 2, jy2.adn, false);
        b.a(parcel, 3, jy2.pm, false);
        b.a(parcel, 4, jy2.adr, n2, false);
        b.a(parcel, 5, jy2.ads, n2, false);
        b.a(parcel, 6, jy2.adt, n2, false);
        b.F(parcel, n3);
    }

    public jy bx(Parcel parcel) {
        jw jw2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        jw jw3 = null;
        ju ju2 = null;
        String string2 = null;
        String string3 = null;
        block8 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block8;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block8;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block8;
                }
                case 3: {
                    string2 = a.n(parcel, n4);
                    continue block8;
                }
                case 4: {
                    ju2 = a.a(parcel, n4, ju.CREATOR);
                    continue block8;
                }
                case 5: {
                    jw3 = a.a(parcel, n4, jw.CREATOR);
                    continue block8;
                }
                case 6: 
            }
            jw2 = a.a(parcel, n4, jw.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new jy(n3, string3, string2, ju2, jw3, jw2);
    }

    public jy[] cL(int n2) {
        return new jy[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bx(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cL(n2);
    }
}

