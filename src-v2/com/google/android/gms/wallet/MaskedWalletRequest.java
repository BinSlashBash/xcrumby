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
import com.google.android.gms.wallet.CountrySpecification;
import com.google.android.gms.wallet.l;
import java.util.ArrayList;
import java.util.Collection;

public final class MaskedWalletRequest
implements SafeParcelable {
    public static final Parcelable.Creator<MaskedWalletRequest> CREATOR = new l();
    boolean abV;
    boolean abW;
    boolean abX;
    String abY;
    String abZ;
    String abd;
    String abi;
    Cart abr;
    boolean aca;
    boolean acb;
    CountrySpecification[] acc;
    boolean acd;
    boolean ace;
    ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> acf;
    private final int xH;

    MaskedWalletRequest() {
        this.xH = 3;
        this.acd = true;
        this.ace = true;
    }

    MaskedWalletRequest(int n2, String string2, boolean bl2, boolean bl3, boolean bl4, String string3, String string4, String string5, Cart cart, boolean bl5, boolean bl6, CountrySpecification[] arrcountrySpecification, boolean bl7, boolean bl8, ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> arrayList) {
        this.xH = n2;
        this.abi = string2;
        this.abV = bl2;
        this.abW = bl3;
        this.abX = bl4;
        this.abY = string3;
        this.abd = string4;
        this.abZ = string5;
        this.abr = cart;
        this.aca = bl5;
        this.acb = bl6;
        this.acc = arrcountrySpecification;
        this.acd = bl7;
        this.ace = bl8;
        this.acf = arrayList;
    }

    public static Builder newBuilder() {
        MaskedWalletRequest maskedWalletRequest = new MaskedWalletRequest();
        maskedWalletRequest.getClass();
        return maskedWalletRequest.new Builder();
    }

    public boolean allowDebitCard() {
        return this.ace;
    }

    public boolean allowPrepaidCard() {
        return this.acd;
    }

    public int describeContents() {
        return 0;
    }

    public ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> getAllowedCountrySpecificationsForShipping() {
        return this.acf;
    }

    public CountrySpecification[] getAllowedShippingCountrySpecifications() {
        return this.acc;
    }

    public Cart getCart() {
        return this.abr;
    }

    public String getCurrencyCode() {
        return this.abd;
    }

    public String getEstimatedTotalPrice() {
        return this.abY;
    }

    public String getMerchantName() {
        return this.abZ;
    }

    public String getMerchantTransactionId() {
        return this.abi;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public boolean isBillingAgreement() {
        return this.acb;
    }

    public boolean isPhoneNumberRequired() {
        return this.abV;
    }

    public boolean isShippingAddressRequired() {
        return this.abW;
    }

    public boolean shouldRetrieveWalletObjects() {
        return this.aca;
    }

    public boolean useMinimalBillingAddress() {
        return this.abX;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        l.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public Builder addAllowedCountrySpecificationForShipping(com.google.android.gms.identity.intents.model.CountrySpecification countrySpecification) {
            if (MaskedWalletRequest.this.acf == null) {
                MaskedWalletRequest.this.acf = new ArrayList();
            }
            MaskedWalletRequest.this.acf.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecificationsForShipping(Collection<com.google.android.gms.identity.intents.model.CountrySpecification> collection) {
            if (collection != null) {
                if (MaskedWalletRequest.this.acf == null) {
                    MaskedWalletRequest.this.acf = new ArrayList();
                }
                MaskedWalletRequest.this.acf.addAll(collection);
            }
            return this;
        }

        public MaskedWalletRequest build() {
            return MaskedWalletRequest.this;
        }

        public Builder setAllowDebitCard(boolean bl2) {
            MaskedWalletRequest.this.ace = bl2;
            return this;
        }

        public Builder setAllowPrepaidCard(boolean bl2) {
            MaskedWalletRequest.this.acd = bl2;
            return this;
        }

        public Builder setCart(Cart cart) {
            MaskedWalletRequest.this.abr = cart;
            return this;
        }

        public Builder setCurrencyCode(String string2) {
            MaskedWalletRequest.this.abd = string2;
            return this;
        }

        public Builder setEstimatedTotalPrice(String string2) {
            MaskedWalletRequest.this.abY = string2;
            return this;
        }

        public Builder setIsBillingAgreement(boolean bl2) {
            MaskedWalletRequest.this.acb = bl2;
            return this;
        }

        public Builder setMerchantName(String string2) {
            MaskedWalletRequest.this.abZ = string2;
            return this;
        }

        public Builder setMerchantTransactionId(String string2) {
            MaskedWalletRequest.this.abi = string2;
            return this;
        }

        public Builder setPhoneNumberRequired(boolean bl2) {
            MaskedWalletRequest.this.abV = bl2;
            return this;
        }

        public Builder setShippingAddressRequired(boolean bl2) {
            MaskedWalletRequest.this.abW = bl2;
            return this;
        }

        public Builder setShouldRetrieveWalletObjects(boolean bl2) {
            MaskedWalletRequest.this.aca = bl2;
            return this;
        }

        public Builder setUseMinimalBillingAddress(boolean bl2) {
            MaskedWalletRequest.this.abX = bl2;
            return this;
        }
    }

}

