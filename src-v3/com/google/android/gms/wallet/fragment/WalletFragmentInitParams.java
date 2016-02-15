package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class WalletFragmentInitParams implements SafeParcelable {
    public static final Creator<WalletFragmentInitParams> CREATOR;
    private MaskedWalletRequest acB;
    private int acO;
    private MaskedWallet acP;
    private String wG;
    final int xH;

    public final class Builder {
        final /* synthetic */ WalletFragmentInitParams acQ;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.acQ = walletFragmentInitParams;
        }

        public WalletFragmentInitParams build() {
            boolean z = true;
            boolean z2 = (this.acQ.acP != null && this.acQ.acB == null) || (this.acQ.acP == null && this.acQ.acB != null);
            fq.m980a(z2, "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            if (this.acQ.acO < 0) {
                z = false;
            }
            fq.m980a(z, "masked wallet request code is required and must be non-negative");
            return this.acQ;
        }

        public Builder setAccountName(String accountName) {
            this.acQ.wG = accountName;
            return this;
        }

        public Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.acQ.acP = maskedWallet;
            return this;
        }

        public Builder setMaskedWalletRequest(MaskedWalletRequest request) {
            this.acQ.acB = request;
            return this;
        }

        public Builder setMaskedWalletRequestCode(int requestCode) {
            this.acQ.acO = requestCode;
            return this;
        }
    }

    static {
        CREATOR = new C0551a();
    }

    private WalletFragmentInitParams() {
        this.xH = 1;
        this.acO = -1;
    }

    WalletFragmentInitParams(int versionCode, String accountName, MaskedWalletRequest maskedWalletRequest, int maskedWalletRequestCode, MaskedWallet maskedWallet) {
        this.xH = versionCode;
        this.wG = accountName;
        this.acB = maskedWalletRequest;
        this.acO = maskedWalletRequestCode;
        this.acP = maskedWallet;
    }

    public static Builder newBuilder() {
        WalletFragmentInitParams walletFragmentInitParams = new WalletFragmentInitParams();
        walletFragmentInitParams.getClass();
        return new Builder(null);
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

    public void writeToParcel(Parcel dest, int flags) {
        C0551a.m1511a(this, dest, flags);
    }
}
