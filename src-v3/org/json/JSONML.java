package org.json;

import java.util.Iterator;

public class JSONML {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object parse(org.json.XMLTokener r12, boolean r13, org.json.JSONArray r14) throws org.json.JSONException {
        /*
        r11 = 91;
        r10 = 45;
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
    L_0x0008:
        r8 = r12.more();
        if (r8 != 0) goto L_0x0015;
    L_0x000e:
        r8 = "Bad XML";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x0015:
        r7 = r12.nextContent();
        r8 = org.json.XML.LT;
        if (r7 != r8) goto L_0x01e9;
    L_0x001d:
        r7 = r12.nextToken();
        r8 = r7 instanceof java.lang.Character;
        if (r8 == 0) goto L_0x00d0;
    L_0x0025:
        r8 = org.json.XML.SLASH;
        if (r7 != r8) goto L_0x005f;
    L_0x0029:
        r7 = r12.nextToken();
        r8 = r7 instanceof java.lang.String;
        if (r8 != 0) goto L_0x0050;
    L_0x0031:
        r8 = new org.json.JSONException;
        r9 = new java.lang.StringBuffer;
        r9.<init>();
        r10 = "Expected a closing name instead of '";
        r9 = r9.append(r10);
        r9 = r9.append(r7);
        r10 = "'.";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.<init>(r9);
        throw r8;
    L_0x0050:
        r8 = r12.nextToken();
        r9 = org.json.XML.GT;
        if (r8 == r9) goto L_0x018f;
    L_0x0058:
        r8 = "Misshaped close tag";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x005f:
        r8 = org.json.XML.BANG;
        if (r7 != r8) goto L_0x00be;
    L_0x0063:
        r1 = r12.next();
        if (r1 != r10) goto L_0x0079;
    L_0x0069:
        r8 = r12.next();
        if (r8 != r10) goto L_0x0075;
    L_0x006f:
        r8 = "-->";
        r12.skipPast(r8);
        goto L_0x0008;
    L_0x0075:
        r12.back();
        goto L_0x0008;
    L_0x0079:
        if (r1 != r11) goto L_0x009f;
    L_0x007b:
        r7 = r12.nextToken();
        r8 = "CDATA";
        r8 = r7.equals(r8);
        if (r8 == 0) goto L_0x0098;
    L_0x0087:
        r8 = r12.next();
        if (r8 != r11) goto L_0x0098;
    L_0x008d:
        if (r14 == 0) goto L_0x0008;
    L_0x008f:
        r8 = r12.nextCDATA();
        r14.put(r8);
        goto L_0x0008;
    L_0x0098:
        r8 = "Expected 'CDATA['";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x009f:
        r3 = 1;
    L_0x00a0:
        r7 = r12.nextMeta();
        if (r7 != 0) goto L_0x00ad;
    L_0x00a6:
        r8 = "Missing '>' after '<!'.";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x00ad:
        r8 = org.json.XML.LT;
        if (r7 != r8) goto L_0x00b7;
    L_0x00b1:
        r3 = r3 + 1;
    L_0x00b3:
        if (r3 > 0) goto L_0x00a0;
    L_0x00b5:
        goto L_0x0008;
    L_0x00b7:
        r8 = org.json.XML.GT;
        if (r7 != r8) goto L_0x00b3;
    L_0x00bb:
        r3 = r3 + -1;
        goto L_0x00b3;
    L_0x00be:
        r8 = org.json.XML.QUEST;
        if (r7 != r8) goto L_0x00c9;
    L_0x00c2:
        r8 = "?>";
        r12.skipPast(r8);
        goto L_0x0008;
    L_0x00c9:
        r8 = "Misshaped tag";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x00d0:
        r8 = r7 instanceof java.lang.String;
        if (r8 != 0) goto L_0x00f2;
    L_0x00d4:
        r8 = new java.lang.StringBuffer;
        r8.<init>();
        r9 = "Bad tagName '";
        r8 = r8.append(r9);
        r8 = r8.append(r7);
        r9 = "'.";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x00f2:
        r6 = r7;
        r6 = (java.lang.String) r6;
        r4 = new org.json.JSONArray;
        r4.<init>();
        r5 = new org.json.JSONObject;
        r5.<init>();
        if (r13 == 0) goto L_0x011a;
    L_0x0101:
        r4.put(r6);
        if (r14 == 0) goto L_0x0109;
    L_0x0106:
        r14.put(r4);
    L_0x0109:
        r7 = 0;
    L_0x010a:
        if (r7 != 0) goto L_0x01fa;
    L_0x010c:
        r7 = r12.nextToken();
        r0 = r7;
    L_0x0111:
        if (r0 != 0) goto L_0x0125;
    L_0x0113:
        r8 = "Misshaped tag";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x011a:
        r8 = "tagName";
        r5.put(r8, r6);
        if (r14 == 0) goto L_0x0109;
    L_0x0121:
        r14.put(r5);
        goto L_0x0109;
    L_0x0125:
        r8 = r0 instanceof java.lang.String;
        if (r8 != 0) goto L_0x0147;
    L_0x0129:
        if (r13 == 0) goto L_0x0134;
    L_0x012b:
        r8 = r5.length();
        if (r8 <= 0) goto L_0x0134;
    L_0x0131:
        r4.put(r5);
    L_0x0134:
        r8 = org.json.XML.SLASH;
        if (r0 != r8) goto L_0x0192;
    L_0x0138:
        r8 = r12.nextToken();
        r9 = org.json.XML.GT;
        if (r8 == r9) goto L_0x018a;
    L_0x0140:
        r8 = "Misshaped tag";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x0147:
        r0 = (java.lang.String) r0;
        if (r13 != 0) goto L_0x0162;
    L_0x014b:
        r8 = "tagName";
        r8 = r8.equals(r0);
        if (r8 != 0) goto L_0x015b;
    L_0x0153:
        r8 = "childNode";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x0162;
    L_0x015b:
        r8 = "Reserved attribute.";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x0162:
        r7 = r12.nextToken();
        r8 = org.json.XML.EQ;
        if (r7 != r8) goto L_0x0184;
    L_0x016a:
        r7 = r12.nextToken();
        r8 = r7 instanceof java.lang.String;
        if (r8 != 0) goto L_0x0179;
    L_0x0172:
        r8 = "Missing value";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x0179:
        r7 = (java.lang.String) r7;
        r8 = org.json.XML.stringToValue(r7);
        r5.accumulate(r0, r8);
        r7 = 0;
        goto L_0x010a;
    L_0x0184:
        r8 = "";
        r5.accumulate(r0, r8);
        goto L_0x010a;
    L_0x018a:
        if (r14 != 0) goto L_0x0008;
    L_0x018c:
        if (r13 == 0) goto L_0x0190;
    L_0x018e:
        r7 = r4;
    L_0x018f:
        return r7;
    L_0x0190:
        r7 = r5;
        goto L_0x018f;
    L_0x0192:
        r8 = org.json.XML.GT;
        if (r0 == r8) goto L_0x019d;
    L_0x0196:
        r8 = "Misshaped tag";
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x019d:
        r2 = parse(r12, r13, r4);
        r2 = (java.lang.String) r2;
        if (r2 == 0) goto L_0x0008;
    L_0x01a5:
        r8 = r2.equals(r6);
        if (r8 != 0) goto L_0x01d3;
    L_0x01ab:
        r8 = new java.lang.StringBuffer;
        r8.<init>();
        r9 = "Mismatched '";
        r8 = r8.append(r9);
        r8 = r8.append(r6);
        r9 = "' and '";
        r8 = r8.append(r9);
        r8 = r8.append(r2);
        r9 = "'";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r8 = r12.syntaxError(r8);
        throw r8;
    L_0x01d3:
        r6 = 0;
        if (r13 != 0) goto L_0x01e1;
    L_0x01d6:
        r8 = r4.length();
        if (r8 <= 0) goto L_0x01e1;
    L_0x01dc:
        r8 = "childNodes";
        r5.put(r8, r4);
    L_0x01e1:
        if (r14 != 0) goto L_0x0008;
    L_0x01e3:
        if (r13 == 0) goto L_0x01e7;
    L_0x01e5:
        r7 = r4;
        goto L_0x018f;
    L_0x01e7:
        r7 = r5;
        goto L_0x018f;
    L_0x01e9:
        if (r14 == 0) goto L_0x0008;
    L_0x01eb:
        r8 = r7 instanceof java.lang.String;
        if (r8 == 0) goto L_0x01f5;
    L_0x01ef:
        r7 = (java.lang.String) r7;
        r7 = org.json.XML.stringToValue(r7);
    L_0x01f5:
        r14.put(r7);
        goto L_0x0008;
    L_0x01fa:
        r0 = r7;
        goto L_0x0111;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONML.parse(org.json.XMLTokener, boolean, org.json.JSONArray):java.lang.Object");
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return toJSONArray(new XMLTokener(string));
    }

    public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
        return (JSONArray) parse(x, true, null);
    }

    public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
        return (JSONObject) parse(x, false, null);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return toJSONObject(new XMLTokener(string));
    }

    public static String toString(JSONArray ja) throws JSONException {
        int i;
        StringBuffer sb = new StringBuffer();
        String tagName = ja.getString(0);
        XML.noSpace(tagName);
        tagName = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName);
        JSONObject object = ja.opt(1);
        if (object instanceof JSONObject) {
            i = 2;
            JSONObject jo = object;
            Iterator keys = jo.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                XML.noSpace(key);
                String value = jo.optString(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value));
                    sb.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = ja.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                Object object2 = ja.get(i);
                i++;
                if (object2 != null) {
                    if (object2 instanceof String) {
                        sb.append(XML.escape(object2.toString()));
                        continue;
                    } else if (object2 instanceof JSONObject) {
                        sb.append(toString((JSONObject) object2));
                        continue;
                    } else if (object2 instanceof JSONArray) {
                        sb.append(toString((JSONArray) object2));
                        continue;
                    } else {
                        continue;
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(tagName);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String toString(JSONObject jo) throws JSONException {
        StringBuffer sb = new StringBuffer();
        String tagName = jo.optString("tagName");
        if (tagName == null) {
            return XML.escape(jo.toString());
        }
        XML.noSpace(tagName);
        tagName = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName);
        Iterator keys = jo.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            if (!("tagName".equals(key) || "childNodes".equals(key))) {
                XML.noSpace(key);
                String value = jo.optString(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value));
                    sb.append('\"');
                }
            }
        }
        JSONArray ja = jo.optJSONArray("childNodes");
        if (ja == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            int length = ja.length();
            for (int i = 0; i < length; i++) {
                Object object = ja.get(i);
                if (object != null) {
                    if (object instanceof String) {
                        sb.append(XML.escape(object.toString()));
                    } else if (object instanceof JSONObject) {
                        sb.append(toString((JSONObject) object));
                    } else if (object instanceof JSONArray) {
                        sb.append(toString((JSONArray) object));
                    } else {
                        sb.append(object.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(tagName);
            sb.append('>');
        }
        return sb.toString();
    }
}
