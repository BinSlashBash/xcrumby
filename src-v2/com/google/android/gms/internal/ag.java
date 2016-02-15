/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.ap;
import com.google.android.gms.internal.aq;
import com.google.android.gms.internal.bp;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.v;

public final class ag
extends g<aq> {
    private static final ag lG = new ag();

    private ag() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ap a(Context context, ak ak2, String string2, bp bp2) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            ap ap2;
            ap ap3 = ap2 = lG.b(context, ak2, string2, bp2);
            if (ap2 != null) return ap3;
        }
        dw.v("Using AdManager from the client jar.");
        return new v(context, ak2, string2, bp2, new dx(4452000, 4452000, true));
    }

    private ap b(Context object, ak ak2, String string2, bp bp2) {
        try {
            d d2 = e.h(object);
            object = ap.a.f(((aq)this.z((Context)object)).a(d2, ak2, string2, bp2, 4452000));
            return object;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not create remote AdManager.", (Throwable)var1_2);
            return null;
        }
        catch (g.a var1_3) {
            dw.c("Could not create remote AdManager.", var1_3);
            return null;
        }
    }

    protected aq c(IBinder iBinder) {
        return aq.a.g(iBinder);
    }

    @Override
    protected /* synthetic */ Object d(IBinder iBinder) {
        return this.c(iBinder);
    }
}

