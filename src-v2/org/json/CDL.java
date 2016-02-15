/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CDL {
    private static String getValue(JSONTokener jSONTokener) throws JSONException {
        char c2;
        while ((c2 = jSONTokener.next()) == ' ' || c2 == '\t') {
        }
        switch (c2) {
            default: {
                jSONTokener.back();
                return jSONTokener.nextTo(',');
            }
            case '\u0000': {
                return null;
            }
            case '\"': 
            case '\'': {
                StringBuffer stringBuffer = new StringBuffer();
                do {
                    char c3;
                    if ((c3 = jSONTokener.next()) == c2) {
                        return stringBuffer.toString();
                    }
                    if (c3 == '\u0000' || c3 == '\n' || c3 == '\r') {
                        throw jSONTokener.syntaxError(new StringBuffer().append("Missing close quote '").append(c2).append("'.").toString());
                    }
                    stringBuffer.append(c3);
                } while (true);
            }
            case ',': 
        }
        jSONTokener.back();
        return "";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JSONArray rowToJSONArray(JSONTokener jSONTokener) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        block0 : do {
            Object object = CDL.getValue(jSONTokener);
            char c2 = jSONTokener.next();
            if (object == null) return null;
            if (jSONArray.length() == 0 && object.length() == 0 && c2 != ',') {
                return null;
            }
            jSONArray.put(object);
            do {
                if (c2 == ',') continue block0;
                if (c2 != ' ') {
                    object = jSONArray;
                    if (c2 == '\n') return object;
                    object = jSONArray;
                    if (c2 == '\r') return object;
                    object = jSONArray;
                    if (c2 == '\u0000') return object;
                    throw jSONTokener.syntaxError(new StringBuffer().append("Bad character '").append(c2).append("' (").append((int)c2).append(").").toString());
                }
                c2 = jSONTokener.next();
            } while (true);
            break;
        } while (true);
    }

    public static JSONObject rowToJSONObject(JSONArray jSONArray, JSONTokener object) throws JSONException {
        if ((object = CDL.rowToJSONArray((JSONTokener)object)) != null) {
            return object.toJSONObject(jSONArray);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String rowToString(JSONArray jSONArray) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        do {
            Object object;
            if (n2 >= jSONArray.length()) {
                stringBuffer.append('\n');
                return stringBuffer.toString();
            }
            if (n2 > 0) {
                stringBuffer.append(',');
            }
            if ((object = jSONArray.opt(n2)) != null) {
                if ((object = object.toString()).length() > 0 && (object.indexOf(44) >= 0 || object.indexOf(10) >= 0 || object.indexOf(13) >= 0 || object.indexOf(0) >= 0 || object.charAt(0) == '\"')) {
                    stringBuffer.append('\"');
                    int n3 = object.length();
                    for (int i2 = 0; i2 < n3; ++i2) {
                        char c2 = object.charAt(i2);
                        if (c2 < ' ' || c2 == '\"') continue;
                        stringBuffer.append(c2);
                    }
                    stringBuffer.append('\"');
                } else {
                    stringBuffer.append((String)object);
                }
            }
            ++n2;
        } while (true);
    }

    public static JSONArray toJSONArray(String string2) throws JSONException {
        return CDL.toJSONArray(new JSONTokener(string2));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, String string2) throws JSONException {
        return CDL.toJSONArray(jSONArray, new JSONTokener(string2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JSONArray toJSONArray(JSONArray jSONArray, JSONTokener jSONTokener) throws JSONException {
        if (jSONArray == null) return null;
        if (jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        do {
            JSONObject jSONObject;
            if ((jSONObject = CDL.rowToJSONObject(jSONArray, jSONTokener)) == null) {
                jSONArray = jSONArray2;
                if (jSONArray2.length() != 0) return jSONArray;
                return null;
            }
            jSONArray2.put(jSONObject);
        } while (true);
    }

    public static JSONArray toJSONArray(JSONTokener jSONTokener) throws JSONException {
        return CDL.toJSONArray(CDL.rowToJSONArray(jSONTokener), jSONTokener);
    }

    public static String toString(JSONArray jSONArray) throws JSONException {
        Object object = jSONArray.optJSONObject(0);
        if (object != null && (object = object.names()) != null) {
            return new StringBuffer().append(CDL.rowToString((JSONArray)object)).append(CDL.toString((JSONArray)object, jSONArray)).toString();
        }
        return null;
    }

    public static String toString(JSONArray jSONArray, JSONArray jSONArray2) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < jSONArray2.length(); ++i2) {
            JSONObject jSONObject = jSONArray2.optJSONObject(i2);
            if (jSONObject == null) continue;
            stringBuffer.append(CDL.rowToString(jSONObject.toJSONArray(jSONArray)));
        }
        return stringBuffer.toString();
    }
}

