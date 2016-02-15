package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class OpenContentsRequest implements SafeParcelable {
    public static final Creator<OpenContentsRequest> CREATOR;
    final DriveId EV;
    final int Ev;
    final int xH;

    static {
        CREATOR = new ah();
    }

    OpenContentsRequest(int versionCode, DriveId id, int mode) {
        this.xH = versionCode;
        this.EV = id;
        this.Ev = mode;
    }

    public OpenContentsRequest(DriveId id, int mode) {
        this(1, id, mode);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ah.m264a(this, dest, flags);
    }
}
