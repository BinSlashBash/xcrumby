/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.ads;

import android.content.Context;

@Deprecated
public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final AdSize BANNER;
    public static final int FULL_WIDTH = -1;
    public static final AdSize IAB_BANNER;
    public static final AdSize IAB_LEADERBOARD;
    public static final AdSize IAB_MRECT;
    public static final AdSize IAB_WIDE_SKYSCRAPER;
    public static final int LANDSCAPE_AD_HEIGHT = 32;
    public static final int LARGE_AD_HEIGHT = 90;
    public static final int PORTRAIT_AD_HEIGHT = 50;
    public static final AdSize SMART_BANNER;
    private final com.google.android.gms.ads.AdSize c;

    static {
        SMART_BANNER = new AdSize(-1, -2, "mb");
        BANNER = new AdSize(320, 50, "mb");
        IAB_MRECT = new AdSize(300, 250, "as");
        IAB_BANNER = new AdSize(468, 60, "as");
        IAB_LEADERBOARD = new AdSize(728, 90, "as");
        IAB_WIDE_SKYSCRAPER = new AdSize(160, 600, "as");
    }

    public AdSize(int n2, int n3) {
        this(new com.google.android.gms.ads.AdSize(n2, n3));
    }

    private AdSize(int n2, int n3, String string2) {
        this(new com.google.android.gms.ads.AdSize(n2, n3));
    }

    public AdSize(com.google.android.gms.ads.AdSize adSize) {
        this.c = adSize;
    }

    public boolean equals(Object object) {
        if (!(object instanceof AdSize)) {
            return false;
        }
        object = (AdSize)object;
        return this.c.equals(object.c);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public /* varargs */ AdSize findBestSize(AdSize ... var1_1) {
        var11_2 = null;
        var12_3 = null;
        if (var1_1 == null) {
            return var12_3;
        }
        var2_4 = 0.0f;
        var6_5 = this.getWidth();
        var7_6 = this.getHeight();
        var8_7 = var1_1.length;
        var5_8 = 0;
        do {
            var12_3 = var11_2;
            if (var5_8 >= var8_7) return var12_3;
            var12_3 = var1_1[var5_8];
            var9_11 = var12_3.getWidth();
            if (!this.isSizeAppropriate(var9_11, var10_12 = var12_3.getHeight())) ** GOTO lbl-1000
            var3_9 = var4_10 = (float)(var9_11 * var10_12) / (float)(var6_5 * var7_6);
            if (var4_10 > 1.0f) {
                var3_9 = 1.0f / var4_10;
            }
            if (var3_9 > var2_4) {
                var11_2 = var12_3;
            } else lbl-1000: // 2 sources:
            {
                var3_9 = var2_4;
            }
            ++var5_8;
            var2_4 = var3_9;
        } while (true);
    }

    public int getHeight() {
        return this.c.getHeight();
    }

    public int getHeightInPixels(Context context) {
        return this.c.getHeightInPixels(context);
    }

    public int getWidth() {
        return this.c.getWidth();
    }

    public int getWidthInPixels(Context context) {
        return this.c.getWidthInPixels(context);
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public boolean isAutoHeight() {
        return this.c.isAutoHeight();
    }

    public boolean isCustomAdSize() {
        return false;
    }

    public boolean isFullWidth() {
        return this.c.isFullWidth();
    }

    public boolean isSizeAppropriate(int n2, int n3) {
        int n4 = this.getWidth();
        int n5 = this.getHeight();
        if ((float)n2 <= (float)n4 * 1.25f && (float)n2 >= (float)n4 * 0.8f && (float)n3 <= (float)n5 * 1.25f && (float)n3 >= (float)n5 * 0.8f) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.c.toString();
    }
}

