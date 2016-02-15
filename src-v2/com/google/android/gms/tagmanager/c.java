/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.a;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class c
extends aj {
    private static final String ID = com.google.android.gms.internal.a.v.toString();
    private final a Wz;

    public c(Context context) {
        this(a.E(context));
    }

    c(a a2) {
        super(ID, new String[0]);
        this.Wz = a2;
    }

    @Override
    public boolean jX() {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public d.a x(Map<String, d.a> map) {
        boolean bl2;
        if (!this.Wz.isLimitAdTrackingEnabled()) {
            bl2 = true;
            do {
                return dh.r(bl2);
                break;
            } while (true);
        }
        bl2 = false;
        return dh.r(bl2);
    }
}

