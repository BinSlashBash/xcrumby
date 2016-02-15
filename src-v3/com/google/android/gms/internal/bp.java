package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.MediationServerParameters;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.bq.C0845a;
import java.util.Map;

public final class bp extends C0845a {
    private Map<Class<? extends NetworkExtras>, NetworkExtras> nB;
    private Map<Class<? extends MediationAdapter>, Bundle> nC;

    private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> br m2910n(String str) throws RemoteException {
        try {
            Class cls = Class.forName(str, false, bp.class.getClassLoader());
            if (com.google.ads.mediation.MediationAdapter.class.isAssignableFrom(cls)) {
                com.google.ads.mediation.MediationAdapter mediationAdapter = (com.google.ads.mediation.MediationAdapter) cls.newInstance();
                return new bw(mediationAdapter, (com.google.ads.mediation.NetworkExtras) this.nB.get(mediationAdapter.getAdditionalParametersType()));
            } else if (MediationAdapter.class.isAssignableFrom(cls)) {
                return new bu((MediationAdapter) cls.newInstance(), (Bundle) this.nC.get(cls));
            } else {
                dw.m823z("Could not instantiate mediation adapter: " + str + " (not a valid adapter).");
                throw new RemoteException();
            }
        } catch (Throwable th) {
            dw.m823z("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
            RemoteException remoteException = new RemoteException();
        }
    }

    public void m2911c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
        this.nB = map;
    }

    public void m2912d(Map<Class<? extends MediationAdapter>, Bundle> map) {
        this.nC = map;
    }

    public br m2913m(String str) throws RemoteException {
        return m2910n(str);
    }
}
