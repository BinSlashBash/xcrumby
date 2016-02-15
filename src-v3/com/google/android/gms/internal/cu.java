package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.cv.C1365a;
import com.google.android.gms.internal.cv.C1366b;

public final class cu {

    /* renamed from: com.google.android.gms.internal.cu.a */
    public interface C0353a {
        void m713a(cz czVar);
    }

    public static C0359do m714a(Context context, cx cxVar, C0353a c0353a) {
        return cxVar.kK.rt ? m715b(context, cxVar, c0353a) : m716c(context, cxVar, c0353a);
    }

    private static C0359do m715b(Context context, cx cxVar, C0353a c0353a) {
        dw.m819v("Fetching ad response from local ad request service.");
        C0359do c1365a = new C1365a(context, cxVar, c0353a);
        c1365a.start();
        return c1365a;
    }

    private static C0359do m716c(Context context, cx cxVar, C0353a c0353a) {
        dw.m819v("Fetching ad response from remote ad request service.");
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            return new C1366b(context, cxVar, c0353a);
        }
        dw.m823z("Failed to connect to remote ad request service.");
        return null;
    }
}
