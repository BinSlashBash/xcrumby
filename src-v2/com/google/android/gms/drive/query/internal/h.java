/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.query.internal.MatchAllFilter;

public class h
implements Parcelable.Creator<MatchAllFilter> {
    static void a(MatchAllFilter matchAllFilter, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1000, matchAllFilter.xH);
        b.F(parcel, n2);
    }

    public MatchAllFilter[] aO(int n2) {
        return new MatchAllFilter[n2];
    }

    public MatchAllFilter ak(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        block3 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block3;
                }
                case 1000: 
            }
            n3 = a.g(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new MatchAllFilter(n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ak(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aO(n2);
    }
}

