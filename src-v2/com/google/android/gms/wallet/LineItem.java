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
import com.google.android.gms.wallet.i;

public final class LineItem
implements SafeParcelable {
    public static final Parcelable.Creator<LineItem> CREATOR = new i();
    String abc;
    String abd;
    String abv;
    String abw;
    int abx;
    String description;
    private final int xH;

    LineItem() {
        this.xH = 1;
        this.abx = 0;
    }

    LineItem(int n2, String string2, String string3, String string4, String string5, int n3, String string6) {
        this.xH = n2;
        this.description = string2;
        this.abv = string3;
        this.abw = string4;
        this.abc = string5;
        this.abx = n3;
        this.abd = string6;
    }

    public static Builder newBuilder() {
        LineItem lineItem = new LineItem();
        lineItem.getClass();
        return lineItem.new Builder();
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

    public void writeToParcel(Parcel parcel, int n2) {
        i.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public LineItem build() {
            return LineItem.this;
        }

        public Builder setCurrencyCode(String string2) {
            LineItem.this.abd = string2;
            return this;
        }

        public Builder setDescription(String string2) {
            LineItem.this.description = string2;
            return this;
        }

        public Builder setQuantity(String string2) {
            LineItem.this.abv = string2;
            return this;
        }

        public Builder setRole(int n2) {
            LineItem.this.abx = n2;
            return this;
        }

        public Builder setTotalPrice(String string2) {
            LineItem.this.abc = string2;
            return this;
        }

        public Builder setUnitPrice(String string2) {
            LineItem.this.abw = string2;
            return this;
        }
    }

    public static interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

}

