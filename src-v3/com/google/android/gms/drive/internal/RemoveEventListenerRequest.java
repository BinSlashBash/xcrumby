package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class RemoveEventListenerRequest implements SafeParcelable {
    public static final Creator<RemoveEventListenerRequest> CREATOR;
    final int ES;
    final DriveId Ew;
    final int xH;

    static {
        CREATOR = new ak();
    }

    RemoveEventListenerRequest(int versionCode, DriveId driveId, int eventType) {
        this.xH = versionCode;
        this.Ew = driveId;
        this.ES = eventType;
    }

    public RemoveEventListenerRequest(DriveId id, int eventType) {
        this(1, id, eventType);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ak.m270a(this, dest, flags);
    }
}
