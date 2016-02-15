/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class ak {
    private static final char[] wo = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /*
     * Enabled aggressive block sorting
     */
    public static Map<String, String> N(String arrstring) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        arrstring = arrstring.split("&");
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String[] arrstring2 = arrstring[n3].split("=");
            if (arrstring2.length > 1) {
                hashMap.put(arrstring2[0], arrstring2[1]);
            } else if (arrstring2.length == 1 && arrstring2[0].length() != 0) {
                hashMap.put(arrstring2[0], null);
            }
            ++n3;
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String O(String map) {
        Object object;
        if (TextUtils.isEmpty(map)) {
            return null;
        }
        String[] arrstring = map;
        if (map.contains((CharSequence)"?")) {
            object = map.split("[\\?]");
            arrstring = map;
            if (object.length > 1) {
                arrstring = object[1];
            }
        }
        if (arrstring.contains("%3D")) {
            try {
                map = URLDecoder.decode(arrstring, "UTF-8");
            }
            catch (UnsupportedEncodingException var0_1) {
                return null;
            }
        } else {
            map = arrstring;
            if (!arrstring.contains("=")) {
                return null;
            }
        }
        map = ak.N((String)((Object)map));
        arrstring = new String[]{"dclid", "utm_source", "gclid", "utm_campaign", "utm_medium", "utm_term", "utm_content", "utm_id", "gmob_t"};
        object = new StringBuilder();
        int n2 = 0;
        while (n2 < arrstring.length) {
            if (!TextUtils.isEmpty((CharSequence)map.get(arrstring[n2]))) {
                if (object.length() > 0) {
                    object.append("&");
                }
                object.append(arrstring[n2]).append("=").append(map.get(arrstring[n2]));
            }
            ++n2;
        }
        return object.toString();
    }

    public static double a(String string2, double d2) {
        if (string2 == null) {
            return d2;
        }
        try {
            double d3 = Double.parseDouble(string2);
            return d3;
        }
        catch (NumberFormatException var0_1) {
            return d2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static String a(Locale locale) {
        if (locale == null || TextUtils.isEmpty((CharSequence)locale.getLanguage())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getLanguage().toLowerCase());
        if (!TextUtils.isEmpty((CharSequence)locale.getCountry())) {
            stringBuilder.append("-").append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    public static void a(Map<String, String> map, String string2, String string3) {
        if (!map.containsKey(string2)) {
            map.put(string2, string3);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean d(String string2, boolean bl2) {
        boolean bl3 = bl2;
        if (string2 == null) return bl3;
        if (string2.equalsIgnoreCase("true")) return true;
        if (string2.equalsIgnoreCase("yes")) return true;
        if (string2.equalsIgnoreCase("1")) {
            return true;
        }
        if (string2.equalsIgnoreCase("false")) return false;
        if (string2.equalsIgnoreCase("no")) return false;
        bl3 = bl2;
        if (!string2.equalsIgnoreCase("0")) return bl3;
        return false;
    }

    static String u(boolean bl2) {
        if (bl2) {
            return "1";
        }
        return "0";
    }
}

