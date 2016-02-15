package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.util.HashMap;
import java.util.Map;

class ay {
    private static String Yk;
    static Map<String, String> Yl;

    static {
        Yl = new HashMap();
    }

    ay() {
    }

    static void bF(String str) {
        synchronized (ay.class) {
            Yk = str;
        }
    }

    static void m1372c(Context context, String str) {
        cy.m1452a(context, "gtm_install_referrer", "referrer", str);
        m1375e(context, str);
    }

    static String m1373d(Context context, String str) {
        if (Yk == null) {
            synchronized (ay.class) {
                if (Yk == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        Yk = sharedPreferences.getString("referrer", UnsupportedUrlFragment.DISPLAY_NAME);
                    } else {
                        Yk = UnsupportedUrlFragment.DISPLAY_NAME;
                    }
                }
            }
        }
        return m1376m(Yk, str);
    }

    static String m1374e(Context context, String str, String str2) {
        String str3 = (String) Yl.get(str);
        if (str3 == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str3 = sharedPreferences != null ? sharedPreferences.getString(str, UnsupportedUrlFragment.DISPLAY_NAME) : UnsupportedUrlFragment.DISPLAY_NAME;
            Yl.put(str, str3);
        }
        return m1376m(str3, str2);
    }

    static void m1375e(Context context, String str) {
        String m = m1376m(str, "conv");
        if (m != null && m.length() > 0) {
            Yl.put(m, str);
            cy.m1452a(context, "gtm_click_referrers", m, str);
        }
    }

    static String m1376m(String str, String str2) {
        return str2 == null ? str.length() > 0 ? str : null : Uri.parse("http://hostname/?" + str).getQueryParameter(str2);
    }
}
