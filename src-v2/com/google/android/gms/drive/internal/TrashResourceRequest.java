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
import com.google.android.gms.drive.internal.am;

public class TrashResourceRequest
implements SafeParcelable {
    public static final Parcelable.Creator<TrashResourceRequest> CREATOR = new am();
    final DriveId EV;
    final int xH;

    TrashResourceRequest(int n2, DriveId driveId) {
        this.xH = n2;
        this.EV = driveId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        am.a(this, parcel, n2);
    }
}

