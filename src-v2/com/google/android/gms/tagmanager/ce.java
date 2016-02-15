/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class ce
extends aj {
    private static final String ID = a.O.toString();
    private static final String YX = b.da.toString();
    private static final String YY = b.cZ.toString();

    public ce() {
        super(ID, new String[0]);
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
    public d.a x(Map<String, d.a> object) {
        double d2;
        double d3;
        Object object2 = (d.a)object.get(YX);
        object = (d.a)object.get(YY);
        if (object2 != null && object2 != dh.lT() && object != null && object != dh.lT()) {
            object2 = dh.k((d.a)object2);
            object = dh.k((d.a)object);
            if (object2 != dh.lR() && object != dh.lR() && (d2 = object2.doubleValue()) <= (d3 = object.doubleValue())) {
                do {
                    return dh.r(Math.round((d3 - d2) * Math.random() + d2));
                    break;
                } while (true);
            }
        }
        d3 = 2.147483647E9;
        d2 = 0.0;
        return dh.r(Math.round((d3 - d2) * Math.random() + d2));
    }
}

