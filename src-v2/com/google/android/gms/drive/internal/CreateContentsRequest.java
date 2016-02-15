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
import com.google.android.gms.drive.internal.f;

public class CreateContentsRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CreateContentsRequest> CREATOR = new f();
    final int xH;

    public CreateContentsRequest() {
        this(1);
    }

    CreateContentsRequest(int n2) {
        this.xH = n2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        f.a(this, parcel, n2);
    }
}

