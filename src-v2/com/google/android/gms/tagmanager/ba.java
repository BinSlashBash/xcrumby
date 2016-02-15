/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.cq;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.m;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ba {
    public static cq.c bG(String object) throws JSONException {
        object = ba.k(new JSONObject((String)object));
        cq.d d2 = cq.c.lh();
        for (int i2 = 0; i2 < object.fP.length; ++i2) {
            d2.a(cq.a.ld().b(b.cI.toString(), object.fP[i2]).b(b.cx.toString(), dh.bX(m.ka())).b(m.kb(), object.fQ[i2]).lg());
        }
        return d2.lk();
    }

    private static d.a k(Object object) throws JSONException {
        return dh.r(ba.l(object));
    }

    static Object l(Object hashMap) throws JSONException {
        if (hashMap instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        }
        if (JSONObject.NULL.equals(hashMap)) {
            throw new RuntimeException("JSON nulls are not supported");
        }
        HashMap<String, Object> hashMap2 = hashMap;
        if (hashMap instanceof JSONObject) {
            hashMap = (JSONObject)((Object)hashMap);
            hashMap2 = new HashMap<String, Object>();
            Iterator iterator = hashMap.keys();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                hashMap2.put(string2, ba.l(hashMap.get(string2)));
            }
        }
        return hashMap2;
    }
}

