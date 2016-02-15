/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.b;

public final class UserAddress
implements SafeParcelable {
    public static final Parcelable.Creator<UserAddress> CREATOR = new b();
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

    UserAddress() {
        this.xH = 1;
    }

    UserAddress(int n2, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, boolean bl2, String string14, String string15) {
        this.xH = n2;
        this.name = string2;
        this.NB = string3;
        this.NC = string4;
        this.ND = string5;
        this.NE = string6;
        this.NF = string7;
        this.NG = string8;
        this.NH = string9;
        this.qd = string10;
        this.NI = string11;
        this.NJ = string12;
        this.NK = string13;
        this.NL = bl2;
        this.NM = string14;
        this.NN = string15;
    }

    public static UserAddress fromIntent(Intent intent) {
        if (intent == null || !intent.hasExtra("com.google.android.gms.identity.intents.EXTRA_ADDRESS")) {
            return null;
        }
        return (UserAddress)intent.getParcelableExtra("com.google.android.gms.identity.intents.EXTRA_ADDRESS");
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

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

