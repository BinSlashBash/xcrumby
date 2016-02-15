/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.jp;
import com.google.android.gms.internal.jr;
import com.google.android.gms.internal.ju;

public final class jo
implements SafeParcelable {
    public static final Parcelable.Creator<jo> CREATOR = new jr();
    ju abJ;
    jp adg;
    String label;
    String type;
    private final int xH;

    jo() {
        this.xH = 1;
    }

    jo(int n2, String string2, jp jp2, String string3, ju ju2) {
        this.xH = n2;
        this.label = string2;
        this.adg = jp2;
        this.type = string3;
        this.abJ = ju2;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jr.a(this, parcel, n2);
    }
}

