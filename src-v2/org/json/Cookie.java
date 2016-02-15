/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Cookie {
    /*
     * Enabled aggressive block sorting
     */
    public static String escape(String string2) {
        string2 = string2.trim();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            char c2 = string2.charAt(n3);
            if (c2 < ' ' || c2 == '+' || c2 == '%' || c2 == '=' || c2 == ';') {
                stringBuffer.append('%');
                stringBuffer.append(Character.forDigit((char)(c2 >>> 4 & 15), 16));
                stringBuffer.append(Character.forDigit((char)(c2 & 15), 16));
            } else {
                stringBuffer.append(c2);
            }
            ++n3;
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JSONObject toJSONObject(String object) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener((String)object);
        jSONObject.put("name", jSONTokener.nextTo('='));
        jSONTokener.next('=');
        jSONObject.put("value", jSONTokener.nextTo(';'));
        jSONTokener.next();
        while (jSONTokener.more()) {
            String string2 = Cookie.unescape(jSONTokener.nextTo("=;"));
            if (jSONTokener.next() != '=') {
                if (!string2.equals("secure")) {
                    throw jSONTokener.syntaxError("Missing '=' in cookie parameter.");
                }
                object = Boolean.TRUE;
            } else {
                object = Cookie.unescape(jSONTokener.nextTo(';'));
                jSONTokener.next();
            }
            jSONObject.put(string2, object);
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Cookie.escape(jSONObject.getString("name")));
        stringBuffer.append("=");
        stringBuffer.append(Cookie.escape(jSONObject.getString("value")));
        if (jSONObject.has("expires")) {
            stringBuffer.append(";expires=");
            stringBuffer.append(jSONObject.getString("expires"));
        }
        if (jSONObject.has("domain")) {
            stringBuffer.append(";domain=");
            stringBuffer.append(Cookie.escape(jSONObject.getString("domain")));
        }
        if (jSONObject.has("path")) {
            stringBuffer.append(";path=");
            stringBuffer.append(Cookie.escape(jSONObject.getString("path")));
        }
        if (jSONObject.optBoolean("secure")) {
            stringBuffer.append(";secure");
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String unescape(String string2) {
        int n2 = string2.length();
        StringBuffer stringBuffer = new StringBuffer();
        int n3 = 0;
        while (n3 < n2) {
            char c2;
            int n4;
            char c3 = string2.charAt(n3);
            if (c3 == '+') {
                c2 = ' ';
                n4 = n3;
            } else {
                c2 = c3;
                n4 = n3;
                if (c3 == '%') {
                    c2 = c3;
                    n4 = n3;
                    if (n3 + 2 < n2) {
                        int n5 = JSONTokener.dehexchar(string2.charAt(n3 + 1));
                        int n6 = JSONTokener.dehexchar(string2.charAt(n3 + 2));
                        c2 = c3;
                        n4 = n3;
                        if (n5 >= 0) {
                            c2 = c3;
                            n4 = n3;
                            if (n6 >= 0) {
                                c2 = (char)(n5 * 16 + n6);
                                n4 = n3 + 2;
                            }
                        }
                    }
                }
            }
            stringBuffer.append(c2);
            n3 = n4 + 1;
        }
        return stringBuffer.toString();
    }
}

