/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.cs;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class ah
extends aj {
    private static final String ID = a.I.toString();
    private final cs WL;

    public ah(cs cs2) {
        super(ID, new String[0]);
        this.WL = cs2;
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        object = this.WL.lx();
        if (object == null) {
            return dh.lT();
        }
        return dh.r(object);
    }
}

