/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class gp {
    private static final Pattern Es = Pattern.compile("\\\\.");
    private static final Pattern Et = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String av(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) return string2;
        Matcher matcher = Et.matcher(string2);
        StringBuffer stringBuffer = null;
        block10 : while (matcher.find()) {
            StringBuffer stringBuffer2 = stringBuffer;
            if (stringBuffer == null) {
                stringBuffer2 = new StringBuffer();
            }
            switch (matcher.group().charAt(0)) {
                default: {
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\b': {
                    matcher.appendReplacement(stringBuffer2, "\\\\b");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\"': {
                    matcher.appendReplacement(stringBuffer2, "\\\\\\\"");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\\': {
                    matcher.appendReplacement(stringBuffer2, "\\\\\\\\");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '/': {
                    matcher.appendReplacement(stringBuffer2, "\\\\/");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\f': {
                    matcher.appendReplacement(stringBuffer2, "\\\\f");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\n': {
                    matcher.appendReplacement(stringBuffer2, "\\\\n");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\r': {
                    matcher.appendReplacement(stringBuffer2, "\\\\r");
                    stringBuffer = stringBuffer2;
                    continue block10;
                }
                case '\t': 
            }
            matcher.appendReplacement(stringBuffer2, "\\\\t");
            stringBuffer = stringBuffer2;
        }
        return string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public static boolean d(Object var0, Object var1_3) {
        if (!(var0 instanceof JSONObject) || !(var1_3 instanceof JSONObject)) ** GOTO lbl16
        var0 = (JSONObject)var0;
        var1_3 = (JSONObject)var1_3;
        if (var0.length() != var1_3.length()) lbl-1000: // 4 sources:
        {
            do {
                return false;
                break;
            } while (true);
        }
        var4_4 = var0.keys();
        while (var4_4.hasNext()) {
            var5_7 = (String)var4_4.next();
            if (!var1_3.has(var5_7)) ** GOTO lbl-1000
            var3_5 = gp.d(var0.get(var5_7), var1_3.get(var5_7));
            if (var3_5) continue;
            return false;
        }
        return true;
lbl16: // 1 sources:
        if (!(var0 instanceof JSONArray) || !(var1_3 instanceof JSONArray)) ** GOTO lbl25
        var0 = (JSONArray)var0;
        var1_3 = (JSONArray)var1_3;
        if (var0.length() != var1_3.length()) ** GOTO lbl-1000
        for (var2_8 = 0; var2_8 < var0.length(); ++var2_8) {
            if (!(var3_6 = gp.d(var0.get(var2_8), var1_3.get(var2_8)))) ** continue;
            continue;
        }
        return true;
lbl25: // 1 sources:
        return var0.equals(var1_3);
        catch (JSONException var0_1) {
            return false;
        }
        catch (JSONException var0_2) {
            return false;
        }
    }
}

