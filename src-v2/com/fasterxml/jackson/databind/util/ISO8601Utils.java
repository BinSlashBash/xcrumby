/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone("GMT");

    private static void checkOffset(String string2, int n2, char c2) throws ParseException {
        char c3 = string2.charAt(n2);
        if (c3 != c2) {
            throw new ParseException("Expected '" + c2 + "' character but found '" + c3 + "'", n2);
        }
    }

    public static String format(Date date) {
        return ISO8601Utils.format(date, false, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean bl2) {
        return ISO8601Utils.format(date, bl2, TIMEZONE_GMT);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String format(Date serializable, boolean bl2, TimeZone timeZone) {
        void var1_2;
        void var2_3;
        GregorianCalendar gregorianCalendar = new GregorianCalendar((TimeZone)var2_3, Locale.US);
        gregorianCalendar.setTime((Date)serializable);
        int n2 = "yyyy-MM-ddThh:mm:ss".length();
        int n3 = var1_2 != false ? ".sss".length() : 0;
        int n4 = var2_3.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length();
        StringBuilder stringBuilder = new StringBuilder(n2 + n3 + n4);
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(1), "yyyy".length());
        stringBuilder.append('-');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(2) + 1, "MM".length());
        stringBuilder.append('-');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(5), "dd".length());
        stringBuilder.append('T');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(11), "hh".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(12), "mm".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(13), "ss".length());
        if (var1_2 != false) {
            stringBuilder.append('.');
            ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(14), "sss".length());
        }
        if ((n3 = var2_3.getOffset(gregorianCalendar.getTimeInMillis())) == 0) {
            stringBuilder.append('Z');
            return stringBuilder.toString();
        }
        n4 = Math.abs(n3 / 60000 / 60);
        n2 = Math.abs(n3 / 60000 % 60);
        char c2 = n3 < 0 ? '-' : '+';
        stringBuilder.append(c2);
        ISO8601Utils.padInt(stringBuilder, n4, "hh".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, n2, "mm".length());
        return stringBuilder.toString();
    }

    private static void padInt(StringBuilder stringBuilder, int n2, int n3) {
        String string2 = Integer.toString(n2);
        for (n2 = n3 - string2.length(); n2 > 0; --n2) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Date parse(String var0, ParsePosition var1_1) throws ParseException {
        block16 : {
            block15 : {
                block14 : {
                    var4_2 = var1_1.getIndex();
                    var3_3 = var4_2 + 4;
                    var5_4 = ISO8601Utils.parseInt(var0, var4_2, var3_3);
                    ISO8601Utils.checkOffset(var0, var3_3, '-');
                    var4_2 = var3_3 + 1;
                    var3_3 = var4_2 + 2;
                    var6_5 = ISO8601Utils.parseInt(var0, var4_2, var3_3);
                    ISO8601Utils.checkOffset(var0, var3_3, '-');
                    var4_2 = var3_3 + 1;
                    var3_3 = var4_2 + 2;
                    var7_6 = ISO8601Utils.parseInt(var0, var4_2, var3_3);
                    ISO8601Utils.checkOffset(var0, var3_3, 'T');
                    var4_2 = var3_3 + 1;
                    var3_3 = var4_2 + 2;
                    var8_7 = ISO8601Utils.parseInt(var0, var4_2, var3_3);
                    ISO8601Utils.checkOffset(var0, var3_3, ':');
                    var4_2 = var3_3 + 1;
                    var3_3 = var4_2 + 2;
                    var9_8 = ISO8601Utils.parseInt(var0, var4_2, var3_3);
                    ISO8601Utils.checkOffset(var0, var3_3, ':');
                    var4_2 = ++var3_3 + 2;
                    var10_9 = ISO8601Utils.parseInt(var0, var3_3, var4_2);
                    var3_3 = 0;
                    if (var0.charAt(var4_2) != '.') ** GOTO lbl35
                    ISO8601Utils.checkOffset(var0, var4_2, '.');
                    var3_3 = var4_2 + 1;
                    var4_2 = var3_3 + 3;
                    var3_3 = ISO8601Utils.parseInt(var0, var3_3, var4_2);
lbl35: // 2 sources:
                    var2_10 = var0.charAt(var4_2);
                    if (var2_10 != '+' && var2_10 != '-') ** GOTO lbl42
                    var12_11 = var0.substring(var4_2);
                    var11_12 = "GMT" + (String)var12_11;
                    var4_2 += var12_11.length();
                    ** GOTO lbl45
lbl42: // 1 sources:
                    if (var2_10 != 'Z') break block14;
                    var11_12 = "GMT";
                    ++var4_2;
lbl45: // 2 sources:
                    if (!(var12_11 = TimeZone.getTimeZone((String)var11_12)).getID().equals(var11_12)) {
                        throw new IndexOutOfBoundsException();
                    }
                    break block15;
                }
                try {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator " + var2_10);
                }
                catch (IndexOutOfBoundsException var11_13) {}
                ** GOTO lbl-1000
            }
            var11_12 = new GregorianCalendar((TimeZone)var12_11);
            var11_12.setLenient(false);
            var11_12.set(1, var5_4);
            var11_12.set(2, var6_5 - 1);
            var11_12.set(5, var7_6);
            var11_12.set(11, var8_7);
            var11_12.set(12, var9_8);
            var11_12.set(13, var10_9);
            var11_12.set(14, var3_3);
            var1_1.setIndex(var4_2);
            return var11_12.getTime();
            catch (IllegalArgumentException var11_15) {
                ** GOTO lbl-1000
            }
            catch (NumberFormatException var11_16) {}
lbl-1000: // 3 sources:
            {
                if (var0 != null) break block16;
                var0 = null;
                throw new ParseException("Failed to parse date [" + var0 + "]: " + var11_14.getMessage(), var1_1.getIndex());
            }
        }
        var0 = "" + '\"' + var0 + "'";
        throw new ParseException("Failed to parse date [" + var0 + "]: " + var11_14.getMessage(), var1_1.getIndex());
    }

    private static int parseInt(String string2, int n2, int n3) throws NumberFormatException {
        int n4;
        if (n2 < 0 || n3 > string2.length() || n2 > n3) {
            throw new NumberFormatException(string2);
        }
        int n5 = 0;
        if (n2 < n3) {
            n4 = n2 + 1;
            if ((n2 = Character.digit(string2.charAt(n2), 10)) < 0) {
                throw new NumberFormatException("Invalid number: " + string2);
            }
            n5 = - n2;
            n2 = n4;
        }
        while (n2 < n3) {
            n4 = Character.digit(string2.charAt(n2), 10);
            if (n4 < 0) {
                throw new NumberFormatException("Invalid number: " + string2);
            }
            n5 = n5 * 10 - n4;
            ++n2;
        }
        return - n5;
    }

    public static TimeZone timeZoneGMT() {
        return TIMEZONE_GMT;
    }
}

