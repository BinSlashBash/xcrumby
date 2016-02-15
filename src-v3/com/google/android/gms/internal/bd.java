package com.google.android.gms.internal;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public final class bd implements bb {
    private final bc mP;

    public bd(bc bcVar) {
        this.mP = bcVar;
    }

    private static boolean m2035a(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int m2036b(Map<String, String> map) {
        String str = (String) map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return dq.bA();
            }
            if ("l".equalsIgnoreCase(str)) {
                return dq.bz();
            }
        }
        return -1;
    }

    public void m2037b(dz dzVar, Map<String, String> map) {
        String str = (String) map.get("a");
        if (str == null) {
            dw.m823z("Action missing from an open GMSG.");
            return;
        }
        ea bI = dzVar.bI();
        if ("expand".equalsIgnoreCase(str)) {
            if (dzVar.bL()) {
                dw.m823z("Cannot expand WebView that is already expanded.");
            } else {
                bI.m844a(m2035a(map), m2036b(map));
            }
        } else if ("webapp".equalsIgnoreCase(str)) {
            str = (String) map.get("u");
            if (str != null) {
                bI.m845a(m2035a(map), m2036b(map), str);
            } else {
                bI.m846a(m2035a(map), m2036b(map), (String) map.get("html"), (String) map.get("baseurl"));
            }
        } else if ("in_app_purchase".equalsIgnoreCase(str)) {
            str = (String) map.get("product_id");
            String str2 = (String) map.get("report_urls");
            if (this.mP == null) {
                return;
            }
            if (str2 == null || str2.isEmpty()) {
                this.mP.m658a(str, new ArrayList());
                return;
            }
            this.mP.m658a(str, new ArrayList(Arrays.asList(str2.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR))));
        } else {
            bI.m839a(new cb((String) map.get("i"), (String) map.get("u"), (String) map.get("m"), (String) map.get("p"), (String) map.get("c"), (String) map.get("f"), (String) map.get("e")));
        }
    }
}
