/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.au;

public final class PublisherInterstitialAd {
    private final au kv;

    public PublisherInterstitialAd(Context context) {
        this.kv = new au(context);
    }

    public AdListener getAdListener() {
        return this.kv.getAdListener();
    }

    public String getAdUnitId() {
        return this.kv.getAdUnitId();
    }

    public AppEventListener getAppEventListener() {
        return this.kv.getAppEventListener();
    }

    public boolean isLoaded() {
        return this.kv.isLoaded();
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        this.kv.a(publisherAdRequest.O());
    }

    public void setAdListener(AdListener adListener) {
        this.kv.setAdListener(adListener);
    }

    public void setAdUnitId(String string2) {
        this.kv.setAdUnitId(string2);
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        this.kv.setAppEventListener(appEventListener);
    }

    public void show() {
        this.kv.show();
    }
}

