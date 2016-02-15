package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ProxyCard implements SafeParcelable {
    public static final Creator<ProxyCard> CREATOR;
    String ack;
    String acl;
    int acm;
    int acn;
    private final int xH;

    static {
        CREATOR = new C0562o();
    }

    ProxyCard(int versionCode, String pan, String cvn, int expirationMonth, int expirationYear) {
        this.xH = versionCode;
        this.ack = pan;
        this.acl = cvn;
        this.acm = expirationMonth;
        this.acn = expirationYear;
    }

    public int describeContents() {
        return 0;
    }

    public String getCvn() {
        return this.acl;
    }

    public int getExpirationMonth() {
        return this.acm;
    }

    public int getExpirationYear() {
        return this.acn;
    }

    public String getPan() {
        return this.ack;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0562o.m1522a(this, out, flags);
    }
}
