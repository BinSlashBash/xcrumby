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
import com.google.android.gms.wallet.ProxyCard;
import com.google.android.gms.wallet.f;

public final class FullWallet
implements SafeParcelable {
    public static final Parcelable.Creator<FullWallet> CREATOR = new f();
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

    private FullWallet() {
        this.xH = 1;
    }

    FullWallet(int n2, String string2, String string3, ProxyCard proxyCard, String string4, Address address, Address address2, String[] arrstring, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] arrinstrumentInfo) {
        this.xH = n2;
        this.abh = string2;
        this.abi = string3;
        this.abj = proxyCard;
        this.abk = string4;
        this.abl = address;
        this.abm = address2;
        this.abn = arrstring;
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

    public void writeToParcel(Parcel parcel, int n2) {
        f.a(this, parcel, n2);
    }
}

