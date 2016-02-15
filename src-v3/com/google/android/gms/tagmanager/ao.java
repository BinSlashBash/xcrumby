package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

class ao extends aj {
    private static final String ID;
    private static final String XQ;
    private static final String XS;
    private static final String XW;

    static {
        ID = C0321a.HASH.toString();
        XQ = C0325b.ARG0.toString();
        XW = C0325b.ALGORITHM.toString();
        XS = C0325b.INPUT_FORMAT.toString();
    }

    public ao() {
        super(ID, XQ);
    }

    private byte[] m2448c(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(bArr);
        return instance.digest();
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2449x(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(XQ);
        if (c1367a == null || c1367a == dh.lT()) {
            return dh.lT();
        }
        byte[] bytes;
        String j = dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(XW);
        String j2 = c1367a == null ? MessageDigestAlgorithms.MD5 : dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(XS);
        String j3 = c1367a == null ? "text" : dh.m1460j(c1367a);
        if ("text".equals(j3)) {
            bytes = j.getBytes();
        } else if ("base16".equals(j3)) {
            bytes = C0522j.bm(j);
        } else {
            bh.m1384w("Hash: unknown input format: " + j3);
            return dh.lT();
        }
        try {
            return dh.m1471r(C0522j.m1479d(m2448c(j2, bytes)));
        } catch (NoSuchAlgorithmException e) {
            bh.m1384w("Hash: unknown algorithm: " + j2);
            return dh.lT();
        }
    }
}
