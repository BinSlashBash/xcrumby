package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class dx implements SafeParcelable {
    public static final dy CREATOR;
    public String rq;
    public int rr;
    public int rs;
    public boolean rt;
    public final int versionCode;

    static {
        CREATOR = new dy();
    }

    public dx(int i, int i2, boolean z) {
        this(1, "afma-sdk-a-v" + i + "." + i2 + "." + (z ? "0" : "1"), i, i2, z);
    }

    dx(int i, String str, int i2, int i3, boolean z) {
        this.versionCode = i;
        this.rq = str;
        this.rr = i2;
        this.rs = i3;
        this.rt = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        dy.m824a(this, out, flags);
    }
}
