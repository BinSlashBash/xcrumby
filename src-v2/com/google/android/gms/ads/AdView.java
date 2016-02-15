/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.gms.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.at;

public final class AdView
extends ViewGroup {
    private final at ku;

    public AdView(Context context) {
        super(context);
        this.ku = new at(this);
    }

    public AdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ku = new at(this, attributeSet, false);
    }

    public AdView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.ku = new at(this, attributeSet, false);
    }

    public void destroy() {
        this.ku.destroy();
    }

    public AdListener getAdListener() {
        return this.ku.getAdListener();
    }

    public AdSize getAdSize() {
        return this.ku.getAdSize();
    }

    public String getAdUnitId() {
        return this.ku.getAdUnitId();
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.ku.getInAppPurchaseListener();
    }

    public void loadAd(AdRequest adRequest) {
        this.ku.a(adRequest.O());
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        View view = this.getChildAt(0);
        if (view != null && view.getVisibility() != 8) {
            int n6 = view.getMeasuredWidth();
            int n7 = view.getMeasuredHeight();
            n2 = (n4 - n2 - n6) / 2;
            n3 = (n5 - n3 - n7) / 2;
            view.layout(n2, n3, n6 + n2, n7 + n3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        int n5 = 0;
        View view = this.getChildAt(0);
        AdSize adSize = this.getAdSize();
        if (view != null && view.getVisibility() != 8) {
            this.measureChild(view, n2, n3);
            n4 = view.getMeasuredWidth();
            n5 = view.getMeasuredHeight();
        } else if (adSize != null) {
            view = this.getContext();
            n4 = adSize.getWidthInPixels((Context)view);
            n5 = adSize.getHeightInPixels((Context)view);
        } else {
            n4 = 0;
        }
        n4 = Math.max(n4, this.getSuggestedMinimumWidth());
        n5 = Math.max(n5, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSize((int)n4, (int)n2), View.resolveSize((int)n5, (int)n3));
    }

    public void pause() {
        this.ku.pause();
    }

    public void resume() {
        this.ku.resume();
    }

    public void setAdListener(AdListener adListener) {
        this.ku.setAdListener(adListener);
    }

    public void setAdSize(AdSize adSize) {
        this.ku.setAdSizes(adSize);
    }

    public void setAdUnitId(String string2) {
        this.ku.setAdUnitId(string2);
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        this.ku.setInAppPurchaseListener(inAppPurchaseListener);
    }
}

