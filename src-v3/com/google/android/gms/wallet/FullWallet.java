package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class FullWallet implements SafeParcelable {
    public static final Creator<FullWallet> CREATOR;
    String abh;
    String abi;
    ProxyCard abj;
    String abk;
    Address abl;
    Address abm;
    String[] abn;
    UserAddress abo;
    UserAddress abp;
    InstrumentInfo[] abq;
    private final int xH;

    static {
        CREATOR = new C0546f();
    }

    private FullWallet() {
        this.xH = 1;
    }

    FullWallet(int versionCode, String googleTransactionId, String merchantTransactionId, ProxyCard proxyCard, String email, Address billingAddress, Address shippingAddress, String[] paymentDescriptions, UserAddress buyerBillingAddress, UserAddress buyerShippingAddress, InstrumentInfo[] instrumentInfos) {
        this.xH = versionCode;
        this.abh = googleTransactionId;
        this.abi = merchantTransactionId;
        this.abj = proxyCard;
        this.abk = email;
        this.abl = billingAddress;
        this.abm = shippingAddress;
        this.abn = paymentDescriptions;
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

    public String getMerchantTransactionId() {
        return this.abi;
    }

    public String[] getPaymentDescriptions() {
        return this.abn;
    }

    public ProxyCard getProxyCard() {
        return this.abj;
    }

    @Deprecated
    public Address getShippingAddress() {
        return this.abm;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0546f.m1492a(this, out, flags);
    }
}
