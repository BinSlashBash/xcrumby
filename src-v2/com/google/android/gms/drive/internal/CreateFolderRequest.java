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
import com.google.android.gms.drive.internal.i;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;

public class CreateFolderRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CreateFolderRequest> CREATOR = new i();
    final MetadataBundle EZ;
    final DriveId Fa;
    final int xH;

    CreateFolderRequest(int n2, DriveId driveId, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.Fa = fq.f(driveId);
        this.EZ = fq.f(metadataBundle);
    }

    public CreateFolderRequest(DriveId driveId, MetadataBundle metadataBundle) {
        this(1, driveId, metadataBundle);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        i.a(this, parcel, n2);
    }
}

