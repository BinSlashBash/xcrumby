/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.internal.er;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ew {
    private static final String[] Ac;
    private static final String Ad;
    private static final er zb;

    static {
        zb = new er("MetadataUtils");
        Ac = new String[]{"Z", "+hh", "+hhmm", "+hh:mm"};
        Ad = "yyyyMMdd'T'HHmmss" + Ac[0];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String a(Calendar object) {
        String string2;
        if (object == null) {
            zb.b("Calendar object cannot be null", new Object[0]);
            return null;
        }
        Object object2 = string2 = Ad;
        if (object.get(11) == 0) {
            object2 = string2;
            if (object.get(12) == 0) {
                object2 = string2;
                if (object.get(13) == 0) {
                    object2 = "yyyyMMdd";
                }
            }
        }
        object2 = new SimpleDateFormat((String)object2);
        object2.setTimeZone(object.getTimeZone());
        object = object2 = object2.format(object.getTime());
        if (!object2.endsWith("+0000")) return object;
        return object2.replace("+0000", Ac[0]);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void a(List<WebImage> var0, JSONObject var1_2) {
        block5 : {
            try {
                var0.clear();
                var1_2 = var1_2.getJSONArray("images");
                var3_3 = var1_2.length();
                var2_4 = 0;
lbl6: // 2 sources:
                if (var2_4 >= var3_3) return;
                var4_5 = var1_2.getJSONObject(var2_4);
                var0.add(new WebImage(var4_5));
                break block5;
            }
            catch (JSONException var0_1) {
                // empty catch block
            }
            return;
            catch (IllegalArgumentException var4_6) {}
        }
        ++var2_4;
        ** GOTO lbl6
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(JSONObject jSONObject, List<WebImage> iterator) {
        if (iterator == null || iterator.isEmpty()) return;
        JSONArray jSONArray = new JSONArray();
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            jSONArray.put(((WebImage)iterator.next()).dB());
        }
        try {
            jSONObject.put("images", jSONArray);
            return;
        }
        catch (JSONException var0_1) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Calendar ac(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            zb.b("Input string is empty or null", new Object[0]);
            return null;
        }
        Object object2 = ew.ad((String)object);
        if (TextUtils.isEmpty((CharSequence)object2)) {
            zb.b("Invalid date format", new Object[0]);
            return null;
        }
        String string2 = ew.ae((String)object);
        object = "yyyyMMdd";
        String string3 = object2;
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            string3 = (String)object2 + "T" + string2;
            object = string2.length() == "HHmmss".length() ? "yyyyMMdd'T'HHmmss" : Ad;
        }
        object2 = GregorianCalendar.getInstance();
        try {
            object = new SimpleDateFormat((String)object).parse(string3);
            object2.setTime((Date)object);
            return object2;
        }
        catch (ParseException var0_1) {
            zb.b("Error parsing string: %s", var0_1.getMessage());
            return null;
        }
    }

    private static String ad(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            zb.b("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            string2 = string2.substring(0, "yyyyMMdd".length());
            return string2;
        }
        catch (IndexOutOfBoundsException var0_1) {
            zb.c("Error extracting the date: %s", var0_1.getMessage());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String ae(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            zb.b("string is empty or null", new Object[0]);
            return null;
        }
        int n2 = string2.indexOf(84);
        if (n2 != "yyyyMMdd".length()) {
            zb.b("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            string2 = string2.substring(n2 + 1);
        }
        catch (IndexOutOfBoundsException var0_1) {
            zb.b("Error extracting the time substring: %s", var0_1.getMessage());
            return null;
        }
        if (string2.length() == "HHmmss".length()) {
            return string2;
        }
        switch (string2.charAt("HHmmss".length())) {
            default: {
                return null;
            }
            case '+': 
            case '-': {
                if (!ew.af(string2)) return null;
                return string2.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2");
            }
            case 'Z': {
                if (string2.length() != "HHmmss".length() + Ac[0].length()) return null;
                return string2.substring(0, string2.length() - 1) + "+0000";
            }
        }
    }

    private static boolean af(String string2) {
        int n2 = string2.length();
        int n3 = "HHmmss".length();
        if (n2 == Ac[1].length() + n3 || n2 == Ac[2].length() + n3 || n2 == n3 + Ac[3].length()) {
            return true;
        }
        return false;
    }
}

