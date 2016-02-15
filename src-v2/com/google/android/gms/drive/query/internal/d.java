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
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.android.gms.drive.query.internal.FieldOnlyFilter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.InFilter;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.MatchAllFilter;
import com.google.android.gms.drive.query.internal.NotFilter;
import com.google.android.gms.drive.query.internal.a;
import com.google.android.gms.drive.query.internal.f;
import com.google.android.gms.drive.query.internal.h;

public class d
implements Parcelable.Creator<FilterHolder> {
    static void a(FilterHolder filterHolder, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, filterHolder.GK, n2, false);
        b.c(parcel, 1000, filterHolder.xH);
        b.a(parcel, 2, filterHolder.GL, n2, false);
        b.a(parcel, 3, filterHolder.GM, n2, false);
        b.a(parcel, 4, filterHolder.GN, n2, false);
        b.a(parcel, 5, filterHolder.GO, n2, false);
        b.a(parcel, 6, filterHolder.GP, n2, false);
        b.F(parcel, n3);
    }

    public FilterHolder[] aL(int n2) {
        return new FilterHolder[n2];
    }

    public FilterHolder ah(Parcel parcel) {
        MatchAllFilter matchAllFilter = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        InFilter inFilter = null;
        NotFilter notFilter = null;
        LogicalFilter logicalFilter = null;
        FieldOnlyFilter fieldOnlyFilter = null;
        ComparisonFilter comparisonFilter = null;
        block9 : while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    continue block9;
                }
                case 1: {
                    comparisonFilter = (ComparisonFilter)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, ComparisonFilter.CREATOR);
                    continue block9;
                }
                case 1000: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    continue block9;
                }
                case 2: {
                    fieldOnlyFilter = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, FieldOnlyFilter.CREATOR);
                    continue block9;
                }
                case 3: {
                    logicalFilter = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, LogicalFilter.CREATOR);
                    continue block9;
                }
                case 4: {
                    notFilter = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, NotFilter.CREATOR);
                    continue block9;
                }
                case 5: {
                    inFilter = (InFilter)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, InFilter.CREATOR);
                    continue block9;
                }
                case 6: 
            }
            matchAllFilter = (MatchAllFilter)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, MatchAllFilter.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new FilterHolder(n3, comparisonFilter, fieldOnlyFilter, logicalFilter, notFilter, inFilter, matchAllFilter);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ah(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aL(n2);
    }
}

