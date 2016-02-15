/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Handler
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.ay;
import com.google.android.gms.internal.az;
import com.google.android.gms.internal.ba;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.bd;
import com.google.android.gms.internal.cb;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.ce;
import com.google.android.gms.internal.cf;
import com.google.android.gms.internal.ci;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.l;
import com.google.android.gms.internal.m;
import com.google.android.gms.internal.u;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ea
extends WebViewClient {
    protected final dz lC;
    private final Object li = new Object();
    private az mF;
    private bc mP;
    private a oW;
    private final HashMap<String, bb> rA = new HashMap();
    private u rB;
    private cf rC;
    private boolean rD = false;
    private boolean rE;
    private ci rF;

    public ea(dz dz2, boolean bl2) {
        this.lC = dz2;
        this.rE = bl2;
    }

    private static boolean c(Uri object) {
        if ("http".equalsIgnoreCase((String)(object = object.getScheme())) || "https".equalsIgnoreCase((String)object)) {
            return true;
        }
        return false;
    }

    private void d(Uri object) {
        String string2 = object.getPath();
        bb bb2 = this.rA.get(string2);
        if (bb2 != null) {
            object = dq.b((Uri)object);
            if (dw.n(2)) {
                dw.y("Received GMSG: " + (String)string2);
                for (String string3 : object.keySet()) {
                    dw.y("  " + string3 + ": " + (String)object.get(string3));
                }
            }
            bb2.b(this.lC, (Map<String, String>)object);
            return;
        }
        dw.y("No GMSG handler found for GMSG: " + object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(cb cb2) {
        cf cf2 = null;
        boolean bl2 = this.lC.bL();
        u u2 = bl2 && !this.lC.R().lT ? null : this.rB;
        if (!bl2) {
            cf2 = this.rC;
        }
        this.a(new ce(cb2, u2, cf2, this.rF, this.lC.bK()));
    }

    protected void a(ce ce2) {
        cc.a(this.lC.getContext(), ce2);
    }

    public final void a(a a2) {
        this.oW = a2;
    }

    public void a(u u2, cf cf2, az az2, ci ci2, boolean bl2, bc bc2) {
        this.a("/appEvent", new ay(az2));
        this.a("/canOpenURLs", ba.mH);
        this.a("/click", ba.mI);
        this.a("/close", ba.mJ);
        this.a("/customClose", ba.mK);
        this.a("/httpTrack", ba.mL);
        this.a("/log", ba.mM);
        this.a("/open", new bd(bc2));
        this.a("/touch", ba.mN);
        this.a("/video", ba.mO);
        this.rB = u2;
        this.rC = cf2;
        this.mF = az2;
        this.mP = bc2;
        this.rF = ci2;
        this.q(bl2);
    }

    public final void a(String string2, bb bb2) {
        this.rA.put(string2, bb2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(boolean bl2, int n2) {
        u u2 = this.lC.bL() && !this.lC.R().lT ? null : this.rB;
        this.a(new ce(u2, this.rC, this.rF, this.lC, bl2, n2, this.lC.bK()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(boolean bl2, int n2, String string2) {
        cf cf2 = null;
        boolean bl3 = this.lC.bL();
        u u2 = bl3 && !this.lC.R().lT ? null : this.rB;
        if (!bl3) {
            cf2 = this.rC;
        }
        this.a(new ce(u2, cf2, this.mF, this.rF, this.lC, bl2, n2, string2, this.lC.bK(), this.mP));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(boolean bl2, int n2, String string2, String string3) {
        boolean bl3 = this.lC.bL();
        u u2 = bl3 && !this.lC.R().lT ? null : this.rB;
        cf cf2 = bl3 ? null : this.rC;
        this.a(new ce(u2, cf2, this.mF, this.rF, this.lC, bl2, n2, string2, string3, this.lC.bK(), this.mP));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void aM() {
        Object object = this.li;
        synchronized (object) {
            this.rD = false;
            this.rE = true;
            final cc cc2 = this.lC.bH();
            if (cc2 != null) {
                if (!dv.bD()) {
                    dv.rp.post(new Runnable(){

                        @Override
                        public void run() {
                            cc2.aM();
                        }
                    });
                } else {
                    cc2.aM();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean bP() {
        Object object = this.li;
        synchronized (object) {
            return this.rE;
        }
    }

    public final void onLoadResource(WebView webView, String string2) {
        dw.y("Loading resource: " + string2);
        webView = Uri.parse((String)string2);
        if ("gmsg".equalsIgnoreCase(webView.getScheme()) && "mobileads.google.com".equalsIgnoreCase(webView.getHost())) {
            this.d((Uri)webView);
        }
    }

    public final void onPageFinished(WebView webView, String string2) {
        if (this.oW != null) {
            this.oW.a(this.lC);
            this.oW = null;
        }
    }

    public final void q(boolean bl2) {
        this.rD = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void reset() {
        Object object = this.li;
        synchronized (object) {
            this.rA.clear();
            this.rB = null;
            this.rC = null;
            this.oW = null;
            this.mF = null;
            this.rD = false;
            this.rE = false;
            this.mP = null;
            this.rF = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean shouldOverrideUrlLoading(WebView webView, String string2) {
        dw.y("AdWebView shouldOverrideUrlLoading: " + string2);
        Uri uri = Uri.parse((String)string2);
        if ("gmsg".equalsIgnoreCase(uri.getScheme()) && "mobileads.google.com".equalsIgnoreCase(uri.getHost())) {
            this.d(uri);
            return true;
        }
        if (this.rD && webView == this.lC && ea.c(uri)) {
            return super.shouldOverrideUrlLoading(webView, string2);
        }
        if (this.lC.willNotDraw()) {
            dw.z("AdWebView unable to handle URL: " + string2);
            return true;
        }
        try {
            l l2 = this.lC.bJ();
            webView = uri;
            if (l2 != null) {
                webView = uri;
                if (l2.a(uri)) {
                    webView = l2.a(uri, this.lC.getContext());
                }
            }
        }
        catch (m var1_2) {
            dw.z("Unable to append parameter to URL: " + string2);
            webView = uri;
        }
        this.a(new cb("android.intent.action.VIEW", webView.toString(), null, null, null, null, null));
        return true;
    }

    public static interface a {
        public void a(dz var1);
    }

}

