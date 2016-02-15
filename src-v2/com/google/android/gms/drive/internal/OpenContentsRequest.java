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
import com.google.android.gms.drive.internal.ah;

public class OpenContentsRequest
implements SafeParcelable {
    public static final Parcelable.Creator<OpenContentsRequest> CREATOR = new ah();
    final DriveId EV;
    final int Ev;
    final int xH;

    OpenContentsRequest(int n2, DriveId driveId, int n3) {
        this.xH = n2;
        this.EV = driveId;
        this.Ev = n3;
    }

    public OpenContentsRequest(DriveId driveId, int n2) {
        this(1, driveId, n2);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ah.a(this, parcel, n2);
    }
}

