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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.d;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class CloseContentsAndUpdateMetadataRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CloseContentsAndUpdateMetadataRequest> CREATOR = new d();
    final DriveId EV;
    final MetadataBundle EW;
    final Contents EX;
    final int xH;

    CloseContentsAndUpdateMetadataRequest(int n2, DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this.xH = n2;
        this.EV = driveId;
        this.EW = metadataBundle;
        this.EX = contents;
    }

    public CloseContentsAndUpdateMetadataRequest(DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this(1, driveId, metadataBundle, contents);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        d.a(this, parcel, n2);
    }
}

