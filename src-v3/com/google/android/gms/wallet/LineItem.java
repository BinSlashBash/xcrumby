package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LineItem implements SafeParcelable {
    public static final Creator<LineItem> CREATOR;
    String abc;
    String abd;
    String abv;
    String abw;
    int abx;
    String description;
    private final int xH;

    public final class Builder {
        final /* synthetic */ LineItem aby;

        private Builder(LineItem lineItem) {
            this.aby = lineItem;
        }

        public LineItem build() {
            return this.aby;
        }

        public Builder setCurrencyCode(String currencyCode) {
            this.aby.abd = currencyCode;
            return this;
        }

        public Builder setDescription(String description) {
            this.aby.description = description;
            return this;
        }

        public Builder setQuantity(String quantity) {
            this.aby.abv = quantity;
            return this;
        }

        public Builder setRole(int role) {
            this.aby.abx = role;
            return this;
        }

        public Builder setTotalPrice(String totalPrice) {
            this.aby.abc = totalPrice;
            return this;
        }

        public Builder setUnitPrice(String unitPrice) {
            this.aby.abw = unitPrice;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    static {
        CREATOR = new C0556i();
    }

    LineItem() {
        this.xH = 1;
        this.abx = 0;
    }

    LineItem(int versionCode, String description, String quantity, String unitPrice, String totalPrice, int role, String currencyCode) {
        this.xH = versionCode;
        this.description = description;
        this.abv = quantity;
        this.abw = unitPrice;
        this.abc = totalPrice;
        this.abx = role;
        this.abd = currencyCode;
    }

    public static Builder newBuilder() {
        LineItem lineItem = new LineItem();
        lineItem.getClass();
        return new Builder(null);
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrencyCode() {
        return this.abd;
    }

    public String getDescription() {
        return this.description;
    }

    public String getQuantity() {
        return this.abv;
    }

    public int getRole() {
        return this.abx;
    }

    public String getTotalPrice() {
        return this.abc;
    }

    public String getUnitPrice() {
        return this.abw;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0556i.m1516a(this, dest, flags);
    }
}
