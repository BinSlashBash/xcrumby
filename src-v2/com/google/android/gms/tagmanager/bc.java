/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Locale;
import java.util.Map;

class bc
extends aj {
    private static final String ID = a.L.toString();

    public bc() {
        super(ID, new String[0]);
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        object = Locale.getDefault();
        if (object == null) {
            return dh.lT();
        }
        if ((object = object.getLanguage()) == null) {
            return dh.lT();
        }
        return dh.r(object.toLowerCase());
    }
}

