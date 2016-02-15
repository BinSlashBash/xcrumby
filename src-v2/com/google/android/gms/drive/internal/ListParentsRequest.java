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
import com.google.android.gms.drive.internal.x;

public class ListParentsRequest
implements SafeParcelable {
    public static final Parcelable.Creator<ListParentsRequest> CREATOR = new x();
    final DriveId FB;
    final int xH;

    ListParentsRequest(int n2, DriveId driveId) {
        this.xH = n2;
        this.FB = driveId;
    }

    public ListParentsRequest(DriveId driveId) {
        this(1, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        x.a(this, parcel, n2);
    }
}

