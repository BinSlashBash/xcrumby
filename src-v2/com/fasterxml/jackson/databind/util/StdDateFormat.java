/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StdDateFormat
extends DateFormat {
    protected static final String[] ALL_FORMATS = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"};
    protected static final DateFormat DATE_FORMAT_ISO8601;
    protected static final DateFormat DATE_FORMAT_ISO8601_Z;
    protected static final DateFormat DATE_FORMAT_PLAIN;
    protected static final DateFormat DATE_FORMAT_RFC1123;
    protected static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final Locale DEFAULT_LOCALE;
    private static final TimeZone DEFAULT_TIMEZONE;
    public static final StdDateFormat instance;
    protected transient DateFormat _formatISO8601;
    protected transient DateFormat _formatISO8601_z;
    protected transient DateFormat _formatPlain;
    protected transient DateFormat _formatRFC1123;
    protected final Locale _locale;
    protected transient TimeZone _timezone;

    static {
        DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT");
        DEFAULT_LOCALE = Locale.US;
        DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", DEFAULT_LOCALE);
        DATE_FORMAT_RFC1123.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", DEFAULT_LOCALE);
        DATE_FORMAT_ISO8601.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_ISO8601_Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", DEFAULT_LOCALE);
        DATE_FORMAT_ISO8601_Z.setTimeZone(DEFAULT_TIMEZONE);
        DATE_FORMAT_PLAIN = new SimpleDateFormat("yyyy-MM-dd", DEFAULT_LOCALE);
        DATE_FORMAT_PLAIN.setTimeZone(DEFAULT_TIMEZONE);
        instance = new StdDateFormat();
    }

    public StdDateFormat() {
        this._locale = DEFAULT_LOCALE;
    }

    @Deprecated
    public StdDateFormat(TimeZone timeZone) {
        this(timeZone, DEFAULT_LOCALE);
    }

    public StdDateFormat(TimeZone timeZone, Locale locale) {
        this._timezone = timeZone;
        this._locale = locale;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final DateFormat _cloneFormat(DateFormat object, String object2, TimeZone timeZone, Locale locale) {
        if (!locale.equals(DEFAULT_LOCALE)) {
            object2 = new SimpleDateFormat((String)object2, locale);
            object = timeZone;
            if (timeZone == null) {
                object = DEFAULT_TIMEZONE;
            }
            object2.setTimeZone((TimeZone)object);
            return object2;
        }
        object = object2 = (DateFormat)object.clone();
        if (timeZone == null) return object;
        object2.setTimeZone(timeZone);
        return object2;
    }

    @Deprecated
    public static DateFormat getBlueprintISO8601Format() {
        return DATE_FORMAT_ISO8601;
    }

    @Deprecated
    public static DateFormat getBlueprintRFC1123Format() {
        return DATE_FORMAT_RFC1123;
    }

    public static TimeZone getDefaultTimeZone() {
        return DEFAULT_TIMEZONE;
    }

    @Deprecated
    public static DateFormat getISO8601Format(TimeZone timeZone) {
        return StdDateFormat.getISO8601Format(timeZone, DEFAULT_LOCALE);
    }

    public static DateFormat getISO8601Format(TimeZone timeZone, Locale locale) {
        return StdDateFormat._cloneFormat(DATE_FORMAT_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timeZone, locale);
    }

    @Deprecated
    public static DateFormat getRFC1123Format(TimeZone timeZone) {
        return StdDateFormat.getRFC1123Format(timeZone, DEFAULT_LOCALE);
    }

    public static DateFormat getRFC1123Format(TimeZone timeZone, Locale locale) {
        return StdDateFormat._cloneFormat(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", timeZone, locale);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final boolean hasTimeZone(String string2) {
        char c2;
        int n2 = string2.length();
        if (n2 >= 6 && ((c2 = string2.charAt(n2 - 6)) == '+' || c2 == '-' || (c2 = string2.charAt(n2 - 5)) == '+' || c2 == '-' || (n2 = (int)string2.charAt(n2 - 3)) == 43 || n2 == 45)) {
            return true;
        }
        return false;
    }

    @Override
    public StdDateFormat clone() {
        return new StdDateFormat(this._timezone, this._locale);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (this._formatISO8601 == null) {
            this._formatISO8601 = StdDateFormat._cloneFormat(DATE_FORMAT_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", this._timezone, this._locale);
        }
        return this._formatISO8601.format(date, stringBuffer, fieldPosition);
    }

    protected boolean looksLikeISO8601(String string2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (string2.length() >= 5) {
            bl3 = bl2;
            if (Character.isDigit(string2.charAt(0))) {
                bl3 = bl2;
                if (Character.isDigit(string2.charAt(3))) {
                    bl3 = bl2;
                    if (string2.charAt(4) == '-') {
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Date parse(String string2) throws ParseException {
        ParsePosition parsePosition;
        Date date = this.parse(string2 = string2.trim(), parsePosition = new ParsePosition(0));
        if (date != null) {
            return date;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] arrstring = ALL_FORMATS;
        int n2 = arrstring.length;
        int n3 = 0;
        do {
            if (n3 >= n2) {
                stringBuilder.append('\"');
                throw new ParseException(String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", string2, stringBuilder.toString()), parsePosition.getErrorIndex());
            }
            String string3 = arrstring[n3];
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\", \"");
            } else {
                stringBuilder.append('\"');
            }
            stringBuilder.append(string3);
            ++n3;
        } while (true);
    }

    @Override
    public Date parse(String string2, ParsePosition parsePosition) {
        int n2;
        if (this.looksLikeISO8601(string2)) {
            return this.parseAsISO8601(string2, parsePosition);
        }
        int n3 = string2.length();
        while ((n2 = n3 - 1) >= 0) {
            char c2 = string2.charAt(n2);
            if (c2 >= '0') {
                n3 = n2;
                if (c2 <= '9') continue;
            }
            if (n2 > 0) break;
            n3 = n2;
            if (c2 == '-') continue;
        }
        if (n2 < 0 && (string2.charAt(0) == '-' || NumberInput.inLongRange(string2, false))) {
            return new Date(Long.parseLong(string2));
        }
        return this.parseAsRFC1123(string2, parsePosition);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected Date parseAsISO8601(String var1_1, ParsePosition var2_2) {
        var4_3 = var1_1.length();
        var3_4 = var1_1.charAt(var4_3 - 1);
        if (var4_3 <= 10 && Character.isDigit(var3_4)) {
            var6_8 = var8_5 = this._formatPlain;
            var7_9 = var1_1;
            if (var8_5 != null) return var6_8.parse((String)var7_9, var2_2);
            this._formatPlain = var6_8 = StdDateFormat._cloneFormat(StdDateFormat.DATE_FORMAT_PLAIN, "yyyy-MM-dd", this._timezone, this._locale);
            var7_9 = var1_1;
            return var6_8.parse((String)var7_9, var2_2);
        }
        if (var3_4 == 'Z') {
            var8_6 = var6_8 = this._formatISO8601_z;
            if (var6_8 == null) {
                this._formatISO8601_z = var8_6 = StdDateFormat._cloneFormat(StdDateFormat.DATE_FORMAT_ISO8601_Z, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", this._timezone, this._locale);
            }
            var6_8 = var8_6;
            var7_9 = var1_1;
            if (var1_1.charAt(var4_3 - 4) != ':') return var6_8.parse((String)var7_9, var2_2);
            var1_1 = new StringBuilder((String)var1_1);
            var1_1.insert(var4_3 - 1, ".000");
            var7_9 = var1_1.toString();
            var6_8 = var8_6;
            return var6_8.parse((String)var7_9, var2_2);
        }
        if (!StdDateFormat.hasTimeZone((String)var1_1)) ** GOTO lbl45
        var5_10 = var1_1.charAt(var4_3 - 3);
        if (var5_10 != ':') ** GOTO lbl29
        var1_1 = new StringBuilder((String)var1_1);
        var1_1.delete(var4_3 - 3, var4_3 - 2);
        var6_8 = var1_1.toString();
        ** GOTO lbl33
lbl29: // 1 sources:
        if (var5_10 == '+') ** GOTO lbl-1000
        var6_8 = var1_1;
        if (var5_10 == '-') lbl-1000: // 2 sources:
        {
            var6_8 = (String)var1_1 + "00";
        }
lbl33: // 4 sources:
        var4_3 = var6_8.length();
        var1_1 = var6_8;
        if (Character.isDigit(var6_8.charAt(var4_3 - 9))) {
            var1_1 = new StringBuilder((String)var6_8);
            var1_1.insert(var4_3 - 5, ".000");
            var1_1 = var1_1.toString();
        }
        var6_8 = this._formatISO8601;
        var7_9 = var1_1;
        if (this._formatISO8601 != null) return var6_8.parse((String)var7_9, var2_2);
        this._formatISO8601 = var6_8 = StdDateFormat._cloneFormat(StdDateFormat.DATE_FORMAT_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", this._timezone, this._locale);
        var7_9 = var1_1;
        return var6_8.parse((String)var7_9, var2_2);
lbl45: // 1 sources:
        var6_8 = new StringBuilder((String)var1_1);
        if (var4_3 - var1_1.lastIndexOf(84) - 1 <= 8) {
            var6_8.append(".000");
        }
        var6_8.append('Z');
        var1_1 = var6_8.toString();
        var6_8 = var8_7 = this._formatISO8601_z;
        var7_9 = var1_1;
        if (var8_7 != null) return var6_8.parse((String)var7_9, var2_2);
        this._formatISO8601_z = var6_8 = StdDateFormat._cloneFormat(StdDateFormat.DATE_FORMAT_ISO8601_Z, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", this._timezone, this._locale);
        var7_9 = var1_1;
        return var6_8.parse((String)var7_9, var2_2);
    }

    protected Date parseAsRFC1123(String string2, ParsePosition parsePosition) {
        if (this._formatRFC1123 == null) {
            this._formatRFC1123 = StdDateFormat._cloneFormat(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", this._timezone, this._locale);
        }
        return this._formatRFC1123.parse(string2, parsePosition);
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this._timezone)) {
            this._formatRFC1123 = null;
            this._formatISO8601 = null;
            this._formatISO8601_z = null;
            this._formatPlain = null;
            this._timezone = timeZone;
        }
    }

    public String toString() {
        String string2 = "DateFormat " + this.getClass().getName();
        TimeZone timeZone = this._timezone;
        String string3 = string2;
        if (timeZone != null) {
            string3 = string2 + " (timezone: " + timeZone + ")";
        }
        return string3 + "(locale: " + this._locale + ")";
    }

    public StdDateFormat withLocale(Locale locale) {
        if (locale.equals(this._locale)) {
            return this;
        }
        return new StdDateFormat(this._timezone, locale);
    }

    public StdDateFormat withTimeZone(TimeZone timeZone) {
        TimeZone timeZone2 = timeZone;
        if (timeZone == null) {
            timeZone2 = DEFAULT_TIMEZONE;
        }
        if (timeZone2.equals(this._timezone)) {
            return this;
        }
        return new StdDateFormat(timeZone2, this._locale);
    }
}

