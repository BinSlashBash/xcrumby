/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.j;
import java.util.Map;

class ac
extends aj {
    private static final String ID = a.Y.toString();
    private static final String XQ = b.bi.toString();
    private static final String XR = b.di.toString();
    private static final String XS = b.cH.toString();
    private static final String XT = b.dq.toString();

    public ac() {
        super(ID, XQ);
    }

    @Override
    public boolean jX() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public d.a x(Map<String, d.a> object) {
        int n2;
        Object object2;
        block11 : {
            object2 = (d.a)object.get(XQ);
            if (object2 == null) return dh.lT();
            if (object2 == dh.lT()) {
                return dh.lT();
            }
            String string2 = dh.j((d.a)object2);
            object2 = (d.a)object.get(XS);
            String string3 = object2 == null ? "text" : dh.j((d.a)object2);
            object2 = (d.a)object.get(XT);
            object2 = object2 == null ? "base16" : dh.j((d.a)object2);
            n2 = (object = (d.a)object.get(XR)) != null && dh.n((d.a)object) != false ? 3 : 2;
            try {
                if ("text".equals(string3)) {
                    object = string2.getBytes();
                    break block11;
                }
                if ("base16".equals(string3)) {
                    object = j.bm(string2);
                    break block11;
                }
                if ("base64".equals(string3)) {
                    object = Base64.decode((String)string2, (int)n2);
                    break block11;
                }
                if (!"base64url".equals(string3)) {
                    bh.w("Encode: unknown input format: " + string3);
                    return dh.lT();
                }
                object = Base64.decode((String)string2, (int)(n2 | 8));
            }
            catch (IllegalArgumentException var1_2) {
                bh.w("Encode: invalid input:");
                return dh.lT();
            }
        }
        if ("base16".equals(object2)) {
            object = j.d((byte[])object);
            return dh.r(object);
        }
        if ("base64".equals(object2)) {
            object = Base64.encodeToString((byte[])object, (int)n2);
            return dh.r(object);
        }
        if ("base64url".equals(object2)) {
            object = Base64.encodeToString((byte[])object, (int)(n2 | 8));
            return dh.r(object);
        }
        bh.w("Encode: unknown output format: " + (String)object2);
        return dh.lT();
    }
}

