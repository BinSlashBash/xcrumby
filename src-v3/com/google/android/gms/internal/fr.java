package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.C0309g;
import com.google.android.gms.dynamic.C0309g.C0308a;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.fn.C0898a;

public final class fr extends C0309g<fn> {
    private static final fr DK;

    static {
        DK = new fr();
    }

    private fr() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View m2214b(Context context, int i, int i2) throws C0308a {
        return DK.m2215c(context, i, i2);
    }

    private View m2215c(Context context, int i, int i2) throws C0308a {
        try {
            return (View) C1324e.m2709d(((fn) m359z(context)).m974a(C1324e.m2710h(context), i, i2));
        } catch (Throwable e) {
            throw new C0308a("Could not get button with size " + i + " and color " + i2, e);
        }
    }

    public fn m2216E(IBinder iBinder) {
        return C0898a.m2213D(iBinder);
    }

    public /* synthetic */ Object m2217d(IBinder iBinder) {
        return m2216E(iBinder);
    }
}
