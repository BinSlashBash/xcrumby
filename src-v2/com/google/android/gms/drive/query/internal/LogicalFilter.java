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
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.Operator;
import com.google.android.gms.drive.query.internal.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogicalFilter
implements SafeParcelable,
Filter {
    public static final Parcelable.Creator<LogicalFilter> CREATOR = new g();
    private List<Filter> GD;
    final Operator GG;
    final List<FilterHolder> GS;
    final int xH;

    LogicalFilter(int n2, Operator operator, List<FilterHolder> list) {
        this.xH = n2;
        this.GG = operator;
        this.GS = list;
    }

    public /* varargs */ LogicalFilter(Operator object2, Filter filter, Filter ... arrfilter) {
        void var2_4;
        void var3_5;
        this.xH = 1;
        this.GG = object2;
        this.GS = new ArrayList<FilterHolder>(var3_5.length + 1);
        this.GS.add(new FilterHolder((Filter)var2_4));
        this.GD = new ArrayList<Filter>(var3_5.length + 1);
        this.GD.add((Filter)var2_4);
        for (void var1_3 : var3_5) {
            this.GS.add(new FilterHolder((Filter)var1_3));
            this.GD.add((Filter)var1_3);
        }
    }

    public LogicalFilter(Operator object, Iterable<Filter> object2) {
        this.xH = 1;
        this.GG = object;
        this.GD = new ArrayList<Filter>();
        this.GS = new ArrayList<FilterHolder>();
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = (Filter)object.next();
            this.GD.add((Filter)object2);
            this.GS.add(new FilterHolder((Filter)object2));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        g.a(this, parcel, n2);
    }
}

