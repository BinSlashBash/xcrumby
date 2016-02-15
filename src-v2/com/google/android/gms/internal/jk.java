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
import com.google.android.gms.internal.jl;

public final class jk
implements SafeParcelable {
    public static final Parcelable.Creator<jk> CREATOR = new jl();
    String label;
    String value;
    private final int xH;

    jk() {
        this.xH = 1;
    }

    jk(int n2, String string2, String string3) {
        this.xH = n2;
        this.label = string2;
        this.value = string3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jl.a(this, parcel, n2);
    }
}

