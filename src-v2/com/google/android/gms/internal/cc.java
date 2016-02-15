/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.webkit.WebChromeClient
 *  android.webkit.WebChromeClient$CustomViewCallback
 *  android.widget.FrameLayout
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.az;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.ce;
import com.google.android.gms.internal.cf;
import com.google.android.gms.internal.cg;
import com.google.android.gms.internal.ch;
import com.google.android.gms.internal.ci;
import com.google.android.gms.internal.ck;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dr;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.u;

public class cc
extends ck.a {
    private dz lC;
    private final Activity nS;
    private ce nT;
    private cg nU;
    private c nV;
    private ch nW;
    private boolean nX;
    private FrameLayout nY;
    private WebChromeClient.CustomViewCallback nZ;
    private boolean oa = false;
    private boolean ob = false;
    private RelativeLayout oc;

    public cc(Activity activity) {
        this.nS = activity;
    }

    private static RelativeLayout.LayoutParams a(int n2, int n3, int n4, int n5) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(n4, n5);
        layoutParams.setMargins(n2, n3, 0, 0);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        return layoutParams;
    }

    public static void a(Context context, ce ce2) {
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.AdActivity");
        intent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", ce2.kK.rt);
        ce.a(intent, ce2);
        intent.addFlags(524288);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void aN() {
        if (!this.nS.isFinishing()) return;
        if (this.ob) {
            return;
        }
        this.ob = true;
        if (!this.nS.isFinishing()) return;
        if (this.lC != null) {
            this.lC.bF();
            this.oc.removeView((View)this.lC);
            if (this.nV != null) {
                this.lC.p(false);
                this.nV.of.addView((View)this.lC, this.nV.index, this.nV.oe);
            }
        }
        if (this.nT == null) return;
        if (this.nT.oi == null) return;
        this.nT.oi.V();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void j(boolean bl2) throws a {
        if (!this.nX) {
            this.nS.requestWindowFeature(1);
        }
        Window window = this.nS.getWindow();
        window.setFlags(1024, 1024);
        this.setRequestedOrientation(this.nT.orientation);
        if (Build.VERSION.SDK_INT >= 11) {
            dw.v("Enabling hardware acceleration on the AdActivity window.");
            ds.a(window);
        }
        this.oc = new b((Context)this.nS, this.nT.or);
        this.oc.setBackgroundColor(-16777216);
        this.nS.setContentView((View)this.oc);
        this.N();
        boolean bl3 = this.nT.oj.bI().bP();
        if (bl2) {
            this.lC = dz.a((Context)this.nS, this.nT.oj.R(), true, bl3, null, this.nT.kK);
            this.lC.bI().a(null, null, this.nT.ok, this.nT.oo, true, this.nT.oq);
            this.lC.bI().a(new ea.a(){

                @Override
                public void a(dz dz2) {
                    dz2.bG();
                }
            });
            if (this.nT.nO != null) {
                this.lC.loadUrl(this.nT.nO);
            } else {
                if (this.nT.on == null) {
                    throw new a("No URL or HTML to display in ad overlay.");
                }
                this.lC.loadDataWithBaseURL(this.nT.ol, this.nT.on, "text/html", "UTF-8", null);
            }
        } else {
            this.lC = this.nT.oj;
            this.lC.setContext((Context)this.nS);
        }
        this.lC.a(this);
        window = this.lC.getParent();
        if (window != null && window instanceof ViewGroup) {
            ((ViewGroup)window).removeView((View)this.lC);
        }
        this.oc.addView((View)this.lC, -1, -1);
        if (!bl2) {
            this.lC.bG();
        }
        this.h(bl3);
    }

    @Override
    public void N() {
        this.nX = true;
    }

    public void a(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.nY = new FrameLayout((Context)this.nS);
        this.nY.setBackgroundColor(-16777216);
        this.nY.addView(view, -1, -1);
        this.nS.setContentView((View)this.nY);
        this.N();
        this.nZ = customViewCallback;
    }

    public cg aK() {
        return this.nU;
    }

    public void aL() {
        if (this.nT != null) {
            this.setRequestedOrientation(this.nT.orientation);
        }
        if (this.nY != null) {
            this.nS.setContentView((View)this.oc);
            this.N();
            this.nY.removeAllViews();
            this.nY = null;
        }
        if (this.nZ != null) {
            this.nZ.onCustomViewHidden();
            this.nZ = null;
        }
    }

    public void aM() {
        this.oc.removeView((View)this.nW);
        this.h(true);
    }

    public void b(int n2, int n3, int n4, int n5) {
        if (this.nU != null) {
            this.nU.setLayoutParams((ViewGroup.LayoutParams)cc.a(n2, n3, n4, n5));
        }
    }

    public void c(int n2, int n3, int n4, int n5) {
        if (this.nU == null) {
            this.nU = new cg((Context)this.nS, this.lC);
            this.oc.addView((View)this.nU, 0, (ViewGroup.LayoutParams)cc.a(n2, n3, n4, n5));
            this.lC.bI().q(false);
        }
    }

    public void close() {
        this.nS.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void h(boolean bl2) {
        int n2 = bl2 ? 50 : 32;
        this.nW = new ch(this.nS, n2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        n2 = bl2 ? 11 : 9;
        layoutParams.addRule(n2);
        this.nW.i(this.nT.om);
        this.oc.addView((View)this.nW, (ViewGroup.LayoutParams)layoutParams);
    }

    public void i(boolean bl2) {
        if (this.nW != null) {
            this.nW.i(bl2);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onCreate(Bundle var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:366)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
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

    @Override
    public void onDestroy() {
        if (this.nU != null) {
            this.nU.destroy();
        }
        if (this.lC != null) {
            this.oc.removeView((View)this.lC);
        }
        this.aN();
    }

    @Override
    public void onPause() {
        if (this.nU != null) {
            this.nU.pause();
        }
        this.aL();
        if (!(this.lC == null || this.nS.isFinishing() && this.nV != null)) {
            dq.a(this.lC);
        }
        this.aN();
    }

    @Override
    public void onRestart() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onResume() {
        if (this.nT != null && this.nT.op == 4) {
            if (this.oa) {
                this.nS.finish();
            } else {
                this.oa = true;
            }
        }
        if (this.lC != null) {
            dq.b(this.lC);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.oa);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        this.aN();
    }

    public void setRequestedOrientation(int n2) {
        this.nS.setRequestedOrientation(n2);
    }

    private static final class a
    extends Exception {
        public a(String string2) {
            super(string2);
        }
    }

    private static final class b
    extends RelativeLayout {
        private final dr kF;

        public b(Context context, String string2) {
            super(context);
            this.kF = new dr(context, string2);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            this.kF.c(motionEvent);
            return false;
        }
    }

    private static final class c {
        public final int index;
        public final ViewGroup.LayoutParams oe;
        public final ViewGroup of;

        public c(dz dz2) throws a {
            this.oe = dz2.getLayoutParams();
            ViewParent viewParent = dz2.getParent();
            if (viewParent instanceof ViewGroup) {
                this.of = (ViewGroup)viewParent;
                this.index = this.of.indexOfChild((View)dz2);
                this.of.removeView((View)dz2);
                dz2.p(true);
                return;
            }
            throw new a("Could not get the parent of the WebView for an overlay.");
        }
    }

}

