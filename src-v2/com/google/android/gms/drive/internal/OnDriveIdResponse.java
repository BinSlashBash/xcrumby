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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.ab;

public class OnDriveIdResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnDriveIdResponse> CREATOR = new ab();
    DriveId EV;
    final int xH;

    OnDriveIdResponse(int n2, DriveId driveId) {
        this.xH = n2;
        this.EV = driveId;
    }

    public int describeContents() {
        return 0;
    }

    public DriveId getDriveId() {
        return this.EV;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ab.a(this, parcel, n2);
    }
}

