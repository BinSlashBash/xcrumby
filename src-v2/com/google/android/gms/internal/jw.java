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
import com.google.android.gms.internal.jx;

public final class jw
implements SafeParcelable {
    public static final Parcelable.Creator<jw> CREATOR = new jx();
    String adq;
    String description;
    private final int xH;

    jw() {
        this.xH = 1;
    }

    jw(int n2, String string2, String string3) {
        this.xH = n2;
        this.adq = string2;
        this.description = string3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jx.a(this, parcel, n2);
    }
}

