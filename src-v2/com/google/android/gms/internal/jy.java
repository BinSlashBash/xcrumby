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
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jz;

public final class jy
implements SafeParcelable {
    public static final Parcelable.Creator<jy> CREATOR = new jz();
    String adn;
    ju adr;
    jw ads;
    jw adt;
    String pm;
    private final int xH;

    jy() {
        this.xH = 1;
    }

    jy(int n2, String string2, String string3, ju ju2, jw jw2, jw jw3) {
        this.xH = n2;
        this.adn = string2;
        this.pm = string3;
        this.adr = ju2;
        this.ads = jw2;
        this.adt = jw3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        jz.a(this, parcel, n2);
    }
}

