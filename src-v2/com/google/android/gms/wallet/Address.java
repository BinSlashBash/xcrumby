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
import com.google.android.gms.wallet.a;

@Deprecated
public final class Address
implements SafeParcelable {
    public static final Parcelable.Creator<Address> CREATOR = new a();
    String NB;
    String NC;
    String ND;
    String NI;
    String NK;
    boolean NL;
    String NM;
    String aba;
    String abb;
    String name;
    String qd;
    private final int xH;

    Address() {
        this.xH = 1;
    }

    Address(int n2, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, boolean bl2, String string11) {
        this.xH = n2;
        this.name = string2;
        this.NB = string3;
        this.NC = string4;
        this.ND = string5;
        this.qd = string6;
        this.aba = string7;
        this.abb = string8;
        this.NI = string9;
        this.NK = string10;
        this.NL = bl2;
        this.NM = string11;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress1() {
        return this.NB;
    }

    public String getAddress2() {
        return this.NC;
    }

    public String getAddress3() {
        return this.ND;
    }

    public String getCity() {
        return this.aba;
    }

    public String getCompanyName() {
        return this.NM;
    }

    public String getCountryCode() {
        return this.qd;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.NK;
    }

    public String getPostalCode() {
        return this.NI;
    }

    public String getState() {
        return this.abb;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public boolean isPostBox() {
        return this.NL;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }
}

