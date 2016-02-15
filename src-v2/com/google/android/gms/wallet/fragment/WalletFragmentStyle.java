/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wallet.fragment.Dimension;
import com.google.android.gms.wallet.fragment.c;

public final class WalletFragmentStyle
implements SafeParcelable {
    public static final Parcelable.Creator<WalletFragmentStyle> CREATOR = new c();
    Bundle acT;
    int acU;
    final int xH;

    public WalletFragmentStyle() {
        this.xH = 1;
        this.acT = new Bundle();
    }

    WalletFragmentStyle(int n2, Bundle bundle, int n3) {
        this.xH = n2;
        this.acT = bundle;
        this.acU = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(TypedArray typedArray, int n2, String string2) {
        if (this.acT.containsKey(string2) || (typedArray = typedArray.peekValue(n2)) == null) {
            return;
        }
        this.acT.putLong(string2, Dimension.a((TypedValue)typedArray));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(TypedArray typedArray, int n2, String string2, String string3) {
        if (this.acT.containsKey(string2) || this.acT.containsKey(string3) || (typedArray = typedArray.peekValue(n2)) == null) {
            return;
        }
        if (typedArray.type >= 28 && typedArray.type <= 31) {
            this.acT.putInt(string2, typedArray.data);
            return;
        }
        this.acT.putInt(string3, typedArray.resourceId);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(TypedArray typedArray, int n2, String string2) {
        if (this.acT.containsKey(string2) || (typedArray = typedArray.peekValue(n2)) == null) {
            return;
        }
        this.acT.putInt(string2, typedArray.data);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void I(Context context) {
        int n2 = this.acU <= 0 ? R.style.WalletFragmentDefaultStyle : this.acU;
        context = context.obtainStyledAttributes(n2, R.styleable.WalletFragmentStyle);
        this.a((TypedArray)context, 1, "buyButtonWidth");
        this.a((TypedArray)context, 0, "buyButtonHeight");
        this.b((TypedArray)context, 2, "buyButtonText");
        this.b((TypedArray)context, 3, "buyButtonAppearance");
        this.b((TypedArray)context, 4, "maskedWalletDetailsTextAppearance");
        this.b((TypedArray)context, 5, "maskedWalletDetailsHeaderTextAppearance");
        this.a((TypedArray)context, 6, "maskedWalletDetailsBackgroundColor", "maskedWalletDetailsBackgroundResource");
        this.b((TypedArray)context, 7, "maskedWalletDetailsButtonTextAppearance");
        this.a((TypedArray)context, 8, "maskedWalletDetailsButtonBackgroundColor", "maskedWalletDetailsButtonBackgroundResource");
        this.b((TypedArray)context, 9, "maskedWalletDetailsLogoTextColor");
        this.b((TypedArray)context, 10, "maskedWalletDetailsLogoImageType");
        context.recycle();
    }

    public int a(String string2, DisplayMetrics displayMetrics, int n2) {
        if (this.acT.containsKey(string2)) {
            n2 = Dimension.a(this.acT.getLong(string2), displayMetrics);
        }
        return n2;
    }

    public int describeContents() {
        return 0;
    }

    public WalletFragmentStyle setBuyButtonAppearance(int n2) {
        this.acT.putInt("buyButtonAppearance", n2);
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int n2) {
        this.acT.putLong("buyButtonHeight", Dimension.cz(n2));
        return this;
    }

    public WalletFragmentStyle setBuyButtonHeight(int n2, float f2) {
        this.acT.putLong("buyButtonHeight", Dimension.a(n2, f2));
        return this;
    }

    public WalletFragmentStyle setBuyButtonText(int n2) {
        this.acT.putInt("buyButtonText", n2);
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int n2) {
        this.acT.putLong("buyButtonWidth", Dimension.cz(n2));
        return this;
    }

    public WalletFragmentStyle setBuyButtonWidth(int n2, float f2) {
        this.acT.putLong("buyButtonWidth", Dimension.a(n2, f2));
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundColor(int n2) {
        this.acT.remove("maskedWalletDetailsBackgroundResource");
        this.acT.putInt("maskedWalletDetailsBackgroundColor", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsBackgroundResource(int n2) {
        this.acT.remove("maskedWalletDetailsBackgroundColor");
        this.acT.putInt("maskedWalletDetailsBackgroundResource", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundColor(int n2) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundResource");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundColor", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundResource(int n2) {
        this.acT.remove("maskedWalletDetailsButtonBackgroundColor");
        this.acT.putInt("maskedWalletDetailsButtonBackgroundResource", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsButtonTextAppearance(int n2) {
        this.acT.putInt("maskedWalletDetailsButtonTextAppearance", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsHeaderTextAppearance(int n2) {
        this.acT.putInt("maskedWalletDetailsHeaderTextAppearance", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoImageType(int n2) {
        this.acT.putInt("maskedWalletDetailsLogoImageType", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsLogoTextColor(int n2) {
        this.acT.putInt("maskedWalletDetailsLogoTextColor", n2);
        return this;
    }

    public WalletFragmentStyle setMaskedWalletDetailsTextAppearance(int n2) {
        this.acT.putInt("maskedWalletDetailsTextAppearance", n2);
        return this;
    }

    public WalletFragmentStyle setStyleResourceId(int n2) {
        this.acU = n2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        c.a(this, parcel, n2);
    }
}

