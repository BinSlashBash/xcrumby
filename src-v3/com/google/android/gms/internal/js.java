package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class js implements SafeParcelable {
    public static final Creator<js> CREATOR;
    String adn;
    String pm;
    private final int xH;

    static {
        CREATOR = new jt();
    }

    js() {
        this.xH = 1;
    }

    js(int i, String str, String str2) {
        this.xH = i;
        this.adn = str;
        this.pm = str2;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        jt.m1112a(this, dest, flags);
    }
}
