package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.C0192R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class WalletFragmentOptions implements SafeParcelable {
    public static final Creator<WalletFragmentOptions> CREATOR;
    private int Ev;
    private WalletFragmentStyle acR;
    private int acq;
    private int mTheme;
    final int xH;

    public final class Builder {
        final /* synthetic */ WalletFragmentOptions acS;

        private Builder(WalletFragmentOptions walletFragmentOptions) {
            this.acS = walletFragmentOptions;
        }

        public WalletFragmentOptions build() {
            return this.acS;
        }

        public Builder setEnvironment(int environment) {
            this.acS.acq = environment;
            return this;
        }

        public Builder setFragmentStyle(int styleResourceId) {
            this.acS.acR = new WalletFragmentStyle().setStyleResourceId(styleResourceId);
            return this;
        }

        public Builder setFragmentStyle(WalletFragmentStyle fragmentStyle) {
            this.acS.acR = fragmentStyle;
            return this;
        }

        public Builder setMode(int mode) {
            this.acS.Ev = mode;
            return this;
        }

        public Builder setTheme(int theme) {
            this.acS.mTheme = theme;
            return this;
        }
    }

    static {
        CREATOR = new C0552b();
    }

    private WalletFragmentOptions() {
        this.xH = 1;
    }

    WalletFragmentOptions(int versionCode, int environment, int theme, WalletFragmentStyle fragmentStyle, int mode) {
        this.xH = versionCode;
        this.acq = environment;
        this.mTheme = theme;
        this.acR = fragmentStyle;
        this.Ev = mode;
    }

    public static WalletFragmentOptions m2609a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0192R.styleable.WalletFragmentOptions);
        int i = obtainStyledAttributes.getInt(0, 0);
        int i2 = obtainStyledAttributes.getInt(1, 1);
        int resourceId = obtainStyledAttributes.getResourceId(2, 0);
        int i3 = obtainStyledAttributes.getInt(3, 1);
        obtainStyledAttributes.recycle();
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.mTheme = i;
        walletFragmentOptions.acq = i2;
        walletFragmentOptions.acR = new WalletFragmentStyle().setStyleResourceId(resourceId);
        walletFragmentOptions.acR.m2617I(context);
        walletFragmentOptions.Ev = i3;
        return walletFragmentOptions;
    }

    public static Builder newBuilder() {
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.getClass();
        return new Builder(null);
    }

    public void m2613I(Context context) {
        if (this.acR != null) {
            this.acR.m2617I(context);
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

    public void writeToParcel(Parcel dest, int flags) {
        C0552b.m1512a(this, dest, flags);
    }
}
