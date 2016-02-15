/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XMLTokener;

public class XML {
    public static final Character AMP = new Character('&');
    public static final Character APOS = new Character('\'');
    public static final Character BANG = new Character('!');
    public static final Character EQ = new Character('=');
    public static final Character GT = new Character('>');
    public static final Character LT = new Character('<');
    public static final Character QUEST = new Character('?');
    public static final Character QUOT = new Character('\"');
    public static final Character SLASH = new Character('/');

    /*
     * Enabled aggressive block sorting
     */
    public static String escape(String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        int n3 = string2.length();
        while (n2 < n3) {
            char c2 = string2.charAt(n2);
            switch (c2) {
                default: {
                    stringBuffer.append(c2);
                    break;
                }
                case '&': {
                    stringBuffer.append("&amp;");
                    break;
                }
                case '<': {
                    stringBuffer.append("&lt;");
                    break;
                }
                case '>': {
                    stringBuffer.append("&gt;");
                    break;
                }
                case '\"': {
                    stringBuffer.append("&quot;");
                    break;
                }
                case '\'': {
                    stringBuffer.append("&apos;");
                }
            }
            ++n2;
        }
        return stringBuffer.toString();
    }

    public static void noSpace(String string2) throws JSONException {
        int n2 = string2.length();
        if (n2 == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!Character.isWhitespace(string2.charAt(i2))) continue;
            throw new JSONException(new StringBuffer().append("'").append(string2).append("' contains a space character.").toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean parse(XMLTokener object, JSONObject object2, String object3) throws JSONException {
        Object object4 = object.nextToken();
        if (object4 != BANG) {
            if (object4 == QUEST) {
                object.skipPast("?>");
                return false;
            }
            if (object4 == SLASH) {
                object2 = object.nextToken();
                if (object3 == null) {
                    throw object.syntaxError(new StringBuffer().append("Mismatched close tag ").append(object2).toString());
                }
                if (!object2.equals(object3)) {
                    throw object.syntaxError(new StringBuffer().append("Mismatched ").append((String)object3).append(" and ").append(object2).toString());
                }
                if (object.nextToken() == GT) return true;
                throw object.syntaxError("Misshaped close tag");
            }
            if (object4 instanceof Character) {
                throw object.syntaxError("Misshaped tag");
            }
        } else {
            int n2 = object.next();
            if (n2 == 45) {
                if (object.next() == '-') {
                    object.skipPast("-->");
                    return false;
                }
                object.back();
            } else if (n2 == 91) {
                if (!"CDATA".equals(object.nextToken())) throw object.syntaxError("Expected 'CDATA['");
                if (object.next() != '[') throw object.syntaxError("Expected 'CDATA['");
                if ((object = object.nextCDATA()).length() <= 0) return false;
                object2.accumulate("content", object);
                return false;
            }
            int n3 = 1;
            do {
                if ((object2 = object.nextMeta()) == null) {
                    throw object.syntaxError("Missing '>' after '<!'.");
                }
                if (object2 == LT) {
                    n2 = n3 + '\u0001';
                } else {
                    n2 = n3;
                    if (object2 == GT) {
                        n2 = n3 - 1;
                    }
                }
                n3 = n2;
            } while (n2 > 0);
            return false;
        }
        object4 = (String)object4;
        object3 = null;
        JSONObject jSONObject = new JSONObject();
        do {
            if (object3 == null) {
                object3 = object.nextToken();
            }
            if (!(object3 instanceof String)) break;
            String string2 = (String)object3;
            object3 = object.nextToken();
            if (object3 == EQ) {
                object3 = object.nextToken();
                if (!(object3 instanceof String)) {
                    throw object.syntaxError("Missing value");
                }
                jSONObject.accumulate(string2, XML.stringToValue((String)object3));
                object3 = null;
                continue;
            }
            jSONObject.accumulate(string2, "");
        } while (true);
        if (object3 == SLASH) {
            if (object.nextToken() != GT) {
                throw object.syntaxError("Misshaped tag");
            }
            if (jSONObject.length() > 0) {
                object2.accumulate((String)object4, jSONObject);
                return false;
            }
            object2.accumulate((String)object4, "");
            return false;
        }
        if (object3 != GT) throw object.syntaxError("Misshaped tag");
        do {
            if ((object3 = object.nextContent()) == null) {
                if (object4 == null) return false;
                throw object.syntaxError(new StringBuffer().append("Unclosed tag ").append((String)object4).toString());
            }
            if (object3 instanceof String) {
                if ((object3 = (String)object3).length() <= 0) continue;
                jSONObject.accumulate("content", XML.stringToValue((String)object3));
                continue;
            }
            if (object3 == LT && XML.parse((XMLTokener)object, jSONObject, (String)object4)) break;
        } while (true);
        if (jSONObject.length() == 0) {
            object2.accumulate((String)object4, "");
            return false;
        }
        if (jSONObject.length() == 1 && jSONObject.opt("content") != null) {
            object2.accumulate((String)object4, jSONObject.opt("content"));
            return false;
        }
        object2.accumulate((String)object4, jSONObject);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object stringToValue(String string2) {
        void var3_2;
        if ("true".equalsIgnoreCase(string2)) {
            Boolean bl2 = Boolean.TRUE;
            return var3_2;
        }
        if ("false".equalsIgnoreCase(string2)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(string2)) {
            return JSONObject.NULL;
        }
        try {
            boolean bl3;
            Long l2;
            char c2 = string2.charAt(0);
            if (c2 != '-') {
                if (c2 < '0') return string2;
                if (c2 > '9') return string2;
            }
            if (bl3 = (l2 = new Long(string2)).toString().equals(string2)) return var3_2;
            return string2;
        }
        catch (Exception var3_4) {
            try {
                Double d2 = new Double(string2);
                boolean bl4 = d2.toString().equals(string2);
                if (!bl4) return string2;
                return d2;
            }
            catch (Exception var3_6) {
                return string2;
            }
        }
    }

    public static JSONObject toJSONObject(String object) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        object = new XMLTokener((String)object);
        while (object.more() && object.skipPast("<")) {
            XML.parse((XMLTokener)object, jSONObject, null);
        }
        return jSONObject;
    }

    public static String toString(Object object) throws JSONException {
        return XML.toString(object, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(Object object, String string2) throws JSONException {
        Object object2;
        StringBuffer stringBuffer = new StringBuffer();
        if (!(object instanceof JSONObject)) {
            object2 = object;
            if (object.getClass().isArray()) {
                object2 = new JSONArray(object);
            }
            if (!(object2 instanceof JSONArray)) {
                object = object2 == null ? "null" : XML.escape(object2.toString());
                if (string2 == null) {
                    return new StringBuffer().append("\"").append((String)object).append("\"").toString();
                }
                if (object.length() == 0) {
                    return new StringBuffer().append("<").append(string2).append("/>").toString();
                }
                return new StringBuffer().append("<").append(string2).append(">").append((String)object).append("</").append(string2).append(">").toString();
            }
        } else {
            if (string2 != null) {
                stringBuffer.append('<');
                stringBuffer.append(string2);
                stringBuffer.append('>');
            }
            JSONObject jSONObject = (JSONObject)object;
            Iterator iterator = jSONObject.keys();
            while (iterator.hasNext()) {
                Object object3;
                int n2;
                int n3;
                String string3 = iterator.next().toString();
                object = object3 = jSONObject.opt(string3);
                if (object3 == null) {
                    object = "";
                }
                if (object instanceof String) {
                    object3 = (String)object;
                }
                if ("content".equals(string3)) {
                    if (object instanceof JSONArray) {
                        object = (JSONArray)object;
                        n2 = object.length();
                        for (n3 = 0; n3 < n2; ++n3) {
                            if (n3 > 0) {
                                stringBuffer.append('\n');
                            }
                            stringBuffer.append(XML.escape(object.get(n3).toString()));
                        }
                        continue;
                    }
                    stringBuffer.append(XML.escape(object.toString()));
                    continue;
                }
                if (object instanceof JSONArray) {
                    object = (JSONArray)object;
                    n2 = object.length();
                } else {
                    if ("".equals(object)) {
                        stringBuffer.append('<');
                        stringBuffer.append(string3);
                        stringBuffer.append("/>");
                        continue;
                    }
                    stringBuffer.append(XML.toString(object, string3));
                    continue;
                }
                for (n3 = 0; n3 < n2; ++n3) {
                    object3 = object.get(n3);
                    if (object3 instanceof JSONArray) {
                        stringBuffer.append('<');
                        stringBuffer.append(string3);
                        stringBuffer.append('>');
                        stringBuffer.append(XML.toString(object3));
                        stringBuffer.append("</");
                        stringBuffer.append(string3);
                        stringBuffer.append('>');
                        continue;
                    }
                    stringBuffer.append(XML.toString(object3, string3));
                }
            }
            if (string2 != null) {
                stringBuffer.append("</");
                stringBuffer.append(string2);
                stringBuffer.append('>');
            }
            return stringBuffer.toString();
        }
        object2 = (JSONArray)object2;
        int n4 = object2.length();
        int n5 = 0;
        while (n5 < n4) {
            Object object4 = object2.opt(n5);
            object = string2 == null ? "array" : string2;
            stringBuffer.append(XML.toString(object4, (String)object));
            ++n5;
        }
        return stringBuffer.toString();
    }
}

