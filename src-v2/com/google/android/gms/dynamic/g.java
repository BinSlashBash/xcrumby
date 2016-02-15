/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fq;

public abstract class g<T> {
    private final String Hx;
    private T Hy;

    protected g(String string2) {
        this.Hx = string2;
    }

    protected abstract T d(IBinder var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final T z(Context object) throws a {
        if (this.Hy != null) return this.Hy;
        fq.f(object);
        object = GooglePlayServicesUtil.getRemoteContext((Context)object);
        if (object == null) {
            throw new a("Could not get remote context.");
        }
        object = object.getClassLoader();
        try {
            this.Hy = this.d((IBinder)object.loadClass(this.Hx).newInstance());
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
        return this.Hy;
    }

    public static class a
    extends Exception {
        public a(String string2) {
            super(string2);
        }

        public a(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

}

