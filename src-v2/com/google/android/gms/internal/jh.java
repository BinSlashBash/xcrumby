/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.ja;
import com.google.android.gms.internal.jc;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public class jh
extends g<jc> {
    private static jh adc;

    protected jh() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static iz a(Activity object, c c2, WalletFragmentOptions walletFragmentOptions, ja ja2) throws GooglePlayServicesNotAvailableException {
        int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)object);
        if (n2 != 0) {
            throw new GooglePlayServicesNotAvailableException(n2);
        }
        try {
            object = ((jc)jh.lY().z((Context)object)).a(e.h(object), c2, walletFragmentOptions, ja2);
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeException((Throwable)var0_1);
        }
        catch (g.a var0_2) {
            throw new RuntimeException(var0_2);
        }
    }

    private static jh lY() {
        if (adc == null) {
            adc = new jh();
        }
        return adc;
    }

    protected jc aZ(IBinder iBinder) {
        return jc.a.aV(iBinder);
    }

    @Override
    protected /* synthetic */ Object d(IBinder iBinder) {
        return this.aZ(iBinder);
    }
}

