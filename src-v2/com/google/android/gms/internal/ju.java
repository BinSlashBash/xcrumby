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
import com.google.android.gms.internal.jv;

public final class ju
implements SafeParcelable {
    public static final Parcelable.Creator<ju> CREATOR = new jv();
    long ado;
    long adp;
    private final int xH;

    ju() {
        this.xH = 1;
    }

    ju(int n2, long l2, long l3) {
        this.xH = n2;
        this.ado = l2;
        this.adp = l3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jv.a(this, parcel, n2);
    }
}

