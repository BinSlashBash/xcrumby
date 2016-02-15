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
import com.google.android.gms.internal.cb;

public class ca
implements Parcelable.Creator<cb> {
    static void a(cb cb2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, cb2.versionCode);
        b.a(parcel, 2, cb2.nN, false);
        b.a(parcel, 3, cb2.nO, false);
        b.a(parcel, 4, cb2.mimeType, false);
        b.a(parcel, 5, cb2.packageName, false);
        b.a(parcel, 6, cb2.nP, false);
        b.a(parcel, 7, cb2.nQ, false);
        b.a(parcel, 8, cb2.nR, false);
        b.F(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.d(parcel);
    }

    public cb d(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        block10 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block10;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block10;
                }
                case 2: {
                    string8 = a.n(parcel, n4);
                    continue block10;
                }
                case 3: {
                    string7 = a.n(parcel, n4);
                    continue block10;
                }
                case 4: {
                    string6 = a.n(parcel, n4);
                    continue block10;
                }
                case 5: {
                    string5 = a.n(parcel, n4);
                    continue block10;
                }
                case 6: {
                    string4 = a.n(parcel, n4);
                    continue block10;
                }
                case 7: {
                    string3 = a.n(parcel, n4);
                    continue block10;
                }
                case 8: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new cb(n3, string8, string7, string6, string5, string4, string3, string2);
    }

    public cb[] h(int n2) {
        return new cb[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.h(n2);
    }
}

