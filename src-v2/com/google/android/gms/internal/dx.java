/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.dy;

public final class dx
implements SafeParcelable {
    public static final dy CREATOR = new dy();
    public String rq;
    public int rr;
    public int rs;
    public boolean rt;
    public final int versionCode;

    /*
     * Enabled aggressive block sorting
     */
    public dx(int n2, int n3, boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder().append("afma-sdk-a-v").append(n2).append(".").append(n3).append(".");
        String string2 = bl2 ? "0" : "1";
        this(1, stringBuilder.append(string2).toString(), n2, n3, bl2);
    }

    dx(int n2, String string2, int n3, int n4, boolean bl2) {
        this.versionCode = n2;
        this.rq = string2;
        this.rr = n3;
        this.rs = n4;
        this.rt = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        dy.a(this, parcel, n2);
    }
}

