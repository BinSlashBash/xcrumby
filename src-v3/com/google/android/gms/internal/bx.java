package com.google.android.gms.internal;

import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;

public final class bx<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {
    private final bs nG;

    /* renamed from: com.google.android.gms.internal.bx.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ bx nJ;
        final /* synthetic */ ErrorCode nK;

        AnonymousClass10(bx bxVar, ErrorCode errorCode) {
            this.nJ = bxVar;
            this.nK = errorCode;
        }

        public void run() {
            try {
                this.nJ.nG.onAdFailedToLoad(by.m674a(this.nK));
            } catch (Throwable e) {
                dw.m817c("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.1 */
    class C03291 implements Runnable {
        final /* synthetic */ bx nJ;

        C03291(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.m673P();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClicked.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.2 */
    class C03302 implements Runnable {
        final /* synthetic */ bx nJ;

        C03302(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdOpened();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.3 */
    class C03313 implements Runnable {
        final /* synthetic */ bx nJ;

        C03313(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdLoaded();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.4 */
    class C03324 implements Runnable {
        final /* synthetic */ bx nJ;

        C03324(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdClosed();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClosed.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.5 */
    class C03335 implements Runnable {
        final /* synthetic */ bx nJ;
        final /* synthetic */ ErrorCode nK;

        C03335(bx bxVar, ErrorCode errorCode) {
            this.nJ = bxVar;
            this.nK = errorCode;
        }

        public void run() {
            try {
                this.nJ.nG.onAdFailedToLoad(by.m674a(this.nK));
            } catch (Throwable e) {
                dw.m817c("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.6 */
    class C03346 implements Runnable {
        final /* synthetic */ bx nJ;

        C03346(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdLeftApplication();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLeftApplication.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.7 */
    class C03357 implements Runnable {
        final /* synthetic */ bx nJ;

        C03357(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdOpened();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.8 */
    class C03368 implements Runnable {
        final /* synthetic */ bx nJ;

        C03368(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdLoaded();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bx.9 */
    class C03379 implements Runnable {
        final /* synthetic */ bx nJ;

        C03379(bx bxVar) {
            this.nJ = bxVar;
        }

        public void run() {
            try {
                this.nJ.nG.onAdClosed();
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClosed.", e);
            }
        }
    }

    public bx(bs bsVar) {
        this.nG = bsVar;
    }

    public void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.m819v("Adapter called onClick.");
        if (dv.bD()) {
            try {
                this.nG.m673P();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClicked.", e);
                return;
            }
        }
        dw.m823z("onClick must be called on the main UI thread.");
        dv.rp.post(new C03291(this));
    }

    public void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.m819v("Adapter called onDismissScreen.");
        if (dv.bD()) {
            try {
                this.nG.onAdClosed();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClosed.", e);
                return;
            }
        }
        dw.m823z("onDismissScreen must be called on the main UI thread.");
        dv.rp.post(new C03324(this));
    }

    public void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.m819v("Adapter called onDismissScreen.");
        if (dv.bD()) {
            try {
                this.nG.onAdClosed();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdClosed.", e);
                return;
            }
        }
        dw.m823z("onDismissScreen must be called on the main UI thread.");
        dv.rp.post(new C03379(this));
    }

    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, ErrorCode errorCode) {
        dw.m819v("Adapter called onFailedToReceiveAd with error. " + errorCode);
        if (dv.bD()) {
            try {
                this.nG.onAdFailedToLoad(by.m674a(errorCode));
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        dw.m823z("onFailedToReceiveAd must be called on the main UI thread.");
        dv.rp.post(new C03335(this, errorCode));
    }

    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, ErrorCode errorCode) {
        dw.m819v("Adapter called onFailedToReceiveAd with error " + errorCode + ".");
        if (dv.bD()) {
            try {
                this.nG.onAdFailedToLoad(by.m674a(errorCode));
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        dw.m823z("onFailedToReceiveAd must be called on the main UI thread.");
        dv.rp.post(new AnonymousClass10(this, errorCode));
    }

    public void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.m819v("Adapter called onLeaveApplication.");
        if (dv.bD()) {
            try {
                this.nG.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        dw.m823z("onLeaveApplication must be called on the main UI thread.");
        dv.rp.post(new C03346(this));
    }

    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.m819v("Adapter called onLeaveApplication.");
        if (dv.bD()) {
            try {
                this.nG.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        dw.m823z("onLeaveApplication must be called on the main UI thread.");
        dv.rp.post(new Runnable() {
            final /* synthetic */ bx nJ;

            {
                this.nJ = r1;
            }

            public void run() {
                try {
                    this.nJ.nG.onAdLeftApplication();
                } catch (Throwable e) {
                    dw.m817c("Could not call onAdLeftApplication.", e);
                }
            }
        });
    }

    public void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.m819v("Adapter called onPresentScreen.");
        if (dv.bD()) {
            try {
                this.nG.onAdOpened();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdOpened.", e);
                return;
            }
        }
        dw.m823z("onPresentScreen must be called on the main UI thread.");
        dv.rp.post(new C03357(this));
    }

    public void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.m819v("Adapter called onPresentScreen.");
        if (dv.bD()) {
            try {
                this.nG.onAdOpened();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdOpened.", e);
                return;
            }
        }
        dw.m823z("onPresentScreen must be called on the main UI thread.");
        dv.rp.post(new C03302(this));
    }

    public void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.m819v("Adapter called onReceivedAd.");
        if (dv.bD()) {
            try {
                this.nG.onAdLoaded();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLoaded.", e);
                return;
            }
        }
        dw.m823z("onReceivedAd must be called on the main UI thread.");
        dv.rp.post(new C03368(this));
    }

    public void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.m819v("Adapter called onReceivedAd.");
        if (dv.bD()) {
            try {
                this.nG.onAdLoaded();
                return;
            } catch (Throwable e) {
                dw.m817c("Could not call onAdLoaded.", e);
                return;
            }
        }
        dw.m823z("onReceivedAd must be called on the main UI thread.");
        dv.rp.post(new C03313(this));
    }
}
