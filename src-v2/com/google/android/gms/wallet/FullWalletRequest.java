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
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.g;

public final class FullWalletRequest
implements SafeParcelable {
    public static final Parcelable.Creator<FullWalletRequest> CREATOR = new g();
    String abh;
    String abi;
    Cart abr;
    private final int xH;

    FullWalletRequest() {
        this.xH = 1;
    }

    FullWalletRequest(int n2, String string2, String string3, Cart cart) {
        this.xH = n2;
        this.abh = string2;
        this.abi = string3;
        this.abr = cart;
    }

    public static Builder newBuilder() {
        FullWalletRequest fullWalletRequest = new FullWalletRequest();
        fullWalletRequest.getClass();
        return fullWalletRequest.new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public Cart getCart() {
        return this.abr;
    }

    public String getGoogleTransactionId() {
        return this.abh;
    }

    public String getMerchantTransactionId() {
        return this.abi;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        g.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public FullWalletRequest build() {
            return FullWalletRequest.this;
        }

        public Builder setCart(Cart cart) {
            FullWalletRequest.this.abr = cart;
            return this;
        }

        public Builder setGoogleTransactionId(String string2) {
            FullWalletRequest.this.abh = string2;
            return this;
        }

        public Builder setMerchantTransactionId(String string2) {
            FullWalletRequest.this.abi = string2;
            return this;
        }
    }

}

