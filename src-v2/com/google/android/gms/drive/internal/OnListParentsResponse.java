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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.ae;

public class OnListParentsResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnListParentsResponse> CREATOR = new ae();
    final DataHolder FK;
    final int xH;

    OnListParentsResponse(int n2, DataHolder dataHolder) {
        this.xH = n2;
        this.FK = dataHolder;
    }

    public int describeContents() {
        return 0;
    }

    public DataHolder fP() {
        return this.FK;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ae.a(this, parcel, n2);
    }
}

