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
import com.google.android.gms.drive.internal.ak;

public class RemoveEventListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<RemoveEventListenerRequest> CREATOR = new ak();
    final int ES;
    final DriveId Ew;
    final int xH;

    RemoveEventListenerRequest(int n2, DriveId driveId, int n3) {
        this.xH = n2;
        this.Ew = driveId;
        this.ES = n3;
    }

    public RemoveEventListenerRequest(DriveId driveId, int n2) {
        this(1, driveId, n2);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ak.a(this, parcel, n2);
    }
}

