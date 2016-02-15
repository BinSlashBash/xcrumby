/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.fq;

public final class bv
implements MediationBannerListener,
MediationInterstitialListener {
    private final bs nG;

    public bv(bs bs2) {
        this.nG = bs2;
    }

    @Override
    public void onAdClicked(MediationBannerAdapter mediationBannerAdapter) {
        fq.aj("onClick must be called on the main UI thread.");
        dw.v("Adapter called onClick.");
        try {
            this.nG.P();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClicked.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdClosed(MediationBannerAdapter mediationBannerAdapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClosed.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdClosed(MediationInterstitialAdapter mediationInterstitialAdapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClosed.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdFailedToLoad(MediationBannerAdapter mediationBannerAdapter, int n2) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.v("Adapter called onAdFailedToLoad with error. " + n2);
        try {
            this.nG.onAdFailedToLoad(n2);
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdFailedToLoad(MediationInterstitialAdapter mediationInterstitialAdapter, int n2) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.v("Adapter called onAdFailedToLoad with error " + n2 + ".");
        try {
            this.nG.onAdFailedToLoad(n2);
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdLeftApplication(MediationBannerAdapter mediationBannerAdapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLeftApplication.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdLeftApplication(MediationInterstitialAdapter mediationInterstitialAdapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLeftApplication.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdLoaded(MediationBannerAdapter mediationBannerAdapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLoaded.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdLoaded(MediationInterstitialAdapter mediationInterstitialAdapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLoaded.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdOpened(MediationBannerAdapter mediationBannerAdapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdOpened.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onAdOpened(MediationInterstitialAdapter mediationInterstitialAdapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdOpened.", (Throwable)var1_2);
            return;
        }
    }
}

