/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.digest;

import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.codec.digest.UnixCrypt;

public class Crypt {
    public static String crypt(String string2) {
        return Crypt.crypt(string2, null);
    }

    public static String crypt(String string2, String string3) {
        return Crypt.crypt(string2.getBytes(Charsets.UTF_8), string3);
    }

    public static String crypt(byte[] arrby) {
        return Crypt.crypt(arrby, null);
    }

    public static String crypt(byte[] arrby, String string2) {
        if (string2 == null) {
            return Sha2Crypt.sha512Crypt(arrby);
        }
        if (string2.startsWith("$6$")) {
            return Sha2Crypt.sha512Crypt(arrby, string2);
        }
        if (string2.startsWith("$5$")) {
            return Sha2Crypt.sha256Crypt(arrby, string2);
        }
        if (string2.startsWith("$1$")) {
            return Md5Crypt.md5Crypt(arrby, string2);
        }
        return UnixCrypt.crypt(arrby, string2);
    }
}

