/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.cb;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class bd
implements bb {
    private final bc mP;

    public bd(bc bc2) {
        this.mP = bc2;
    }

    private static boolean a(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int b(Map<String, String> object) {
        if ((object = object.get("o")) != null) {
            if ("p".equalsIgnoreCase((String)object)) {
                return dq.bA();
            }
            if ("l".equalsIgnoreCase((String)object)) {
                return dq.bz();
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void b(dz object, Map<String, String> arrstring) {
        String string2 = (String)arrstring.get("a");
        if (string2 == null) {
            dw.z("Action missing from an open GMSG.");
            return;
        }
        ea ea2 = object.bI();
        if ("expand".equalsIgnoreCase(string2)) {
            if (object.bL()) {
                dw.z("Cannot expand WebView that is already expanded.");
                return;
            }
            ea2.a(bd.a(arrstring), bd.b(arrstring));
            return;
        }
        if ("webapp".equalsIgnoreCase(string2)) {
            object = (String)arrstring.get("u");
            if (object != null) {
                ea2.a(bd.a(arrstring), bd.b(arrstring), (String)object);
                return;
            }
            ea2.a(bd.a(arrstring), bd.b(arrstring), (String)arrstring.get("html"), (String)arrstring.get("baseurl"));
            return;
        }
        if (!"in_app_purchase".equalsIgnoreCase(string2)) {
            ea2.a(new cb((String)arrstring.get("i"), (String)arrstring.get("u"), (String)arrstring.get("m"), (String)arrstring.get("p"), (String)arrstring.get("c"), (String)arrstring.get("f"), (String)arrstring.get("e")));
            return;
        }
        object = (String)arrstring.get("product_id");
        arrstring = (String)arrstring.get("report_urls");
        if (this.mP == null) return;
        {
            if (arrstring != null && !arrstring.isEmpty()) {
                arrstring = arrstring.split(" ");
                this.mP.a((String)object, new ArrayList<String>(Arrays.asList(arrstring)));
                return;
            }
        }
        this.mP.a((String)object, new ArrayList<String>());
    }
}

