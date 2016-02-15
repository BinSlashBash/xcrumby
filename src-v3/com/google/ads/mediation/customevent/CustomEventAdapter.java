package com.google.ads.mediation.customevent;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.internal.dw;

public final class CustomEventAdapter implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>, MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {
    private View f78n;
    private CustomEventBanner f79o;
    private CustomEventInterstitial f80p;

    /* renamed from: com.google.ads.mediation.customevent.CustomEventAdapter.a */
    private static final class C1288a implements CustomEventBannerListener {
        private final CustomEventAdapter f73q;
        private final MediationBannerListener f74r;

        public C1288a(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.f73q = customEventAdapter;
            this.f74r = mediationBannerListener;
        }

        public void onClick() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f74r.onClick(this.f73q);
        }

        public void onDismissScreen() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f74r.onDismissScreen(this.f73q);
        }

        public void onFailedToReceiveAd() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f74r.onFailedToReceiveAd(this.f73q, ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f74r.onLeaveApplication(this.f73q);
        }

        public void onPresentScreen() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f74r.onPresentScreen(this.f73q);
        }

        public void onReceivedAd(View view) {
            dw.m819v("Custom event adapter called onReceivedAd.");
            this.f73q.m2631a(view);
            this.f74r.onReceivedAd(this.f73q);
        }
    }

    /* renamed from: com.google.ads.mediation.customevent.CustomEventAdapter.b */
    private class C1289b implements CustomEventInterstitialListener {
        private final CustomEventAdapter f75q;
        private final MediationInterstitialListener f76s;
        final /* synthetic */ CustomEventAdapter f77t;

        public C1289b(CustomEventAdapter customEventAdapter, CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
            this.f77t = customEventAdapter;
            this.f75q = customEventAdapter2;
            this.f76s = mediationInterstitialListener;
        }

        public void onDismissScreen() {
            dw.m819v("Custom event adapter called onDismissScreen.");
            this.f76s.onDismissScreen(this.f75q);
        }

        public void onFailedToReceiveAd() {
            dw.m819v("Custom event adapter called onFailedToReceiveAd.");
            this.f76s.onFailedToReceiveAd(this.f75q, ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            dw.m819v("Custom event adapter called onLeaveApplication.");
            this.f76s.onLeaveApplication(this.f75q);
        }

        public void onPresentScreen() {
            dw.m819v("Custom event adapter called onPresentScreen.");
            this.f76s.onPresentScreen(this.f75q);
        }

        public void onReceivedAd() {
            dw.m819v("Custom event adapter called onReceivedAd.");
            this.f76s.onReceivedAd(this.f77t);
        }
    }

    private static <T> T m2630a(String str) {
        try {
            return Class.forName(str).newInstance();
        } catch (Throwable th) {
            dw.m823z("Could not instantiate custom event adapter: " + str + ". " + th.getMessage());
            return null;
        }
    }

    private void m2631a(View view) {
        this.f78n = view;
    }

    public void destroy() {
        if (this.f79o != null) {
            this.f79o.destroy();
        }
        if (this.f80p != null) {
            this.f80p.destroy();
        }
    }

    public Class<CustomEventExtras> getAdditionalParametersType() {
        return CustomEventExtras.class;
    }

    public View getBannerView() {
        return this.f78n;
    }

    public Class<CustomEventServerParameters> getServerParametersType() {
        return CustomEventServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener listener, Activity activity, CustomEventServerParameters serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.f79o = (CustomEventBanner) m2630a(serverParameters.className);
        if (this.f79o == null) {
            listener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.f79o.requestBannerAd(new C1288a(this, listener), activity, serverParameters.label, serverParameters.parameter, adSize, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(serverParameters.label));
        }
    }

    public void requestInterstitialAd(MediationInterstitialListener listener, Activity activity, CustomEventServerParameters serverParameters, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.f80p = (CustomEventInterstitial) m2630a(serverParameters.className);
        if (this.f80p == null) {
            listener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.f80p.requestInterstitialAd(new C1289b(this, this, listener), activity, serverParameters.label, serverParameters.parameter, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(serverParameters.label));
        }
    }

    public void showInterstitial() {
        this.f80p.showInterstitial();
    }
}
