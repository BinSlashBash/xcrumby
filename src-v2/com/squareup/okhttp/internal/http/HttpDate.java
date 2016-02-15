/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class HttpDate {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT;

    static {
        STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return simpleDateFormat;
            }
        };
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = new String[]{"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];
    }

    private HttpDate() {
    }

    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Date parse(String string2) {
        try {
            return STANDARD_DATE_FORMAT.get().parse(string2);
        }
        catch (ParseException var3_2) {
            String[] arrstring = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
            synchronized (arrstring) {
                int n2 = 0;
                int n3 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
                do {
                    DateFormat dateFormat;
                    if (n2 >= n3) {
                        return null;
                    }
                    Cloneable cloneable = dateFormat = BROWSER_COMPATIBLE_DATE_FORMATS[n2];
                    if (dateFormat == null) {
                        HttpDate.BROWSER_COMPATIBLE_DATE_FORMATS[n2] = cloneable = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[n2], Locale.US);
                    }
                    try {
                        return cloneable.parse(string2);
                    }
                    catch (ParseException var3_4) {
                        ++n2;
                        continue;
                    }
                    break;
                } while (true);
            }
        }
    }

}

