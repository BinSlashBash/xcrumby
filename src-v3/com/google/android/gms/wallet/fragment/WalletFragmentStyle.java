package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.gms.C0192R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class WalletFragmentStyle implements SafeParcelable {
    public static final Creator<WalletFragmentStyle> CREATOR;
    Bundle acT;
    int acU;
    final int xH;

    static {
        CREATOR = new C0553c();
    }

    public WalletFragmentStyle() {
        this.xH = 1;
        this.acT = new Bundle();
    }

    WalletFragmentStyle(int versionCode, Bundle attributes, int styleResourceId) {
        this.xH = versionCode;
        this.acT = attributes;
        this.acU = styleResourceId;
    }

    private void m2614a(TypedArray typedArray, int i, String str) {
        if (!this.acT.containsKey(str)) {
            TypedValue peekValue = typedArray.peekValue(i);
            if (peekValue != null) {
                this.acT.putLong(str, Dimension.m1495a(peekValue));
            }
        }
    }

    private void m2615a(TypedArray typedArray, int i, String str, String str2) {
        if (!this.acT.containsKey(str) && !this.acT.containsKey(str2)) {
            TypedValue peekValue = typedArray.peekValue(i);
            if (peekValue == null) {
                return;
            }
            if (peekValue.type < 28 || peekValue.type > 31) {
                this.acT.putInt(str2, peekValue.resourceId);
            } else {
                this.acT.putInt(str, peekValue.data);
            }
        }
    }

    private void m2616b(TypedArray typedArray, int i, String str) {
        if (!this.acT.containsKey(str)) {
            TypedValue peekValue = typedArray.peekValue(i);
            if (peekValue != null) {
                this.acT.putInt(str, peekValue.data);
            }
        }
    }

    public void m2617I(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(this.acU <= 0 ? C0192R.style.WalletFragmentDefaultStyle : this.acU, C0192R.styleable.WalletFragmentStyle);
        m2614a(obtainStyledAttributes, 1, "buyButtonWidth");
        m2614a(obtainStyledAttributes, 0, "buyButtonHeight");
        m2616b(obtainStyledAttributes, 2, "buyButtonText");
        m2616b(obtainStyledAttributes, 3, "buyButtonAppearance");
        m2616b(obtainStyledAttributes, 4, "maskedWalletDetailsTextAppearance");
        m2616b(obtainStyledAttributes, 5, "maskedWalletDetailsHeaderTextAppearance");
        m2615a(obtainStyledAttributes, 6, "maskedWalletDetailsBackgroundColor", "maskedWalletDetailsBackgroundResource");
        m2616b(obtainStyledAttributes, 7, "maskedWalletDetailsButtonTextAppearance");
        m2615a(obtainStyledAttributes, 8, "maskedWalletDetailsButtonBackgroundColor", "maskedWalletDetailsButtonBackgroundResource");
        m2616b(obtainStyledAttributes, 9, "maskedWalletDetailsLogoTextColor");
        m2616b(obtainStyledAttributes, 10, "maskedWalletDetailsLogoImageType");
        obtainStyledAttributes.recycle();
    }

    public int m2618a(String str, DisplayMetrics displayMetrics, int i) {
        return this.acT.containsKey(str) ? Dimension.m1493a(this.acT.getLong(str), displayMetrics) : i;
    }

    public int describeContents() {
        return 0;
    }

    public WalletFragmentStyle setBuyButtonAppearance(int buyButtonAppearance) {
        this.acT.putInt("buyButtonAppearance", buyButtonAppearance);
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int height) {
        this.acT.putLong("buyButtonHeight", Dimension.cz(height));
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int unit, float height) {
        this.acT.putLong("buyButtonHeight", Dimension.m1494a(unit, height));
        return this;
    }

    public WalletFragmentStyle setBuyButtonText(int buyButtonText) {
        this.acT.putInt("buyButtonText", buyButtonText);
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int width) {
        this.acT.putLong("buyButtonWidth", Dimension.cz(width));
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int unit, float width) {
        this.acT.putLong("buyButtonWidth", Dimension.m1494a(unit, width));
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundColor(int color) {
        this.acT.remove("maskedWalletDetailsBackgroundResource");
        this.acT.putInt("maskedWalletDetailsBackgroundColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundResource(int resourceId) {
        this.acT.remove("maskedWalletDetailsBackgroundColor");
        this.acT.putInt("maskedWalletDetailsBackgroundResource", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundColor(int color) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundResource");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundResource(int resourceId) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundColor");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundResource", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsButtonTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsHeaderTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsHeaderTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoImageType(int imageType) {
        this.acT.putInt("maskedWalletDetailsLogoImageType", imageType);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoTextColor(int color) {
        this.acT.putInt("maskedWalletDetailsLogoTextColor", color);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsTextAppearance(int resourceId) {
        this.acT.putInt("maskedWalletDetailsTextAppearance", resourceId);
        return this;
    }

    public WalletFragmentStyle setStyleResourceId(int id) {
        this.acU = id;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0553c.m1513a(this, dest, flags);
    }
}
