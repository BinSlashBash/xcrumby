/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.digest;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.B64;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5Crypt {
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(String string2) {
        return Md5Crypt.apr1Crypt(string2.getBytes(Charsets.UTF_8));
    }

    public static String apr1Crypt(String string2, String string3) {
        return Md5Crypt.apr1Crypt(string2.getBytes(Charsets.UTF_8), string3);
    }

    public static String apr1Crypt(byte[] arrby) {
        return Md5Crypt.apr1Crypt(arrby, "$apr1$" + B64.getRandomSalt(8));
    }

    public static String apr1Crypt(byte[] arrby, String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2;
            if (!string2.startsWith("$apr1$")) {
                string3 = "$apr1$" + string2;
            }
        }
        return Md5Crypt.md5Crypt(arrby, string3, "$apr1$");
    }

    public static String md5Crypt(byte[] arrby) {
        return Md5Crypt.md5Crypt(arrby, "$1$" + B64.getRandomSalt(8));
    }

    public static String md5Crypt(byte[] arrby, String string2) {
        return Md5Crypt.md5Crypt(arrby, string2, "$1$");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String md5Crypt(byte[] arrby, String string2, String object) {
        int n2;
        Object object2;
        void var1_3;
        Object object3;
        int n3 = arrby.length;
        if (string2 == null) {
            String string3 = B64.getRandomSalt(8);
        } else {
            object3 = Pattern.compile("^" + object2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(string2);
            if (object3 == null || !object3.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + string2);
            }
            String string4 = object3.group(1);
        }
        byte[] arrby2 = var1_3.getBytes(Charsets.UTF_8);
        MessageDigest messageDigest = DigestUtils.getMd5Digest();
        messageDigest.update(arrby);
        messageDigest.update(object2.getBytes(Charsets.UTF_8));
        messageDigest.update(arrby2);
        object3 = DigestUtils.getMd5Digest();
        object3.update(arrby);
        object3.update(arrby2);
        object3.update(arrby);
        Object object4 = object3.digest();
        for (n2 = n3; n2 > 0; n2 -= 16) {
            int n4 = n2 > 16 ? 16 : n2;
            messageDigest.update((byte[])object4, 0, n4);
        }
        Arrays.fill((byte[])object4, 0);
        while (n3 > 0) {
            if ((n3 & 1) == 1) {
                messageDigest.update(object4[0]);
            } else {
                messageDigest.update(arrby[0]);
            }
            n3 >>= 1;
        }
        object4 = new StringBuilder((String)object2 + (String)var1_3 + "$");
        byte[] arrby3 = messageDigest.digest();
        n2 = 0;
        object2 = object3;
        do {
            void var1_6;
            if (n2 >= 1000) {
                B64.b64from24bit((byte)var1_6[0], (byte)var1_6[6], (byte)var1_6[12], 4, (StringBuilder)object4);
                B64.b64from24bit((byte)var1_6[1], (byte)var1_6[7], (byte)var1_6[13], 4, (StringBuilder)object4);
                B64.b64from24bit((byte)var1_6[2], (byte)var1_6[8], (byte)var1_6[14], 4, (StringBuilder)object4);
                B64.b64from24bit((byte)var1_6[3], (byte)var1_6[9], (byte)var1_6[15], 4, (StringBuilder)object4);
                B64.b64from24bit((byte)var1_6[4], (byte)var1_6[10], (byte)var1_6[5], 4, (StringBuilder)object4);
                B64.b64from24bit(0, 0, (byte)var1_6[11], 2, (StringBuilder)object4);
                messageDigest.reset();
                object2.reset();
                Arrays.fill(arrby, 0);
                Arrays.fill(arrby2, 0);
                Arrays.fill((byte[])var1_6, 0);
                return object4.toString();
            }
            object2 = DigestUtils.getMd5Digest();
            if ((n2 & 1) != 0) {
                object2.update(arrby);
            } else {
                object2.update((byte[])var1_6, 0, 16);
            }
            if (n2 % 3 != 0) {
                object2.update(arrby2);
            }
            if (n2 % 7 != 0) {
                object2.update(arrby);
            }
            if ((n2 & 1) != 0) {
                object2.update((byte[])var1_6, 0, 16);
            } else {
                object2.update(arrby);
            }
            byte[] arrby4 = object2.digest();
            ++n2;
        } while (true);
    }
}

