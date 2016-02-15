/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.ads.mediation;

import android.app.Activity;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;

@Deprecated
public interface MediationInterstitialAdapter<ADDITIONAL_PARAMETERS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters>
extends MediationAdapter<ADDITIONAL_PARAMETERS, SERVER_PARAMETERS> {
    public void requestInterstitialAd(MediationInterstitialListener var1, Activity var2, SERVER_PARAMETERS var3, MediationAdRequest var4, ADDITIONAL_PARAMETERS var5);

    public void showInterstitial();
}

