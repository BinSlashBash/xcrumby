/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ca;
import com.google.android.gms.internal.cb;
import com.google.android.gms.internal.ce;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dy;

public class cd
implements Parcelable.Creator<ce> {
    static void a(ce ce2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, ce2.versionCode);
        b.a(parcel, 2, ce2.og, n2, false);
        b.a(parcel, 3, ce2.aO(), false);
        b.a(parcel, 4, ce2.aP(), false);
        b.a(parcel, 5, ce2.aQ(), false);
        b.a(parcel, 6, ce2.aR(), false);
        b.a(parcel, 7, ce2.ol, false);
        b.a(parcel, 8, ce2.om);
        b.a(parcel, 9, ce2.on, false);
        b.a(parcel, 10, ce2.aT(), false);
        b.c(parcel, 11, ce2.orientation);
        b.c(parcel, 12, ce2.op);
        b.a(parcel, 13, ce2.nO, false);
        b.a(parcel, 14, ce2.kK, n2, false);
        b.a(parcel, 15, ce2.aS(), false);
        b.a(parcel, 16, ce2.or, false);
        b.F(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.e(parcel);
    }

    public ce e(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        cb cb2 = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        String string2 = null;
        boolean bl2 = false;
        String string3 = null;
        IBinder iBinder5 = null;
        int n4 = 0;
        int n5 = 0;
        String string4 = null;
        dx dx2 = null;
        IBinder iBinder6 = null;
        String string5 = null;
        block18 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block18;
                }
                case 1: {
                    n3 = a.g(parcel, n6);
                    continue block18;
                }
                case 2: {
                    cb2 = (cb)a.a(parcel, n6, cb.CREATOR);
                    continue block18;
                }
                case 3: {
                    iBinder = a.o(parcel, n6);
                    continue block18;
                }
                case 4: {
                    iBinder2 = a.o(parcel, n6);
                    continue block18;
                }
                case 5: {
                    iBinder3 = a.o(parcel, n6);
                    continue block18;
                }
                case 6: {
                    iBinder4 = a.o(parcel, n6);
                    continue block18;
                }
                case 7: {
                    string2 = a.n(parcel, n6);
                    continue block18;
                }
                case 8: {
                    bl2 = a.c(parcel, n6);
                    continue block18;
                }
                case 9: {
                    string3 = a.n(parcel, n6);
                    continue block18;
                }
                case 10: {
                    iBinder5 = a.o(parcel, n6);
                    continue block18;
                }
                case 11: {
                    n4 = a.g(parcel, n6);
                    continue block18;
                }
                case 12: {
                    n5 = a.g(parcel, n6);
                    continue block18;
                }
                case 13: {
                    string4 = a.n(parcel, n6);
                    continue block18;
                }
                case 14: {
                    dx2 = (dx)a.a(parcel, n6, dx.CREATOR);
                    continue block18;
                }
                case 15: {
                    iBinder6 = a.o(parcel, n6);
                    continue block18;
                }
                case 16: 
            }
            string5 = a.n(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ce(n3, cb2, iBinder, iBinder2, iBinder3, iBinder4, string2, bl2, string3, iBinder5, n4, n5, string4, dx2, iBinder6, string5);
    }

    public ce[] i(int n2) {
        return new ce[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.i(n2);
    }
}

