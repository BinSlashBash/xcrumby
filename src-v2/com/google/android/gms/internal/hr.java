/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.hf;
import com.google.android.gms.internal.hs;
import java.util.Locale;

public class hr {
    private static final String TAG = hr.class.getSimpleName();
    private final hf<ha> Ok;
    private final hs Rj;
    private final Locale Rk;

    public hr(Context context, String string2, hf<ha> hf2) {
        this.Ok = hf2;
        this.Rk = Locale.getDefault();
        this.Rj = new hs(string2, this.Rk);
    }
}

