/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ca;

public final class cb
implements SafeParcelable {
    public static final ca CREATOR = new ca();
    public final String mimeType;
    public final String nN;
    public final String nO;
    public final String nP;
    public final String nQ;
    public final String nR;
    public final String packageName;
    public final int versionCode;

    public cb(int n2, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        this.versionCode = n2;
        this.nN = string2;
        this.nO = string3;
        this.mimeType = string4;
        this.packageName = string5;
        this.nP = string6;
        this.nQ = string7;
        this.nR = string8;
    }

    public cb(String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        this(1, string2, string3, string4, string5, string6, string7, string8);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ca.a(this, parcel, n2);
    }
}

