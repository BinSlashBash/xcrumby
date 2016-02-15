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
import com.google.android.gms.drive.internal.h;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;

public class CreateFileRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CreateFileRequest> CREATOR = new h();
    final Contents EX;
    final MetadataBundle EZ;
    final DriveId Fa;
    final int xH;

    CreateFileRequest(int n2, DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this.xH = n2;
        this.Fa = fq.f(driveId);
        this.EZ = fq.f(metadataBundle);
        this.EX = fq.f(contents);
    }

    public CreateFileRequest(DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this(1, driveId, metadataBundle, contents);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        h.a(this, parcel, n2);
    }
}

