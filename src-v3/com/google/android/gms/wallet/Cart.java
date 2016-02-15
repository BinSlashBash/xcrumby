package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

public final class Cart implements SafeParcelable {
    public static final Creator<Cart> CREATOR;
    String abc;
    String abd;
    ArrayList<LineItem> abe;
    private final int xH;

    public final class Builder {
        final /* synthetic */ Cart abf;

        private Builder(Cart cart) {
            this.abf = cart;
        }

        public Builder addLineItem(LineItem lineItem) {
            this.abf.abe.add(lineItem);
            return this;
        }

        public Cart build() {
            return this.abf;
        }

        public Builder setCurrencyCode(String currencyCode) {
            this.abf.abd = currencyCode;
            return this;
        }

        public Builder setLineItems(List<LineItem> lineItems) {
            this.abf.abe.clear();
            this.abf.abe.addAll(lineItems);
            return this;
        }

        public Builder setTotalPrice(String totalPrice) {
            this.abf.abc = totalPrice;
            return this;
        }
    }

    static {
        CREATOR = new C0543b();
    }

    Cart() {
        this.xH = 1;
        this.abe = new ArrayList();
    }

    Cart(int versionCode, String totalPrice, String currencyCode, ArrayList<LineItem> lineItems) {
        this.xH = versionCode;
        this.abc = totalPrice;
        this.abd = currencyCode;
        this.abe = lineItems;
    }

    public static Builder newBuilder() {
        Cart cart = new Cart();
        cart.getClass();
        return new Builder(null);
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

    public void writeToParcel(Parcel dest, int flags) {
        C0543b.m1489a(this, dest, flags);
    }
}
