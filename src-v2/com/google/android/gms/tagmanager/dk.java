/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.by;
import com.google.android.gms.tagmanager.dh;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class dk {
    private static by<d.a> a(by<d.a> by2) {
        try {
            by<d.a> by3 = new by<d.a>(dh.r(dk.cd(dh.j(by2.getObject()))), by2.kQ());
            return by3;
        }
        catch (UnsupportedEncodingException var1_2) {
            bh.b("Escape URI: unsupported encoding", var1_2);
            return by2;
        }
    }

    private static by<d.a> a(by<d.a> by2, int n2) {
        if (!dk.q(by2.getObject())) {
            bh.w("Escaping can only be applied to strings.");
            return by2;
        }
        switch (n2) {
            default: {
                bh.w("Unsupported Value Escaping: " + n2);
                return by2;
            }
            case 12: 
        }
        return dk.a(by2);
    }

    static /* varargs */ by<d.a> a(by<d.a> by2, int ... arrn) {
        int n2 = arrn.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            by2 = dk.a(by2, arrn[i2]);
        }
        return by2;
    }

    static String cd(String string2) throws UnsupportedEncodingException {
        return URLEncoder.encode(string2, "UTF-8").replaceAll("\\+", "%20");
    }

    private static boolean q(d.a a2) {
        return dh.o(a2) instanceof String;
    }
}

