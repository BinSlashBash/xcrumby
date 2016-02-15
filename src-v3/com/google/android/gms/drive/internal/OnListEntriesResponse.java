package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnListEntriesResponse implements SafeParcelable {
    public static final Creator<OnListEntriesResponse> CREATOR;
    final DataHolder FJ;
    final boolean Fg;
    final int xH;

    static {
        CREATOR = new ad();
    }

    OnListEntriesResponse(int versionCode, DataHolder entries, boolean moreEntriesMayExist) {
        this.xH = versionCode;
        this.FJ = entries;
        this.Fg = moreEntriesMayExist;
    }

    public int describeContents() {
        return 0;
    }

    public DataHolder fN() {
        return this.FJ;
    }

    public boolean fO() {
        return this.Fg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ad.m256a(this, dest, flags);
    }
}
