/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class p
extends aj {
    private static final String ID = a.D.toString();
    private final String Xl;

    public p(String string2) {
        super(ID, new String[0]);
        this.Xl = string2;
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> map) {
        if (this.Xl == null) {
            return dh.lT();
        }
        return dh.r(this.Xl);
    }
}

