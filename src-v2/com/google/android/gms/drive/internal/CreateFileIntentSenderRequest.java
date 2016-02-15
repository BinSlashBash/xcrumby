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
import com.google.android.gms.drive.internal.g;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class CreateFileIntentSenderRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CreateFileIntentSenderRequest> CREATOR = new g();
    final String EB;
    final DriveId EC;
    final MetadataBundle EZ;
    final int Eu;
    final int xH;

    CreateFileIntentSenderRequest(int n2, MetadataBundle metadataBundle, int n3, String string2, DriveId driveId) {
        this.xH = n2;
        this.EZ = metadataBundle;
        this.Eu = n3;
        this.EB = string2;
        this.EC = driveId;
    }

    public CreateFileIntentSenderRequest(MetadataBundle metadataBundle, int n2, String string2, DriveId driveId) {
        this(1, metadataBundle, n2, string2, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        g.a(this, parcel, n2);
    }
}

