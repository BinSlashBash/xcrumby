package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class jw implements SafeParcelable {
    public static final Creator<jw> CREATOR;
    String adq;
    String description;
    private final int xH;

    static {
        CREATOR = new jx();
    }

    jw() {
        this.xH = 1;
    }

    jw(int i, String str, String str2) {
        this.xH = i;
        this.adq = str;
        this.description = str2;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        jx.m1114a(this, dest, flags);
    }
}
