/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.f;
import com.google.android.gms.plus.internal.h;

public class j
implements Parcelable.Creator<h> {
    static void a(h h2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, h2.getAccountName(), false);
        b.c(parcel, 1000, h2.getVersionCode());
        b.a(parcel, 2, h2.iP(), false);
        b.a(parcel, 3, h2.iQ(), false);
        b.a(parcel, 4, h2.iR(), false);
        b.a(parcel, 5, h2.iS(), false);
        b.a(parcel, 6, h2.iT(), false);
        b.a(parcel, 7, h2.iU(), false);
        b.a(parcel, 8, h2.iV(), false);
        b.a(parcel, 9, h2.iW(), n2, false);
        b.F(parcel, n3);
    }

    public h aK(Parcel parcel) {
        PlusCommonExtras plusCommonExtras = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String[] arrstring = null;
        String[] arrstring2 = null;
        String[] arrstring3 = null;
        String string6 = null;
        block12 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block12;
                }
                case 1: {
                    string6 = a.n(parcel, n4);
                    continue block12;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block12;
                }
                case 2: {
                    arrstring3 = a.z(parcel, n4);
                    continue block12;
                }
                case 3: {
                    arrstring2 = a.z(parcel, n4);
                    continue block12;
                }
                case 4: {
                    arrstring = a.z(parcel, n4);
                    continue block12;
                }
                case 5: {
                    string5 = a.n(parcel, n4);
                    continue block12;
                }
                case 6: {
                    string4 = a.n(parcel, n4);
                    continue block12;
                }
                case 7: {
                    string3 = a.n(parcel, n4);
                    continue block12;
                }
                case 8: {
                    string2 = a.n(parcel, n4);
                    continue block12;
                }
                case 9: 
            }
            plusCommonExtras = (PlusCommonExtras)a.a(parcel, n4, PlusCommonExtras.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new h(n3, string6, arrstring3, arrstring2, arrstring, string5, string4, string3, string2, plusCommonExtras);
    }

    public h[] bN(int n2) {
        return new h[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aK(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bN(n2);
    }
}

