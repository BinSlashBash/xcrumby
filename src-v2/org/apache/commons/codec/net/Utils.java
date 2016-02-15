/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

class Utils {
    Utils() {
    }

    static int digit16(byte by2) throws DecoderException {
        int n2 = Character.digit((char)by2, 16);
        if (n2 == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + by2);
        }
        return n2;
    }
}

