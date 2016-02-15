/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wallet.c;

@Deprecated
public class CountrySpecification
implements SafeParcelable {
    public static final Parcelable.Creator<CountrySpecification> CREATOR = new c();
    String qd;
    private final int xH;

    CountrySpecification(int n2, String string2) {
        this.xH = n2;
        this.qd = string2;
    }

    public CountrySpecification(String string2) {
        this.xH = 1;
        this.qd = string2;
    }

    public int describeContents() {
        return 0;
    }

    public String getCountryCode() {
        return this.qd;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        c.a(this, parcel, n2);
    }
}

