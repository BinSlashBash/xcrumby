package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class FullWalletRequest implements SafeParcelable {
    public static final Creator<FullWalletRequest> CREATOR;
    String abh;
    String abi;
    Cart abr;
    private final int xH;

    public final class Builder {
        final /* synthetic */ FullWalletRequest abs;

        private Builder(FullWalletRequest fullWalletRequest) {
            this.abs = fullWalletRequest;
        }

        public FullWalletRequest build() {
            return this.abs;
        }

        public Builder setCart(Cart cart) {
            this.abs.abr = cart;
            return this;
        }

        public Builder setGoogleTransactionId(String googleTransactionId) {
            this.abs.abh = googleTransactionId;
            return this;
        }

        public Builder setMerchantTransactionId(String merchantTransactionId) {
            this.abs.abi = merchantTransactionId;
            return this;
        }
    }

    static {
        CREATOR = new C0554g();
    }

    FullWalletRequest() {
        this.xH = 1;
    }

    FullWalletRequest(int versionCode, String googleTransactionId, String merchantTransactionId, Cart cart) {
        this.xH = versionCode;
        this.abh = googleTransactionId;
        this.abi = merchantTransactionId;
        this.abr = cart;
    }

    public static Builder newBuilder() {
        FullWalletRequest fullWalletRequest = new FullWalletRequest();
        fullWalletRequest.getClass();
        return new Builder(null);
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

    public void writeToParcel(Parcel dest, int flags) {
        C0554g.m1514a(this, dest, flags);
    }
}
