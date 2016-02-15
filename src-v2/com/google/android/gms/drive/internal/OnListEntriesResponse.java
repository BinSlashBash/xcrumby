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
import com.google.android.gms.drive.internal.ad;

public class OnListEntriesResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnListEntriesResponse> CREATOR = new ad();
    final DataHolder FJ;
    final boolean Fg;
    final int xH;

    OnListEntriesResponse(int n2, DataHolder dataHolder, boolean bl2) {
        this.xH = n2;
        this.FJ = dataHolder;
        this.Fg = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public DataHolder fN() {
        return this.FJ;
    }

    public boolean fO() {
        return this.Fg;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ad.a(this, parcel, n2);
    }
}

