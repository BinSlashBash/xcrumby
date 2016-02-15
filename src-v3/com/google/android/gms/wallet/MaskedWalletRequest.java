package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;

public final class MaskedWalletRequest implements SafeParcelable {
    public static final Creator<MaskedWalletRequest> CREATOR;
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
    ArrayList<CountrySpecification> acf;
    private final int xH;

    public final class Builder {
        final /* synthetic */ MaskedWalletRequest acg;

        private Builder(MaskedWalletRequest maskedWalletRequest) {
            this.acg = maskedWalletRequest;
        }

        public Builder addAllowedCountrySpecificationForShipping(CountrySpecification countrySpecification) {
            if (this.acg.acf == null) {
                this.acg.acf = new ArrayList();
            }
            this.acg.acf.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecificationsForShipping(Collection<CountrySpecification> countrySpecifications) {
            if (countrySpecifications != null) {
                if (this.acg.acf == null) {
                    this.acg.acf = new ArrayList();
                }
                this.acg.acf.addAll(countrySpecifications);
            }
            return this;
        }

        public MaskedWalletRequest build() {
            return this.acg;
        }

        public Builder setAllowDebitCard(boolean allowDebitCard) {
            this.acg.ace = allowDebitCard;
            return this;
        }

        public Builder setAllowPrepaidCard(boolean allowPrepaidCard) {
            this.acg.acd = allowPrepaidCard;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.acg.abr = cart;
            return this;
        }

        public Builder setCurrencyCode(String currencyCode) {
            this.acg.abd = currencyCode;
            return this;
        }

        public Builder setEstimatedTotalPrice(String estimatedTotalPrice) {
            this.acg.abY = estimatedTotalPrice;
            return this;
        }

        public Builder setIsBillingAgreement(boolean isBillingAgreement) {
            this.acg.acb = isBillingAgreement;
            return this;
        }

        public Builder setMerchantName(String merchantName) {
            this.acg.abZ = merchantName;
            return this;
        }

        public Builder setMerchantTransactionId(String merchantTransactionId) {
            this.acg.abi = merchantTransactionId;
            return this;
        }

        public Builder setPhoneNumberRequired(boolean phoneNumberRequired) {
            this.acg.abV = phoneNumberRequired;
            return this;
        }

        public Builder setShippingAddressRequired(boolean shippingAddressRequired) {
            this.acg.abW = shippingAddressRequired;
            return this;
        }

        public Builder setShouldRetrieveWalletObjects(boolean shouldRetrieveWalletObjects) {
            this.acg.aca = shouldRetrieveWalletObjects;
            return this;
        }

        public Builder setUseMinimalBillingAddress(boolean useMinimalBillingAddress) {
            this.acg.abX = useMinimalBillingAddress;
            return this;
        }
    }

    static {
        CREATOR = new C0559l();
    }

    MaskedWalletRequest() {
        this.xH = 3;
        this.acd = true;
        this.ace = true;
    }

    MaskedWalletRequest(int versionCode, String merchantTransactionId, boolean phoneNumberRequired, boolean shippingAddressRequired, boolean useMinimalBillingAddress, String estimatedTotalPrice, String currencyCode, String merchantName, Cart cart, boolean shouldRetrieveWalletObjects, boolean isBillingAgreement, CountrySpecification[] allowedShippingCountrySpecifications, boolean allowPrepaidCard, boolean allowDebitCard, ArrayList<CountrySpecification> allowedCountrySpecificationsForShipping) {
        this.xH = versionCode;
        this.abi = merchantTransactionId;
        this.abV = phoneNumberRequired;
        this.abW = shippingAddressRequired;
        this.abX = useMinimalBillingAddress;
        this.abY = estimatedTotalPrice;
        this.abd = currencyCode;
        this.abZ = merchantName;
        this.abr = cart;
        this.aca = shouldRetrieveWalletObjects;
        this.acb = isBillingAgreement;
        this.acc = allowedShippingCountrySpecifications;
        this.acd = allowPrepaidCard;
        this.ace = allowDebitCard;
        this.acf = allowedCountrySpecificationsForShipping;
    }

    public static Builder newBuilder() {
        MaskedWalletRequest maskedWalletRequest = new MaskedWalletRequest();
        maskedWalletRequest.getClass();
        return new Builder(null);
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

    public ArrayList<CountrySpecification> getAllowedCountrySpecificationsForShipping() {
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

    public void writeToParcel(Parcel dest, int flags) {
        C0559l.m1519a(this, dest, flags);
    }
}
