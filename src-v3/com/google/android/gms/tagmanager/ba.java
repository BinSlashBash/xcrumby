package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import com.google.android.gms.tagmanager.cq.C0507a;
import com.google.android.gms.tagmanager.cq.C0509c;
import com.google.android.gms.tagmanager.cq.C0510d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ba {
    public static C0509c bG(String str) throws JSONException {
        C1367a k = m1377k(new JSONObject(str));
        C0510d lh = C0509c.lh();
        for (int i = 0; i < k.fP.length; i++) {
            lh.m1410a(C0507a.ld().m1408b(C0325b.INSTANCE_NAME.toString(), k.fP[i]).m1408b(C0325b.FUNCTION.toString(), dh.bX(C1080m.ka())).m1408b(C1080m.kb(), k.fQ[i]).lg());
        }
        return lh.lk();
    }

    private static C1367a m1377k(Object obj) throws JSONException {
        return dh.m1471r(m1378l(obj));
    }

    static Object m1378l(Object obj) throws JSONException {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        } else if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        } else if (!(obj instanceof JSONObject)) {
            return obj;
        } else {
            JSONObject jSONObject = (JSONObject) obj;
            Map hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, m1378l(jSONObject.get(str)));
            }
            return hashMap;
        }
    }
}
