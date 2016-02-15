/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.cc;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

abstract class dc
extends cc {
    public dc(String string2) {
        super(string2);
    }

    @Override
    protected boolean a(d.a object, d.a object2, Map<String, d.a> map) {
        object = dh.j((d.a)object);
        object2 = dh.j((d.a)object2);
        if (object == dh.lS() || object2 == dh.lS()) {
            return false;
        }
        return this.a((String)object, (String)object2, map);
    }

    protected abstract boolean a(String var1, String var2, Map<String, d.a> var3);
}

