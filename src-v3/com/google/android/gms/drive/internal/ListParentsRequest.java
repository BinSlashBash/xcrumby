package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class ListParentsRequest implements SafeParcelable {
    public static final Creator<ListParentsRequest> CREATOR;
    final DriveId FB;
    final int xH;

    static {
        CREATOR = new C0286x();
    }

    ListParentsRequest(int versionCode, DriveId driveId) {
        this.xH = versionCode;
        this.FB = driveId;
    }

    public ListParentsRequest(DriveId id) {
        this(1, id);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0286x.m322a(this, dest, flags);
    }
}
