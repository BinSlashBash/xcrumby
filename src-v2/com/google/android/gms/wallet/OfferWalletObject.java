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
import com.google.android.gms.wallet.n;

public final class OfferWalletObject
implements SafeParcelable {
    public static final Parcelable.Creator<OfferWalletObject> CREATOR = new n();
    String acj;
    String eC;
    private final int xH;

    OfferWalletObject() {
        this.xH = 2;
    }

    OfferWalletObject(int n2, String string2, String string3) {
        this.xH = n2;
        this.eC = string2;
        this.acj = string3;
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

    public void writeToParcel(Parcel parcel, int n2) {
        n.a(this, parcel, n2);
    }
}

