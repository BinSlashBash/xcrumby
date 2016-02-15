/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.ck;
import com.google.android.gms.internal.cl;
import com.google.android.gms.internal.dw;

public final class cj
extends g<cl> {
    private static final cj oC = new cj();

    private cj() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static ck a(Activity object) {
        try {
            if (cj.b((Activity)object)) {
                dw.v("Using AdOverlay from the client jar.");
                return new cc((Activity)object);
            }
            object = oC.c((Activity)object);
            return object;
        }
        catch (a var0_1) {
            dw.z(var0_1.getMessage());
            return null;
        }
    }

    private static boolean b(Activity activity) throws a {
        if (!(activity = activity.getIntent()).hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            throw new a("Ad overlay requires the useClientJar flag in intent extras.");
        }
        return activity.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
    }

    private ck c(Activity object) {
        try {
            d d2 = e.h(object);
            object = ck.a.m(((cl)this.z((Context)object)).a(d2));
            return object;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not create remote AdOverlay.", (Throwable)var1_2);
            return null;
        }
        catch (g.a var1_3) {
            dw.c("Could not create remote AdOverlay.", var1_3);
            return null;
        }
    }

    @Override
    protected /* synthetic */ Object d(IBinder iBinder) {
        return this.l(iBinder);
    }

    protected cl l(IBinder iBinder) {
        return cl.a.n(iBinder);
    }

    private static final class a
    extends Exception {
        public a(String string2) {
            super(string2);
        }
    }

}

