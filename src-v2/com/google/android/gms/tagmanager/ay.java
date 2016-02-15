/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.tagmanager.cy;
import java.util.HashMap;
import java.util.Map;

class ay {
    private static String Yk;
    static Map<String, String> Yl;

    static {
        Yl = new HashMap<String, String>();
    }

    ay() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void bF(String string2) {
        synchronized (ay.class) {
            Yk = string2;
            return;
        }
    }

    static void c(Context context, String string2) {
        cy.a(context, "gtm_install_referrer", "referrer", string2);
        ay.e(context, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String d(Context context, String string2) {
        if (Yk == null) {
            synchronized (ay.class) {
                if (Yk == null) {
                    Yk = (context = context.getSharedPreferences("gtm_install_referrer", 0)) != null ? context.getString("referrer", "") : "";
                }
            }
        }
        return ay.m(Yk, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    static String e(Context object, String string2, String string3) {
        String string4;
        Object object2 = string4 = Yl.get(string2);
        if (string4 == null) {
            object = (object = object.getSharedPreferences("gtm_click_referrers", 0)) != null ? object.getString(string2, "") : "";
            Yl.put(string2, (String)object);
            object2 = object;
        }
        return ay.m((String)object2, string3);
    }

    static void e(Context context, String string2) {
        String string3 = ay.m(string2, "conv");
        if (string3 != null && string3.length() > 0) {
            Yl.put(string3, string2);
            cy.a(context, "gtm_click_referrers", string3, string2);
        }
    }

    static String m(String string2, String string3) {
        if (string3 == null) {
            if (string2.length() > 0) {
                return string2;
            }
            return null;
        }
        return Uri.parse((String)("http://hostname/?" + string2)).getQueryParameter(string3);
    }
}

