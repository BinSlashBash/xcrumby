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
import com.google.android.gms.drive.query.b;
import com.google.android.gms.drive.query.internal.FieldWithSortOrder;
import java.util.List;

public class SortOrder
implements SafeParcelable {
    public static final Parcelable.Creator<SortOrder> CREATOR = new b();
    final List<FieldWithSortOrder> GF;
    final int xH;

    SortOrder(int n2, List<FieldWithSortOrder> list) {
        this.xH = n2;
        this.GF = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

