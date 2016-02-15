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

class b
extends aj {
    private static final String ID = com.google.android.gms.internal.a.u.toString();
    private final a Wz;

    public b(Context context) {
        this(a.E(context));
    }

    b(a a2) {
        super(ID, new String[0]);
        this.Wz = a2;
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        object = this.Wz.jT();
        if (object == null) {
            return dh.lT();
        }
        return dh.r(object);
    }
}

