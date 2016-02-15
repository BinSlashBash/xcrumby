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
import com.google.android.gms.internal.hd;

public class he
implements Parcelable.Creator<hd> {
    static void a(hd hd2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, hd2.getRequestId(), false);
        b.c(parcel, 1000, hd2.getVersionCode());
        b.a(parcel, 2, hd2.getExpirationTime());
        b.a(parcel, 3, hd2.hS());
        b.a(parcel, 4, hd2.getLatitude());
        b.a(parcel, 5, hd2.getLongitude());
        b.a(parcel, 6, hd2.hT());
        b.c(parcel, 7, hd2.hU());
        b.c(parcel, 8, hd2.getNotificationResponsiveness());
        b.c(parcel, 9, hd2.hV());
        b.F(parcel, n2);
    }

    public hd aC(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        int n4 = 0;
        short s2 = 0;
        double d2 = 0.0;
        double d3 = 0.0;
        float f2 = 0.0f;
        long l2 = 0;
        int n5 = 0;
        int n6 = -1;
        block12 : while (parcel.dataPosition() < n2) {
            int n7 = a.n(parcel);
            switch (a.R(n7)) {
                default: {
                    a.b(parcel, n7);
                    continue block12;
                }
                case 1: {
                    string2 = a.n(parcel, n7);
                    continue block12;
                }
                case 1000: {
                    n3 = a.g(parcel, n7);
                    continue block12;
                }
                case 2: {
                    l2 = a.i(parcel, n7);
                    continue block12;
                }
                case 3: {
                    s2 = a.f(parcel, n7);
                    continue block12;
                }
                case 4: {
                    d2 = a.l(parcel, n7);
                    continue block12;
                }
                case 5: {
                    d3 = a.l(parcel, n7);
                    continue block12;
                }
                case 6: {
                    f2 = a.k(parcel, n7);
                    continue block12;
                }
                case 7: {
                    n4 = a.g(parcel, n7);
                    continue block12;
                }
                case 8: {
                    n5 = a.g(parcel, n7);
                    continue block12;
                }
                case 9: 
            }
            n6 = a.g(parcel, n7);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new hd(n3, string2, n4, s2, d2, d3, f2, l2, n5, n6);
    }

    public hd[] bD(int n2) {
        return new hd[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aC(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bD(n2);
    }
}

