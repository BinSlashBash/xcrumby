/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

abstract class df
extends aj {
    public /* varargs */ df(String string2, String ... arrstring) {
        super(string2, arrstring);
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> map) {
        this.z(map);
        return dh.lT();
    }

    public abstract void z(Map<String, d.a> var1);
}

