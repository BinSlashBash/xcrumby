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
import com.google.android.gms.internal.jq;

public final class jp
implements SafeParcelable {
    public static final Parcelable.Creator<jp> CREATOR = new jq();
    int adh;
    String adi;
    double adj;
    String adk;
    long adl;
    int adm;
    private final int xH;

    jp() {
        this.xH = 1;
        this.adm = -1;
        this.adh = -1;
        this.adj = -1.0;
    }

    jp(int n2, int n3, String string2, double d2, String string3, long l2, int n4) {
        this.xH = n2;
        this.adh = n3;
        this.adi = string2;
        this.adj = d2;
        this.adk = string3;
        this.adl = l2;
        this.adm = n4;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jq.a(this, parcel, n2);
    }
}

