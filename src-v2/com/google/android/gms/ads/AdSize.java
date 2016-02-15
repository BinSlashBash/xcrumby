/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 */
package com.google.android.gms.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.dv;

public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
    public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
    public static final int FULL_WIDTH = -1;
    public static final AdSize LARGE_BANNER = new AdSize(320, 100, "320x100_as");
    public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
    public static final AdSize MEDIUM_RECTANGLE = new AdSize(300, 250, "300x250_as");
    public static final AdSize SMART_BANNER;
    public static final AdSize WIDE_SKYSCRAPER;
    private final int kr;
    private final int ks;
    private final String kt;

    static {
        WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
        SMART_BANNER = new AdSize(-1, -2, "smart_banner");
    }

    /*
     * Enabled aggressive block sorting
     */
    public AdSize(int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = n2 == -1 ? "FULL" : String.valueOf(n2);
        stringBuilder = stringBuilder.append(string2).append("x");
        string2 = n3 == -2 ? "AUTO" : String.valueOf(n3);
        this(n2, n3, stringBuilder.append(string2).append("_as").toString());
    }

    AdSize(int n2, int n3, String string2) {
        if (n2 < 0 && n2 != -1) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + n2);
        }
        if (n3 < 0 && n3 != -2) {
            throw new IllegalArgumentException("Invalid height for AdSize: " + n3);
        }
        this.kr = n2;
        this.ks = n3;
        this.kt = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AdSize)) {
            return false;
        }
        object = (AdSize)object;
        if (this.kr != object.kr) return false;
        if (this.ks != object.ks) return false;
        if (this.kt.equals(object.kt)) return true;
        return false;
    }

    public int getHeight() {
        return this.ks;
    }

    public int getHeightInPixels(Context context) {
        if (this.ks == -2) {
            return ak.b(context.getResources().getDisplayMetrics());
        }
        return dv.a(context, this.ks);
    }

    public int getWidth() {
        return this.kr;
    }

    public int getWidthInPixels(Context context) {
        if (this.kr == -1) {
            return ak.a(context.getResources().getDisplayMetrics());
        }
        return dv.a(context, this.kr);
    }

    public int hashCode() {
        return this.kt.hashCode();
    }

    public boolean isAutoHeight() {
        if (this.ks == -2) {
            return true;
        }
        return false;
    }

    public boolean isFullWidth() {
        if (this.kr == -1) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.kt;
    }
}

