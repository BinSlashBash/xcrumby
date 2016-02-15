/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String string2, String string3) {
        try {
            string2 = ByteString.of((string2 + ":" + string3).getBytes("ISO-8859-1")).base64();
            string2 = "Basic " + string2;
            return string2;
        }
        catch (UnsupportedEncodingException var0_1) {
            throw new AssertionError();
        }
    }
}

