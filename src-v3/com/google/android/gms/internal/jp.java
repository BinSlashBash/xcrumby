package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class jp implements SafeParcelable {
    public static final Creator<jp> CREATOR;
    int adh;
    String adi;
    double adj;
    String adk;
    long adl;
    int adm;
    private final int xH;

    static {
        CREATOR = new jq();
    }

    jp() {
        this.xH = 1;
        this.adm = -1;
        this.adh = -1;
        this.adj = -1.0d;
    }

    jp(int i, int i2, String str, double d, String str2, long j, int i3) {
        this.xH = i;
        this.adh = i2;
        this.adi = str;
        this.adj = d;
        this.adk = str2;
        this.adl = j;
        this.adm = i3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        jq.m1110a(this, dest, flags);
    }
}
