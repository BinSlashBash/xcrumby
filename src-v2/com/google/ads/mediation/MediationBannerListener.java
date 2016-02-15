/*
 * Decompiled with CFR 0_110.
 */
package com.google.ads.mediation;

import com.google.ads.AdRequest;
import com.google.ads.mediation.MediationBannerAdapter;

@Deprecated
public interface MediationBannerListener {
    public void onClick(MediationBannerAdapter<?, ?> var1);

    public void onDismissScreen(MediationBannerAdapter<?, ?> var1);

    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> var1, AdRequest.ErrorCode var2);

    public void onLeaveApplication(MediationBannerAdapter<?, ?> var1);

    public void onPresentScreen(MediationBannerAdapter<?, ?> var1);

    public void onReceivedAd(MediationBannerAdapter<?, ?> var1);
}

