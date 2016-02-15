package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnListParentsResponse implements SafeParcelable {
    public static final Creator<OnListParentsResponse> CREATOR;
    final DataHolder FK;
    final int xH;

    static {
        CREATOR = new ae();
    }

    OnListParentsResponse(int versionCode, DataHolder parents) {
        this.xH = versionCode;
        this.FK = parents;
    }

    public int describeContents() {
        return 0;
    }

    public DataHolder fP() {
        return this.FK;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ae.m258a(this, dest, flags);
    }
}
