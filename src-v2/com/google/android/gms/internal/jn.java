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
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jk;
import com.google.android.gms.internal.jm;
import java.util.ArrayList;

public class jn
implements Parcelable.Creator<jm> {
    static void a(jm jm2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, jm2.getVersionCode());
        b.a(parcel, 2, jm2.add, false);
        b.a(parcel, 3, jm2.ade, false);
        b.b(parcel, 4, jm2.adf, false);
        b.F(parcel, n2);
    }

    public jm br(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = gi.fs();
        String string3 = null;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block6;
                }
                case 3: {
                    string2 = a.n(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            arrayList = a.c(parcel, n4, jk.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new jm(n3, string3, string2, arrayList);
    }

    public jm[] cF(int n2) {
        return new jm[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.br(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cF(n2);
    }
}

