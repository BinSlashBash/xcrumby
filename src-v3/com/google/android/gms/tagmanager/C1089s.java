package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.google.android.gms.tagmanager.s */
class C1089s extends aj {
    private static final String ID;
    private static final String WC;
    private static final String Xn;
    private final C0530a Xo;

    /* renamed from: com.google.android.gms.tagmanager.s.a */
    public interface C0530a {
        Object m1483b(String str, Map<String, Object> map);
    }

    static {
        ID = C0321a.FUNCTION_CALL.toString();
        Xn = C0325b.FUNCTION_CALL_NAME.toString();
        WC = C0325b.ADDITIONAL_PARAMS.toString();
    }

    public C1089s(C0530a c0530a) {
        super(ID, Xn);
        this.Xo = c0530a;
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2544x(Map<String, C1367a> map) {
        String j = dh.m1460j((C1367a) map.get(Xn));
        Map hashMap = new HashMap();
        C1367a c1367a = (C1367a) map.get(WC);
        if (c1367a != null) {
            Object o = dh.m1468o(c1367a);
            if (o instanceof Map) {
                for (Entry entry : ((Map) o).entrySet()) {
                    hashMap.put(entry.getKey().toString(), entry.getValue());
                }
            } else {
                bh.m1387z("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return dh.lT();
            }
        }
        try {
            return dh.m1471r(this.Xo.m1483b(j, hashMap));
        } catch (Exception e) {
            bh.m1387z("Custom macro/tag " + j + " threw exception " + e.getMessage());
            return dh.lT();
        }
    }
}
