package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.C0433c.C0979a;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.maps.internal.u */
public class C0451u {
    private static C0433c SA;
    private static Context Sz;

    public static C0433c m1247A(Context context) throws GooglePlayServicesNotAvailableException {
        fq.m985f(context);
        if (SA != null) {
            return SA;
        }
        C0451u.m1248B(context);
        SA = C0451u.m1249C(context);
        try {
            SA.m1231a(C1324e.m2710h(C0451u.getRemoteContext(context).getResources()), (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return SA;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static void m1248B(Context context) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (isGooglePlayServicesAvailable) {
            case JSONzip.zipEmptyObject /*0*/:
            default:
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static C0433c m1249C(Context context) {
        if (C0451u.iz()) {
            Log.i(C0451u.class.getSimpleName(), "Making Creator statically");
            return (C0433c) C0451u.m1251c(C0451u.iA());
        }
        Log.i(C0451u.class.getSimpleName(), "Making Creator dynamically");
        return C0979a.ab((IBinder) C0451u.m1250a(C0451u.getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    }

    private static <T> T m1250a(ClassLoader classLoader, String str) {
        try {
            return C0451u.m1251c(((ClassLoader) fq.m985f(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class " + str);
        }
    }

    private static <T> T m1251c(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
        }
    }

    private static Context getRemoteContext(Context context) {
        if (Sz == null) {
            if (C0451u.iz()) {
                Sz = context.getApplicationContext();
            } else {
                Sz = GooglePlayServicesUtil.getRemoteContext(context);
            }
        }
        return Sz;
    }

    private static Class<?> iA() {
        try {
            return VERSION.SDK_INT < 15 ? Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6") : Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean iz() {
        return false;
    }
}
