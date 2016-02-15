/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.internal.http.HeaderParser;

public final class CacheControl {
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    private CacheControl(boolean bl2, boolean bl3, int n2, int n3, boolean bl4, boolean bl5, int n4, int n5, boolean bl6) {
        this.noCache = bl2;
        this.noStore = bl3;
        this.maxAgeSeconds = n2;
        this.sMaxAgeSeconds = n3;
        this.isPublic = bl4;
        this.mustRevalidate = bl5;
        this.maxStaleSeconds = n4;
        this.minFreshSeconds = n5;
        this.onlyIfCached = bl6;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static CacheControl parse(Headers var0) {
        block16 : {
            var15_1 = false;
            var14_2 = false;
            var5_3 = -1;
            var4_4 = -1;
            var13_5 = false;
            var12_6 = false;
            var3_7 = -1;
            var2_8 = -1;
            var11_9 = false;
            var6_10 = 0;
            block0 : while (var6_10 < var0.size()) {
                if (var0.name(var6_10).equalsIgnoreCase("Cache-Control") || var0.name(var6_10).equalsIgnoreCase("Pragma")) {
                    var22_22 = var0.value(var6_10);
                    var7_12 = 0;
                    break block16;
                }
                var16_16 = var11_9;
                var1_11 = var2_8;
                var8_13 = var3_7;
                var17_17 = var12_6;
                var18_18 = var13_5;
                var9_14 = var4_4;
                var10_15 = var5_3;
                var19_19 = var14_2;
                var20_20 = var15_1;
                do {
                    ++var6_10;
                    var15_1 = var20_20;
                    var14_2 = var19_19;
                    var5_3 = var10_15;
                    var4_4 = var9_14;
                    var13_5 = var18_18;
                    var12_6 = var17_17;
                    var3_7 = var8_13;
                    var2_8 = var1_11;
                    var11_9 = var16_16;
                    continue block0;
                    break;
                } while (true);
            }
            return new CacheControl(var15_1, var14_2, var5_3, var4_4, var13_5, var12_6, var3_7, var2_8, var11_9);
        }
        do {
            var20_20 = var15_1;
            var19_19 = var14_2;
            var10_15 = var5_3;
            var9_14 = var4_4;
            var18_18 = var13_5;
            var17_17 = var12_6;
            var8_13 = var3_7;
            var1_11 = var2_8;
            var16_16 = var11_9;
            if (var7_12 >= var22_22.length()) ** continue;
            var1_11 = HeaderParser.skipUntil(var22_22, var7_12, "=,;");
            var23_23 = var22_22.substring(var7_12, var1_11).trim();
            if (var1_11 == var22_22.length() || var22_22.charAt(var1_11) == ',' || var22_22.charAt(var1_11) == ';') {
                ++var1_11;
                var21_21 = null;
            } else {
                var7_12 = HeaderParser.skipWhitespace(var22_22, var1_11 + 1);
                if (var7_12 < var22_22.length() && var22_22.charAt(var7_12) == '\"') {
                    var1_11 = var7_12 + 1;
                    var7_12 = HeaderParser.skipUntil(var22_22, var1_11, "\"");
                    var21_21 = var22_22.substring(var1_11, var7_12);
                    var1_11 = var7_12 + 1;
                } else {
                    var1_11 = HeaderParser.skipUntil(var22_22, var7_12, ",;");
                    var21_21 = var22_22.substring(var7_12, var1_11).trim();
                }
            }
            if ("no-cache".equalsIgnoreCase(var23_23)) {
                var15_1 = true;
                var7_12 = var1_11;
                continue;
            }
            if ("no-store".equalsIgnoreCase(var23_23)) {
                var14_2 = true;
                var7_12 = var1_11;
                continue;
            }
            if ("max-age".equalsIgnoreCase(var23_23)) {
                var5_3 = HeaderParser.parseSeconds(var21_21);
                var7_12 = var1_11;
                continue;
            }
            if ("s-maxage".equalsIgnoreCase(var23_23)) {
                var4_4 = HeaderParser.parseSeconds(var21_21);
                var7_12 = var1_11;
                continue;
            }
            if ("public".equalsIgnoreCase(var23_23)) {
                var13_5 = true;
                var7_12 = var1_11;
                continue;
            }
            if ("must-revalidate".equalsIgnoreCase(var23_23)) {
                var12_6 = true;
                var7_12 = var1_11;
                continue;
            }
            if ("max-stale".equalsIgnoreCase(var23_23)) {
                var3_7 = HeaderParser.parseSeconds(var21_21);
                var7_12 = var1_11;
                continue;
            }
            if ("min-fresh".equalsIgnoreCase(var23_23)) {
                var2_8 = HeaderParser.parseSeconds(var21_21);
                var7_12 = var1_11;
                continue;
            }
            var7_12 = var1_11;
            if (!"only-if-cached".equalsIgnoreCase(var23_23)) continue;
            var11_9 = true;
            var7_12 = var1_11;
        } while (true);
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }
}

