/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import java.math.BigDecimal;

public final class NumberInput {
    static final long L_BILLION = 1000000000;
    static final String MAX_LONG_STR;
    static final String MIN_LONG_STR_NO_SIGN;
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    static {
        MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
        MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
    }

    private static NumberFormatException _badBD(String string2) {
        return new NumberFormatException("Value \"" + string2 + "\" can not be represented as BigDecimal");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean inLongRange(String string2, boolean bl2) {
        String string3 = bl2 ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n2 = string3.length();
        int n3 = string2.length();
        if (n3 < n2) return true;
        if (n3 > n2) {
            return false;
        }
        n3 = 0;
        while (n3 < n2) {
            int n4 = string2.charAt(n3) - string3.charAt(n3);
            if (n4 != 0) {
                if (n4 < 0) return true;
                return false;
            }
            ++n3;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean inLongRange(char[] arrc, int n2, int n3, boolean bl2) {
        String string2 = bl2 ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n4 = string2.length();
        if (n3 < n4) return true;
        if (n3 > n4) {
            return false;
        }
        n3 = 0;
        while (n3 < n4) {
            int n5 = arrc[n2 + n3] - string2.charAt(n3);
            if (n5 != 0) {
                if (n5 < 0) return true;
                return false;
            }
            ++n3;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static double parseAsDouble(String string2, double d2) {
        if (string2 == null) {
            return d2;
        }
        if ((string2 = string2.trim()).length() == 0) return d2;
        try {
            return NumberInput.parseDouble(string2);
        }
        catch (NumberFormatException var0_1) {
            return d2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int parseAsInt(String string2, int n2) {
        int n3;
        if (string2 == null) return n2;
        String string3 = string2.trim();
        int n4 = string3.length();
        if (n4 == 0) {
            return n2;
        }
        int n5 = n3 = 0;
        int n6 = n4;
        string2 = string3;
        if (n4 < 0) {
            char c2 = string3.charAt(0);
            if (c2 == '+') {
                string2 = string3.substring(1);
                n6 = string2.length();
                n5 = n3;
            } else {
                n5 = n3;
                n6 = n4;
                string2 = string3;
                if (c2 == '-') {
                    n5 = 0 + 1;
                    n6 = n4;
                    string2 = string3;
                }
            }
        }
        while (n5 < n6) {
            n4 = string2.charAt(n5);
            if (n4 > 57 || n4 < 48) {
                double d2;
                try {
                    d2 = NumberInput.parseDouble(string2);
                }
                catch (NumberFormatException var0_1) {
                    return n2;
                }
                return (int)d2;
            }
            ++n5;
        }
        try {
            return Integer.parseInt(string2);
        }
        catch (NumberFormatException var0_2) {
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long parseAsLong(String string2, long l2) {
        int n2;
        if (string2 == null) return l2;
        String string3 = string2.trim();
        int n3 = string3.length();
        if (n3 == 0) {
            return l2;
        }
        int n4 = n2 = 0;
        int n5 = n3;
        string2 = string3;
        if (n3 < 0) {
            char c2 = string3.charAt(0);
            if (c2 == '+') {
                string2 = string3.substring(1);
                n5 = string2.length();
                n4 = n2;
            } else {
                n4 = n2;
                n5 = n3;
                string2 = string3;
                if (c2 == '-') {
                    n4 = 0 + 1;
                    n5 = n3;
                    string2 = string3;
                }
            }
        }
        while (n4 < n5) {
            n3 = string2.charAt(n4);
            if (n3 > 57 || n3 < 48) {
                double d2;
                try {
                    d2 = NumberInput.parseDouble(string2);
                }
                catch (NumberFormatException var0_1) {
                    return l2;
                }
                return (long)d2;
            }
            ++n4;
        }
        try {
            return Long.parseLong(string2);
        }
        catch (NumberFormatException var0_2) {
            return l2;
        }
    }

    public static BigDecimal parseBigDecimal(String string2) throws NumberFormatException {
        try {
            BigDecimal bigDecimal = new BigDecimal(string2);
            return bigDecimal;
        }
        catch (NumberFormatException var1_2) {
            throw NumberInput._badBD(string2);
        }
    }

    public static BigDecimal parseBigDecimal(char[] arrc) throws NumberFormatException {
        return NumberInput.parseBigDecimal(arrc, 0, arrc.length);
    }

    public static BigDecimal parseBigDecimal(char[] arrc, int n2, int n3) throws NumberFormatException {
        try {
            BigDecimal bigDecimal = new BigDecimal(arrc, n2, n3);
            return bigDecimal;
        }
        catch (NumberFormatException var3_4) {
            throw NumberInput._badBD(new String(arrc, n2, n3));
        }
    }

    public static double parseDouble(String string2) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals(string2)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int parseInt(String string2) {
        int n2;
        int n3;
        boolean bl2 = false;
        int n4 = string2.charAt(0);
        int n5 = string2.length();
        if (n4 == 45) {
            bl2 = true;
        }
        if (bl2) {
            if (n5 == 1) return Integer.parseInt(string2);
            if (n5 > 10) {
                return Integer.parseInt(string2);
            }
            n3 = 1 + 1;
            n4 = string2.charAt(1);
        } else {
            if (n5 > 9) {
                return Integer.parseInt(string2);
            }
            n3 = 1;
        }
        if (n4 > 57) return Integer.parseInt(string2);
        if (n4 < 48) {
            return Integer.parseInt(string2);
        }
        n4 = n2 = n4 - 48;
        int n6 = n3;
        if (n3 < n5) {
            int n7 = n3 + 1;
            n4 = string2.charAt(n3);
            if (n4 > 57) return Integer.parseInt(string2);
            if (n4 < 48) {
                return Integer.parseInt(string2);
            }
            n4 = n6 = n2 * 10 + (n4 - 48);
            if (n7 < n5) {
                n3 = n7 + 1;
                n4 = string2.charAt(n7);
                if (n4 > 57) return Integer.parseInt(string2);
                if (n4 < 48) {
                    return Integer.parseInt(string2);
                }
                n4 = n2 = n6 * 10 + (n4 - 48);
                n6 = n3;
                if (n3 < n5) {
                    n4 = n3;
                    n3 = n2;
                    do {
                        n6 = n4 + 1;
                        if ((n4 = (int)string2.charAt(n4)) > 57) return Integer.parseInt(string2);
                        if (n4 < 48) {
                            return Integer.parseInt(string2);
                        }
                        n3 = n2 = n3 * 10 + (n4 - 48);
                        n4 = n6;
                    } while (n6 < n5);
                    n4 = n2;
                }
            }
        }
        n3 = n4;
        if (!bl2) return n3;
        return - n4;
    }

    public static int parseInt(char[] arrc, int n2, int n3) {
        int n4;
        int n5 = n4 = arrc[n2] - 48;
        int n6 = n2;
        int n7 = n3;
        if (n3 > 4) {
            n5 = n2 + 1;
            n2 = arrc[n5];
            n7 = n5 + 1;
            n5 = arrc[n7];
            n6 = n7 + 1;
            n7 = arrc[n6];
            int n8 = n6 + 1;
            n2 = (((n4 * 10 + (n2 - 48)) * 10 + (n5 - 48)) * 10 + (n7 - 48)) * 10 + (arrc[n8] - 48);
            n5 = n2;
            n6 = n8;
            n7 = n3 -= 4;
            if (n3 > 4) {
                n5 = n8 + 1;
                n3 = arrc[n5];
                n7 = n5 + 1;
                n5 = arrc[n7];
                return (((n2 * 10 + (n3 - 48)) * 10 + (n5 - 48)) * 10 + (arrc[n7] - 48)) * 10 + (arrc[++n7 + 1] - 48);
            }
        }
        n2 = n5;
        if (n7 > 1) {
            n2 = n3 = n5 * 10 + (arrc[++n6] - 48);
            if (n7 > 2) {
                n5 = n6 + 1;
                n2 = n3 = n3 * 10 + (arrc[n5] - 48);
                if (n7 > 3) {
                    n2 = n3 * 10 + (arrc[n5 + 1] - 48);
                }
            }
        }
        return n2;
    }

    public static long parseLong(String string2) {
        if (string2.length() <= 9) {
            return NumberInput.parseInt(string2);
        }
        return Long.parseLong(string2);
    }

    public static long parseLong(char[] arrc, int n2, int n3) {
        long l2 = NumberInput.parseInt(arrc, n2, n3 -= 9);
        return (long)NumberInput.parseInt(arrc, n2 + n3, 9) + l2 * 1000000000;
    }
}

