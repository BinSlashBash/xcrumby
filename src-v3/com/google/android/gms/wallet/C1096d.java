package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* renamed from: com.google.android.gms.wallet.d */
public final class C1096d implements SafeParcelable {
    public static final Creator<C1096d> CREATOR;
    LoyaltyWalletObject abg;
    private final int xH;

    static {
        CREATOR = new C0545e();
    }

    C1096d() {
        this.xH = 1;
    }

    C1096d(int i, LoyaltyWalletObject loyaltyWalletObject) {
        this.xH = i;
        this.abg = loyaltyWalletObject;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0545e.m1491a(this, dest, flags);
    }
}
