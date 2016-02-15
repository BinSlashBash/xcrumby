/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ab {
    private final Map<String, Integer> vt = new HashMap<String, Integer>();
    private final Map<String, String> vu = new HashMap<String, String>();
    private final boolean vv;
    private final String vw;

    ab(String string2, boolean bl2) {
        this.vv = bl2;
        this.vw = string2;
    }

    void c(String string2, int n2) {
        Integer n3;
        if (!this.vv) {
            return;
        }
        Integer n4 = n3 = this.vt.get(string2);
        if (n3 == null) {
            n4 = 0;
        }
        this.vt.put(string2, n4 + n2);
    }

    String cU() {
        if (!this.vv) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.vw);
        for (String string22 : this.vt.keySet()) {
            stringBuilder.append("&").append(string22).append("=").append(this.vt.get(string22));
        }
        for (String string22 : this.vu.keySet()) {
            stringBuilder.append("&").append(string22).append("=").append(this.vu.get(string22));
        }
        return stringBuilder.toString();
    }
}

