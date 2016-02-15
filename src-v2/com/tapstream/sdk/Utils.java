/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Logging;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class Utils {
    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String encodeEventPair(String string2, String string3, Object object, boolean bl2) {
        if (string3 == null) return null;
        if (object == null) {
            return null;
        }
        if (string3.length() > 255) {
            Logging.log(5, "Tapstream Warning: Event key exceeds 255 characters, this field will not be included in the post (key=%s)", string3);
            return null;
        }
        if ((string2 = Utils.encodeString(string2 + string3)) == null) return null;
        string3 = Utils.stringify(object);
        if (bl2 && string3.length() > 255) {
            Logging.log(5, "Tapstream Warning: Event value exceeds 255 characters, this field will not be included in the post (value=%s)", object);
            return null;
        }
        if ((string3 = Utils.encodeString(string3)) == null) return null;
        return string2 + "=" + string3;
    }

    public static String encodeString(String string2) {
        try {
            string2 = URLEncoder.encode(string2, "UTF-8").replace("+", "%20");
            return string2;
        }
        catch (UnsupportedEncodingException var0_1) {
            var0_1.printStackTrace();
            return null;
        }
    }

    public static String stringify(Object object) {
        double d2;
        try {
            d2 = (Double)object;
        }
        catch (ClassCastException var3_2) {
            return object.toString();
        }
        if (Math.floor(d2) == d2) {
            return String.format(Locale.US, "%.0f", d2);
        }
        return object.toString();
    }
}

