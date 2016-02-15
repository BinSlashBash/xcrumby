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
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.e;

public final class d
implements SafeParcelable {
    public static final Parcelable.Creator<d> CREATOR = new e();
    LoyaltyWalletObject abg;
    private final int xH;

    d() {
        this.xH = 1;
    }

    d(int n2, LoyaltyWalletObject loyaltyWalletObject) {
        this.xH = n2;
        this.abg = loyaltyWalletObject;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        e.a(this, parcel, n2);
    }
}

