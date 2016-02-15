/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

public final class NumberOutput {
    private static int BILLION;
    static final char[] FULL_3;
    static final byte[] FULL_TRIPLETS_B;
    static final char[] LEAD_3;
    private static long MAX_INT_AS_LONG;
    private static int MILLION;
    private static long MIN_INT_AS_LONG;
    private static final char NC = '\u0000';
    static final String SMALLEST_LONG;
    private static long TEN_BILLION_L;
    private static long THOUSAND_L;
    static final String[] sSmallIntStrs;
    static final String[] sSmallIntStrs2;

    /*
     * Enabled aggressive block sorting
     */
    static {
        MILLION = 1000000;
        BILLION = 1000000000;
        TEN_BILLION_L = 10000000000L;
        THOUSAND_L = 1000;
        MIN_INT_AS_LONG = Integer.MIN_VALUE;
        MAX_INT_AS_LONG = Integer.MAX_VALUE;
        SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
        LEAD_3 = new char[4000];
        FULL_3 = new char[4000];
        int n2 = 0;
        int n3 = 0;
        do {
            char c2;
            char c3;
            if (n3 < 10) {
                c2 = (char)(n3 + 48);
                c3 = n3 == 0 ? '\u0000' : c2;
            } else {
                FULL_TRIPLETS_B = new byte[4000];
                n3 = 0;
                do {
                    if (n3 >= 4000) {
                        sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                        sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
                        return;
                    }
                    NumberOutput.FULL_TRIPLETS_B[n3] = (byte)FULL_3[n3];
                    ++n3;
                } while (true);
            }
            for (int i2 = 0; i2 < 10; ++i2) {
                char c4 = (char)(i2 + 48);
                char c5 = n3 == 0 && i2 == 0 ? '\u0000' : c4;
                for (int i3 = 0; i3 < 10; n2 += 4, ++i3) {
                    char c6 = (char)(i3 + 48);
                    NumberOutput.LEAD_3[n2] = c3;
                    NumberOutput.LEAD_3[n2 + 1] = c5;
                    NumberOutput.LEAD_3[n2 + 2] = c6;
                    NumberOutput.FULL_3[n2] = c2;
                    NumberOutput.FULL_3[n2 + 1] = c4;
                    NumberOutput.FULL_3[n2 + 2] = c6;
                }
            }
            ++n3;
        } while (true);
    }

    private static int calcLongStrLength(long l2) {
        int n2 = 10;
        long l3 = TEN_BILLION_L;
        while (l2 >= l3 && n2 != 19) {
            ++n2;
            l3 = (l3 << 3) + (l3 << 1);
        }
        return n2;
    }

    private static int full3(int n2, byte[] arrby, int n3) {
        int n4 = n2 << 2;
        n2 = n3 + 1;
        byte[] arrby2 = FULL_TRIPLETS_B;
        int n5 = n4 + 1;
        arrby[n3] = arrby2[n4];
        n3 = n2 + 1;
        arrby[n2] = FULL_TRIPLETS_B[n5];
        arrby[n3] = FULL_TRIPLETS_B[n5 + 1];
        return n3 + 1;
    }

    private static int full3(int n2, char[] arrc, int n3) {
        int n4 = n2 << 2;
        n2 = n3 + 1;
        char[] arrc2 = FULL_3;
        int n5 = n4 + 1;
        arrc[n3] = arrc2[n4];
        n3 = n2 + 1;
        arrc[n2] = FULL_3[n5];
        arrc[n3] = FULL_3[n5 + 1];
        return n3 + 1;
    }

    private static int leading3(int n2, byte[] arrby, int n3) {
        char[] arrc = LEAD_3;
        int n4 = (n2 <<= 2) + 1;
        char c2 = arrc[n2];
        n2 = n3;
        if (c2 != '\u0000') {
            arrby[n3] = (byte)c2;
            n2 = n3 + 1;
        }
        c2 = LEAD_3[n4];
        n3 = n2;
        if (c2 != '\u0000') {
            arrby[n2] = (byte)c2;
            n3 = n2 + 1;
        }
        arrby[n3] = (byte)LEAD_3[n4 + 1];
        return n3 + 1;
    }

    private static int leading3(int n2, char[] arrc, int n3) {
        char[] arrc2 = LEAD_3;
        int n4 = (n2 <<= 2) + 1;
        char c2 = arrc2[n2];
        n2 = n3;
        if (c2 != '\u0000') {
            arrc[n3] = c2;
            n2 = n3 + 1;
        }
        c2 = LEAD_3[n4];
        n3 = n2;
        if (c2 != '\u0000') {
            arrc[n2] = c2;
            n3 = n2 + 1;
        }
        arrc[n3] = LEAD_3[n4 + 1];
        return n3 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputInt(int n2, byte[] arrby, int n3) {
        int n4 = n2;
        int n5 = n3;
        if (n2 < 0) {
            if (n2 == Integer.MIN_VALUE) {
                return NumberOutput.outputLong((long)n2, arrby, n3);
            }
            arrby[n3] = 45;
            n4 = - n2;
            n5 = n3 + 1;
        }
        if (n4 < MILLION) {
            if (n4 >= 1000) {
                n2 = n4 / 1000;
                return NumberOutput.full3(n4 - n2 * 1000, arrby, NumberOutput.leading3(n2, arrby, n5));
            }
            if (n4 >= 10) return NumberOutput.leading3(n4, arrby, n5);
            arrby[n5] = (byte)(n4 + 48);
            return n5 + 1;
        }
        boolean bl2 = n4 >= BILLION;
        n2 = n4;
        n3 = n5;
        if (bl2) {
            n2 = n4 - BILLION;
            if (n2 >= BILLION) {
                n2 -= BILLION;
                arrby[n5] = 50;
                n3 = n5 + 1;
            } else {
                arrby[n5] = 49;
                n3 = n5 + 1;
            }
        }
        n5 = n2 / 1000;
        n4 = n5 / 1000;
        if (bl2) {
            n3 = NumberOutput.full3(n4, arrby, n3);
            return NumberOutput.full3(n2 - n5 * 1000, arrby, NumberOutput.full3(n5 - n4 * 1000, arrby, n3));
        }
        n3 = NumberOutput.leading3(n4, arrby, n3);
        return NumberOutput.full3(n2 - n5 * 1000, arrby, NumberOutput.full3(n5 - n4 * 1000, arrby, n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputInt(int n2, char[] arrc, int n3) {
        int n4 = n2;
        int n5 = n3;
        if (n2 < 0) {
            if (n2 == Integer.MIN_VALUE) {
                return NumberOutput.outputLong((long)n2, arrc, n3);
            }
            arrc[n3] = 45;
            n4 = - n2;
            n5 = n3 + 1;
        }
        if (n4 < MILLION) {
            if (n4 >= 1000) {
                n2 = n4 / 1000;
                return NumberOutput.full3(n4 - n2 * 1000, arrc, NumberOutput.leading3(n2, arrc, n5));
            }
            if (n4 >= 10) return NumberOutput.leading3(n4, arrc, n5);
            arrc[n5] = (char)(n4 + 48);
            return n5 + 1;
        }
        boolean bl2 = n4 >= BILLION;
        n2 = n4;
        n3 = n5;
        if (bl2) {
            n2 = n4 - BILLION;
            if (n2 >= BILLION) {
                n2 -= BILLION;
                arrc[n5] = 50;
                n3 = n5 + 1;
            } else {
                arrc[n5] = 49;
                n3 = n5 + 1;
            }
        }
        n5 = n2 / 1000;
        n4 = n5 / 1000;
        if (bl2) {
            n3 = NumberOutput.full3(n4, arrc, n3);
            return NumberOutput.full3(n2 - n5 * 1000, arrc, NumberOutput.full3(n5 - n4 * 1000, arrc, n3));
        }
        n3 = NumberOutput.leading3(n4, arrc, n3);
        return NumberOutput.full3(n2 - n5 * 1000, arrc, NumberOutput.full3(n5 - n4 * 1000, arrc, n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputLong(long l2, byte[] arrby, int n2) {
        int n3;
        long l3;
        int n4;
        if (l2 < 0) {
            if (l2 > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l2, arrby, n2);
            }
            if (l2 == Long.MIN_VALUE) {
                int n5 = SMALLEST_LONG.length();
                int n6 = 0;
                while (n6 < n5) {
                    arrby[n2] = (byte)SMALLEST_LONG.charAt(n6);
                    ++n6;
                    ++n2;
                }
                return n2;
            }
            arrby[n2] = 45;
            l3 = - l2;
            n4 = n2 + 1;
        } else {
            l3 = l2;
            n4 = n2;
            if (l2 <= MAX_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l2, arrby, n2);
            }
        }
        n2 = n3 = n4 + NumberOutput.calcLongStrLength(l3);
        while (l3 > MAX_INT_AS_LONG) {
            l2 = l3 / THOUSAND_L;
            NumberOutput.full3((int)(l3 - THOUSAND_L * l2), arrby, n2 -= 3);
            l3 = l2;
        }
        int n7 = (int)l3;
        int n8 = n2;
        n2 = n7;
        do {
            if (n2 < 1000) {
                NumberOutput.leading3(n2, arrby, n4);
                return n3;
            }
            n7 = n2 / 1000;
            NumberOutput.full3(n2 - n7 * 1000, arrby, n8 -= 3);
            n2 = n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputLong(long l2, char[] arrc, int n2) {
        long l3;
        int n3;
        int n4;
        if (l2 < 0) {
            if (l2 > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l2, arrc, n2);
            }
            if (l2 == Long.MIN_VALUE) {
                int n5 = SMALLEST_LONG.length();
                SMALLEST_LONG.getChars(0, n5, arrc, n2);
                return n2 + n5;
            }
            arrc[n2] = 45;
            l3 = - l2;
            n3 = n2 + 1;
        } else {
            l3 = l2;
            n3 = n2;
            if (l2 <= MAX_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l2, arrc, n2);
            }
        }
        n2 = n4 = n3 + NumberOutput.calcLongStrLength(l3);
        while (l3 > MAX_INT_AS_LONG) {
            l2 = l3 / THOUSAND_L;
            NumberOutput.full3((int)(l3 - THOUSAND_L * l2), arrc, n2 -= 3);
            l3 = l2;
        }
        int n6 = (int)l3;
        int n7 = n2;
        n2 = n6;
        do {
            if (n2 < 1000) {
                NumberOutput.leading3(n2, arrc, n3);
                return n4;
            }
            n6 = n2 / 1000;
            NumberOutput.full3(n2 - n6 * 1000, arrc, n7 -= 3);
            n2 = n6;
        } while (true);
    }

    public static String toString(double d2) {
        return Double.toString(d2);
    }

    public static String toString(int n2) {
        if (n2 < sSmallIntStrs.length) {
            if (n2 >= 0) {
                return sSmallIntStrs[n2];
            }
            int n3 = - n2 - 1;
            if (n3 < sSmallIntStrs2.length) {
                return sSmallIntStrs2[n3];
            }
        }
        return Integer.toString(n2);
    }

    public static String toString(long l2) {
        if (l2 <= Integer.MAX_VALUE && l2 >= Integer.MIN_VALUE) {
            return NumberOutput.toString((int)l2);
        }
        return Long.toString(l2);
    }
}

