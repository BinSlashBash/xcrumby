package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class OnMetadataResponse implements SafeParcelable {
    public static final Creator<OnMetadataResponse> CREATOR;
    final MetadataBundle EZ;
    final int xH;

    static {
        CREATOR = new af();
    }

    OnMetadataResponse(int versionCode, MetadataBundle metadata) {
        this.xH = versionCode;
        this.EZ = metadata;
    }

    public int describeContents() {
        return 0;
    }

    public MetadataBundle fQ() {
        return this.EZ;
    }

    public void writeToParcel(Parcel dest, int flags) {
        af.m260a(this, dest, flags);
    }
}
