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
import com.google.android.gms.internal.iy;

public final class ix
implements SafeParcelable {
    public static final Parcelable.Creator<ix> CREATOR = new iy();
    String[] act;
    byte[][] acu;
    private final int xH;

    ix() {
        this(1, new String[0], new byte[0][]);
    }

    ix(int n2, String[] arrstring, byte[][] arrby) {
        this.xH = n2;
        this.act = arrstring;
        this.acu = arrby;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        iy.a(this, parcel, n2);
    }
}

