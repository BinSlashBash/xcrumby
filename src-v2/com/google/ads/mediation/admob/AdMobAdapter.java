/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.View
 */
package com.google.ads.mediation.admob;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.dv;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public final class AdMobAdapter
implements MediationBannerAdapter,
MediationInterstitialAdapter {
    private AdView i;
    private InterstitialAd j;

    /*
     * Enabled aggressive block sorting
     */
    private static AdRequest a(Context context, MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        int n2;
        AdRequest.Builder builder = new AdRequest.Builder();
        Object object = mediationAdRequest.getBirthday();
        if (object != null) {
            builder.setBirthday((Date)object);
        }
        if ((n2 = mediationAdRequest.getGender()) != 0) {
            builder.setGender(n2);
        }
        if ((object = mediationAdRequest.getKeywords()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                builder.addKeyword((String)object.next());
            }
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(dv.l(context));
        }
        if (bundle2.getInt("tagForChildDirectedTreatment") != -1) {
            boolean bl2 = bundle2.getInt("tagForChildDirectedTreatment") == 1;
            builder.tagForChildDirectedTreatment(bl2);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("gw", 1);
        bundle.putString("mad_hac", bundle2.getString("mad_hac"));
        if (!TextUtils.isEmpty((CharSequence)bundle2.getString("adJson"))) {
            bundle.putString("_ad", bundle2.getString("adJson"));
        }
        bundle.putBoolean("_noRefresh", true);
        builder.addNetworkExtrasBundle(AdMobAdapter.class, bundle);
        return builder.build();
    }

    @Override
    public View getBannerView() {
        return this.i;
    }

    @Override
    public void onDestroy() {
        if (this.i != null) {
            this.i.destroy();
            this.i = null;
        }
        if (this.j != null) {
            this.j = null;
        }
    }

    @Override
    public void onPause() {
        if (this.i != null) {
            this.i.pause();
        }
    }

    @Override
    public void onResume() {
        if (this.i != null) {
            this.i.resume();
        }
    }

    @Override
    public void requestBannerAd(Context context, MediationBannerListener mediationBannerListener, Bundle bundle, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.i = new AdView(context);
        this.i.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        this.i.setAdUnitId(bundle.getString("pubid"));
        this.i.setAdListener(new a(this, mediationBannerListener));
        this.i.loadAd(AdMobAdapter.a(context, mediationAdRequest, bundle2, bundle));
    }

    @Override
    public void requestInterstitialAd(Context context, MediationInterstitialListener mediationInterstitialListener, Bundle bundle, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.j = new InterstitialAd(context);
        this.j.setAdUnitId(bundle.getString("pubid"));
        this.j.setAdListener(new b(this, mediationInterstitialListener));
        this.j.loadAd(AdMobAdapter.a(context, mediationAdRequest, bundle2, bundle));
    }

    @Override
    public void showInterstitial() {
        this.j.show();
    }

    private static final class a
    extends AdListener {
        private final AdMobAdapter k;
        private final MediationBannerListener l;

        public a(AdMobAdapter adMobAdapter, MediationBannerListener mediationBannerListener) {
            this.k = adMobAdapter;
            this.l = mediationBannerListener;
        }

        @Override
        public void onAdClosed() {
            this.l.onAdClosed(this.k);
        }

        @Override
        public void onAdFailedToLoad(int n2) {
            this.l.onAdFailedToLoad(this.k, n2);
        }

        @Override
        public void onAdLeftApplication() {
            this.l.onAdLeftApplication(this.k);
        }

        @Override
        public void onAdLoaded() {
            this.l.onAdLoaded(this.k);
        }

        @Override
        public void onAdOpened() {
            this.l.onAdClicked(this.k);
            this.l.onAdOpened(this.k);
        }
    }

    private static final class b
    extends AdListener {
        private final AdMobAdapter k;
        private final MediationInterstitialListener m;

        public b(AdMobAdapter adMobAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.k = adMobAdapter;
            this.m = mediationInterstitialListener;
        }

        @Override
        public void onAdClosed() {
            this.m.onAdClosed(this.k);
        }

        @Override
        public void onAdFailedToLoad(int n2) {
            this.m.onAdFailedToLoad(this.k, n2);
        }

        @Override
        public void onAdLeftApplication() {
            this.m.onAdLeftApplication(this.k);
        }

        @Override
        public void onAdLoaded() {
            this.m.onAdLoaded(this.k);
        }

        @Override
        public void onAdOpened() {
            this.m.onAdOpened(this.k);
        }
    }

}

