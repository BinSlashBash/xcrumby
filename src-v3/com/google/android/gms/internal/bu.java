package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.C0194a;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.br.C0847a;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONObject;

public final class bu extends C0847a {
    private final MediationAdapter nE;
    private final Bundle nF;

    public bu(MediationAdapter mediationAdapter, Bundle bundle) {
        this.nE = mediationAdapter;
        this.nF = bundle;
    }

    private Bundle m2914a(String str, int i, String str2) throws RemoteException {
        dw.m823z("Server parameters: " + str);
        try {
            Bundle bundle = new Bundle();
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                Bundle bundle2 = new Bundle();
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str3 = (String) keys.next();
                    bundle2.putString(str3, jSONObject.getString(str3));
                }
                bundle = bundle2;
            }
            if (this.nE instanceof AdMobAdapter) {
                bundle.putString("adJson", str2);
                bundle.putInt("tagForChildDirectedTreatment", i);
            }
            return bundle;
        } catch (Throwable th) {
            dw.m817c("Could not get Server Parameters Bundle.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void m2915a(C0306d c0306d, ah ahVar, String str, bs bsVar) throws RemoteException {
        m2916a(c0306d, ahVar, str, null, bsVar);
    }

    public void m2916a(C0306d c0306d, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (this.nE instanceof MediationInterstitialAdapter) {
            dw.m819v("Requesting interstitial ad from adapter.");
            try {
                MediationInterstitialAdapter mediationInterstitialAdapter = (MediationInterstitialAdapter) this.nE;
                mediationInterstitialAdapter.requestInterstitialAd((Context) C1324e.m2709d(c0306d), new bv(bsVar), m2914a(str, ahVar.lL, str2), new bt(new Date(ahVar.lH), ahVar.lI, ahVar.lJ != null ? new HashSet(ahVar.lJ) : null, ahVar.lK, ahVar.lL), this.nF);
            } catch (Throwable th) {
                dw.m817c("Could not request interstitial ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void m2917a(C0306d c0306d, ak akVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        m2918a(c0306d, akVar, ahVar, str, null, bsVar);
    }

    public void m2918a(C0306d c0306d, ak akVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (this.nE instanceof MediationBannerAdapter) {
            dw.m819v("Requesting banner ad from adapter.");
            try {
                MediationBannerAdapter mediationBannerAdapter = (MediationBannerAdapter) this.nE;
                mediationBannerAdapter.requestBannerAd((Context) C1324e.m2709d(c0306d), new bv(bsVar), m2914a(str, ahVar.lL, str2), C0194a.m5a(akVar.width, akVar.height, akVar.lS), new bt(new Date(ahVar.lH), ahVar.lI, ahVar.lJ != null ? new HashSet(ahVar.lJ) : null, ahVar.lK, ahVar.lL), this.nF);
            } catch (Throwable th) {
                dw.m817c("Could not request banner ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void destroy() throws RemoteException {
        try {
            this.nE.onDestroy();
        } catch (Throwable th) {
            dw.m817c("Could not destroy adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public C0306d getView() throws RemoteException {
        if (this.nE instanceof MediationBannerAdapter) {
            try {
                return C1324e.m2710h(((MediationBannerAdapter) this.nE).getBannerView());
            } catch (Throwable th) {
                dw.m817c("Could not get banner view from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationBannerAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void pause() throws RemoteException {
        try {
            this.nE.onPause();
        } catch (Throwable th) {
            dw.m817c("Could not pause adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void resume() throws RemoteException {
        try {
            this.nE.onResume();
        } catch (Throwable th) {
            dw.m817c("Could not resume adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void showInterstitial() throws RemoteException {
        if (this.nE instanceof MediationInterstitialAdapter) {
            dw.m819v("Showing interstitial from adapter.");
            try {
                ((MediationInterstitialAdapter) this.nE).showInterstitial();
            } catch (Throwable th) {
                dw.m817c("Could not show interstitial from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nE.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }
}
