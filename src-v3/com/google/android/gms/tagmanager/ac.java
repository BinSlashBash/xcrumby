package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ac extends aj {
    private static final String ID;
    private static final String XQ;
    private static final String XR;
    private static final String XS;
    private static final String XT;

    static {
        ID = C0321a.ENCODE.toString();
        XQ = C0325b.ARG0.toString();
        XR = C0325b.NO_PADDING.toString();
        XS = C0325b.INPUT_FORMAT.toString();
        XT = C0325b.OUTPUT_FORMAT.toString();
    }

    public ac() {
        super(ID, XQ);
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2445x(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(XQ);
        if (c1367a == null || c1367a == dh.lT()) {
            return dh.lT();
        }
        String j = dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(XS);
        String j2 = c1367a == null ? "text" : dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(XT);
        String j3 = c1367a == null ? "base16" : dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(XR);
        int i = (c1367a == null || !dh.m1466n(c1367a).booleanValue()) ? 2 : 3;
        try {
            byte[] bytes;
            Object d;
            if ("text".equals(j2)) {
                bytes = j.getBytes();
            } else if ("base16".equals(j2)) {
                bytes = C0522j.bm(j);
            } else if ("base64".equals(j2)) {
                bytes = Base64.decode(j, i);
            } else if ("base64url".equals(j2)) {
                bytes = Base64.decode(j, i | 8);
            } else {
                bh.m1384w("Encode: unknown input format: " + j2);
                return dh.lT();
            }
            if ("base16".equals(j3)) {
                d = C0522j.m1479d(bytes);
            } else if ("base64".equals(j3)) {
                d = Base64.encodeToString(bytes, i);
            } else if ("base64url".equals(j3)) {
                d = Base64.encodeToString(bytes, i | 8);
            } else {
                bh.m1384w("Encode: unknown output format: " + j3);
                return dh.lT();
            }
            return dh.m1471r(d);
        } catch (IllegalArgumentException e) {
            bh.m1384w("Encode: invalid input:");
            return dh.lT();
        }
    }
}
