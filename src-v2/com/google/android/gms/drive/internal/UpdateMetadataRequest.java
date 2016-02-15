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
import com.google.android.gms.drive.internal.an;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class UpdateMetadataRequest
implements SafeParcelable {
    public static final Parcelable.Creator<UpdateMetadataRequest> CREATOR = new an();
    final DriveId EV;
    final MetadataBundle EW;
    final int xH;

    UpdateMetadataRequest(int n2, DriveId driveId, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.EV = driveId;
        this.EW = metadataBundle;
    }

    public UpdateMetadataRequest(DriveId driveId, MetadataBundle metadataBundle) {
        this(1, driveId, metadataBundle);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        an.a(this, parcel, n2);
    }
}

