/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.view.View
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.a;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.bt;
import com.google.android.gms.internal.bv;
import com.google.android.gms.internal.dw;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public final class bu
extends br.a {
    private final MediationAdapter nE;
    private final Bundle nF;

    public bu(MediationAdapter mediationAdapter, Bundle bundle) {
        this.nE = mediationAdapter;
        this.nF = bundle;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bundle a(String object, int n2, String string2) throws RemoteException {
        dw.z("Server parameters: " + (String)object);
        try {
            Bundle bundle = new Bundle();
            if (object != null) {
                object = new JSONObject((String)object);
                bundle = new Bundle();
                Iterator iterator = object.keys();
                while (iterator.hasNext()) {
                    String string3 = (String)iterator.next();
                    bundle.putString(string3, object.getString(string3));
                }
            }
            if (this.nE instanceof AdMobAdapter) {
                bundle.putString("adJson", string2);
                bundle.putInt("tagForChildDirectedTreatment", n2);
            }
            return bundle;
        }
        catch (Throwable var1_2) {
            dw.c("Could not get Server Parameters Bundle.", var1_2);
            throw new RemoteException();
        }
    }

    @Override
    public void a(d d2, ah ah2, String string2, bs bs2) throws RemoteException {
        this.a(d2, ah2, string2, null, bs2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(d d2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
        if (!(this.nE instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting interstitial ad from adapter.");
        try {
            MediationInterstitialAdapter mediationInterstitialAdapter = (MediationInterstitialAdapter)this.nE;
            HashSet<String> hashSet = ah2.lJ != null ? new HashSet<String>(ah2.lJ) : null;
            hashSet = new bt(new Date(ah2.lH), ah2.lI, hashSet, ah2.lK, ah2.lL);
            mediationInterstitialAdapter.requestInterstitialAd((Context)e.d(d2), new bv(bs2), this.a(string2, ah2.lL, string3), (MediationAdRequest)((Object)hashSet), this.nF);
            return;
        }
        catch (Throwable var1_2) {
            dw.c("Could not request interstitial ad from adapter.", var1_2);
            throw new RemoteException();
        }
    }

    @Override
    public void a(d d2, ak ak2, ah ah2, String string2, bs bs2) throws RemoteException {
        this.a(d2, ak2, ah2, string2, null, bs2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(d d2, ak ak2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
        if (!(this.nE instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting banner ad from adapter.");
        try {
            MediationBannerAdapter mediationBannerAdapter = (MediationBannerAdapter)this.nE;
            HashSet<String> hashSet = ah2.lJ != null ? new HashSet<String>(ah2.lJ) : null;
            hashSet = new bt(new Date(ah2.lH), ah2.lI, hashSet, ah2.lK, ah2.lL);
            mediationBannerAdapter.requestBannerAd((Context)e.d(d2), new bv(bs2), this.a(string2, ah2.lL, string3), a.a(ak2.width, ak2.height, ak2.lS), (MediationAdRequest)((Object)hashSet), this.nF);
            return;
        }
        catch (Throwable var1_2) {
            dw.c("Could not request banner ad from adapter.", var1_2);
            throw new RemoteException();
        }
    }

    @Override
    public void destroy() throws RemoteException {
        try {
            this.nE.onDestroy();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not destroy adapter.", var1_1);
            throw new RemoteException();
        }
    }

    @Override
    public d getView() throws RemoteException {
        if (!(this.nE instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            d d2 = e.h(((MediationBannerAdapter)this.nE).getBannerView());
            return d2;
        }
        catch (Throwable var1_2) {
            dw.c("Could not get banner view from adapter.", var1_2);
            throw new RemoteException();
        }
    }

    @Override
    public void pause() throws RemoteException {
        try {
            this.nE.onPause();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not pause adapter.", var1_1);
            throw new RemoteException();
        }
    }

    @Override
    public void resume() throws RemoteException {
        try {
            this.nE.onResume();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not resume adapter.", var1_1);
            throw new RemoteException();
        }
    }

    @Override
    public void showInterstitial() throws RemoteException {
        if (!(this.nE instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter)this.nE).showInterstitial();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not show interstitial from adapter.", var1_1);
            throw new RemoteException();
        }
    }
}

