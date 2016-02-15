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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.android.gms.drive.query.internal.FieldOnlyFilter;
import com.google.android.gms.drive.query.internal.InFilter;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.MatchAllFilter;
import com.google.android.gms.drive.query.internal.NotFilter;
import com.google.android.gms.drive.query.internal.d;

public class FilterHolder
implements SafeParcelable {
    public static final Parcelable.Creator<FilterHolder> CREATOR = new d();
    final ComparisonFilter<?> GK;
    final FieldOnlyFilter GL;
    final LogicalFilter GM;
    final NotFilter GN;
    final InFilter<?> GO;
    final MatchAllFilter GP;
    private final Filter GQ;
    final int xH;

    FilterHolder(int n2, ComparisonFilter<?> comparisonFilter, FieldOnlyFilter fieldOnlyFilter, LogicalFilter logicalFilter, NotFilter notFilter, InFilter<?> inFilter, MatchAllFilter matchAllFilter) {
        this.xH = n2;
        this.GK = comparisonFilter;
        this.GL = fieldOnlyFilter;
        this.GM = logicalFilter;
        this.GN = notFilter;
        this.GO = inFilter;
        this.GP = matchAllFilter;
        if (this.GK != null) {
            this.GQ = this.GK;
            return;
        }
        if (this.GL != null) {
            this.GQ = this.GL;
            return;
        }
        if (this.GM != null) {
            this.GQ = this.GM;
            return;
        }
        if (this.GN != null) {
            this.GQ = this.GN;
            return;
        }
        if (this.GO != null) {
            this.GQ = this.GO;
            return;
        }
        if (this.GP != null) {
            this.GQ = this.GP;
            return;
        }
        throw new IllegalArgumentException("At least one filter must be set.");
    }

    /*
     * Enabled aggressive block sorting
     */
    public FilterHolder(Filter filter) {
        void var2_5;
        void var2_7;
        void var2_13;
        void var2_11;
        void var2_3;
        void var2_9;
        this.xH = 1;
        if (filter instanceof ComparisonFilter) {
            ComparisonFilter comparisonFilter = (ComparisonFilter)filter;
        } else {
            Object var2_14 = null;
        }
        this.GK = var2_3;
        if (filter instanceof FieldOnlyFilter) {
            FieldOnlyFilter fieldOnlyFilter = (FieldOnlyFilter)filter;
        } else {
            Object var2_15 = null;
        }
        this.GL = var2_5;
        if (filter instanceof LogicalFilter) {
            LogicalFilter logicalFilter = (LogicalFilter)filter;
        } else {
            Object var2_16 = null;
        }
        this.GM = var2_7;
        if (filter instanceof NotFilter) {
            NotFilter notFilter = (NotFilter)filter;
        } else {
            Object var2_17 = null;
        }
        this.GN = var2_9;
        if (filter instanceof InFilter) {
            InFilter inFilter = (InFilter)filter;
        } else {
            Object var2_18 = null;
        }
        this.GO = var2_11;
        if (filter instanceof MatchAllFilter) {
            MatchAllFilter matchAllFilter = (MatchAllFilter)filter;
        } else {
            Object var2_19 = null;
        }
        this.GP = var2_13;
        if (this.GK == null && this.GL == null && this.GM == null && this.GN == null && this.GO == null && this.GP == null) {
            throw new IllegalArgumentException("Invalid filter type or null filter.");
        }
        this.GQ = filter;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        d.a(this, parcel, n2);
    }
}

