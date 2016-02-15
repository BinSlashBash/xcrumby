package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class hi implements SafeParcelable {
    public static final hj CREATOR;
    private final String OE;
    private final String mTag;
    final int xH;

    static {
        CREATOR = new hj();
    }

    hi(int i, String str, String str2) {
        this.xH = i;
        this.OE = str;
        this.mTag = str2;
    }

    public int describeContents() {
        hj hjVar = CREATOR;
        return 0;
    }

    public boolean equals(Object that) {
        if (!(that instanceof hi)) {
            return false;
        }
        hi hiVar = (hi) that;
        return fo.equal(this.OE, hiVar.OE) && fo.equal(this.mTag, hiVar.mTag);
    }

    public String getTag() {
        return this.mTag;
    }

    public String hY() {
        return this.OE;
    }

    public int hashCode() {
        return fo.hashCode(this.OE, this.mTag);
    }

    public String toString() {
        return fo.m976e(this).m975a("mPlaceId", this.OE).m975a("mTag", this.mTag).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        hj hjVar = CREATOR;
        hj.m1068a(this, out, flags);
    }
}
