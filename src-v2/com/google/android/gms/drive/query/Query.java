/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.a;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.MatchAllFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.ArrayList;
import java.util.List;

public class Query
implements SafeParcelable {
    public static final Parcelable.Creator<Query> CREATOR = new a();
    final LogicalFilter GA;
    final String GB;
    final SortOrder GC;
    final int xH;

    Query(int n2, LogicalFilter logicalFilter, String string2, SortOrder sortOrder) {
        this.xH = n2;
        this.GA = logicalFilter;
        this.GB = string2;
        this.GC = sortOrder;
    }

    Query(LogicalFilter logicalFilter, String string2, SortOrder sortOrder) {
        this(1, logicalFilter, string2, sortOrder);
    }

    public int describeContents() {
        return 0;
    }

    public SortOrder fV() {
        return this.GC;
    }

    public Filter getFilter() {
        return this.GA;
    }

    public String getPageToken() {
        return this.GB;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }

    public static class Builder {
        private String GB;
        private SortOrder GC;
        private final List<Filter> GD = new ArrayList<Filter>();

        public Builder a(SortOrder sortOrder) {
            this.GC = sortOrder;
            return this;
        }

        public Builder addFilter(Filter filter) {
            if (!(filter instanceof MatchAllFilter)) {
                this.GD.add(filter);
            }
            return this;
        }

        public Query build() {
            return new Query(new LogicalFilter(Operator.GZ, this.GD), this.GB, this.GC);
        }

        public Builder setPageToken(String string2) {
            this.GB = string2;
            return this;
        }
    }

}

