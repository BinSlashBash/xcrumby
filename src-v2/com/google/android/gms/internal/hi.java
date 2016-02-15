/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.hj;

public class hi
implements SafeParcelable {
    public static final hj CREATOR = new hj();
    private final String OE;
    private final String mTag;
    final int xH;

    hi(int n2, String string2, String string3) {
        this.xH = n2;
        this.OE = string2;
        this.mTag = string3;
    }

    public int describeContents() {
        hj hj2 = CREATOR;
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof hi)) {
            return false;
        }
        object = (hi)object;
        if (!fo.equal(this.OE, object.OE)) return false;
        if (!fo.equal(this.mTag, object.mTag)) return false;
        return true;
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
        return fo.e(this).a("mPlaceId", this.OE).a("mTag", this.mTag).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        hj hj2 = CREATOR;
        hj.a(this, parcel, n2);
    }
}

