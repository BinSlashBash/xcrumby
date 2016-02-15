/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HeaderParser;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class OkHeaders {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>(){

        @Override
        public int compare(String string2, String string3) {
            if (string2 == string3) {
                return 0;
            }
            if (string2 == null) {
                return -1;
            }
            if (string3 == null) {
                return 1;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(string2, string3);
        }
    };
    static final String PREFIX = Platform.get().getPrefix();
    public static final String RECEIVED_MILLIS;
    public static final String SELECTED_PROTOCOL;
    public static final String SENT_MILLIS;

    static {
        SENT_MILLIS = PREFIX + "-Sent-Millis";
        RECEIVED_MILLIS = PREFIX + "-Received-Millis";
        SELECTED_PROTOCOL = PREFIX + "-Selected-Protocol";
    }

    private OkHeaders() {
    }

    public static void addCookies(Request.Builder builder, Map<String, List<String>> object) {
        for (Map.Entry entry : object.entrySet()) {
            String string2 = (String)entry.getKey();
            if (!"Cookie".equalsIgnoreCase(string2) && !"Cookie2".equalsIgnoreCase(string2) || ((List)entry.getValue()).isEmpty()) continue;
            builder.addHeader(string2, OkHeaders.buildCookieHeader((List)entry.getValue()));
        }
    }

    private static String buildCookieHeader(List<String> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < list.size(); ++i2) {
            if (i2 > 0) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(list.get(i2));
        }
        return stringBuilder.toString();
    }

    public static long contentLength(Headers headers) {
        return OkHeaders.stringToLong(headers.get("Content-Length"));
    }

    public static long contentLength(Request request) {
        return OkHeaders.contentLength(request.headers());
    }

    public static long contentLength(Response response) {
        return OkHeaders.contentLength(response.headers());
    }

    public static boolean hasVaryAll(Response response) {
        return OkHeaders.varyFields(response).contains("*");
    }

    static boolean isEndToEnd(String string2) {
        if (!("Connection".equalsIgnoreCase(string2) || "Keep-Alive".equalsIgnoreCase(string2) || "Proxy-Authenticate".equalsIgnoreCase(string2) || "Proxy-Authorization".equalsIgnoreCase(string2) || "TE".equalsIgnoreCase(string2) || "Trailers".equalsIgnoreCase(string2) || "Transfer-Encoding".equalsIgnoreCase(string2) || "Upgrade".equalsIgnoreCase(string2))) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List<Challenge> parseChallenges(Headers headers, String string2) {
        ArrayList<Challenge> arrayList = new ArrayList<Challenge>();
        int n2 = 0;
        while (n2 < headers.size()) {
            if (string2.equalsIgnoreCase(headers.name(n2))) {
                String string3 = headers.value(n2);
                int n3 = 0;
                while (n3 < string3.length()) {
                    int n4 = HeaderParser.skipUntil(string3, n3, " ");
                    String string4 = string3.substring(n3, n4).trim();
                    n3 = HeaderParser.skipWhitespace(string3, n4);
                    if (!string3.regionMatches(true, n3, "realm=\"", 0, "realm=\"".length())) break;
                    n4 = HeaderParser.skipUntil(string3, n3 += "realm=\"".length(), "\"");
                    String string5 = string3.substring(n3, n4);
                    n3 = HeaderParser.skipWhitespace(string3, HeaderParser.skipUntil(string3, n4 + 1, ",") + 1);
                    arrayList.add(new Challenge(string4, string5));
                }
            }
            ++n2;
        }
        return arrayList;
    }

    public static Request processAuthHeader(Authenticator authenticator, Response response, Proxy proxy) throws IOException {
        if (response.code() == 407) {
            return authenticator.authenticateProxy(proxy, response);
        }
        return authenticator.authenticate(proxy, response);
    }

    private static long stringToLong(String string2) {
        if (string2 == null) {
            return -1;
        }
        try {
            long l2 = Long.parseLong(string2);
            return l2;
        }
        catch (NumberFormatException var0_1) {
            return -1;
        }
    }

    public static Map<String, List<String>> toMultimap(Headers headers, String string2) {
        TreeMap treeMap = new TreeMap(FIELD_NAME_COMPARATOR);
        for (int i2 = 0; i2 < headers.size(); ++i2) {
            String string3 = headers.name(i2);
            String string4 = headers.value(i2);
            ArrayList<String> arrayList = new ArrayList<String>();
            List list = (List)treeMap.get(string3);
            if (list != null) {
                arrayList.addAll(list);
            }
            arrayList.add(string4);
            treeMap.put(string3, Collections.unmodifiableList(arrayList));
        }
        if (string2 != null) {
            treeMap.put(null, Collections.unmodifiableList(Collections.singletonList(string2)));
        }
        return Collections.unmodifiableMap(treeMap);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Set<String> varyFields(Response set) {
        Set set2 = Collections.emptySet();
        Headers headers = set.headers();
        int n2 = 0;
        while (n2 < headers.size()) {
            if ("Vary".equalsIgnoreCase(headers.name(n2))) {
                String[] arrstring = headers.value(n2);
                set = set2;
                if (set2.isEmpty()) {
                    set = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                }
                arrstring = arrstring.split(",");
                int n3 = arrstring.length;
                int n4 = 0;
                do {
                    set2 = set;
                    if (n4 >= n3) break;
                    set.add(arrstring[n4].trim());
                    ++n4;
                } while (true);
            }
            ++n2;
        }
        return set2;
    }

    public static Headers varyHeaders(Response object) {
        Set<String> set = OkHeaders.varyFields((Response)object);
        if (set.isEmpty()) {
            return new Headers.Builder().build();
        }
        object = object.networkResponse().request().headers();
        Headers.Builder builder = new Headers.Builder();
        for (int i2 = 0; i2 < object.size(); ++i2) {
            String string2 = object.name(i2);
            if (!set.contains(string2)) continue;
            builder.add(string2, object.value(i2));
        }
        return builder.build();
    }

    public static boolean varyMatches(Response object, Headers headers, Request request) {
        for (String string2 : OkHeaders.varyFields((Response)object)) {
            if (Util.equal(headers.values(string2), request.headers(string2))) continue;
            return false;
        }
        return true;
    }

}

