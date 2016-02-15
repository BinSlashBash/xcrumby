/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import java.io.IOException;

public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2-12");
    
    private final String protocol;

    private Protocol(String string3) {
        this.protocol = string3;
    }

    public static Protocol get(String string2) throws IOException {
        if (string2.equals(Protocol.HTTP_1_0.protocol)) {
            return HTTP_1_0;
        }
        if (string2.equals(Protocol.HTTP_1_1.protocol)) {
            return HTTP_1_1;
        }
        if (string2.equals(Protocol.HTTP_2.protocol)) {
            return HTTP_2;
        }
        if (string2.equals(Protocol.SPDY_3.protocol)) {
            return SPDY_3;
        }
        throw new IOException("Unexpected protocol: " + string2);
    }

    public String toString() {
        return this.protocol;
    }
}

