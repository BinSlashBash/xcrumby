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

class e
extends aj {
    private static final String ID = a.W.toString();
    private static final String WA = b.bH.toString();
    private static final String WB = b.bK.toString();
    private final Context kI;

    public e(Context context) {
        super(ID, WB);
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
        Object object2 = (d.a)object.get(WB);
        if (object2 == null) {
            return dh.lT();
        }
        object2 = dh.j((d.a)object2);
        object = (object = (d.a)object.get(WA)) != null ? dh.j((d.a)object) : null;
        if ((object = ay.e(this.kI, (String)object2, (String)object)) == null) return dh.lT();
        return dh.r(object);
    }
}

