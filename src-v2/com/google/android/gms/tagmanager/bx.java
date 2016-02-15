/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.cc;
import com.google.android.gms.tagmanager.dg;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

abstract class bx
extends cc {
    public bx(String string2) {
        super(string2);
    }

    @Override
    protected boolean a(d.a object, d.a object2, Map<String, d.a> map) {
        object = dh.k((d.a)object);
        object2 = dh.k((d.a)object2);
        if (object == dh.lR() || object2 == dh.lR()) {
            return false;
        }
        return this.a((dg)object, (dg)object2, map);
    }

    protected abstract boolean a(dg var1, dg var2, Map<String, d.a> var3);
}

