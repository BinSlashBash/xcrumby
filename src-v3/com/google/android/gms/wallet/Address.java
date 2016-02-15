package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@Deprecated
public final class Address implements SafeParcelable {
    public static final Creator<Address> CREATOR;
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

    static {
        CREATOR = new C0542a();
    }

    Address() {
        this.xH = 1;
    }

    Address(int versionCode, String name, String address1, String address2, String address3, String countryCode, String city, String state, String postalCode, String phoneNumber, boolean isPostBox, String companyName) {
        this.xH = versionCode;
        this.name = name;
        this.NB = address1;
        this.NC = address2;
        this.ND = address3;
        this.qd = countryCode;
        this.aba = city;
        this.abb = state;
        this.NI = postalCode;
        this.NK = phoneNumber;
        this.NL = isPostBox;
        this.NM = companyName;
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

    public void writeToParcel(Parcel out, int flags) {
        C0542a.m1488a(this, out, flags);
    }
}
