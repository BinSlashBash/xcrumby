/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.nio.charset.Charset;

final class Util {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() {
    }

    public static void checkOffsetAndCount(long l2, long l3, long l4) {
        if ((l3 | l4) < 0 || l3 > l2 || l2 - l3 < l4) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", l2, l3, l4));
        }
    }

    public static int reverseBytesInt(int n2) {
        return (-16777216 & n2) >>> 24 | (16711680 & n2) >>> 8 | (65280 & n2) << 8 | (n2 & 255) << 24;
    }

    public static long reverseBytesLong(long l2) {
        return (-72057594037927936L & l2) >>> 56 | (0xFF000000000000L & l2) >>> 40 | (0xFF0000000000L & l2) >>> 24 | (0xFF00000000L & l2) >>> 8 | (0xFF000000L & l2) << 8 | (0xFF0000 & l2) << 24 | (65280 & l2) << 40 | (255 & l2) << 56;
    }

    public static short reverseBytesShort(short s2) {
        s2 = (short)(s2 & 65535);
        return (short)((65280 & s2) >>> 8 | (s2 & 255) << 8);
    }

    public static void sneakyRethrow(Throwable throwable) {
        Util.sneakyThrow2(throwable);
    }

    private static <T extends Throwable> void sneakyThrow2(Throwable throwable) throws Throwable {
        throw throwable;
    }
}

