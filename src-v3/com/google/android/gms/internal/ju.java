package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ju implements SafeParcelable {
    public static final Creator<ju> CREATOR;
    long ado;
    long adp;
    private final int xH;

    static {
        CREATOR = new jv();
    }

    ju() {
        this.xH = 1;
    }

    ju(int i, long j, long j2) {
        this.xH = i;
        this.ado = j;
        this.adp = j2;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        jv.m1113a(this, dest, flags);
    }
}
