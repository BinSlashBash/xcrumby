/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.dc;
import java.util.Map;

class ae
extends dc {
    private static final String ID = a.ak.toString();

    public ae() {
        super(ID);
    }

    @Override
    protected boolean a(String string2, String string3, Map<String, d.a> map) {
        return string2.equals(string3);
    }
}

