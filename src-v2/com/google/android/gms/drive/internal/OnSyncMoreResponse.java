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
import com.google.android.gms.drive.internal.ag;

public class OnSyncMoreResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnSyncMoreResponse> CREATOR = new ag();
    final boolean Fg;
    final int xH;

    OnSyncMoreResponse(int n2, boolean bl2) {
        this.xH = n2;
        this.Fg = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ag.a(this, parcel, n2);
    }
}

