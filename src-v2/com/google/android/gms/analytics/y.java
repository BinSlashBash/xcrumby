/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.x;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class y {
    static String a(x x2, long l2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(x2.cO());
        if (x2.cQ() > 0 && (l2 -= x2.cQ()) >= 0) {
            stringBuilder.append("&qt").append("=").append(l2);
        }
        stringBuilder.append("&z").append("=").append(x2.cP());
        return stringBuilder.toString();
    }

    static String encode(String string2) {
        try {
            String string3 = URLEncoder.encode(string2, "UTF-8");
            return string3;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new AssertionError((Object)("URL encoding failed for: " + string2));
        }
    }

    static Map<String, String> v(Map<String, String> object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry entry : object.entrySet()) {
            String string2;
            if (!((String)entry.getKey()).startsWith("&") || entry.getValue() == null || TextUtils.isEmpty((CharSequence)(string2 = ((String)entry.getKey()).substring(1)))) continue;
            hashMap.put(string2, (String)entry.getValue());
        }
        return hashMap;
    }
}

