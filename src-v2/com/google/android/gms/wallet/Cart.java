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
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Cart
implements SafeParcelable {
    public static final Parcelable.Creator<Cart> CREATOR = new b();
    String abc;
    String abd;
    ArrayList<LineItem> abe;
    private final int xH;

    Cart() {
        this.xH = 1;
        this.abe = new ArrayList();
    }

    Cart(int n2, String string2, String string3, ArrayList<LineItem> arrayList) {
        this.xH = n2;
        this.abc = string2;
        this.abd = string3;
        this.abe = arrayList;
    }

    public static Builder newBuilder() {
        Cart cart = new Cart();
        cart.getClass();
        return cart.new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrencyCode() {
        return this.abd;
    }

    public ArrayList<LineItem> getLineItems() {
        return this.abe;
    }

    public String getTotalPrice() {
        return this.abc;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public Builder addLineItem(LineItem lineItem) {
            Cart.this.abe.add(lineItem);
            return this;
        }

        public Cart build() {
            return Cart.this;
        }

        public Builder setCurrencyCode(String string2) {
            Cart.this.abd = string2;
            return this;
        }

        public Builder setLineItems(List<LineItem> list) {
            Cart.this.abe.clear();
            Cart.this.abe.addAll(list);
            return this;
        }

        public Builder setTotalPrice(String string2) {
            Cart.this.abc = string2;
            return this;
        }
    }

}

