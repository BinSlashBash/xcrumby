/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

abstract class cc
extends aj {
    private static final String XQ = b.bi.toString();
    private static final String YN = b.bj.toString();

    public cc(String string2) {
        super(string2, XQ, YN);
    }

    protected abstract boolean a(d.a var1, d.a var2, Map<String, d.a> var3);

    @Override
    public boolean jX() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public d.a x(Map<String, d.a> map) {
        boolean bl2;
        Object object = map.values().iterator();
        while (object.hasNext()) {
            if (object.next() != dh.lT()) continue;
            return dh.r(false);
        }
        object = map.get(XQ);
        d.a a2 = map.get(YN);
        if (object == null || a2 == null) {
            bl2 = false;
            do {
                return dh.r(bl2);
                break;
            } while (true);
        }
        bl2 = this.a((d.a)object, a2, map);
        return dh.r(bl2);
    }
}

