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
import com.google.android.gms.drive.internal.af;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class OnMetadataResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnMetadataResponse> CREATOR = new af();
    final MetadataBundle EZ;
    final int xH;

    OnMetadataResponse(int n2, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.EZ = metadataBundle;
    }

    public int describeContents() {
        return 0;
    }

    public MetadataBundle fQ() {
        return this.EZ;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        af.a(this, parcel, n2);
    }
}

