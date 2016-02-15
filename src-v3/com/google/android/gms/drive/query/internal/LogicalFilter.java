package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;
import java.util.ArrayList;
import java.util.List;

public class LogicalFilter implements SafeParcelable, Filter {
    public static final Creator<LogicalFilter> CREATOR;
    private List<Filter> GD;
    final Operator GG;
    final List<FilterHolder> GS;
    final int xH;

    static {
        CREATOR = new C0298g();
    }

    LogicalFilter(int versionCode, Operator operator, List<FilterHolder> filterHolders) {
        this.xH = versionCode;
        this.GG = operator;
        this.GS = filterHolders;
    }

    public LogicalFilter(Operator operator, Filter filter, Filter... additionalFilters) {
        this.xH = 1;
        this.GG = operator;
        this.GS = new ArrayList(additionalFilters.length + 1);
        this.GS.add(new FilterHolder(filter));
        this.GD = new ArrayList(additionalFilters.length + 1);
        this.GD.add(filter);
        for (Filter filter2 : additionalFilters) {
            this.GS.add(new FilterHolder(filter2));
            this.GD.add(filter2);
        }
    }

    public LogicalFilter(Operator operator, Iterable<Filter> filters) {
        this.xH = 1;
        this.GG = operator;
        this.GD = new ArrayList();
        this.GS = new ArrayList();
        for (Filter filter : filters) {
            this.GD.add(filter);
            this.GS.add(new FilterHolder(filter));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0298g.m341a(this, out, flags);
    }
}
