/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.ay;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class ax
extends aj {
    private static final String ID = a.ab.toString();
    private static final String WA = b.bH.toString();
    private final Context kI;

    public ax(Context context) {
        super(ID, new String[0]);
        this.kI = context;
    }

    @Override
    public boolean jX() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public d.a x(Map<String, d.a> object) {
        object = (d.a)object.get(WA) != null ? dh.j((d.a)object.get(WA)) : null;
        if ((object = ay.d(this.kI, (String)object)) == null) return dh.lT();
        return dh.r(object);
    }
}

