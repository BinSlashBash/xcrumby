/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.view.View
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.fn;

public final class fr
extends g<fn> {
    private static final fr DK = new fr();

    private fr() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View b(Context context, int n2, int n3) throws g.a {
        return DK.c(context, n2, n3);
    }

    private View c(Context context, int n2, int n3) throws g.a {
        try {
            d d2 = e.h(context);
            context = (View)e.d(((fn)this.z(context)).a(d2, n2, n3));
            return context;
        }
        catch (Exception var1_2) {
            throw new g.a("Could not get button with size " + n2 + " and color " + n3, var1_2);
        }
    }

    public fn E(IBinder iBinder) {
        return fn.a.D(iBinder);
    }

    @Override
    public /* synthetic */ Object d(IBinder iBinder) {
        return this.E(iBinder);
    }
}

