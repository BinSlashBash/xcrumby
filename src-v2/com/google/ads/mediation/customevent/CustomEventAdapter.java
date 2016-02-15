/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 */
package com.google.ads.mediation.customevent;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.ads.mediation.customevent.CustomEventBanner;
import com.google.ads.mediation.customevent.CustomEventBannerListener;
import com.google.ads.mediation.customevent.CustomEventInterstitial;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;
import com.google.ads.mediation.customevent.CustomEventServerParameters;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.internal.dw;

public final class CustomEventAdapter
implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>,
MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {
    private View n;
    private CustomEventBanner o;
    private CustomEventInterstitial p;

    private static <T> T a(String string2) {
        Object obj;
        try {
            obj = Class.forName(string2).newInstance();
        }
        catch (Throwable var1_2) {
            dw.z("Could not instantiate custom event adapter: " + string2 + ". " + var1_2.getMessage());
            return null;
        }
        return (T)obj;
    }

    private void a(View view) {
        this.n = view;
    }

    @Override
    public void destroy() {
        if (this.o != null) {
            this.o.destroy();
        }
        if (this.p != null) {
            this.p.destroy();
        }
    }

    @Override
    public Class<CustomEventExtras> getAdditionalParametersType() {
        return CustomEventExtras.class;
    }

    @Override
    public View getBannerView() {
        return this.n;
    }

    @Override
    public Class<CustomEventServerParameters> getServerParametersType() {
        return CustomEventServerParameters.class;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, CustomEventServerParameters customEventServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras object) {
        this.o = (CustomEventBanner)CustomEventAdapter.a(customEventServerParameters.className);
        if (this.o == null) {
            mediationBannerListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
            return;
        }
        object = object == null ? null : object.getExtra(customEventServerParameters.label);
        this.o.requestBannerAd(new a(this, mediationBannerListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, adSize, mediationAdRequest, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, Activity activity, CustomEventServerParameters customEventServerParameters, MediationAdRequest mediationAdRequest, CustomEventExtras object) {
        this.p = (CustomEventInterstitial)CustomEventAdapter.a(customEventServerParameters.className);
        if (this.p == null) {
            mediationInterstitialListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
            return;
        }
        object = object == null ? null : object.getExtra(customEventServerParameters.label);
        this.p.requestInterstitialAd(new b(this, mediationInterstitialListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, mediationAdRequest, object);
    }

    @Override
    public void showInterstitial() {
        this.p.showInterstitial();
    }

    private static final class a
    implements CustomEventBannerListener {
        private final CustomEventAdapter q;
        private final MediationBannerListener r;

        public a(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.q = customEventAdapter;
            this.r = mediationBannerListener;
        }

        @Override
        public void onClick() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.r.onClick(this.q);
        }

        @Override
        public void onDismissScreen() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.r.onDismissScreen(this.q);
        }

        @Override
        public void onFailedToReceiveAd() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.r.onFailedToReceiveAd(this.q, AdRequest.ErrorCode.NO_FILL);
        }

        @Override
        public void onLeaveApplication() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.r.onLeaveApplication(this.q);
        }

        @Override
        public void onPresentScreen() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.r.onPresentScreen(this.q);
        }

        @Override
        public void onReceivedAd(View view) {
            dw.v("Custom event adapter called onReceivedAd.");
            this.q.a(view);
            this.r.onReceivedAd(this.q);
        }
    }

    private class b
    implements CustomEventInterstitialListener {
        private final CustomEventAdapter q;
        private final MediationInterstitialListener s;

        public b(CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
            this.q = customEventAdapter2;
            this.s = mediationInterstitialListener;
        }

        @Override
        public void onDismissScreen() {
            dw.v("Custom event adapter called onDismissScreen.");
            this.s.onDismissScreen(this.q);
        }

        @Override
        public void onFailedToReceiveAd() {
            dw.v("Custom event adapter called onFailedToReceiveAd.");
            this.s.onFailedToReceiveAd(this.q, AdRequest.ErrorCode.NO_FILL);
        }

        @Override
        public void onLeaveApplication() {
            dw.v("Custom event adapter called onLeaveApplication.");
            this.s.onLeaveApplication(this.q);
        }

        @Override
        public void onPresentScreen() {
            dw.v("Custom event adapter called onPresentScreen.");
            this.s.onPresentScreen(this.q);
        }

        @Override
        public void onReceivedAd() {
            dw.v("Custom event adapter called onReceivedAd.");
            this.s.onReceivedAd(CustomEventAdapter.this);
        }
    }

}

