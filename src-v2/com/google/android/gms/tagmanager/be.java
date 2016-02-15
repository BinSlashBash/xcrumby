/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.bx;
import com.google.android.gms.tagmanager.dg;
import java.util.Map;

class be
extends bx {
    private static final String ID = a.al.toString();

    public be() {
        super(ID);
    }

    @Override
    protected boolean a(dg dg2, dg dg3, Map<String, d.a> map) {
        if (dg2.a(dg3) < 0) {
            return true;
        }
        return false;
    }
}

