/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.h;

public class MatchAllFilter
implements SafeParcelable,
Filter {
    public static final h CREATOR = new h();
    final int xH;

    public MatchAllFilter() {
        this(1);
    }

    MatchAllFilter(int n2) {
        this.xH = n2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        h.a(this, parcel, n2);
    }
}

