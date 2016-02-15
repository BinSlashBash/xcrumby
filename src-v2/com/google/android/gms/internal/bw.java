/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.RemoteException
 *  android.view.View
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.os.RemoteException;
import android.view.View;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.bx;
import com.google.android.gms.internal.by;
import com.google.android.gms.internal.dw;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public final class bw<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters>
extends br.a {
    private final MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> nH;
    private final NETWORK_EXTRAS nI;

    public bw(MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> mediationAdapter, NETWORK_EXTRAS NETWORK_EXTRAS) {
        this.nH = mediationAdapter;
        this.nI = NETWORK_EXTRAS;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private SERVER_PARAMETERS b(String var1_1, int var2_3, String var3_4) throws RemoteException {
        block5 : {
            if (var1_1 == null) ** GOTO lbl13
            try {
                var4_5 = new JSONObject((String)var1_1);
                var3_4 = new HashMap<K, V>(var4_5.length());
                var5_6 = var4_5.keys();
                do {
                    var1_1 = var3_4;
                    if (var5_6.hasNext()) {
                        var1_1 = (String)var5_6.next();
                        var3_4.put(var1_1, var4_5.getString((String)var1_1));
                        continue;
                    }
                    break block5;
                    break;
                } while (true);
lbl13: // 1 sources:
                var1_1 = new HashMap<String, String>(0);
            }
            catch (Throwable var1_2) {
                dw.c("Could not get MediationServerParameters.", var1_2);
                throw new RemoteException();
            }
        }
        var4_5 = this.nH.getServerParametersType();
        var3_4 = null;
        if (var4_5 == null) return (SERVER_PARAMETERS)var3_4;
        var3_4 = (MediationServerParameters)var4_5.newInstance();
        var3_4.load((Map<String, String>)var1_1);
        return (SERVER_PARAMETERS)var3_4;
    }

    @Override
    public void a(d d2, ah ah2, String string2, bs bs2) throws RemoteException {
        this.a(d2, ah2, string2, null, bs2);
    }

    @Override
    public void a(d d2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
        if (!(this.nH instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting interstitial ad from adapter.");
        try {
            ((MediationInterstitialAdapter)this.nH).requestInterstitialAd(new bx(bs2), (Activity)e.d(d2), this.b(string2, ah2.lL, string3), by.e(ah2), this.nI);
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

    @Override
    public void a(d d2, ak ak2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
        if (!(this.nH instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Requesting banner ad from adapter.");
        try {
            ((MediationBannerAdapter)this.nH).requestBannerAd(new bx(bs2), (Activity)e.d(d2), this.b(string2, ah2.lL, string3), by.b(ak2), by.e(ah2), this.nI);
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
            this.nH.destroy();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not destroy adapter.", var1_1);
            throw new RemoteException();
        }
    }

    @Override
    public d getView() throws RemoteException {
        if (!(this.nH instanceof MediationBannerAdapter)) {
            dw.z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            d d2 = e.h(((MediationBannerAdapter)this.nH).getBannerView());
            return d2;
        }
        catch (Throwable var1_2) {
            dw.c("Could not get banner view from adapter.", var1_2);
            throw new RemoteException();
        }
    }

    @Override
    public void pause() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void resume() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void showInterstitial() throws RemoteException {
        if (!(this.nH instanceof MediationInterstitialAdapter)) {
            dw.z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
        dw.v("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter)this.nH).showInterstitial();
            return;
        }
        catch (Throwable var1_1) {
            dw.c("Could not show interstitial from adapter.", var1_1);
            throw new RemoteException();
        }
    }
}

