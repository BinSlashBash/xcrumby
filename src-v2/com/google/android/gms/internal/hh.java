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
import com.google.android.gms.internal.hg;
import com.google.android.gms.internal.hm;
import com.google.android.gms.internal.hn;
import java.util.ArrayList;
import java.util.List;

public class hh
implements Parcelable.Creator<hg> {
    static void a(hg hg2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.b(parcel, 1, hg2.OA, false);
        b.c(parcel, 1000, hg2.xH);
        b.a(parcel, 2, hg2.hW(), false);
        b.a(parcel, 3, hg2.hX());
        b.F(parcel, n2);
    }

    public hg aD(Parcel parcel) {
        String string2 = null;
        boolean bl2 = false;
        int n2 = a.o(parcel);
        ArrayList<hm> arrayList = null;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    arrayList = a.c(parcel, n4, hm.CREATOR);
                    continue block6;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string2 = a.n(parcel, n4);
                    continue block6;
                }
                case 3: 
            }
            bl2 = a.c(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new hg(n3, arrayList, string2, bl2);
    }

    public hg[] bE(int n2) {
        return new hg[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aD(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bE(n2);
    }
}

