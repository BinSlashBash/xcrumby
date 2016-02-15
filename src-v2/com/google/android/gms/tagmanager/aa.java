/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class aa
extends aj {
    private static final String ID = a.F.toString();

    public aa() {
        super(ID, new String[0]);
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        String string2;
        String string3 = Build.MANUFACTURER;
        object = string2 = Build.MODEL;
        if (!string2.startsWith(string3)) {
            object = string2;
            if (!string3.equals("unknown")) {
                object = string3 + " " + string2;
            }
        }
        return dh.r(object);
    }
}

