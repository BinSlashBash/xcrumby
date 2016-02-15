package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class iv implements SafeParcelable {
    public static final Creator<iv> CREATOR;
    int[] acs;
    private final int xH;

    static {
        CREATOR = new iw();
    }

    iv() {
        this(1, null);
    }

    iv(int i, int[] iArr) {
        this.xH = i;
        this.acs = iArr;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        iw.m1089a(this, out, flags);
    }
}
