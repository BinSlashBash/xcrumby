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
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.Address;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.OfferWalletObject;
import com.google.android.gms.wallet.k;

public final class MaskedWallet
implements SafeParcelable {
    public static final Parcelable.Creator<MaskedWallet> CREATOR = new k();
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

    private MaskedWallet() {
        this.xH = 2;
    }

    MaskedWallet(int n2, String string2, String string3, String[] arrstring, String string4, Address address, Address address2, LoyaltyWalletObject[] arrloyaltyWalletObject, OfferWalletObject[] arrofferWalletObject, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] arrinstrumentInfo) {
        this.xH = n2;
        this.abh = string2;
        this.abi = string3;
        this.abn = arrstring;
        this.abk = string4;
        this.abl = address;
        this.abm = address2;
        this.abT = arrloyaltyWalletObject;
        this.abU = arrofferWalletObject;
        this.abo = userAddress;
        this.abp = userAddress2;
        this.abq = arrinstrumentInfo;
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

    public void writeToParcel(Parcel parcel, int n2) {
        k.a(this, parcel, n2);
    }
}

