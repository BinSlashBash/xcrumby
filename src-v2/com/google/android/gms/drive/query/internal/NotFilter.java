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
import com.google.android.gms.drive.query.internal.i;

public class NotFilter
implements SafeParcelable,
Filter {
    public static final Parcelable.Creator<NotFilter> CREATOR = new i();
    final FilterHolder GT;
    final int xH;

    NotFilter(int n2, FilterHolder filterHolder) {
        this.xH = n2;
        this.GT = filterHolder;
    }

    public NotFilter(Filter filter) {
        this(1, new FilterHolder(filter));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        i.a(this, parcel, n2);
    }
}

