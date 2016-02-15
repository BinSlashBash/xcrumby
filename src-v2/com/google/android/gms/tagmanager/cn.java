/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.c;
import com.google.android.gms.tagmanager.bg;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bm;
import com.google.android.gms.tagmanager.cd;
import com.google.android.gms.tagmanager.r;

class cn
implements Runnable {
    private final String WJ;
    private volatile String Xg;
    private final bm Zd;
    private final String Ze;
    private bg<c.j> Zf;
    private volatile r Zg;
    private volatile String Zh;
    private final Context mContext;

    cn(Context context, String string2, bm bm2, r r2) {
        this.mContext = context;
        this.Zd = bm2;
        this.WJ = string2;
        this.Zg = r2;
        this.Xg = this.Ze = "/r?id=" + string2;
        this.Zh = null;
    }

    public cn(Context context, String string2, r r2) {
        this(context, string2, new bm(), r2);
    }

    private boolean kW() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            bh.y("...no network connectivity");
            return false;
        }
        return true;
    }

    /*
     * Exception decompiling
     */
    private void kX() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
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

    void a(bg<c.j> bg2) {
        this.Zf = bg2;
    }

    void bJ(String string2) {
        bh.v("Setting previous container version: " + string2);
        this.Zh = string2;
    }

    void bu(String string2) {
        if (string2 == null) {
            this.Xg = this.Ze;
            return;
        }
        bh.v("Setting CTFE URL path: " + string2);
        this.Xg = string2;
    }

    String kY() {
        String string2;
        String string3 = string2 = this.Zg.kn() + this.Xg + "&v=a65833898";
        if (this.Zh != null) {
            string3 = string2;
            if (!this.Zh.trim().equals("")) {
                string3 = string2 + "&pv=" + this.Zh;
            }
        }
        string2 = string3;
        if (cd.kT().kU().equals((Object)cd.a.YV)) {
            string2 = string3 + "&gtm_debug=x";
        }
        return string2;
    }

    @Override
    public void run() {
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.Zf.kl();
        this.kX();
    }
}

