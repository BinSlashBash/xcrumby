package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0339c.C1357c;
import com.google.android.gms.internal.C0339c.C1358d;
import com.google.android.gms.internal.C0339c.C1363i;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ai {
    private static void m1360a(DataLayer dataLayer, C1358d c1358d) {
        for (C1367a j : c1358d.eS) {
            dataLayer.bv(dh.m1460j(j));
        }
    }

    public static void m1361a(DataLayer dataLayer, C1363i c1363i) {
        if (c1363i.fI == null) {
            bh.m1387z("supplemental missing experimentSupplemental");
            return;
        }
        m1360a(dataLayer, c1363i.fI);
        m1362b(dataLayer, c1363i.fI);
        m1364c(dataLayer, c1363i.fI);
    }

    private static void m1362b(DataLayer dataLayer, C1358d c1358d) {
        for (C1367a c : c1358d.eR) {
            Map c2 = m1363c(c);
            if (c2 != null) {
                dataLayer.push(c2);
            }
        }
    }

    private static Map<String, Object> m1363c(C1367a c1367a) {
        Object o = dh.m1468o(c1367a);
        if (o instanceof Map) {
            return (Map) o;
        }
        bh.m1387z("value: " + o + " is not a map value, ignored.");
        return null;
    }

    private static void m1364c(DataLayer dataLayer, C1358d c1358d) {
        for (C1357c c1357c : c1358d.eT) {
            if (c1357c.eM == null) {
                bh.m1387z("GaExperimentRandom: No key");
            } else {
                Object obj = dataLayer.get(c1357c.eM);
                Long valueOf = !(obj instanceof Number) ? null : Long.valueOf(((Number) obj).longValue());
                long j = c1357c.eN;
                long j2 = c1357c.eO;
                if (!c1357c.eP || valueOf == null || valueOf.longValue() < j || valueOf.longValue() > j2) {
                    if (j <= j2) {
                        obj = Long.valueOf(Math.round((Math.random() * ((double) (j2 - j))) + ((double) j)));
                    } else {
                        bh.m1387z("GaExperimentRandom: random range invalid");
                    }
                }
                dataLayer.bv(c1357c.eM);
                Map c = dataLayer.m1348c(c1357c.eM, obj);
                if (c1357c.eQ > 0) {
                    if (c.containsKey("gtm")) {
                        Object obj2 = c.get("gtm");
                        if (obj2 instanceof Map) {
                            ((Map) obj2).put("lifetime", Long.valueOf(c1357c.eQ));
                        } else {
                            bh.m1387z("GaExperimentRandom: gtm not a map");
                        }
                    } else {
                        c.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(c1357c.eQ)));
                    }
                }
                dataLayer.push(c);
            }
        }
    }
}
