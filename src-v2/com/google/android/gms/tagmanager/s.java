/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class s
extends aj {
    private static final String ID = com.google.android.gms.internal.a.J.toString();
    private static final String WC;
    private static final String Xn;
    private final a Xo;

    static {
        Xn = b.cy.toString();
        WC = b.aX.toString();
    }

    public s(a a2) {
        super(ID, Xn);
        this.Xo = a2;
    }

    @Override
    public boolean jX() {
        return false;
    }

    @Override
    public d.a x(Map<String, d.a> iterator) {
        String string2 = dh.j(iterator.get(Xn));
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if ((iterator = iterator.get(WC)) != null) {
            if (!((iterator = dh.o((d.a)((Object)iterator))) instanceof Map)) {
                bh.z("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return dh.lT();
            }
            for (Map.Entry entry : ((Map)((Object)iterator)).entrySet()) {
                hashMap.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            iterator = dh.r(this.Xo.b(string2, hashMap));
            return iterator;
        }
        catch (Exception var1_2) {
            bh.z("Custom macro/tag " + string2 + " threw exception " + var1_2.getMessage());
            return dh.lT();
        }
    }

    public static interface a {
        public Object b(String var1, Map<String, Object> var2);
    }

}

