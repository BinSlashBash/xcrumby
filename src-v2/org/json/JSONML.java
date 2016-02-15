/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.XMLTokener;

public class JSONML {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Object parse(XMLTokener var0, boolean var1_1, JSONArray var2_2) throws JSONException {
        do {
            block28 : {
                if (!var0.more()) {
                    throw var0.syntaxError("Bad XML");
                }
                var6_6 = var0.nextContent();
                if (var6_6 != XML.LT) ** GOTO lbl46
                var5_5 = var0.nextToken();
                if (!(var5_5 instanceof Character)) ** GOTO lbl32
                if (var5_5 == XML.SLASH) {
                    var2_2 = var0.nextToken();
                    if (!(var2_2 instanceof String)) {
                        throw new JSONException(new StringBuffer().append("Expected a closing name instead of '").append(var2_2).append("'.").toString());
                    }
                    if (var0.nextToken() == XML.GT) return var2_2;
                    throw var0.syntaxError("Misshaped close tag");
                }
                if (var5_5 != XML.BANG) ** GOTO lbl29
                var3_3 = var0.next();
                if (var3_3 == 45) {
                    if (var0.next() == '-') {
                        var0.skipPast("-->");
                        continue;
                    }
                    var0.back();
                    continue;
                }
                if (var3_3 == 91) {
                    if (var0.nextToken().equals("CDATA") == false) throw var0.syntaxError("Expected 'CDATA['");
                    if (var0.next() != '[') throw var0.syntaxError("Expected 'CDATA['");
                    if (var2_2 == null) continue;
                    var2_2.put(var0.nextCDATA());
                    continue;
                }
                ** GOTO lbl52
lbl29: // 1 sources:
                if (var5_5 != XML.QUEST) throw var0.syntaxError("Misshaped tag");
                var0.skipPast("?>");
                continue;
lbl32: // 1 sources:
                if (!(var5_5 instanceof String)) {
                    throw var0.syntaxError(new StringBuffer().append("Bad tagName '").append(var5_5).append("'.").toString());
                }
                var7_7 = (String)var5_5;
                var6_6 = new JSONArray();
                var8_8 = new JSONObject();
                if (!var1_1) ** GOTO lbl42
                var6_6.put(var7_7);
                if (var2_2 != null) {
                    var2_2.put(var6_6);
                }
                ** GOTO lbl65
lbl42: // 1 sources:
                var8_8.put("tagName", var7_7);
                if (var2_2 == null) ** GOTO lbl65
                var2_2.put(var8_8);
                ** GOTO lbl65
lbl46: // 1 sources:
                if (var2_2 == null) continue;
                var5_5 = var6_6;
                if (var6_6 instanceof String) {
                    var5_5 = XML.stringToValue((String)var6_6);
                }
                var2_2.put(var5_5);
                continue;
lbl52: // 1 sources:
                var4_4 = 1;
                do {
                    if ((var5_5 = var0.nextMeta()) == null) {
                        throw var0.syntaxError("Missing '>' after '<!'.");
                    }
                    if (var5_5 == XML.LT) {
                        var3_3 = var4_4 + '\u0001';
                    } else {
                        var3_3 = var4_4;
                        if (var5_5 == XML.GT) {
                            var3_3 = var4_4 - 1;
                        }
                    }
                    var4_4 = var3_3;
                } while (var3_3 > 0);
                continue;
lbl65: // 3 sources:
                var5_5 = null;
                do {
                    if (var5_5 == null) {
                        var5_5 = var0.nextToken();
                    }
                    if (var5_5 == null) {
                        throw var0.syntaxError("Misshaped tag");
                    }
                    if (!(var5_5 instanceof String)) {
                        if (var1_1 && var8_8.length() > 0) {
                            var6_6.put(var8_8);
                        }
                        if (var5_5 == XML.SLASH) {
                            if (var0.nextToken() == XML.GT) break;
                            throw var0.syntaxError("Misshaped tag");
                        }
                        break block28;
                    }
                    var9_9 = (String)var5_5;
                    if (!var1_1) {
                        if ("tagName".equals(var9_9) != false) throw var0.syntaxError("Reserved attribute.");
                        if ("childNode".equals(var9_9)) {
                            throw var0.syntaxError("Reserved attribute.");
                        }
                    }
                    if ((var5_5 = var0.nextToken()) == XML.EQ) {
                        var5_5 = var0.nextToken();
                        if (!(var5_5 instanceof String)) {
                            throw var0.syntaxError("Missing value");
                        }
                        var8_8.accumulate(var9_9, XML.stringToValue((String)var5_5));
                        var5_5 = null;
                        continue;
                    }
                    var8_8.accumulate(var9_9, "");
                } while (true);
                if (var2_2 != null) continue;
                if (var1_1 == false) return var8_8;
                return var6_6;
            }
            if (var5_5 != XML.GT) {
                throw var0.syntaxError("Misshaped tag");
            }
            var5_5 = (String)JSONML.parse(var0, var1_1, (JSONArray)var6_6);
            if (var5_5 == null) continue;
            if (!var5_5.equals(var7_7)) {
                throw var0.syntaxError(new StringBuffer().append("Mismatched '").append(var7_7).append("' and '").append((String)var5_5).append("'").toString());
            }
            if (!var1_1 && var6_6.length() > 0) {
                var8_8.put("childNodes", var6_6);
            }
            if (var2_2 == null) break;
        } while (true);
        if (var1_1 == false) return var8_8;
        return var6_6;
    }

    public static JSONArray toJSONArray(String string2) throws JSONException {
        return JSONML.toJSONArray(new XMLTokener(string2));
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener) throws JSONException {
        return (JSONArray)JSONML.parse(xMLTokener, true, null);
    }

    public static JSONObject toJSONObject(String string2) throws JSONException {
        return JSONML.toJSONObject(new XMLTokener(string2));
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener) throws JSONException {
        return (JSONObject)JSONML.parse(xMLTokener, false, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(JSONArray jSONArray) throws JSONException {
        int n2;
        int n3;
        int n4;
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = jSONArray.getString(0);
        XML.noSpace(string2);
        string2 = XML.escape(string2);
        stringBuffer.append('<');
        stringBuffer.append(string2);
        Object object = jSONArray.opt(1);
        if (object instanceof JSONObject) {
            n3 = 2;
            object = (JSONObject)object;
            Iterator iterator = object.keys();
            do {
                n4 = n3;
                if (iterator.hasNext()) {
                    String string3 = iterator.next().toString();
                    XML.noSpace(string3);
                    String string4 = object.optString(string3);
                    if (string4 == null) continue;
                    stringBuffer.append(' ');
                    stringBuffer.append(XML.escape(string3));
                    stringBuffer.append('=');
                    stringBuffer.append('\"');
                    stringBuffer.append(XML.escape(string4));
                    stringBuffer.append('\"');
                    continue;
                }
                break;
                break;
            } while (true);
        } else {
            n4 = 1;
        }
        if (n4 >= (n2 = jSONArray.length())) {
            stringBuffer.append('/');
            stringBuffer.append('>');
            return stringBuffer.toString();
        }
        stringBuffer.append('>');
        do {
            object = jSONArray.get(n4);
            n3 = n4 + 1;
            if (object != null) {
                if (object instanceof String) {
                    stringBuffer.append(XML.escape(object.toString()));
                } else if (object instanceof JSONObject) {
                    stringBuffer.append(JSONML.toString((JSONObject)object));
                } else if (object instanceof JSONArray) {
                    stringBuffer.append(JSONML.toString((JSONArray)object));
                }
            }
            n4 = n3;
        } while (n3 < n2);
        stringBuffer.append('<');
        stringBuffer.append('/');
        stringBuffer.append(string2);
        stringBuffer.append('>');
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(JSONObject object) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = object.optString("tagName");
        if (string2 == null) {
            return XML.escape(object.toString());
        }
        XML.noSpace(string2);
        string2 = XML.escape(string2);
        stringBuffer.append('<');
        stringBuffer.append(string2);
        Object object2 = object.keys();
        while (object2.hasNext()) {
            String string3 = object2.next().toString();
            if ("tagName".equals(string3) || "childNodes".equals(string3)) continue;
            XML.noSpace(string3);
            String string4 = object.optString(string3);
            if (string4 == null) continue;
            stringBuffer.append(' ');
            stringBuffer.append(XML.escape(string3));
            stringBuffer.append('=');
            stringBuffer.append('\"');
            stringBuffer.append(XML.escape(string4));
            stringBuffer.append('\"');
        }
        if ((object = object.optJSONArray("childNodes")) == null) {
            stringBuffer.append('/');
            stringBuffer.append('>');
            return stringBuffer.toString();
        }
        stringBuffer.append('>');
        int n2 = object.length();
        int n3 = 0;
        do {
            if (n3 >= n2) {
                stringBuffer.append('<');
                stringBuffer.append('/');
                stringBuffer.append(string2);
                stringBuffer.append('>');
                return stringBuffer.toString();
            }
            object2 = object.get(n3);
            if (object2 != null) {
                if (object2 instanceof String) {
                    stringBuffer.append(XML.escape(object2.toString()));
                } else if (object2 instanceof JSONObject) {
                    stringBuffer.append(JSONML.toString((JSONObject)object2));
                } else if (object2 instanceof JSONArray) {
                    stringBuffer.append(JSONML.toString((JSONArray)object2));
                } else {
                    stringBuffer.append(object2.toString());
                }
            }
            ++n3;
        } while (true);
    }
}

