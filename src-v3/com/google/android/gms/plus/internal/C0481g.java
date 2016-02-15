package com.google.android.gms.plus.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.plus.PlusOneDummyView;
import com.google.android.gms.plus.internal.C0477c.C1048a;

/* renamed from: com.google.android.gms.plus.internal.g */
public final class C0481g {
    private static Context Sz;
    private static C0477c Uj;

    /* renamed from: com.google.android.gms.plus.internal.g.a */
    public static class C0480a extends Exception {
        public C0480a(String str) {
            super(str);
        }
    }

    private static C0477c m1322D(Context context) throws C0480a {
        fq.m985f(context);
        if (Uj == null) {
            if (Sz == null) {
                Sz = GooglePlayServicesUtil.getRemoteContext(context);
                if (Sz == null) {
                    throw new C0480a("Could not get remote context.");
                }
            }
            try {
                Uj = C1048a.aP((IBinder) Sz.getClassLoader().loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
            } catch (ClassNotFoundException e) {
                throw new C0480a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new C0480a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new C0480a("Could not access creator.");
            }
        }
        return Uj;
    }

    public static View m1323a(Context context, int i, int i2, String str, int i3) {
        if (str != null) {
            return (View) C1324e.m2709d(C0481g.m1322D(context).m1305a(C1324e.m2710h(context), i, i2, str, i3));
        }
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }
}
