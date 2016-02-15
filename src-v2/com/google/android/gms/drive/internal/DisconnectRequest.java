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
import com.google.android.gms.drive.internal.k;

public class DisconnectRequest
implements SafeParcelable {
    public static final Parcelable.Creator<DisconnectRequest> CREATOR = new k();
    final int xH;

    public DisconnectRequest() {
        this(1);
    }

    DisconnectRequest(int n2) {
        this.xH = n2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        k.a(this, parcel, n2);
    }
}

