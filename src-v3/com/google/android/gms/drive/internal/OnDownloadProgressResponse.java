package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnDownloadProgressResponse implements SafeParcelable {
    public static final Creator<OnDownloadProgressResponse> CREATOR;
    final long FF;
    final long FG;
    final int xH;

    static {
        CREATOR = new aa();
    }

    OnDownloadProgressResponse(int versionCode, long bytesLoaded, long bytesExpected) {
        this.xH = versionCode;
        this.FF = bytesLoaded;
        this.FG = bytesExpected;
    }

    public int describeContents() {
        return 0;
    }

    public long fJ() {
        return this.FF;
    }

    public long fK() {
        return this.FG;
    }

    public void writeToParcel(Parcel dest, int flags) {
        aa.m250a(this, dest, flags);
    }
}
