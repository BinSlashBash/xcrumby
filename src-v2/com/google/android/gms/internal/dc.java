/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.Handler
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.ax;
import com.google.android.gms.internal.ba;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bf;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.db;
import com.google.android.gms.internal.dd;
import com.google.android.gms.internal.de;
import com.google.android.gms.internal.dg;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class dc
extends db.a {
    private static final Object px = new Object();
    private static dc py;
    private final Context mContext;
    private final ax pA;
    private final bf pz;

    private dc(Context context, ax ax2, bf bf2) {
        this.mContext = context;
        this.pz = bf2;
        this.pA = ax2;
    }

    private static cz a(final Context context, ax object, bf object2, final cx cx2) {
        Object object3;
        dw.v("Starting ad request from service.");
        object2.init();
        dg dg2 = new dg(context);
        if (dg2.qk == -1) {
            dw.v("Device is offline.");
            return new cz(2);
        }
        final de de2 = new de(cx2.applicationInfo.packageName);
        if (cx2.pg.extras != null && (object3 = cx2.pg.extras.getString("_ad")) != null) {
            return dd.a(context, cx2, (String)object3);
        }
        object3 = object2.a(250);
        object2 = object.aH();
        if ((object = dd.a(cx2, dg2, (Location)object3, object.aI())) == null) {
            return new cz(0);
        }
        object = dc.p((String)object);
        dv.rp.post(new Runnable((ea.a)object, (String)object2){
            final /* synthetic */ ea.a pE;
            final /* synthetic */ String pF;

            @Override
            public void run() {
                dz dz2 = dz.a(context, new ak(), false, false, null, cx2.kK);
                dz2.setWillNotDraw(true);
                de2.b(dz2);
                ea ea2 = dz2.bI();
                ea2.a("/invalidRequest", de2.pK);
                ea2.a("/loadAdURL", de2.pL);
                ea2.a("/log", ba.mM);
                ea2.a(this.pE);
                dw.v("Loading the JS library.");
                dz2.loadUrl(this.pF);
            }
        });
        object = de2.bj();
        if (TextUtils.isEmpty((CharSequence)object)) {
            return new cz(de2.getErrorCode());
        }
        return dc.a(context, cx2.kK.rq, (String)object);
    }

    /*
     * Exception decompiling
     */
    public static cz a(Context var0, String var1_3, String var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 17[UNCONDITIONALDOLOOP]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static dc a(Context object, ax ax2, bf bf2) {
        Object object2 = px;
        synchronized (object2) {
            if (py != null) return py;
            py = new dc(object.getApplicationContext(), ax2, bf2);
            return py;
        }
    }

    private static void a(String object, Map<String, List<String>> map, String string2, int n2) {
        if (dw.n(2)) {
            dw.y("Http Response: {\n  URL:\n    " + (String)object + "\n  Headers:");
            if (map != null) {
                object = map.keySet().iterator();
                while (object.hasNext()) {
                    String string3 = (String)object.next();
                    dw.y("    " + (String)string3 + ":");
                    for (String string4 : map.get(string3)) {
                        dw.y("      " + string4);
                    }
                }
            }
            dw.y("  Body:");
            if (string2 != null) {
                for (int i2 = 0; i2 < Math.min(string2.length(), 100000); i2 += 1000) {
                    dw.y(string2.substring(i2, Math.min(string2.length(), i2 + 1000)));
                }
            } else {
                dw.y("    null");
            }
            dw.y("  Response Code:\n    " + n2 + "\n}");
        }
    }

    private static ea.a p(final String string2) {
        return new ea.a(){

            @Override
            public void a(dz dz2) {
                String string22 = String.format("javascript:%s(%s);", "AFMA_buildAdURL", string2);
                dw.y("About to execute: " + string22);
                dz2.loadUrl(string22);
            }
        };
    }

    @Override
    public cz b(cx cx2) {
        return dc.a(this.mContext, this.pA, this.pz, cx2);
    }

}

