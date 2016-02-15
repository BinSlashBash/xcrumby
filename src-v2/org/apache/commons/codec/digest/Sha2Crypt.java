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

public class Sha2Crypt {
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final Pattern SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";

    public static String sha256Crypt(byte[] arrby) {
        return Sha2Crypt.sha256Crypt(arrby, null);
    }

    public static String sha256Crypt(byte[] arrby, String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "$5$" + B64.getRandomSalt(8);
        }
        return Sha2Crypt.sha2Crypt(arrby, string3, "$5$", 32, "SHA-256");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String sha2Crypt(byte[] arrby, String object, String charSequence, int n2, String string2) {
        void var3_4;
        void var4_5;
        int n3;
        int n4 = arrby.length;
        int n5 = 5000;
        boolean bl2 = false;
        if (object == null) {
            throw new IllegalArgumentException("Salt must not be null");
        }
        Object object2 = SALT_PATTERN.matcher((CharSequence)object);
        if (object2 == null || !object2.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + (String)object);
        }
        if (object2.group(3) != null) {
            n5 = Math.max(1000, Math.min(999999999, Integer.parseInt(object2.group(3))));
            bl2 = true;
        }
        String string3 = object2.group(4);
        byte[] arrby2 = string3.getBytes(Charsets.UTF_8);
        int n6 = arrby2.length;
        object2 = DigestUtils.getDigest((String)var4_5);
        object2.update(arrby);
        object2.update(arrby2);
        object = DigestUtils.getDigest((String)var4_5);
        object.update(arrby);
        object.update(arrby2);
        object.update(arrby);
        object = object.digest();
        for (n3 = arrby.length; n3 > var3_4; n3 -= var3_4) {
            object2.update((byte[])object, 0, (int)var3_4);
        }
        object2.update((byte[])object, 0, n3);
        for (n3 = arrby.length; n3 > 0; n3 >>= 1) {
            if ((n3 & 1) != 0) {
                object2.update((byte[])object, 0, (int)var3_4);
                continue;
            }
            object2.update(arrby);
        }
        object = object2.digest();
        MessageDigest messageDigest = DigestUtils.getDigest((String)var4_5);
        for (n3 = 1; n3 <= n4; ++n3) {
            messageDigest.update(arrby);
        }
        Object object3 = messageDigest.digest();
        byte[] arrby3 = new byte[n4];
        for (n3 = 0; n3 < n4 - var3_4; n3 += var3_4) {
            System.arraycopy(object3, 0, arrby3, n3, (int)var3_4);
        }
        System.arraycopy(object3, 0, arrby3, n3, n4 - n3);
        object3 = DigestUtils.getDigest((String)var4_5);
        for (n3 = 1; n3 <= (object[0] & 255) + 16; ++n3) {
            object3.update(arrby2);
        }
        byte[] arrby4 = object3.digest();
        byte[] arrby5 = new byte[n6];
        for (n3 = 0; n3 < n6 - var3_4; n3 += var3_4) {
            System.arraycopy(arrby4, 0, arrby5, n3, (int)var3_4);
        }
        System.arraycopy(arrby4, 0, arrby5, n3, n6 - n3);
        for (n3 = 0; n3 <= n5 - 1; ++n3) {
            object2 = DigestUtils.getDigest((String)var4_5);
            if ((n3 & 1) != 0) {
                object2.update(arrby3, 0, n4);
            } else {
                object2.update((byte[])object, 0, (int)var3_4);
            }
            if (n3 % 3 != 0) {
                object2.update(arrby5, 0, n6);
            }
            if (n3 % 7 != 0) {
                object2.update(arrby3, 0, n4);
            }
            if ((n3 & 1) != 0) {
                object2.update((byte[])object, 0, (int)var3_4);
            } else {
                object2.update(arrby3, 0, n4);
            }
            object = object2.digest();
        }
        StringBuilder stringBuilder = new StringBuilder((String)charSequence);
        if (bl2) {
            stringBuilder.append("rounds=");
            stringBuilder.append(n5);
            stringBuilder.append("$");
        }
        stringBuilder.append(string3);
        stringBuilder.append("$");
        if (var3_4 == 32) {
            B64.b64from24bit((byte)object[0], (byte)object[10], (byte)object[20], 4, stringBuilder);
            B64.b64from24bit((byte)object[21], (byte)object[1], (byte)object[11], 4, stringBuilder);
            B64.b64from24bit((byte)object[12], (byte)object[22], (byte)object[2], 4, stringBuilder);
            B64.b64from24bit((byte)object[3], (byte)object[13], (byte)object[23], 4, stringBuilder);
            B64.b64from24bit((byte)object[24], (byte)object[4], (byte)object[14], 4, stringBuilder);
            B64.b64from24bit((byte)object[15], (byte)object[25], (byte)object[5], 4, stringBuilder);
            B64.b64from24bit((byte)object[6], (byte)object[16], (byte)object[26], 4, stringBuilder);
            B64.b64from24bit((byte)object[27], (byte)object[7], (byte)object[17], 4, stringBuilder);
            B64.b64from24bit((byte)object[18], (byte)object[28], (byte)object[8], 4, stringBuilder);
            B64.b64from24bit((byte)object[9], (byte)object[19], (byte)object[29], 4, stringBuilder);
            B64.b64from24bit(0, (byte)object[31], (byte)object[30], 3, stringBuilder);
        } else {
            B64.b64from24bit((byte)object[0], (byte)object[21], (byte)object[42], 4, stringBuilder);
            B64.b64from24bit((byte)object[22], (byte)object[43], (byte)object[1], 4, stringBuilder);
            B64.b64from24bit((byte)object[44], (byte)object[2], (byte)object[23], 4, stringBuilder);
            B64.b64from24bit((byte)object[3], (byte)object[24], (byte)object[45], 4, stringBuilder);
            B64.b64from24bit((byte)object[25], (byte)object[46], (byte)object[4], 4, stringBuilder);
            B64.b64from24bit((byte)object[47], (byte)object[5], (byte)object[26], 4, stringBuilder);
            B64.b64from24bit((byte)object[6], (byte)object[27], (byte)object[48], 4, stringBuilder);
            B64.b64from24bit((byte)object[28], (byte)object[49], (byte)object[7], 4, stringBuilder);
            B64.b64from24bit((byte)object[50], (byte)object[8], (byte)object[29], 4, stringBuilder);
            B64.b64from24bit((byte)object[9], (byte)object[30], (byte)object[51], 4, stringBuilder);
            B64.b64from24bit((byte)object[31], (byte)object[52], (byte)object[10], 4, stringBuilder);
            B64.b64from24bit((byte)object[53], (byte)object[11], (byte)object[32], 4, stringBuilder);
            B64.b64from24bit((byte)object[12], (byte)object[33], (byte)object[54], 4, stringBuilder);
            B64.b64from24bit((byte)object[34], (byte)object[55], (byte)object[13], 4, stringBuilder);
            B64.b64from24bit((byte)object[56], (byte)object[14], (byte)object[35], 4, stringBuilder);
            B64.b64from24bit((byte)object[15], (byte)object[36], (byte)object[57], 4, stringBuilder);
            B64.b64from24bit((byte)object[37], (byte)object[58], (byte)object[16], 4, stringBuilder);
            B64.b64from24bit((byte)object[59], (byte)object[17], (byte)object[38], 4, stringBuilder);
            B64.b64from24bit((byte)object[18], (byte)object[39], (byte)object[60], 4, stringBuilder);
            B64.b64from24bit((byte)object[40], (byte)object[61], (byte)object[19], 4, stringBuilder);
            B64.b64from24bit((byte)object[62], (byte)object[20], (byte)object[41], 4, stringBuilder);
            B64.b64from24bit(0, 0, (byte)object[63], 2, stringBuilder);
        }
        Arrays.fill(arrby4, 0);
        Arrays.fill(arrby3, 0);
        Arrays.fill(arrby5, 0);
        object2.reset();
        object3.reset();
        Arrays.fill(arrby, 0);
        Arrays.fill(arrby2, 0);
        return stringBuilder.toString();
    }

    public static String sha512Crypt(byte[] arrby) {
        return Sha2Crypt.sha512Crypt(arrby, null);
    }

    public static String sha512Crypt(byte[] arrby, String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "$6$" + B64.getRandomSalt(8);
        }
        return Sha2Crypt.sha2Crypt(arrby, string3, "$6$", 64, "SHA-512");
    }
}

