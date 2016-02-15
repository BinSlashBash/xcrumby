package com.google.android.gms.identity.intents.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.AddressConstants.Extras;

public final class UserAddress implements SafeParcelable {
    public static final Creator<UserAddress> CREATOR;
    String NB;
    String NC;
    String ND;
    String NE;
    String NF;
    String NG;
    String NH;
    String NI;
    String NJ;
    String NK;
    boolean NL;
    String NM;
    String NN;
    String name;
    String qd;
    private final int xH;

    static {
        CREATOR = new C0320b();
    }

    UserAddress() {
        this.xH = 1;
    }

    UserAddress(int versionCode, String name, String address1, String address2, String address3, String address4, String address5, String administrativeArea, String locality, String countryCode, String postalCode, String sortingCode, String phoneNumber, boolean isPostBox, String companyName, String emailAddress) {
        this.xH = versionCode;
        this.name = name;
        this.NB = address1;
        this.NC = address2;
        this.ND = address3;
        this.NE = address4;
        this.NF = address5;
        this.NG = administrativeArea;
        this.NH = locality;
        this.qd = countryCode;
        this.NI = postalCode;
        this.NJ = sortingCode;
        this.NK = phoneNumber;
        this.NL = isPostBox;
        this.NM = companyName;
        this.NN = emailAddress;
    }

    public static UserAddress fromIntent(Intent data) {
        return (data == null || !data.hasExtra(Extras.EXTRA_ADDRESS)) ? null : (UserAddress) data.getParcelableExtra(Extras.EXTRA_ADDRESS);
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

    public String getAddress4() {
        return this.NE;
    }

    public String getAddress5() {
        return this.NF;
    }

    public String getAdministrativeArea() {
        return this.NG;
    }

    public String getCompanyName() {
        return this.NM;
    }

    public String getCountryCode() {
        return this.qd;
    }

    public String getEmailAddress() {
        return this.NN;
    }

    public String getLocality() {
        return this.NH;
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

    public String getSortingCode() {
        return this.NJ;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public boolean isPostBox() {
        return this.NL;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0320b.m588a(this, out, flags);
    }
}
