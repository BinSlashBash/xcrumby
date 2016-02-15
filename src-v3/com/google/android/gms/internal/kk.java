package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.C0569f;

public class kk implements SafeParcelable, C0569f {
    public static final Creator<kk> CREATOR;
    private final String HA;
    private final String wp;
    final int xH;

    static {
        CREATOR = new kl();
    }

    kk(int i, String str, String str2) {
        this.xH = i;
        this.wp = str;
        this.HA = str2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (!(o instanceof kk)) {
            return false;
        }
        kk kkVar = (kk) o;
        return kkVar.wp.equals(this.wp) && kkVar.HA.equals(this.HA);
    }

    public String getDisplayName() {
        return this.HA;
    }

    public String getId() {
        return this.wp;
    }

    public int hashCode() {
        return ((this.wp.hashCode() + 629) * 37) + this.HA.hashCode();
    }

    public String toString() {
        return "NodeParcelable{" + this.wp + "," + this.HA + "}";
    }

    public void writeToParcel(Parcel dest, int flags) {
        kl.m1121a(this, dest, flags);
    }
}
