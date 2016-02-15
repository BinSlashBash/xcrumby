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
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jk;
import com.google.android.gms.internal.jn;
import java.util.ArrayList;

public final class jm
implements SafeParcelable {
    public static final Parcelable.Creator<jm> CREATOR = new jn();
    String add;
    String ade;
    ArrayList<jk> adf;
    private final int xH;

    jm() {
        this.xH = 1;
        this.adf = gi.fs();
    }

    jm(int n2, String string2, String string3, ArrayList<jk> arrayList) {
        this.xH = n2;
        this.add = string2;
        this.ade = string3;
        this.adf = arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jn.a(this, parcel, n2);
    }
}

