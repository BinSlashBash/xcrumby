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
import com.google.android.gms.internal.fw;
import com.google.android.gms.internal.ga;

public class gb
implements Parcelable.Creator<ga.a> {
    static void a(ga.a a2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, a2.getVersionCode());
        b.c(parcel, 2, a2.eW());
        b.a(parcel, 3, a2.fc());
        b.c(parcel, 4, a2.eX());
        b.a(parcel, 5, a2.fd());
        b.a(parcel, 6, a2.fe(), false);
        b.c(parcel, 7, a2.ff());
        b.a(parcel, 8, a2.fh(), false);
        b.a(parcel, 9, a2.fj(), n2, false);
        b.F(parcel, n3);
    }

    public ga.a[] V(int n2) {
        return new ga.a[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.t(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.V(n2);
    }

    public ga.a t(Parcel parcel) {
        fv fv2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        String string2 = null;
        String string3 = null;
        boolean bl2 = false;
        int n4 = 0;
        boolean bl3 = false;
        int n5 = 0;
        int n6 = 0;
        block11 : while (parcel.dataPosition() < n3) {
            int n7 = a.n(parcel);
            switch (a.R(n7)) {
                default: {
                    a.b(parcel, n7);
                    continue block11;
                }
                case 1: {
                    n6 = a.g(parcel, n7);
                    continue block11;
                }
                case 2: {
                    n5 = a.g(parcel, n7);
                    continue block11;
                }
                case 3: {
                    bl3 = a.c(parcel, n7);
                    continue block11;
                }
                case 4: {
                    n4 = a.g(parcel, n7);
                    continue block11;
                }
                case 5: {
                    bl2 = a.c(parcel, n7);
                    continue block11;
                }
                case 6: {
                    string3 = a.n(parcel, n7);
                    continue block11;
                }
                case 7: {
                    n2 = a.g(parcel, n7);
                    continue block11;
                }
                case 8: {
                    string2 = a.n(parcel, n7);
                    continue block11;
                }
                case 9: 
            }
            fv2 = (fv)a.a(parcel, n7, fv.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ga.a(n6, n5, bl3, n4, bl2, string3, n2, string2, fv2);
    }
}

