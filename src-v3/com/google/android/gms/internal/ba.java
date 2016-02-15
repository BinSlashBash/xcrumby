package com.google.android.gms.internal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast;
import java.util.HashMap;
import java.util.Map;

public final class ba {
    public static final bb mG;
    public static final bb mH;
    public static final bb mI;
    public static final bb mJ;
    public static final bb mK;
    public static final bb mL;
    public static final bb mM;
    public static final bb mN;
    public static final bb mO;

    /* renamed from: com.google.android.gms.internal.ba.1 */
    static class C08361 implements bb {
        C08361() {
        }

        public void m2027b(dz dzVar, Map<String, String> map) {
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.2 */
    static class C08372 implements bb {
        C08372() {
        }

        public void m2028b(dz dzVar, Map<String, String> map) {
            String str = (String) map.get("urls");
            if (TextUtils.isEmpty(str)) {
                dw.m823z("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] split = str.split(",");
            Map hashMap = new HashMap();
            PackageManager packageManager = dzVar.getContext().getPackageManager();
            for (String str2 : split) {
                String[] split2 = str2.split(";", 2);
                hashMap.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(split2.length > 1 ? split2[1].trim() : "android.intent.action.VIEW", Uri.parse(split2[0].trim())), Cast.MAX_MESSAGE_LENGTH) != null));
            }
            dzVar.m832a("openableURLs", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.3 */
    static class C08383 implements bb {
        C08383() {
        }

        public void m2029b(dz dzVar, Map<String, String> map) {
            String str = (String) map.get("u");
            if (str == null) {
                dw.m823z("URL missing from click GMSG.");
                return;
            }
            Uri a;
            Uri parse = Uri.parse(str);
            try {
                C0410l bJ = dzVar.bJ();
                if (bJ != null && bJ.m1182a(parse)) {
                    a = bJ.m1180a(parse, dzVar.getContext());
                    new du(dzVar.getContext(), dzVar.bK().rq, a.toString()).start();
                }
            } catch (C0411m e) {
                dw.m823z("Unable to append parameter to URL: " + str);
            }
            a = parse;
            new du(dzVar.getContext(), dzVar.bK().rq, a.toString()).start();
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.4 */
    static class C08394 implements bb {
        C08394() {
        }

        public void m2030b(dz dzVar, Map<String, String> map) {
            cc bH = dzVar.bH();
            if (bH == null) {
                dw.m823z("A GMSG tried to close something that wasn't an overlay.");
            } else {
                bH.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.5 */
    static class C08405 implements bb {
        C08405() {
        }

        public void m2031b(dz dzVar, Map<String, String> map) {
            cc bH = dzVar.bH();
            if (bH == null) {
                dw.m823z("A GMSG tried to use a custom close button on something that wasn't an overlay.");
            } else {
                bH.m2989i("1".equals(map.get("custom_close")));
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.6 */
    static class C08416 implements bb {
        C08416() {
        }

        public void m2032b(dz dzVar, Map<String, String> map) {
            String str = (String) map.get("u");
            if (str == null) {
                dw.m823z("URL missing from httpTrack GMSG.");
            } else {
                new du(dzVar.getContext(), dzVar.bK().rq, str).start();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.7 */
    static class C08427 implements bb {
        C08427() {
        }

        public void m2033b(dz dzVar, Map<String, String> map) {
            dw.m821x("Received log message: " + ((String) map.get("string")));
        }
    }

    /* renamed from: com.google.android.gms.internal.ba.8 */
    static class C08438 implements bb {
        C08438() {
        }

        public void m2034b(dz dzVar, Map<String, String> map) {
            String str = (String) map.get("ty");
            String str2 = (String) map.get("td");
            try {
                int parseInt = Integer.parseInt((String) map.get("tx"));
                int parseInt2 = Integer.parseInt(str);
                int parseInt3 = Integer.parseInt(str2);
                C0410l bJ = dzVar.bJ();
                if (bJ != null) {
                    bJ.m1183y().m1042a(parseInt, parseInt2, parseInt3);
                }
            } catch (NumberFormatException e) {
                dw.m823z("Could not parse touch parameters from gmsg.");
            }
        }
    }

    static {
        mG = new C08361();
        mH = new C08372();
        mI = new C08383();
        mJ = new C08394();
        mK = new C08405();
        mL = new C08416();
        mM = new C08427();
        mN = new C08438();
        mO = new be();
    }
}
