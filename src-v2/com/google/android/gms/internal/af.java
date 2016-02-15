/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.internal.ao;

public final class af
extends ao.a {
    private final AdListener lF;

    public af(AdListener adListener) {
        this.lF = adListener;
    }

    @Override
    public void onAdClosed() {
        this.lF.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int n2) {
        this.lF.onAdFailedToLoad(n2);
    }

    @Override
    public void onAdLeftApplication() {
        this.lF.onAdLeftApplication();
    }

    @Override
    public void onAdLoaded() {
        this.lF.onAdLoaded();
    }

    @Override
    public void onAdOpened() {
        this.lF.onAdOpened();
    }
}

