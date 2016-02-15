/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.j;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class ao
extends aj {
    private static final String ID = a.aa.toString();
    private static final String XQ = b.bi.toString();
    private static final String XS;
    private static final String XW;

    static {
        XW = b.aZ.toString();
        XS = b.cH.toString();
    }

    public ao() {
        super(ID, XQ);
    }

    private byte[] c(String object, byte[] arrby) throws NoSuchAlgorithmException {
        object = MessageDigest.getInstance((String)object);
        object.update(arrby);
        return object.digest();
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
        Object object2 = (d.a)object.get(XQ);
        if (object2 == null) return dh.lT();
        if (object2 == dh.lT()) {
            return dh.lT();
        }
        String string2 = dh.j((d.a)object2);
        object2 = (d.a)object.get(XW);
        object2 = object2 == null ? "MD5" : dh.j((d.a)object2);
        object = (object = (d.a)object.get(XS)) == null ? "text" : dh.j((d.a)object);
        if ("text".equals(object)) {
            object = string2.getBytes();
        } else {
            if (!"base16".equals(object)) {
                bh.w("Hash: unknown input format: " + (String)object);
                return dh.lT();
            }
            object = j.bm(string2);
        }
        try {
            return dh.r(j.d(this.c((String)object2, (byte[])object)));
        }
        catch (NoSuchAlgorithmException var1_2) {
            bh.w("Hash: unknown algorithm: " + (String)object2);
            return dh.lT();
        }
    }
}

