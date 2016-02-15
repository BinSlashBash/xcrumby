package org.json;

import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import org.json.zip.JSONzip;

public class CDL {
    private static String getValue(JSONTokener x) throws JSONException {
        while (true) {
            char c = x.next();
            if (c != ' ' && c != '\t') {
                break;
            }
        }
        switch (c) {
            case JSONzip.zipEmptyObject /*0*/:
                return null;
            case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
            case C0065R.styleable.TwoWayView_android_minHeight /*39*/:
                char q = c;
                StringBuffer sb = new StringBuffer();
                while (true) {
                    c = x.next();
                    if (c == q) {
                        return sb.toString();
                    }
                    if (c == '\u0000' || c == '\n' || c == '\r') {
                        throw x.syntaxError(new StringBuffer().append("Missing close quote '").append(q).append("'.").toString());
                    }
                    sb.append(c);
                }
                break;
            case C0065R.styleable.TwoWayView_android_onClick /*44*/:
                x.back();
                return UnsupportedUrlFragment.DISPLAY_NAME;
            default:
                x.back();
                return x.nextTo(',');
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONArray rowToJSONArray(org.json.JSONTokener r5) throws org.json.JSONException {
        /*
        r4 = 44;
        r1 = new org.json.JSONArray;
        r1.<init>();
    L_0x0007:
        r2 = getValue(r5);
        r0 = r5.next();
        if (r2 == 0) goto L_0x001f;
    L_0x0011:
        r3 = r1.length();
        if (r3 != 0) goto L_0x0021;
    L_0x0017:
        r3 = r2.length();
        if (r3 != 0) goto L_0x0021;
    L_0x001d:
        if (r0 == r4) goto L_0x0021;
    L_0x001f:
        r1 = 0;
    L_0x0020:
        return r1;
    L_0x0021:
        r1.put(r2);
    L_0x0024:
        if (r0 == r4) goto L_0x0007;
    L_0x0026:
        r3 = 32;
        if (r0 == r3) goto L_0x005c;
    L_0x002a:
        r3 = 10;
        if (r0 == r3) goto L_0x0020;
    L_0x002e:
        r3 = 13;
        if (r0 == r3) goto L_0x0020;
    L_0x0032:
        if (r0 == 0) goto L_0x0020;
    L_0x0034:
        r3 = new java.lang.StringBuffer;
        r3.<init>();
        r4 = "Bad character '";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r4 = "' (";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r4 = ").";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r3 = r5.syntaxError(r3);
        throw r3;
    L_0x005c:
        r0 = r5.next();
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.CDL.rowToJSONArray(org.json.JSONTokener):org.json.JSONArray");
    }

    public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) throws JSONException {
        JSONArray ja = rowToJSONArray(x);
        return ja != null ? ja.toJSONObject(names) : null;
    }

    public static String rowToString(JSONArray ja) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ja.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            Object object = ja.opt(i);
            if (object != null) {
                String string = object.toString();
                if (string.length() <= 0 || (string.indexOf(44) < 0 && string.indexOf(10) < 0 && string.indexOf(13) < 0 && string.indexOf(0) < 0 && string.charAt(0) != '\"')) {
                    sb.append(string);
                } else {
                    sb.append('\"');
                    int length = string.length();
                    for (int j = 0; j < length; j++) {
                        char c = string.charAt(j);
                        if (c >= ' ' && c != '\"') {
                            sb.append(c);
                        }
                    }
                    sb.append('\"');
                }
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return toJSONArray(new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONTokener x) throws JSONException {
        return toJSONArray(rowToJSONArray(x), x);
    }

    public static JSONArray toJSONArray(JSONArray names, String string) throws JSONException {
        return toJSONArray(names, new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONArray names, JSONTokener x) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        JSONArray ja = new JSONArray();
        while (true) {
            Object jo = rowToJSONObject(names, x);
            if (jo == null) {
                break;
            }
            ja.put(jo);
        }
        if (ja.length() == 0) {
            return null;
        }
        return ja;
    }

    public static String toString(JSONArray ja) throws JSONException {
        JSONObject jo = ja.optJSONObject(0);
        if (jo != null) {
            JSONArray names = jo.names();
            if (names != null) {
                return new StringBuffer().append(rowToString(names)).append(toString(names, ja)).toString();
            }
        }
        return null;
    }

    public static String toString(JSONArray names, JSONArray ja) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(i);
            if (jo != null) {
                sb.append(rowToString(jo.toJSONArray(names)));
            }
        }
        return sb.toString();
    }
}
