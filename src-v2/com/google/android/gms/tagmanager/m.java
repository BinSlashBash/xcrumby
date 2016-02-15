/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import java.util.Map;

class m
extends aj {
    private static final String ID = a.A.toString();
    private static final String VALUE = b.ew.toString();

    public m() {
        super(ID, VALUE);
    }

    public static String ka() {
        return ID;
    }

    public static String kb() {
        return VALUE;
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> map) {
        return map.get(VALUE);
    }
}

