/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.df;
import com.google.android.gms.tagmanager.dh;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class w
extends df {
    private static final String ID = a.af.toString();
    private static final String VALUE = b.ew.toString();
    private static final String XL = b.bD.toString();
    private final DataLayer WK;

    public w(DataLayer dataLayer) {
        super(ID, VALUE);
        this.WK = dataLayer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(d.a object) {
        if (object == null || object == dh.lN() || (object = dh.j((d.a)object)) == dh.lS()) {
            return;
        }
        this.WK.bv((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(d.a iterator) {
        if (iterator != null && iterator != dh.lN() && (iterator = dh.o(iterator)) instanceof List) {
            iterator = ((List)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (!(object instanceof Map)) continue;
                object = (Map)object;
                this.WK.push((Map<String, Object>)object);
            }
        }
    }

    @Override
    public void z(Map<String, d.a> map) {
        this.b(map.get(VALUE));
        this.a(map.get(XL));
    }
}

