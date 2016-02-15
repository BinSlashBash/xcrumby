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
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.NotFilter;

public class i
implements Parcelable.Creator<NotFilter> {
    static void a(NotFilter notFilter, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, notFilter.xH);
        b.a(parcel, 1, notFilter.GT, n2, false);
        b.F(parcel, n3);
    }

    public NotFilter[] aP(int n2) {
        return new NotFilter[n2];
    }

    public NotFilter al(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        FilterHolder filterHolder = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 1: 
            }
            filterHolder = a.a(parcel, n4, FilterHolder.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new NotFilter(n3, filterHolder);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.al(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aP(n2);
    }
}

