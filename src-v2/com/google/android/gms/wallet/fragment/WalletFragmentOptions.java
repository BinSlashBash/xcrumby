/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 */
package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.google.android.gms.wallet.fragment.b;

public final class WalletFragmentOptions
implements SafeParcelable {
    public static final Parcelable.Creator<WalletFragmentOptions> CREATOR = new b();
    private int Ev;
    private WalletFragmentStyle acR;
    private int acq;
    private int mTheme;
    final int xH;

    private WalletFragmentOptions() {
        this.xH = 1;
    }

    WalletFragmentOptions(int n2, int n3, int n4, WalletFragmentStyle walletFragmentStyle, int n5) {
        this.xH = n2;
        this.acq = n3;
        this.mTheme = n4;
        this.acR = walletFragmentStyle;
        this.Ev = n5;
    }

    public static WalletFragmentOptions a(Context context, AttributeSet object) {
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.WalletFragmentOptions);
        int n2 = object.getInt(0, 0);
        int n3 = object.getInt(1, 1);
        int n4 = object.getResourceId(2, 0);
        int n5 = object.getInt(3, 1);
        object.recycle();
        object = new WalletFragmentOptions();
        object.mTheme = n2;
        object.acq = n3;
        object.acR = new WalletFragmentStyle().setStyleResourceId(n4);
        object.acR.I(context);
        object.Ev = n5;
        return object;
    }

    public static Builder newBuilder() {
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.getClass();
        return walletFragmentOptions.new Builder();
    }

    public void I(Context context) {
        if (this.acR != null) {
            this.acR.I(context);
        }
    }

    public int describeContents() {
        return 0;
    }

    public int getEnvironment() {
        return this.acq;
    }

    public WalletFragmentStyle getFragmentStyle() {
        return this.acR;
    }

    public int getMode() {
        return this.Ev;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public WalletFragmentOptions build() {
            return WalletFragmentOptions.this;
        }

        public Builder setEnvironment(int n2) {
            WalletFragmentOptions.this.acq = n2;
            return this;
        }

        public Builder setFragmentStyle(int n2) {
            WalletFragmentOptions.this.acR = new WalletFragmentStyle().setStyleResourceId(n2);
            return this;
        }

        public Builder setFragmentStyle(WalletFragmentStyle walletFragmentStyle) {
            WalletFragmentOptions.this.acR = walletFragmentStyle;
            return this;
        }

        public Builder setMode(int n2) {
            WalletFragmentOptions.this.Ev = n2;
            return this;
        }

        public Builder setTheme(int n2) {
            WalletFragmentOptions.this.mTheme = n2;
            return this;
        }
    }

}

