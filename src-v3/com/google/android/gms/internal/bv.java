package com.google.android.gms.internal;

import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;

public final class bv implements MediationBannerListener, MediationInterstitialListener {
    private final bs nG;

    public bv(bs bsVar) {
        this.nG = bsVar;
    }

    public void onAdClicked(MediationBannerAdapter adapter) {
        fq.aj("onClick must be called on the main UI thread.");
        dw.m819v("Adapter called onClick.");
        try {
            this.nG.m673P();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdClicked.", e);
        }
    }

    public void onAdClosed(MediationBannerAdapter adapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.m819v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdClosed.", e);
        }
    }

    public void onAdClosed(MediationInterstitialAdapter adapter) {
        fq.aj("onAdClosed must be called on the main UI thread.");
        dw.m819v("Adapter called onAdClosed.");
        try {
            this.nG.onAdClosed();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdClosed.", e);
        }
    }

    public void onAdFailedToLoad(MediationBannerAdapter adapter, int errorCode) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.m819v("Adapter called onAdFailedToLoad with error. " + errorCode);
        try {
            this.nG.onAdFailedToLoad(errorCode);
        } catch (Throwable e) {
            dw.m817c("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onAdFailedToLoad(MediationInterstitialAdapter adapter, int errorCode) {
        fq.aj("onAdFailedToLoad must be called on the main UI thread.");
        dw.m819v("Adapter called onAdFailedToLoad with error " + errorCode + ".");
        try {
            this.nG.onAdFailedToLoad(errorCode);
        } catch (Throwable e) {
            dw.m817c("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onAdLeftApplication(MediationBannerAdapter adapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.m819v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdLeftApplication.", e);
        }
    }

    public void onAdLeftApplication(MediationInterstitialAdapter adapter) {
        fq.aj("onAdLeftApplication must be called on the main UI thread.");
        dw.m819v("Adapter called onAdLeftApplication.");
        try {
            this.nG.onAdLeftApplication();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdLeftApplication.", e);
        }
    }

    public void onAdLoaded(MediationBannerAdapter adapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.m819v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdLoaded.", e);
        }
    }

    public void onAdLoaded(MediationInterstitialAdapter adapter) {
        fq.aj("onAdLoaded must be called on the main UI thread.");
        dw.m819v("Adapter called onAdLoaded.");
        try {
            this.nG.onAdLoaded();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdLoaded.", e);
        }
    }

    public void onAdOpened(MediationBannerAdapter adapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.m819v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdOpened.", e);
        }
    }

    public void onAdOpened(MediationInterstitialAdapter adapter) {
        fq.aj("onAdOpened must be called on the main UI thread.");
        dw.m819v("Adapter called onAdOpened.");
        try {
            this.nG.onAdOpened();
        } catch (Throwable e) {
            dw.m817c("Could not call onAdOpened.", e);
        }
    }
}
