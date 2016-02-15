package com.google.ads.mediation.admob;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.dv;
import java.util.Date;
import java.util.Set;

public final class AdMobAdapter implements MediationBannerAdapter, MediationInterstitialAdapter {
    private AdView f71i;
    private InterstitialAd f72j;

    /* renamed from: com.google.ads.mediation.admob.AdMobAdapter.a */
    private static final class C0762a extends AdListener {
        private final AdMobAdapter f62k;
        private final MediationBannerListener f63l;

        public C0762a(AdMobAdapter adMobAdapter, MediationBannerListener mediationBannerListener) {
            this.f62k = adMobAdapter;
            this.f63l = mediationBannerListener;
        }

        public void onAdClosed() {
            this.f63l.onAdClosed(this.f62k);
        }

        public void onAdFailedToLoad(int errorCode) {
            this.f63l.onAdFailedToLoad(this.f62k, errorCode);
        }

        public void onAdLeftApplication() {
            this.f63l.onAdLeftApplication(this.f62k);
        }

        public void onAdLoaded() {
            this.f63l.onAdLoaded(this.f62k);
        }

        public void onAdOpened() {
            this.f63l.onAdClicked(this.f62k);
            this.f63l.onAdOpened(this.f62k);
        }
    }

    /* renamed from: com.google.ads.mediation.admob.AdMobAdapter.b */
    private static final class C0763b extends AdListener {
        private final AdMobAdapter f64k;
        private final MediationInterstitialListener f65m;

        public C0763b(AdMobAdapter adMobAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.f64k = adMobAdapter;
            this.f65m = mediationInterstitialListener;
        }

        public void onAdClosed() {
            this.f65m.onAdClosed(this.f64k);
        }

        public void onAdFailedToLoad(int errorCode) {
            this.f65m.onAdFailedToLoad(this.f64k, errorCode);
        }

        public void onAdLeftApplication() {
            this.f65m.onAdLeftApplication(this.f64k);
        }

        public void onAdLoaded() {
            this.f65m.onAdLoaded(this.f64k);
        }

        public void onAdOpened() {
            this.f65m.onAdOpened(this.f64k);
        }
    }

    private static AdRequest m2629a(Context context, MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        Builder builder = new Builder();
        Date birthday = mediationAdRequest.getBirthday();
        if (birthday != null) {
            builder.setBirthday(birthday);
        }
        int gender = mediationAdRequest.getGender();
        if (gender != 0) {
            builder.setGender(gender);
        }
        Set<String> keywords = mediationAdRequest.getKeywords();
        if (keywords != null) {
            for (String addKeyword : keywords) {
                builder.addKeyword(addKeyword);
            }
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(dv.m813l(context));
        }
        if (bundle2.getInt("tagForChildDirectedTreatment") != -1) {
            builder.tagForChildDirectedTreatment(bundle2.getInt("tagForChildDirectedTreatment") == 1);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("gw", 1);
        bundle.putString("mad_hac", bundle2.getString("mad_hac"));
        if (!TextUtils.isEmpty(bundle2.getString("adJson"))) {
            bundle.putString("_ad", bundle2.getString("adJson"));
        }
        bundle.putBoolean("_noRefresh", true);
        builder.addNetworkExtrasBundle(AdMobAdapter.class, bundle);
        return builder.build();
    }

    public View getBannerView() {
        return this.f71i;
    }

    public void onDestroy() {
        if (this.f71i != null) {
            this.f71i.destroy();
            this.f71i = null;
        }
        if (this.f72j != null) {
            this.f72j = null;
        }
    }

    public void onPause() {
        if (this.f71i != null) {
            this.f71i.pause();
        }
    }

    public void onResume() {
        if (this.f71i != null) {
            this.f71i.resume();
        }
    }

    public void requestBannerAd(Context context, MediationBannerListener bannerListener, Bundle serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle extras) {
        this.f71i = new AdView(context);
        this.f71i.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        this.f71i.setAdUnitId(serverParameters.getString("pubid"));
        this.f71i.setAdListener(new C0762a(this, bannerListener));
        this.f71i.loadAd(m2629a(context, mediationAdRequest, extras, serverParameters));
    }

    public void requestInterstitialAd(Context context, MediationInterstitialListener interstitialListener, Bundle serverParameters, MediationAdRequest mediationAdRequest, Bundle extras) {
        this.f72j = new InterstitialAd(context);
        this.f72j.setAdUnitId(serverParameters.getString("pubid"));
        this.f72j.setAdListener(new C0763b(this, interstitialListener));
        this.f72j.loadAd(m2629a(context, mediationAdRequest, extras, serverParameters));
    }

    public void showInterstitial() {
        this.f72j.show();
    }
}
