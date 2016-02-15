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
import com.google.android.gms.internal.jt;

public final class js
implements SafeParcelable {
    public static final Parcelable.Creator<js> CREATOR = new jt();
    String adn;
    String pm;
    private final int xH;

    js() {
        this.xH = 1;
    }

    js(int n2, String string2, String string3) {
        this.xH = n2;
        this.adn = string2;
        this.pm = string3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jt.a(this, parcel, n2);
    }
}

