/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.text.TextUtils
 *  android.util.AttributeSet
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.ads.AdSize;

public final class an {
    private final AdSize[] lW;
    private final String lX;

    /*
     * Enabled aggressive block sorting
     */
    public an(Context context, AttributeSet object) {
        boolean bl2 = true;
        context = context.getResources().obtainAttributes((AttributeSet)object, R.styleable.AdsAttrs);
        object = context.getString(0);
        String string2 = context.getString(1);
        boolean bl3 = !TextUtils.isEmpty((CharSequence)object);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            bl2 = false;
        }
        if (bl3 && !bl2) {
            this.lW = an.f((String)object);
        } else {
            if (bl3 || !bl2) {
                if (!bl3 || !bl2) throw new IllegalArgumentException("Required XML attribute \"adSize\" was missing.");
                {
                    throw new IllegalArgumentException("Either XML attribute \"adSize\" or XML attribute \"supportedAdSizes\" should be specified, but not both.");
                }
            }
            this.lW = an.f(string2);
        }
        this.lX = context.getString(2);
        if (!TextUtils.isEmpty((CharSequence)this.lX)) return;
        {
            throw new IllegalArgumentException("Required XML attribute \"adUnitId\" was missing.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static AdSize[] f(String string2) {
        String[] arrstring = string2.split("\\s*,\\s*");
        AdSize[] arradSize = new AdSize[arrstring.length];
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            String string3 = arrstring[i2].trim();
            if (string3.matches("^(\\d+|FULL_WIDTH)\\s*[xX]\\s*(\\d+|AUTO_HEIGHT)$")) {
                int n2;
                int n3;
                String[] arrstring2 = string3.split("[xX]");
                arrstring2[0] = arrstring2[0].trim();
                arrstring2[1] = arrstring2[1].trim();
                try {
                    n3 = "FULL_WIDTH".equals(arrstring2[0]) ? -1 : Integer.parseInt(arrstring2[0]);
                    boolean bl2 = "AUTO_HEIGHT".equals(arrstring2[1]);
                    n2 = bl2 ? -2 : Integer.parseInt(arrstring2[1]);
                }
                catch (NumberFormatException var0_1) {
                    throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + string3);
                }
                arradSize[i2] = new AdSize(n3, n2);
                continue;
            }
            if ("BANNER".equals(string3)) {
                arradSize[i2] = AdSize.BANNER;
                continue;
            }
            if ("LARGE_BANNER".equals(string3)) {
                arradSize[i2] = AdSize.LARGE_BANNER;
                continue;
            }
            if ("FULL_BANNER".equals(string3)) {
                arradSize[i2] = AdSize.FULL_BANNER;
                continue;
            }
            if ("LEADERBOARD".equals(string3)) {
                arradSize[i2] = AdSize.LEADERBOARD;
                continue;
            }
            if ("MEDIUM_RECTANGLE".equals(string3)) {
                arradSize[i2] = AdSize.MEDIUM_RECTANGLE;
                continue;
            }
            if ("SMART_BANNER".equals(string3)) {
                arradSize[i2] = AdSize.SMART_BANNER;
                continue;
            }
            if (!"WIDE_SKYSCRAPER".equals(string3)) {
                throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + string3);
            }
            arradSize[i2] = AdSize.WIDE_SKYSCRAPER;
        }
        if (arradSize.length == 0) {
            throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + string2);
        }
        return arradSize;
    }

    public AdSize[] e(boolean bl2) {
        if (!bl2 && this.lW.length != 1) {
            throw new IllegalArgumentException("The adSizes XML attribute is only allowed on PublisherAdViews.");
        }
        return this.lW;
    }

    public String getAdUnitId() {
        return this.lX;
    }
}

