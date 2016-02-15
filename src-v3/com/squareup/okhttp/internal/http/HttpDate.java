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

    /* renamed from: com.squareup.okhttp.internal.http.HttpDate.1 */
    static class C05961 extends ThreadLocal<DateFormat> {
        C05961() {
        }

        protected DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            rfc1123.setTimeZone(TimeZone.getTimeZone("GMT"));
            return rfc1123;
        }
    }

    static {
        STANDARD_DATE_FORMAT = new C05961();
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = new String[]{"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];
    }

    public static Date parse(String value) {
        int i;
        try {
            return ((DateFormat) STANDARD_DATE_FORMAT.get()).parse(value);
        } catch (ParseException e) {
            synchronized (BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS) {
                i = 0;
            }
            int count = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
            while (i < count) {
                DateFormat format = BROWSER_COMPATIBLE_DATE_FORMATS[i];
                if (format == null) {
                    format = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[i], Locale.US);
                    BROWSER_COMPATIBLE_DATE_FORMATS[i] = format;
                }
                try {
                    return format.parse(value);
                } catch (ParseException e2) {
                    i++;
                }
            }
            return null;
        }
    }

    public static String format(Date value) {
        return ((DateFormat) STANDARD_DATE_FORMAT.get()).format(value);
    }

    private HttpDate() {
    }
}
