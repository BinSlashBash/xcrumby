/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.MediationServerParameters;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.bu;
import com.google.android.gms.internal.bw;
import com.google.android.gms.internal.dw;
import java.util.Map;

public final class bp
extends bq.a {
    private Map<Class<? extends NetworkExtras>, NetworkExtras> nB;
    private Map<Class<? extends MediationAdapter>, Bundle> nC;

    private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> br n(String string2) throws RemoteException {
        try {
            Object object = Class.forName(string2, false, bp.class.getClassLoader());
            if (com.google.ads.mediation.MediationAdapter.class.isAssignableFrom(object)) {
                object = (com.google.ads.mediation.MediationAdapter)object.newInstance();
                return new bw(object, (com.google.ads.mediation.NetworkExtras)this.nB.get(object.getAdditionalParametersType()));
            }
            if (MediationAdapter.class.isAssignableFrom(object)) {
                return new bu((MediationAdapter)object.newInstance(), this.nC.get(object));
            }
            dw.z("Could not instantiate mediation adapter: " + string2 + " (not a valid adapter).");
            throw new RemoteException();
        }
        catch (Throwable var2_3) {
            dw.z("Could not instantiate mediation adapter: " + string2 + ". " + var2_3.getMessage());
            throw new RemoteException();
        }
    }

    public void c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
        this.nB = map;
    }

    public void d(Map<Class<? extends MediationAdapter>, Bundle> map) {
        this.nC = map;
    }

    @Override
    public br m(String string2) throws RemoteException {
        return this.n(string2);
    }
}

