package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C0309g;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.ap.C0831a;
import com.google.android.gms.internal.aq.C0833a;

public final class ag extends C0309g<aq> {
    private static final ag lG;

    static {
        lG = new ag();
    }

    private ag() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    public static ap m2007a(Context context, ak akVar, String str, bp bpVar) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            ap b = lG.m2008b(context, akVar, str, bpVar);
            if (b != null) {
                return b;
            }
        }
        dw.m819v("Using AdManager from the client jar.");
        return new C1396v(context, akVar, str, bpVar, new dx(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, true));
    }

    private ap m2008b(Context context, ak akVar, String str, bp bpVar) {
        try {
            return C0831a.m2022f(((aq) m359z(context)).m626a(C1324e.m2710h(context), akVar, str, bpVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE));
        } catch (Throwable e) {
            dw.m817c("Could not create remote AdManager.", e);
            return null;
        } catch (Throwable e2) {
            dw.m817c("Could not create remote AdManager.", e2);
            return null;
        }
    }

    protected aq m2009c(IBinder iBinder) {
        return C0833a.m2024g(iBinder);
    }

    protected /* synthetic */ Object m2010d(IBinder iBinder) {
        return m2009c(iBinder);
    }
}
