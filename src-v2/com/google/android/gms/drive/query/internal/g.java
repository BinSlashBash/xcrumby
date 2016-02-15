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
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.ArrayList;
import java.util.List;

public class g
implements Parcelable.Creator<LogicalFilter> {
    static void a(LogicalFilter logicalFilter, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, logicalFilter.xH);
        b.a(parcel, 1, logicalFilter.GG, n2, false);
        b.b(parcel, 2, logicalFilter.GS, false);
        b.F(parcel, n3);
    }

    public LogicalFilter[] aN(int n2) {
        return new LogicalFilter[n2];
    }

    public LogicalFilter aj(Parcel parcel) {
        ArrayList<FilterHolder> arrayList = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        Operator operator = null;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 1: {
                    operator = a.a(parcel, n4, Operator.CREATOR);
                    continue block5;
                }
                case 2: 
            }
            arrayList = a.c(parcel, n4, FilterHolder.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new LogicalFilter(n3, operator, arrayList);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aj(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aN(n2);
    }
}

