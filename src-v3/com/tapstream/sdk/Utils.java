package com.tapstream.sdk;

import android.support.v4.view.MotionEventCompat;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import org.apache.commons.codec.binary.Hex;

public class Utils {
    public static String encodeEventPair(String str, String str2, Object obj, boolean z) {
        if (str2 == null || obj == null) {
            return null;
        }
        if (str2.length() > MotionEventCompat.ACTION_MASK) {
            Logging.log(5, "Tapstream Warning: Event key exceeds 255 characters, this field will not be included in the post (key=%s)", str2);
            return null;
        }
        String encodeString = encodeString(str + str2);
        if (encodeString == null) {
            return null;
        }
        String stringify = stringify(obj);
        if (!z || stringify.length() <= MotionEventCompat.ACTION_MASK) {
            stringify = encodeString(stringify);
            return stringify != null ? encodeString + "=" + stringify : null;
        } else {
            Logging.log(5, "Tapstream Warning: Event value exceeds 255 characters, this field will not be included in the post (value=%s)", obj);
            return null;
        }
    }

    public static String encodeString(String str) {
        try {
            return URLEncoder.encode(str, Hex.DEFAULT_CHARSET_NAME).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stringify(Object obj) {
        try {
            double doubleValue = ((Double) obj).doubleValue();
            if (Math.floor(doubleValue) != doubleValue) {
                return obj.toString();
            }
            return String.format(Locale.US, "%.0f", new Object[]{Double.valueOf(doubleValue)});
        } catch (ClassCastException e) {
            return obj.toString();
        }
    }
}
