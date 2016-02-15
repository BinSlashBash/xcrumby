package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ix implements SafeParcelable {
    public static final Creator<ix> CREATOR;
    String[] act;
    byte[][] acu;
    private final int xH;

    static {
        CREATOR = new iy();
    }

    ix() {
        this(1, new String[0], new byte[0][]);
    }

    ix(int i, String[] strArr, byte[][] bArr) {
        this.xH = i;
        this.act = strArr;
        this.acu = bArr;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        iy.m1090a(this, out, flags);
    }
}
