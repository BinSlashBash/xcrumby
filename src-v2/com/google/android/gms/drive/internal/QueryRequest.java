/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.aj;
import com.google.android.gms.drive.query.Query;

public class QueryRequest
implements SafeParcelable {
    public static final Parcelable.Creator<QueryRequest> CREATOR = new aj();
    final Query FL;
    final int xH;

    QueryRequest(int n2, Query query) {
        this.xH = n2;
        this.FL = query;
    }

    public QueryRequest(Query query) {
        this(1, query);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        aj.a(this, parcel, n2);
    }
}

