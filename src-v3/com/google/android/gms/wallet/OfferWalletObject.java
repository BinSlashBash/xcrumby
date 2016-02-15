package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class OfferWalletObject implements SafeParcelable {
    public static final Creator<OfferWalletObject> CREATOR;
    String acj;
    String eC;
    private final int xH;

    static {
        CREATOR = new C0561n();
    }

    OfferWalletObject() {
        this.xH = 2;
    }

    OfferWalletObject(int versionCode, String id, String redemptionCode) {
        this.xH = versionCode;
        this.eC = id;
        this.acj = redemptionCode;
    }

    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.eC;
    }

    public String getRedemptionCode() {
        return this.acj;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0561n.m1521a(this, dest, flags);
    }
}
