package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;

public class FilterHolder implements SafeParcelable {
    public static final Creator<FilterHolder> CREATOR;
    final ComparisonFilter<?> GK;
    final FieldOnlyFilter GL;
    final LogicalFilter GM;
    final NotFilter GN;
    final InFilter<?> GO;
    final MatchAllFilter GP;
    private final Filter GQ;
    final int xH;

    static {
        CREATOR = new C0295d();
    }

    FilterHolder(int versionCode, ComparisonFilter<?> comparisonField, FieldOnlyFilter fieldOnlyFilter, LogicalFilter logicalFilter, NotFilter notFilter, InFilter<?> containsFilter, MatchAllFilter matchAllFilter) {
        this.xH = versionCode;
        this.GK = comparisonField;
        this.GL = fieldOnlyFilter;
        this.GM = logicalFilter;
        this.GN = notFilter;
        this.GO = containsFilter;
        this.GP = matchAllFilter;
        if (this.GK != null) {
            this.GQ = this.GK;
        } else if (this.GL != null) {
            this.GQ = this.GL;
        } else if (this.GM != null) {
            this.GQ = this.GM;
        } else if (this.GN != null) {
            this.GQ = this.GN;
        } else if (this.GO != null) {
            this.GQ = this.GO;
        } else if (this.GP != null) {
            this.GQ = this.GP;
        } else {
            throw new IllegalArgumentException("At least one filter must be set.");
        }
    }

    public FilterHolder(Filter filter) {
        this.xH = 1;
        this.GK = filter instanceof ComparisonFilter ? (ComparisonFilter) filter : null;
        this.GL = filter instanceof FieldOnlyFilter ? (FieldOnlyFilter) filter : null;
        this.GM = filter instanceof LogicalFilter ? (LogicalFilter) filter : null;
        this.GN = filter instanceof NotFilter ? (NotFilter) filter : null;
        this.GO = filter instanceof InFilter ? (InFilter) filter : null;
        this.GP = filter instanceof MatchAllFilter ? (MatchAllFilter) filter : null;
        if (this.GK == null && this.GL == null && this.GM == null && this.GN == null && this.GO == null && this.GP == null) {
            throw new IllegalArgumentException("Invalid filter type or null filter.");
        }
        this.GQ = filter;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0295d.m338a(this, out, flags);
    }
}
