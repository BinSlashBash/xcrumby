/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.maps.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.c;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class u {
    private static c SA;
    private static Context Sz;

    public static c A(Context context) throws GooglePlayServicesNotAvailableException {
        fq.f(context);
        if (SA != null) {
            return SA;
        }
        u.B(context);
        SA = u.C(context);
        try {
            SA.a(e.h(u.getRemoteContext(context).getResources()), 4452000);
            return SA;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    private static void B(Context context) throws GooglePlayServicesNotAvailableException {
        int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (n2) {
            default: {
                throw new GooglePlayServicesNotAvailableException(n2);
            }
            case 0: 
        }
    }

    private static c C(Context context) {
        if (u.iz()) {
            Log.i((String)u.class.getSimpleName(), (String)"Making Creator statically");
            return (c)u.c(u.iA());
        }
        Log.i((String)u.class.getSimpleName(), (String)"Making Creator dynamically");
        return c.a.ab((IBinder)u.a(u.getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    }

    private static <T> T a(ClassLoader classLoader, String string2) {
        try {
            classLoader = u.c(fq.f(classLoader).loadClass(string2));
        }
        catch (ClassNotFoundException var0_1) {
            throw new IllegalStateException("Unable to find dynamic class " + string2);
        }
        return (T)classLoader;
    }

    private static <T> T c(Class<?> class_) {
        Object obj;
        try {
            obj = class_.newInstance();
        }
        catch (InstantiationException var1_2) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + class_.getName());
        }
        catch (IllegalAccessException var1_3) {
            throw new IllegalStateException("Unable to call the default constructor of " + class_.getName());
        }
        return (T)obj;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Context getRemoteContext(Context context) {
        if (Sz != null) return Sz;
        if (u.iz()) {
            Sz = context.getApplicationContext();
            return Sz;
        }
        Sz = GooglePlayServicesUtil.getRemoteContext(context);
        return Sz;
    }

    private static Class<?> iA() {
        try {
            if (Build.VERSION.SDK_INT < 15) {
                return Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6");
            }
            Class class_ = Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
            return class_;
        }
        catch (ClassNotFoundException var0_1) {
            throw new RuntimeException(var0_1);
        }
    }

    private static boolean iz() {
        return false;
    }
}

