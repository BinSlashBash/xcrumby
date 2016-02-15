/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.a;

public final class WalletFragmentInitParams
implements SafeParcelable {
    public static final Parcelable.Creator<WalletFragmentInitParams> CREATOR = new a();
    private MaskedWalletRequest acB;
    private int acO;
    private MaskedWallet acP;
    private String wG;
    final int xH;

    private WalletFragmentInitParams() {
        this.xH = 1;
        this.acO = -1;
    }

    WalletFragmentInitParams(int n2, String string2, MaskedWalletRequest maskedWalletRequest, int n3, MaskedWallet maskedWallet) {
        this.xH = n2;
        this.wG = string2;
        this.acB = maskedWalletRequest;
        this.acO = n3;
        this.acP = maskedWallet;
    }

    public static Builder newBuilder() {
        WalletFragmentInitParams walletFragmentInitParams = new WalletFragmentInitParams();
        walletFragmentInitParams.getClass();
        return walletFragmentInitParams.new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getAccountName() {
        return this.wG;
    }

    public MaskedWallet getMaskedWallet() {
        return this.acP;
    }

    public MaskedWalletRequest getMaskedWalletRequest() {
        return this.acB;
    }

    public int getMaskedWalletRequestCode() {
        return this.acO;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public WalletFragmentInitParams build() {
            boolean bl2 = true;
            boolean bl3 = WalletFragmentInitParams.this.acP != null && WalletFragmentInitParams.this.acB == null || WalletFragmentInitParams.this.acP == null && WalletFragmentInitParams.this.acB != null;
            fq.a(bl3, "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            bl3 = WalletFragmentInitParams.this.acO >= 0 ? bl2 : false;
            fq.a(bl3, "masked wallet request code is required and must be non-negative");
            return WalletFragmentInitParams.this;
        }

        public Builder setAccountName(String string2) {
            WalletFragmentInitParams.this.wG = string2;
            return this;
        }

        public Builder setMaskedWallet(MaskedWallet maskedWallet) {
            WalletFragmentInitParams.this.acP = maskedWallet;
            return this;
        }

        public Builder setMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            WalletFragmentInitParams.this.acB = maskedWalletRequest;
            return this;
        }

        public Builder setMaskedWalletRequestCode(int n2) {
            WalletFragmentInitParams.this.acO = n2;
            return this;
        }
    }

}

