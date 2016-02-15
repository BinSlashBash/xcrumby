package com.google.android.gms.analytics;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.codec.binary.Hex;

/* renamed from: com.google.android.gms.analytics.y */
class C0234y {
    static String m72a(C0233x c0233x, long j) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c0233x.cO());
        if (c0233x.cQ() > 0) {
            long cQ = j - c0233x.cQ();
            if (cQ >= 0) {
                stringBuilder.append("&qt").append("=").append(cQ);
            }
        }
        stringBuilder.append("&z").append("=").append(c0233x.cP());
        return stringBuilder.toString();
    }

    static String encode(String input) {
        try {
            return URLEncoder.encode(input, Hex.DEFAULT_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("URL encoding failed for: " + input);
        }
    }

    static Map<String, String> m73v(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            if (((String) entry.getKey()).startsWith("&") && entry.getValue() != null) {
                CharSequence substring = ((String) entry.getKey()).substring(1);
                if (!TextUtils.isEmpty(substring)) {
                    hashMap.put(substring, entry.getValue());
                }
            }
        }
        return hashMap;
    }
}
