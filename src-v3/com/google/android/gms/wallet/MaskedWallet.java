package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class MaskedWallet implements SafeParcelable {
    public static final Creator<MaskedWallet> CREATOR;
    LoyaltyWalletObject[] abT;
    OfferWalletObject[] abU;
    String abh;
    String abi;
    String abk;
    Address abl;
    Address abm;
    String[] abn;
    UserAddress abo;
    UserAddress abp;
    InstrumentInfo[] abq;
    private final int xH;

    static {
        CREATOR = new C0558k();
    }

    private MaskedWallet() {
        this.xH = 2;
    }

    MaskedWallet(int versionCode, String googleTransactionId, String merchantTransactionId, String[] paymentDescriptions, String email, Address billingAddress, Address shippingAddress, LoyaltyWalletObject[] loyaltyWalletObjects, OfferWalletObject[] offerWalletObjects, UserAddress buyerBillingAddress, UserAddress buyerShippingAddress, InstrumentInfo[] instrumentInfos) {
        this.xH = versionCode;
        this.abh = googleTransactionId;
        this.abi = merchantTransactionId;
        this.abn = paymentDescriptions;
        this.abk = email;
        this.abl = billingAddress;
        this.abm = shippingAddress;
        this.abT = loyaltyWalletObjects;
        this.abU = offerWalletObjects;
        this.abo = buyerBillingAddress;
        this.abp = buyerShippingAddress;
        this.abq = instrumentInfos;
    }

    public int describeContents() {
        return 0;
    }

    @Deprecated
    public Address getBillingAddress() {
        return this.abl;
    }

    public UserAddress getBuyerBillingAddress() {
        return this.abo;
    }

    public UserAddress getBuyerShippingAddress() {
        return this.abp;
    }

    public String getEmail() {
        return this.abk;
    }

    public String getGoogleTransactionId() {
        return this.abh;
    }

    public InstrumentInfo[] getInstrumentInfos() {
        return this.abq;
    }

    public LoyaltyWalletObject[] getLoyaltyWalletObjects() {
        return this.abT;
    }

    public String getMerchantTransactionId() {
        return this.abi;
    }

    public OfferWalletObject[] getOfferWalletObjects() {
        return this.abU;
    }

    public String[] getPaymentDescriptions() {
        return this.abn;
    }

    @Deprecated
    public Address getShippingAddress() {
        return this.abm;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0558k.m1518a(this, dest, flags);
    }
}
