/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.lib.widget.thirdparty;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutPageTransformer
implements ViewPager.PageTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void transformPage(View view, float f2) {
        int n2 = view.getWidth();
        int n3 = view.getHeight();
        if (f2 < -1.0f) {
            view.setAlpha(0.0f);
            return;
        }
        if (f2 > 1.0f) {
            view.setAlpha(0.0f);
            return;
        }
        float f3 = Math.max(0.85f, 1.0f - Math.abs(f2));
        float f4 = (float)n3 * (1.0f - f3) / 2.0f;
        float f5 = (float)n2 * (1.0f - f3) / 2.0f;
        if (f2 < 0.0f) {
            view.setTranslationX(f5 - f4 / 2.0f);
        } else {
            view.setTranslationX(- f5 + f4 / 2.0f);
        }
        view.setScaleX(f3);
        view.setScaleY(f3);
        view.setAlpha((f3 - 0.85f) / 0.14999998f * 0.5f + 0.5f);
    }
}

