/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.view.View
 */
package com.google.android.gms.plus.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.plus.PlusOneDummyView;
import com.google.android.gms.plus.internal.c;

public final class g {
    private static Context Sz;
    private static c Uj;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static c D(Context object) throws a {
        fq.f(object);
        if (Uj != null) return Uj;
        if (Sz == null && (g.Sz = GooglePlayServicesUtil.getRemoteContext((Context)object)) == null) {
            throw new a("Could not get remote context.");
        }
        object = Sz.getClassLoader();
        try {
            Uj = c.a.aP((IBinder)object.loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new a("Could not load creator class.");
        }
        catch (InstantiationException instantiationException) {
            throw new a("Could not instantiate creator.");
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new a("Could not access creator.");
        }
        return Uj;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static View a(Context context, int n2, int n3, String string2, int n4) {
        if (string2 != null) return (View)e.d(g.D(context).a(e.h(context), n2, n3, string2, n4));
        try {
            throw new NullPointerException();
        }
        catch (Exception var3_4) {
            return new PlusOneDummyView(context, n2);
        }
    }

    public static class a
    extends Exception {
        public a(String string2) {
            super(string2);
        }
    }

}

