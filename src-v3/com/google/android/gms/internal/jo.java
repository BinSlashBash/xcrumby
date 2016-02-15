package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class jo implements SafeParcelable {
    public static final Creator<jo> CREATOR;
    ju abJ;
    jp adg;
    String label;
    String type;
    private final int xH;

    static {
        CREATOR = new jr();
    }

    jo() {
        this.xH = 1;
    }

    jo(int i, String str, jp jpVar, String str2, ju juVar) {
        this.xH = i;
        this.label = str;
        this.adg = jpVar;
        this.type = str2;
        this.abJ = juVar;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        jr.m1111a(this, dest, flags);
    }
}
