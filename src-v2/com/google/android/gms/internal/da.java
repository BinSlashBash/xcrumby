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
import com.google.android.gms.internal.cz;
import java.util.ArrayList;
import java.util.List;

public class da
implements Parcelable.Creator<cz> {
    static void a(cz cz2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, cz2.versionCode);
        b.a(parcel, 2, cz2.ol, false);
        b.a(parcel, 3, cz2.pm, false);
        b.a(parcel, 4, cz2.ne, false);
        b.c(parcel, 5, cz2.errorCode);
        b.a(parcel, 6, cz2.nf, false);
        b.a(parcel, 7, cz2.pn);
        b.a(parcel, 8, cz2.po);
        b.a(parcel, 9, cz2.pp);
        b.a(parcel, 10, cz2.pq, false);
        b.a(parcel, 11, cz2.ni);
        b.c(parcel, 12, cz2.orientation);
        b.a(parcel, 13, cz2.pr, false);
        b.a(parcel, 14, cz2.ps);
        b.a(parcel, 15, cz2.pt, false);
        b.a(parcel, 19, cz2.pv, false);
        b.a(parcel, 18, cz2.pu);
        b.a(parcel, 21, cz2.pw, false);
        b.F(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.g(parcel);
    }

    public cz g(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        ArrayList<String> arrayList = null;
        int n4 = 0;
        ArrayList<String> arrayList2 = null;
        long l2 = 0;
        boolean bl2 = false;
        long l3 = 0;
        ArrayList<String> arrayList3 = null;
        long l4 = 0;
        int n5 = 0;
        String string4 = null;
        long l5 = 0;
        String string5 = null;
        boolean bl3 = false;
        String string6 = null;
        String string7 = null;
        block20 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block20;
                }
                case 1: {
                    n3 = a.g(parcel, n6);
                    continue block20;
                }
                case 2: {
                    string2 = a.n(parcel, n6);
                    continue block20;
                }
                case 3: {
                    string3 = a.n(parcel, n6);
                    continue block20;
                }
                case 4: {
                    arrayList = a.A(parcel, n6);
                    continue block20;
                }
                case 5: {
                    n4 = a.g(parcel, n6);
                    continue block20;
                }
                case 6: {
                    arrayList2 = a.A(parcel, n6);
                    continue block20;
                }
                case 7: {
                    l2 = a.i(parcel, n6);
                    continue block20;
                }
                case 8: {
                    bl2 = a.c(parcel, n6);
                    continue block20;
                }
                case 9: {
                    l3 = a.i(parcel, n6);
                    continue block20;
                }
                case 10: {
                    arrayList3 = a.A(parcel, n6);
                    continue block20;
                }
                case 11: {
                    l4 = a.i(parcel, n6);
                    continue block20;
                }
                case 12: {
                    n5 = a.g(parcel, n6);
                    continue block20;
                }
                case 13: {
                    string4 = a.n(parcel, n6);
                    continue block20;
                }
                case 14: {
                    l5 = a.i(parcel, n6);
                    continue block20;
                }
                case 15: {
                    string5 = a.n(parcel, n6);
                    continue block20;
                }
                case 19: {
                    string6 = a.n(parcel, n6);
                    continue block20;
                }
                case 18: {
                    bl3 = a.c(parcel, n6);
                    continue block20;
                }
                case 21: 
            }
            string7 = a.n(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new cz(n3, string2, string3, arrayList, n4, arrayList2, l2, bl2, l3, arrayList3, l4, n5, string4, l5, string5, bl3, string6, string7);
    }

    public cz[] l(int n2) {
        return new cz[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.l(n2);
    }
}

