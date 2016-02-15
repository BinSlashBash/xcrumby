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
import com.google.android.gms.internal.iw;

public final class iv
implements SafeParcelable {
    public static final Parcelable.Creator<iv> CREATOR = new iw();
    int[] acs;
    private final int xH;

    iv() {
        this(1, null);
    }

    iv(int n2, int[] arrn) {
        this.xH = n2;
        this.acs = arrn;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        iw.a(this, parcel, n2);
    }
}

