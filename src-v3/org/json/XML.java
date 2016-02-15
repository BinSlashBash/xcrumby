package org.json;

import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.util.Iterator;

public class XML {
    public static final Character AMP;
    public static final Character APOS;
    public static final Character BANG;
    public static final Character EQ;
    public static final Character GT;
    public static final Character LT;
    public static final Character QUEST;
    public static final Character QUOT;
    public static final Character SLASH;

    static {
        AMP = new Character('&');
        APOS = new Character('\'');
        BANG = new Character('!');
        EQ = new Character('=');
        GT = new Character('>');
        LT = new Character('<');
        QUEST = new Character('?');
        QUOT = new Character('\"');
        SLASH = new Character('/');
    }

    public static String escape(String string) {
        StringBuffer sb = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            switch (c) {
                case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                    sb.append("&quot;");
                    break;
                case C0065R.styleable.TwoWayView_android_minWidth /*38*/:
                    sb.append("&amp;");
                    break;
                case C0065R.styleable.TwoWayView_android_minHeight /*39*/:
                    sb.append("&apos;");
                    break;
                case C0065R.styleable.TwoWayView_android_rotationY /*60*/:
                    sb.append("&lt;");
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusForward /*62*/:
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    public static void noSpace(String string) throws JSONException {
        int length = string.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                throw new JSONException(new StringBuffer().append("'").append(string).append("' contains a space character.").toString());
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean parse(org.json.XMLTokener r11, org.json.JSONObject r12, java.lang.String r13) throws org.json.JSONException {
        /*
        r10 = 91;
        r9 = 45;
        r7 = 1;
        r6 = 0;
        r2 = 0;
        r5 = r11.nextToken();
        r8 = BANG;
        if (r5 != r8) goto L_0x006d;
    L_0x000f:
        r0 = r11.next();
        if (r0 != r9) goto L_0x0032;
    L_0x0015:
        r7 = r11.next();
        if (r7 != r9) goto L_0x0021;
    L_0x001b:
        r7 = "-->";
        r11.skipPast(r7);
    L_0x0020:
        return r6;
    L_0x0021:
        r11.back();
    L_0x0024:
        r1 = 1;
    L_0x0025:
        r5 = r11.nextMeta();
        if (r5 != 0) goto L_0x005d;
    L_0x002b:
        r6 = "Missing '>' after '<!'.";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x0032:
        if (r0 != r10) goto L_0x0024;
    L_0x0034:
        r5 = r11.nextToken();
        r7 = "CDATA";
        r7 = r7.equals(r5);
        if (r7 == 0) goto L_0x0056;
    L_0x0040:
        r7 = r11.next();
        if (r7 != r10) goto L_0x0056;
    L_0x0046:
        r3 = r11.nextCDATA();
        r7 = r3.length();
        if (r7 <= 0) goto L_0x0020;
    L_0x0050:
        r7 = "content";
        r12.accumulate(r7, r3);
        goto L_0x0020;
    L_0x0056:
        r6 = "Expected 'CDATA['";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x005d:
        r7 = LT;
        if (r5 != r7) goto L_0x0066;
    L_0x0061:
        r1 = r1 + 1;
    L_0x0063:
        if (r1 > 0) goto L_0x0025;
    L_0x0065:
        goto L_0x0020;
    L_0x0066:
        r7 = GT;
        if (r5 != r7) goto L_0x0063;
    L_0x006a:
        r1 = r1 + -1;
        goto L_0x0063;
    L_0x006d:
        r8 = QUEST;
        if (r5 != r8) goto L_0x0077;
    L_0x0071:
        r7 = "?>";
        r11.skipPast(r7);
        goto L_0x0020;
    L_0x0077:
        r8 = SLASH;
        if (r5 != r8) goto L_0x00d3;
    L_0x007b:
        r5 = r11.nextToken();
        if (r13 != 0) goto L_0x0099;
    L_0x0081:
        r6 = new java.lang.StringBuffer;
        r6.<init>();
        r7 = "Mismatched close tag ";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r6 = r6.toString();
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x0099:
        r6 = r5.equals(r13);
        if (r6 != 0) goto L_0x00c1;
    L_0x009f:
        r6 = new java.lang.StringBuffer;
        r6.<init>();
        r7 = "Mismatched ";
        r6 = r6.append(r7);
        r6 = r6.append(r13);
        r7 = " and ";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r6 = r6.toString();
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x00c1:
        r6 = r11.nextToken();
        r8 = GT;
        if (r6 == r8) goto L_0x00d0;
    L_0x00c9:
        r6 = "Misshaped close tag";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x00d0:
        r6 = r7;
        goto L_0x0020;
    L_0x00d3:
        r8 = r5 instanceof java.lang.Character;
        if (r8 == 0) goto L_0x00de;
    L_0x00d7:
        r6 = "Misshaped tag";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x00de:
        r4 = r5;
        r4 = (java.lang.String) r4;
        r5 = 0;
        r2 = new org.json.JSONObject;
        r2.<init>();
    L_0x00e7:
        if (r5 != 0) goto L_0x01b8;
    L_0x00e9:
        r5 = r11.nextToken();
        r3 = r5;
    L_0x00ee:
        r8 = r3 instanceof java.lang.String;
        if (r8 == 0) goto L_0x011c;
    L_0x00f2:
        r3 = (java.lang.String) r3;
        r5 = r11.nextToken();
        r8 = EQ;
        if (r5 != r8) goto L_0x0116;
    L_0x00fc:
        r5 = r11.nextToken();
        r8 = r5 instanceof java.lang.String;
        if (r8 != 0) goto L_0x010b;
    L_0x0104:
        r6 = "Missing value";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x010b:
        r5 = (java.lang.String) r5;
        r8 = stringToValue(r5);
        r2.accumulate(r3, r8);
        r5 = 0;
        goto L_0x00e7;
    L_0x0116:
        r8 = "";
        r2.accumulate(r3, r8);
        goto L_0x00e7;
    L_0x011c:
        r8 = SLASH;
        if (r3 != r8) goto L_0x0141;
    L_0x0120:
        r7 = r11.nextToken();
        r8 = GT;
        if (r7 == r8) goto L_0x012f;
    L_0x0128:
        r6 = "Misshaped tag";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x012f:
        r7 = r2.length();
        if (r7 <= 0) goto L_0x013a;
    L_0x0135:
        r12.accumulate(r4, r2);
        goto L_0x0020;
    L_0x013a:
        r7 = "";
        r12.accumulate(r4, r7);
        goto L_0x0020;
    L_0x0141:
        r8 = GT;
        if (r3 != r8) goto L_0x01b1;
    L_0x0145:
        r5 = r11.nextContent();
        if (r5 != 0) goto L_0x0165;
    L_0x014b:
        if (r4 == 0) goto L_0x0020;
    L_0x014d:
        r6 = new java.lang.StringBuffer;
        r6.<init>();
        r7 = "Unclosed tag ";
        r6 = r6.append(r7);
        r6 = r6.append(r4);
        r6 = r6.toString();
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x0165:
        r8 = r5 instanceof java.lang.String;
        if (r8 == 0) goto L_0x017c;
    L_0x0169:
        r3 = r5;
        r3 = (java.lang.String) r3;
        r8 = r3.length();
        if (r8 <= 0) goto L_0x0145;
    L_0x0172:
        r8 = "content";
        r9 = stringToValue(r3);
        r2.accumulate(r8, r9);
        goto L_0x0145;
    L_0x017c:
        r8 = LT;
        if (r5 != r8) goto L_0x0145;
    L_0x0180:
        r8 = parse(r11, r2, r4);
        if (r8 == 0) goto L_0x0145;
    L_0x0186:
        r8 = r2.length();
        if (r8 != 0) goto L_0x0193;
    L_0x018c:
        r7 = "";
        r12.accumulate(r4, r7);
        goto L_0x0020;
    L_0x0193:
        r8 = r2.length();
        if (r8 != r7) goto L_0x01ac;
    L_0x0199:
        r7 = "content";
        r7 = r2.opt(r7);
        if (r7 == 0) goto L_0x01ac;
    L_0x01a1:
        r7 = "content";
        r7 = r2.opt(r7);
        r12.accumulate(r4, r7);
        goto L_0x0020;
    L_0x01ac:
        r12.accumulate(r4, r2);
        goto L_0x0020;
    L_0x01b1:
        r6 = "Misshaped tag";
        r6 = r11.syntaxError(r6);
        throw r6;
    L_0x01b8:
        r3 = r5;
        goto L_0x00ee;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XML.parse(org.json.XMLTokener, org.json.JSONObject, java.lang.String):boolean");
    }

    public static Object stringToValue(String string) {
        Object value;
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(string)) {
            return JSONObject.NULL;
        }
        try {
            char initial = string.charAt(0);
            if (initial == '-' || (initial >= '0' && initial <= '9')) {
                value = new Long(string);
                if (value.toString().equals(string)) {
                    return value;
                }
            }
        } catch (Exception e) {
            try {
                value = new Double(string);
                if (value.toString().equals(string)) {
                    return value;
                }
            } catch (Exception e2) {
            }
        }
        return string;
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(string);
        while (x.more() && x.skipPast("<")) {
            parse(x, jo, null);
        }
        return jo;
    }

    public static String toString(Object object) throws JSONException {
        return toString(object, null);
    }

    public static String toString(Object object, String tagName) throws JSONException {
        StringBuffer sb = new StringBuffer();
        JSONArray ja;
        int length;
        int i;
        if (object instanceof JSONObject) {
            if (tagName != null) {
                sb.append('<');
                sb.append(tagName);
                sb.append('>');
            }
            JSONObject jo = (JSONObject) object;
            Iterator keys = jo.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                String value = jo.opt(key);
                if (value == null) {
                    value = UnsupportedUrlFragment.DISPLAY_NAME;
                }
                if (value instanceof String) {
                    String string = value;
                }
                if ("content".equals(key)) {
                    if (value instanceof JSONArray) {
                        ja = (JSONArray) value;
                        length = ja.length();
                        for (i = 0; i < length; i++) {
                            if (i > 0) {
                                sb.append('\n');
                            }
                            sb.append(escape(ja.get(i).toString()));
                        }
                    } else {
                        sb.append(escape(value.toString()));
                    }
                } else if (value instanceof JSONArray) {
                    ja = (JSONArray) value;
                    length = ja.length();
                    for (i = 0; i < length; i++) {
                        Object value2 = ja.get(i);
                        if (value2 instanceof JSONArray) {
                            sb.append('<');
                            sb.append(key);
                            sb.append('>');
                            sb.append(toString(value2));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        } else {
                            sb.append(toString(value2, key));
                        }
                    }
                } else if (UnsupportedUrlFragment.DISPLAY_NAME.equals(value)) {
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");
                } else {
                    sb.append(toString(value, key));
                }
            }
            if (tagName != null) {
                sb.append("</");
                sb.append(tagName);
                sb.append('>');
            }
            return sb.toString();
        }
        if (object.getClass().isArray()) {
            object = new JSONArray(object);
        }
        if (object instanceof JSONArray) {
            ja = (JSONArray) object;
            length = ja.length();
            for (i = 0; i < length; i++) {
                String str;
                Object opt = ja.opt(i);
                if (tagName == null) {
                    str = "array";
                } else {
                    str = tagName;
                }
                sb.append(toString(opt, str));
            }
            return sb.toString();
        }
        string = object == null ? "null" : escape(object.toString());
        if (tagName == null) {
            return new StringBuffer().append("\"").append(string).append("\"").toString();
        }
        return string.length() == 0 ? new StringBuffer().append("<").append(tagName).append("/>").toString() : new StringBuffer().append("<").append(tagName).append(">").append(string).append("</").append(tagName).append(">").toString();
    }
}
