/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.be;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.du;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.l;
import com.google.android.gms.internal.m;
import java.util.HashMap;
import java.util.Map;

public final class ba {
    public static final bb mG = new bb(){

        @Override
        public void b(dz dz2, Map<String, String> map) {
        }
    };
    public static final bb mH = new bb(){

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void b(dz dz2, Map<String, String> object) {
            if (TextUtils.isEmpty((CharSequence)(object = (String)object.get("urls")))) {
                dw.z("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] arrstring = object.split(",");
            HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
            PackageManager packageManager = dz2.getContext().getPackageManager();
            int n2 = arrstring.length;
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    dz2.a("openableURLs", hashMap);
                    return;
                }
                String string2 = arrstring[n3];
                object = string2.split(";", 2);
                String string3 = object[0].trim();
                object = object.length > 1 ? object[1].trim() : "android.intent.action.VIEW";
                boolean bl2 = packageManager.resolveActivity(new Intent((String)object, Uri.parse((String)string3)), 65536) != null;
                hashMap.put(string2, bl2);
                ++n3;
            } while (true);
        }
    };
    public static final bb mI = new bb(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void b(dz dz2, Map<String, String> object) {
            String string2 = (String)object.get("u");
            if (string2 == null) {
                dw.z("URL missing from click GMSG.");
                return;
            }
            object = Uri.parse((String)string2);
            try {
                l l2 = dz2.bJ();
                if (l2 != null && l2.a((Uri)object)) {
                    object = l2 = l2.a((Uri)object, dz2.getContext());
                }
            }
            catch (m var3_5) {
                dw.z("Unable to append parameter to URL: " + string2);
            }
            object = object.toString();
            new du(dz2.getContext(), dz2.bK().rq, (String)object).start();
        }
    };
    public static final bb mJ = new bb(){

        @Override
        public void b(dz object, Map<String, String> map) {
            if ((object = object.bH()) == null) {
                dw.z("A GMSG tried to close something that wasn't an overlay.");
                return;
            }
            object.close();
        }
    };
    public static final bb mK = new bb(){

        @Override
        public void b(dz object, Map<String, String> map) {
            if ((object = object.bH()) == null) {
                dw.z("A GMSG tried to use a custom close button on something that wasn't an overlay.");
                return;
            }
            object.i("1".equals(map.get("custom_close")));
        }
    };
    public static final bb mL = new bb(){

        @Override
        public void b(dz dz2, Map<String, String> object) {
            if ((object = object.get("u")) == null) {
                dw.z("URL missing from httpTrack GMSG.");
                return;
            }
            new du(dz2.getContext(), dz2.bK().rq, (String)object).start();
        }
    };
    public static final bb mM = new bb(){

        @Override
        public void b(dz dz2, Map<String, String> map) {
            dw.x("Received log message: " + map.get("string"));
        }
    };
    public static final bb mN = new bb(){

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void b(dz object, Map<String, String> object2) {
            int n2;
            int n3;
            int n4;
            String string2 = (String)object2.get("tx");
            String string3 = (String)object2.get("ty");
            object2 = (String)object2.get("td");
            try {
                n2 = Integer.parseInt(string2);
                n3 = Integer.parseInt(string3);
                n4 = Integer.parseInt((String)object2);
                object = object.bJ();
                if (object == null) return;
            }
            catch (NumberFormatException var1_2) {
                dw.z("Could not parse touch parameters from gmsg.");
                return;
            }
            object.y().a(n2, n3, n4);
        }
    };
    public static final bb mO = new be();

}

