package com.google.android.gms.tagmanager;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0355d.C1367a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Hex;

class dk {
    private static by<C1367a> m1475a(by<C1367a> byVar) {
        try {
            return new by(dh.m1471r(cd(dh.m1460j((C1367a) byVar.getObject()))), byVar.kQ());
        } catch (Throwable e) {
            bh.m1381b("Escape URI: unsupported encoding", e);
            return byVar;
        }
    }

    private static by<C1367a> m1476a(by<C1367a> byVar, int i) {
        if (m1478q((C1367a) byVar.getObject())) {
            switch (i) {
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    return m1475a(byVar);
                default:
                    bh.m1384w("Unsupported Value Escaping: " + i);
                    return byVar;
            }
        }
        bh.m1384w("Escaping can only be applied to strings.");
        return byVar;
    }

    static by<C1367a> m1477a(by<C1367a> byVar, int... iArr) {
        by a;
        for (int a2 : iArr) {
            a = m1476a(a, a2);
        }
        return a;
    }

    static String cd(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, Hex.DEFAULT_CHARSET_NAME).replaceAll("\\+", "%20");
    }

    private static boolean m1478q(C1367a c1367a) {
        return dh.m1468o(c1367a) instanceof String;
    }
}
