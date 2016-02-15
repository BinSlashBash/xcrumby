/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class u
extends aj {
    private static final String ID = a.C.toString();
    private static final String NAME = b.dc.toString();
    private static final String XA = b.cb.toString();
    private final DataLayer WK;

    public u(DataLayer dataLayer) {
        super(ID, NAME);
        this.WK = dataLayer;
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        Object object2 = this.WK.get(dh.j(object.get(NAME)));
        if (object2 == null) {
            if ((object = object.get(XA)) != null) {
                return object;
            }
            return dh.lT();
        }
        return dh.r(object2);
    }
}

