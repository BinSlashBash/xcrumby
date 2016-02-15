/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Parcel
 *  android.util.DisplayMetrics
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.a;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.dv;

public final class ak
implements SafeParcelable {
    public static final al CREATOR = new al();
    public final int height;
    public final int heightPixels;
    public final String lS;
    public final boolean lT;
    public final ak[] lU;
    public final int versionCode;
    public final int width;
    public final int widthPixels;

    public ak() {
        this(2, "interstitial_mb", 0, 0, true, 0, 0, null);
    }

    ak(int n2, String string2, int n3, int n4, boolean bl2, int n5, int n6, ak[] arrak) {
        this.versionCode = n2;
        this.lS = string2;
        this.height = n3;
        this.heightPixels = n4;
        this.lT = bl2;
        this.width = n5;
        this.widthPixels = n6;
        this.lU = arrak;
    }

    public ak(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    /*
     * Enabled aggressive block sorting
     */
    public ak(Context context, AdSize[] arradSize) {
        int n2;
        int n3 = 0;
        AdSize adSize = arradSize[0];
        this.versionCode = 2;
        this.lT = false;
        this.width = adSize.getWidth();
        this.height = adSize.getHeight();
        int n4 = this.width == -1 ? 1 : 0;
        boolean bl2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (n4 != 0) {
            this.widthPixels = ak.a(displayMetrics);
            n2 = (int)((float)this.widthPixels / displayMetrics.density);
        } else {
            n2 = this.width;
            this.widthPixels = dv.a(displayMetrics, this.width);
        }
        int n5 = bl2 ? ak.c(displayMetrics) : this.height;
        this.heightPixels = dv.a(displayMetrics, n5);
        this.lS = n4 != 0 || bl2 ? "" + n2 + "x" + n5 + "_as" : adSize.toString();
        if (arradSize.length > 1) {
            this.lU = new ak[arradSize.length];
            for (n4 = n3; n4 < arradSize.length; ++n4) {
                this.lU[n4] = new ak(context, arradSize[n4]);
            }
            return;
        } else {
            this.lU = null;
        }
    }

    public ak(ak ak2, ak[] arrak) {
        this(2, ak2.lS, ak2.height, ak2.heightPixels, ak2.lT, ak2.width, ak2.widthPixels, arrak);
    }

    public static int a(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int b(DisplayMetrics displayMetrics) {
        return (int)((float)ak.c(displayMetrics) * displayMetrics.density);
    }

    private static int c(DisplayMetrics displayMetrics) {
        int n2 = (int)((float)displayMetrics.heightPixels / displayMetrics.density);
        if (n2 <= 400) {
            return 32;
        }
        if (n2 <= 720) {
            return 50;
        }
        return 90;
    }

    public AdSize aA() {
        return a.a(this.width, this.height, this.lS);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        al.a(this, parcel, n2);
    }
}

