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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.internal.z;

public class OnContentsResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnContentsResponse> CREATOR = new z();
    final Contents EA;
    final int xH;

    OnContentsResponse(int n2, Contents contents) {
        this.xH = n2;
        this.EA = contents;
    }

    public int describeContents() {
        return 0;
    }

    public Contents fI() {
        return this.EA;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        z.a(this, parcel, n2);
    }
}

