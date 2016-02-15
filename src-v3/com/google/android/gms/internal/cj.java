package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.dynamic.C0309g;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.ck.C0852a;
import com.google.android.gms.internal.cl.C0854a;

public final class cj extends C0309g<cl> {
    private static final cj oC;

    /* renamed from: com.google.android.gms.internal.cj.a */
    private static final class C0345a extends Exception {
        public C0345a(String str) {
            super(str);
        }
    }

    static {
        oC = new cj();
    }

    private cj() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static ck m2065a(Activity activity) {
        try {
            if (!m2066b(activity)) {
                return oC.m2067c(activity);
            }
            dw.m819v("Using AdOverlay from the client jar.");
            return new cc(activity);
        } catch (C0345a e) {
            dw.m823z(e.getMessage());
            return null;
        }
    }

    private static boolean m2066b(Activity activity) throws C0345a {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new C0345a("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private ck m2067c(Activity activity) {
        try {
            return C0852a.m2071m(((cl) m359z(activity)).m698a(C1324e.m2710h(activity)));
        } catch (Throwable e) {
            dw.m817c("Could not create remote AdOverlay.", e);
            return null;
        } catch (Throwable e2) {
            dw.m817c("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    protected /* synthetic */ Object m2068d(IBinder iBinder) {
        return m2069l(iBinder);
    }

    protected cl m2069l(IBinder iBinder) {
        return C0854a.m2073n(iBinder);
    }
}
