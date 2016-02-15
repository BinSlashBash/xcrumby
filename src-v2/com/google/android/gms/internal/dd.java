/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.location.Location
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.av;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.dc;
import com.google.android.gms.internal.dg;
import com.google.android.gms.internal.dn;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class dd {
    private static final SimpleDateFormat pH = new SimpleDateFormat("yyyyMMdd");

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static cz a(Context var0, cx var1_2, String var2_3) {
        block26 : {
            try {
                var18_4 = new JSONObject(var2_3);
                var2_3 = var18_4.optString("ad_base_url", null);
                var13_5 = var18_4.optString("ad_url", null);
                var16_6 = var18_4.optString("ad_size", null);
                var12_7 = var18_4.optString("ad_html", null);
                var7_8 = -1;
                var17_9 = var18_4.optString("debug_dialog", null);
                var5_10 = var18_4.has("interstitial_timeout") != false ? (long)(var18_4.getDouble("interstitial_timeout") * 1000.0) : -1;
                var14_11 = var18_4.optString("orientation", null);
                var3_12 = -1;
                if ("portrait".equals(var14_11)) {
                    var3_12 = dq.bA();
                } else if ("landscape".equals(var14_11)) {
                    var3_12 = dq.bz();
                }
                if (TextUtils.isEmpty((CharSequence)var12_7)) ** GOTO lbl22
                if (TextUtils.isEmpty((CharSequence)var2_3)) {
                    dw.z("Could not parse the mediation config: Missing required ad_base_url field");
                    return new cz(0);
                }
                ** GOTO lbl30
lbl22: // 1 sources:
                if (!TextUtils.isEmpty((CharSequence)var13_5)) {
                    var13_5 = dc.a((Context)var0, var1_2.kK.rq, (String)var13_5);
                    var2_3 = var13_5.ol;
                    var12_7 = var13_5.pm;
                    var7_8 = var13_5.ps;
                } else {
                    dw.z("Could not parse the mediation config: Missing required ad_html or ad_url field.");
                    return new cz(0);
lbl30: // 1 sources:
                    var13_5 = null;
                }
                var14_11 = var18_4.optJSONArray("click_urls");
                var0 = var13_5 == null ? null : var13_5.ne;
                if (var14_11 == null) {
                    var14_11 = var0;
                } else {
                    var1_2 = var0;
                    if (var0 == null) {
                        var1_2 = new LinkedList<String>();
                    }
                    for (var4_13 = 0; var4_13 < var14_11.length(); ++var4_13) {
                        var1_2.add((String)var14_11.getString(var4_13));
                    }
                    var14_11 = var1_2;
                }
                var15_14 = var18_4.optJSONArray("impression_urls");
                if (var13_5 != null) ** GOTO lbl48
                var0 = null;
                ** GOTO lbl49
lbl48: // 2 sources:
                var0 = var13_5.nf;
lbl49: // 2 sources:
                if (var15_14 == null) {
                    var15_14 = var0;
                    ** break block25
                }
                var1_2 = var0;
                if (var0 == null) {
                    var1_2 = new LinkedList<String>();
                }
                for (var4_13 = 0; var4_13 < var15_14.length(); ++var4_13) {
                    var1_2.add((String)var15_14.getString(var4_13));
                }
                var15_14 = var1_2;
            }
            catch (JSONException var0_1) {
                dw.z("Could not parse the mediation config: " + var0_1.getMessage());
                return new cz(0);
            }
lbl-1000: // 2 sources:
            {
                
                var19_15 = var18_4.optJSONArray("manual_impression_urls");
                if (var13_5 != null) break block26;
                var0 = null;
            }
        }
        var0 = var13_5.pq;
        if (var19_15 != null) {
            var1_2 = var0;
            if (var0 == null) {
                var1_2 = new LinkedList<String>();
            }
            for (var4_13 = 0; var4_13 < var19_15.length(); ++var4_13) {
                var1_2.add((String)var19_15.getString(var4_13));
            }
            var0 = var1_2;
        }
        var9_16 = var5_10;
        var4_13 = var3_12;
        if (var13_5 != null) {
            if (var13_5.orientation != -1) {
                var3_12 = var13_5.orientation;
            }
            var9_16 = var5_10;
            var4_13 = var3_12;
            if (var13_5.pn > 0) {
                var9_16 = var13_5.pn;
                var4_13 = var3_12;
            }
        }
        var13_5 = var18_4.optString("active_view");
        var1_2 = null;
        var11_17 = var18_4.optBoolean("ad_is_javascript", false);
        if (var11_17 == false) return new cz(var2_3, var12_7, var14_11, var15_14, var9_16, false, -1, (List<String>)var0, -1, var4_13, var16_6, var7_8, var17_9, var11_17, (String)var1_2, (String)var13_5);
        var1_2 = var18_4.optString("ad_passback_url", null);
        return new cz(var2_3, var12_7, var14_11, var15_14, var9_16, false, -1, (List<String>)var0, -1, var4_13, var16_6, var7_8, var17_9, var11_17, (String)var1_2, (String)var13_5);
    }

    /*
     * Exception decompiling
     */
    public static String a(cx var0, dg var1_2, Location var2_3, String var3_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[FORLOOP], 1[TRYBLOCK]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void a(HashMap<String, Object> hashMap, Location location) {
        HashMap<String, Number> hashMap2 = new HashMap<String, Number>();
        float f2 = location.getAccuracy();
        long l2 = location.getTime();
        long l3 = (long)(location.getLatitude() * 1.0E7);
        long l4 = (long)(location.getLongitude() * 1.0E7);
        hashMap2.put("radius", Float.valueOf(f2 * 1000.0f));
        hashMap2.put("lat", l3);
        hashMap2.put("long", l4);
        hashMap2.put("time", l2 * 1000);
        hashMap.put("uule", hashMap2);
    }

    private static void a(HashMap<String, Object> hashMap, ah ah2) {
        String string2 = dn.bx();
        if (string2 != null) {
            hashMap.put("abf", string2);
        }
        if (ah2.lH != -1) {
            hashMap.put("cust_age", pH.format(new Date(ah2.lH)));
        }
        if (ah2.extras != null) {
            hashMap.put("extras", (Object)ah2.extras);
        }
        if (ah2.lI != -1) {
            hashMap.put("cust_gender", ah2.lI);
        }
        if (ah2.lJ != null) {
            hashMap.put("kw", ah2.lJ);
        }
        if (ah2.lL != -1) {
            hashMap.put("tag_for_child_directed_treatment", ah2.lL);
        }
        if (ah2.lK) {
            hashMap.put("adtest", "on");
        }
        if (ah2.versionCode >= 2) {
            if (ah2.lM) {
                hashMap.put("d_imp_hdr", 1);
            }
            if (!TextUtils.isEmpty((CharSequence)ah2.lN)) {
                hashMap.put("ppid", ah2.lN);
            }
            if (ah2.lO != null) {
                dd.a(hashMap, ah2.lO);
            }
        }
        if (ah2.versionCode >= 3 && ah2.lQ != null) {
            hashMap.put("url", ah2.lQ);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void a(HashMap<String, Object> hashMap, av av2) {
        String string2;
        Object var3_2 = null;
        if (Color.alpha((int)av2.mq) != 0) {
            hashMap.put("acolor", dd.m(av2.mq));
        }
        if (Color.alpha((int)av2.backgroundColor) != 0) {
            hashMap.put("bgcolor", dd.m(av2.backgroundColor));
        }
        if (Color.alpha((int)av2.mr) != 0 && Color.alpha((int)av2.ms) != 0) {
            hashMap.put("gradientto", dd.m(av2.mr));
            hashMap.put("gradientfrom", dd.m(av2.ms));
        }
        if (Color.alpha((int)av2.mt) != 0) {
            hashMap.put("bcolor", dd.m(av2.mt));
        }
        hashMap.put("bthick", Integer.toString(av2.mu));
        switch (av2.mv) {
            default: {
                string2 = null;
                break;
            }
            case 0: {
                string2 = "none";
                break;
            }
            case 1: {
                string2 = "dashed";
                break;
            }
            case 2: {
                string2 = "dotted";
                break;
            }
            case 3: {
                string2 = "solid";
            }
        }
        if (string2 != null) {
            hashMap.put("btype", string2);
        }
        switch (av2.mw) {
            default: {
                string2 = var3_2;
                break;
            }
            case 2: {
                string2 = "dark";
                break;
            }
            case 0: {
                string2 = "light";
                break;
            }
            case 1: {
                string2 = "medium";
            }
        }
        if (string2 != null) {
            hashMap.put("callbuttoncolor", string2);
        }
        if (av2.mx != null) {
            hashMap.put("channel", av2.mx);
        }
        if (Color.alpha((int)av2.my) != 0) {
            hashMap.put("dcolor", dd.m(av2.my));
        }
        if (av2.mz != null) {
            hashMap.put("font", av2.mz);
        }
        if (Color.alpha((int)av2.mA) != 0) {
            hashMap.put("hcolor", dd.m(av2.mA));
        }
        hashMap.put("headersize", Integer.toString(av2.mB));
        if (av2.mC != null) {
            hashMap.put("q", av2.mC);
        }
    }

    private static void a(HashMap<String, Object> hashMap, dg dg2) {
        hashMap.put("am", dg2.pZ);
        hashMap.put("cog", dd.l(dg2.qa));
        hashMap.put("coh", dd.l(dg2.qb));
        if (!TextUtils.isEmpty((CharSequence)dg2.qc)) {
            hashMap.put("carrier", dg2.qc);
        }
        hashMap.put("gl", dg2.qd);
        if (dg2.qe) {
            hashMap.put("simulator", 1);
        }
        hashMap.put("ma", dd.l(dg2.qf));
        hashMap.put("sp", dd.l(dg2.qg));
        hashMap.put("hl", dg2.qh);
        if (!TextUtils.isEmpty((CharSequence)dg2.qi)) {
            hashMap.put("mv", dg2.qi);
        }
        hashMap.put("muv", dg2.qj);
        if (dg2.qk != -2) {
            hashMap.put("cnt", dg2.qk);
        }
        hashMap.put("gnt", dg2.ql);
        hashMap.put("pt", dg2.qm);
        hashMap.put("rm", dg2.qn);
        hashMap.put("riv", dg2.qo);
        hashMap.put("u_sd", Float.valueOf(dg2.qp));
        hashMap.put("sh", dg2.qr);
        hashMap.put("sw", dg2.qq);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Integer l(boolean bl2) {
        int n2;
        if (bl2) {
            n2 = 1;
            do {
                return n2;
                break;
            } while (true);
        }
        n2 = 0;
        return n2;
    }

    private static String m(int n2) {
        return String.format(Locale.US, "#%06x", 16777215 & n2);
    }
}

