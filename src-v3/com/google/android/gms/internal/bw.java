package com.google.android.gms.internal;

import android.app.Activity;
import android.os.RemoteException;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.br.C0847a;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public final class bw<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> extends C0847a {
    private final MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> nH;
    private final NETWORK_EXTRAS nI;

    public bw(MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> mediationAdapter, NETWORK_EXTRAS network_extras) {
        this.nH = mediationAdapter;
        this.nI = network_extras;
    }

    private SERVER_PARAMETERS m2919b(String str, int i, String str2) throws RemoteException {
        Map hashMap;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                hashMap = new HashMap(jSONObject.length());
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str3 = (String) keys.next();
                    hashMap.put(str3, jSONObject.getString(str3));
                }
            } catch (Throwable th) {
                dw.m817c("Could not get MediationServerParameters.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            hashMap = new HashMap(0);
        }
        Class serverParametersType = this.nH.getServerParametersType();
        if (serverParametersType == null) {
            return null;
        }
        MediationServerParameters mediationServerParameters = (MediationServerParameters) serverParametersType.newInstance();
        mediationServerParameters.load(hashMap);
        return mediationServerParameters;
    }

    public void m2920a(C0306d c0306d, ah ahVar, String str, bs bsVar) throws RemoteException {
        m2921a(c0306d, ahVar, str, null, bsVar);
    }

    public void m2921a(C0306d c0306d, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (this.nH instanceof MediationInterstitialAdapter) {
            dw.m819v("Requesting interstitial ad from adapter.");
            try {
                ((MediationInterstitialAdapter) this.nH).requestInterstitialAd(new bx(bsVar), (Activity) C1324e.m2709d(c0306d), m2919b(str, ahVar.lL, str2), by.m676e(ahVar), this.nI);
            } catch (Throwable th) {
                dw.m817c("Could not request interstitial ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void m2922a(C0306d c0306d, ak akVar, ah ahVar, String str, bs bsVar) throws RemoteException {
        m2923a(c0306d, akVar, ahVar, str, null, bsVar);
    }

    public void m2923a(C0306d c0306d, ak akVar, ah ahVar, String str, String str2, bs bsVar) throws RemoteException {
        if (this.nH instanceof MediationBannerAdapter) {
            dw.m819v("Requesting banner ad from adapter.");
            try {
                ((MediationBannerAdapter) this.nH).requestBannerAd(new bx(bsVar), (Activity) C1324e.m2709d(c0306d), m2919b(str, ahVar.lL, str2), by.m675b(akVar), by.m676e(ahVar), this.nI);
            } catch (Throwable th) {
                dw.m817c("Could not request banner ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void destroy() throws RemoteException {
        try {
            this.nH.destroy();
        } catch (Throwable th) {
            dw.m817c("Could not destroy adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public C0306d getView() throws RemoteException {
        if (this.nH instanceof MediationBannerAdapter) {
            try {
                return C1324e.m2710h(((MediationBannerAdapter) this.nH).getBannerView());
            } catch (Throwable th) {
                dw.m817c("Could not get banner view from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationBannerAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }

    public void pause() throws RemoteException {
        throw new RemoteException();
    }

    public void resume() throws RemoteException {
        throw new RemoteException();
    }

    public void showInterstitial() throws RemoteException {
        if (this.nH instanceof MediationInterstitialAdapter) {
            dw.m819v("Showing interstitial from adapter.");
            try {
                ((MediationInterstitialAdapter) this.nH).showInterstitial();
            } catch (Throwable th) {
                dw.m817c("Could not show interstitial from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            dw.m823z("MediationAdapter is not a MediationInterstitialAdapter: " + this.nH.getClass().getCanonicalName());
            throw new RemoteException();
        }
    }
}
