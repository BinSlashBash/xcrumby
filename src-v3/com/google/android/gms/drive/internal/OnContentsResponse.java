package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.Contents;

public class OnContentsResponse implements SafeParcelable {
    public static final Creator<OnContentsResponse> CREATOR;
    final Contents EA;
    final int xH;

    static {
        CREATOR = new C0287z();
    }

    OnContentsResponse(int versionCode, Contents contents) {
        this.xH = versionCode;
        this.EA = contents;
    }

    public int describeContents() {
        return 0;
    }

    public Contents fI() {
        return this.EA;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0287z.m324a(this, dest, flags);
    }
}
