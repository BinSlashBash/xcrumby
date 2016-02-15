/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Iterator;
import org.json.HTTPTokener;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTP {
    public static final String CRLF = "\r\n";

    /*
     * Enabled aggressive block sorting
     */
    public static JSONObject toJSONObject(String object) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String string2 = (object = new HTTPTokener((String)object)).nextToken();
        if (string2.toUpperCase().startsWith("HTTP")) {
            jSONObject.put("HTTP-Version", string2);
            jSONObject.put("Status-Code", object.nextToken());
            jSONObject.put("Reason-Phrase", object.nextTo('\u0000'));
            object.next();
        } else {
            jSONObject.put("Method", string2);
            jSONObject.put("Request-URI", object.nextToken());
            jSONObject.put("HTTP-Version", object.nextToken());
        }
        while (object.more()) {
            string2 = object.nextTo(':');
            object.next(':');
            jSONObject.put(string2, object.nextTo('\u0000'));
            object.next();
        }
        return jSONObject;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String toString(JSONObject jSONObject) throws JSONException {
        Iterator iterator = jSONObject.keys();
        StringBuffer stringBuffer = new StringBuffer();
        if (jSONObject.has("Status-Code") && jSONObject.has("Reason-Phrase")) {
            stringBuffer.append(jSONObject.getString("HTTP-Version"));
            stringBuffer.append(' ');
            stringBuffer.append(jSONObject.getString("Status-Code"));
            stringBuffer.append(' ');
            stringBuffer.append(jSONObject.getString("Reason-Phrase"));
        } else {
            if (!jSONObject.has("Method")) throw new JSONException("Not enough material for an HTTP header.");
            if (!jSONObject.has("Request-URI")) throw new JSONException("Not enough material for an HTTP header.");
            stringBuffer.append(jSONObject.getString("Method"));
            stringBuffer.append(' ');
            stringBuffer.append('\"');
            stringBuffer.append(jSONObject.getString("Request-URI"));
            stringBuffer.append('\"');
            stringBuffer.append(' ');
            stringBuffer.append(jSONObject.getString("HTTP-Version"));
        }
        stringBuffer.append("\r\n");
        do {
            if (!iterator.hasNext()) {
                stringBuffer.append("\r\n");
                return stringBuffer.toString();
            }
            String string2 = iterator.next().toString();
            if ("HTTP-Version".equals(string2) || "Status-Code".equals(string2) || "Reason-Phrase".equals(string2) || "Method".equals(string2) || "Request-URI".equals(string2) || jSONObject.isNull(string2)) continue;
            stringBuffer.append(string2);
            stringBuffer.append(": ");
            stringBuffer.append(jSONObject.getString(string2));
            stringBuffer.append("\r\n");
        } while (true);
    }
}

