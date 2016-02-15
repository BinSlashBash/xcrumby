/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class df {
    private int mOrientation = -1;
    private String pN;
    private String pO;
    private String pP;
    private List<String> pQ;
    private String pR;
    private String pS;
    private List<String> pT;
    private long pU = -1;
    private boolean pV = false;
    private final long pW = -1;
    private List<String> pX;
    private long pY = -1;

    private static String a(Map<String, List<String>> object, String string2) {
        if ((object = object.get(string2)) != null && !object.isEmpty()) {
            return (String)object.get(0);
        }
        return null;
    }

    private static long b(Map<String, List<String>> object, String string2) {
        if ((object = object.get(string2)) != null && !object.isEmpty()) {
            float f2;
            object = (String)object.get(0);
            try {
                f2 = Float.parseFloat((String)object);
            }
            catch (NumberFormatException var3_3) {
                dw.z("Could not parse float from " + string2 + " header: " + (String)object);
            }
            return (long)(f2 * 1000.0f);
        }
        return -1;
    }

    private static List<String> c(Map<String, List<String>> object, String string2) {
        if ((object = object.get(string2)) != null && !object.isEmpty() && (object = (String)object.get(0)) != null) {
            return Arrays.asList(object.trim().split("\\s+"));
        }
        return null;
    }

    private void f(Map<String, List<String>> map) {
        this.pN = df.a(map, "X-Afma-Ad-Size");
    }

    private void g(Map<String, List<String>> object) {
        if ((object = df.c(object, "X-Afma-Click-Tracking-Urls")) != null) {
            this.pQ = object;
        }
    }

    private void h(Map<String, List<String>> object) {
        if ((object = object.get("X-Afma-Debug-Dialog")) != null && !object.isEmpty()) {
            this.pR = (String)object.get(0);
        }
    }

    private void i(Map<String, List<String>> object) {
        if ((object = df.c(object, "X-Afma-Tracking-Urls")) != null) {
            this.pT = object;
        }
    }

    private void j(Map<String, List<String>> map) {
        long l2 = df.b(map, "X-Afma-Interstitial-Timeout");
        if (l2 != -1) {
            this.pU = l2;
        }
    }

    private void k(Map<String, List<String>> map) {
        this.pS = df.a(map, "X-Afma-ActiveView");
    }

    private void l(Map<String, List<String>> object) {
        if ((object = object.get("X-Afma-Mediation")) != null && !object.isEmpty()) {
            this.pV = Boolean.valueOf((String)object.get(0));
        }
    }

    private void m(Map<String, List<String>> object) {
        if ((object = df.c(object, "X-Afma-Manual-Tracking-Urls")) != null) {
            this.pX = object;
        }
    }

    private void n(Map<String, List<String>> map) {
        long l2 = df.b(map, "X-Afma-Refresh-Rate");
        if (l2 != -1) {
            this.pY = l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void o(Map<String, List<String>> object) {
        if ((object = (List)object.get("X-Afma-Orientation")) == null || object.isEmpty()) return;
        {
            if ("portrait".equalsIgnoreCase((String)(object = (String)object.get(0)))) {
                this.mOrientation = dq.bA();
                return;
            } else {
                if (!"landscape".equalsIgnoreCase((String)object)) return;
                {
                    this.mOrientation = dq.bz();
                    return;
                }
            }
        }
    }

    public void a(String string2, Map<String, List<String>> map, String string3) {
        this.pO = string2;
        this.pP = string3;
        this.e(map);
    }

    public void e(Map<String, List<String>> map) {
        this.f(map);
        this.g(map);
        this.h(map);
        this.i(map);
        this.j(map);
        this.l(map);
        this.m(map);
        this.n(map);
        this.o(map);
        this.k(map);
    }

    public cz g(long l2) {
        return new cz(this.pO, this.pP, this.pQ, this.pT, this.pU, this.pV, -1, this.pX, this.pY, this.mOrientation, this.pN, l2, this.pR, this.pS);
    }
}

