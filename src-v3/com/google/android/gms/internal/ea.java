package com.google.android.gms.internal;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;
import java.util.Map;

public class ea extends WebViewClient {
    protected final dz lC;
    private final Object li;
    private az mF;
    private bc mP;
    private C0368a oW;
    private final HashMap<String, bb> rA;
    private C0419u rB;
    private cf rC;
    private boolean rD;
    private boolean rE;
    private ci rF;

    /* renamed from: com.google.android.gms.internal.ea.1 */
    class C03671 implements Runnable {
        final /* synthetic */ cc rG;
        final /* synthetic */ ea rH;

        C03671(ea eaVar, cc ccVar) {
            this.rH = eaVar;
            this.rG = ccVar;
        }

        public void run() {
            this.rG.aM();
        }
    }

    /* renamed from: com.google.android.gms.internal.ea.a */
    public interface C0368a {
        void m836a(dz dzVar);
    }

    public ea(dz dzVar, boolean z) {
        this.rA = new HashMap();
        this.li = new Object();
        this.rD = false;
        this.lC = dzVar;
        this.rE = z;
    }

    private static boolean m837c(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    private void m838d(Uri uri) {
        String path = uri.getPath();
        bb bbVar = (bb) this.rA.get(path);
        if (bbVar != null) {
            Map b = dq.m787b(uri);
            if (dw.m818n(2)) {
                dw.m822y("Received GMSG: " + path);
                for (String path2 : b.keySet()) {
                    dw.m822y("  " + path2 + ": " + ((String) b.get(path2)));
                }
            }
            bbVar.m657b(this.lC, b);
            return;
        }
        dw.m822y("No GMSG handler found for GMSG: " + uri);
    }

    public final void m839a(cb cbVar) {
        cf cfVar = null;
        boolean bL = this.lC.bL();
        C0419u c0419u = (!bL || this.lC.m828R().lT) ? this.rB : null;
        if (!bL) {
            cfVar = this.rC;
        }
        m840a(new ce(cbVar, c0419u, cfVar, this.rF, this.lC.bK()));
    }

    protected void m840a(ce ceVar) {
        cc.m2982a(this.lC.getContext(), ceVar);
    }

    public final void m841a(C0368a c0368a) {
        this.oW = c0368a;
    }

    public void m842a(C0419u c0419u, cf cfVar, az azVar, ci ciVar, boolean z, bc bcVar) {
        m843a("/appEvent", new ay(azVar));
        m843a("/canOpenURLs", ba.mH);
        m843a("/click", ba.mI);
        m843a("/close", ba.mJ);
        m843a("/customClose", ba.mK);
        m843a("/httpTrack", ba.mL);
        m843a("/log", ba.mM);
        m843a("/open", new bd(bcVar));
        m843a("/touch", ba.mN);
        m843a("/video", ba.mO);
        this.rB = c0419u;
        this.rC = cfVar;
        this.mF = azVar;
        this.mP = bcVar;
        this.rF = ciVar;
        m847q(z);
    }

    public final void m843a(String str, bb bbVar) {
        this.rA.put(str, bbVar);
    }

    public final void m844a(boolean z, int i) {
        C0419u c0419u = (!this.lC.bL() || this.lC.m828R().lT) ? this.rB : null;
        m840a(new ce(c0419u, this.rC, this.rF, this.lC, z, i, this.lC.bK()));
    }

    public final void m845a(boolean z, int i, String str) {
        cf cfVar = null;
        boolean bL = this.lC.bL();
        C0419u c0419u = (!bL || this.lC.m828R().lT) ? this.rB : null;
        if (!bL) {
            cfVar = this.rC;
        }
        m840a(new ce(c0419u, cfVar, this.mF, this.rF, this.lC, z, i, str, this.lC.bK(), this.mP));
    }

    public final void m846a(boolean z, int i, String str, String str2) {
        boolean bL = this.lC.bL();
        C0419u c0419u = (!bL || this.lC.m828R().lT) ? this.rB : null;
        m840a(new ce(c0419u, bL ? null : this.rC, this.mF, this.rF, this.lC, z, i, str, str2, this.lC.bK(), this.mP));
    }

    public final void aM() {
        synchronized (this.li) {
            this.rD = false;
            this.rE = true;
            cc bH = this.lC.bH();
            if (bH != null) {
                if (dv.bD()) {
                    bH.aM();
                } else {
                    dv.rp.post(new C03671(this, bH));
                }
            }
        }
    }

    public boolean bP() {
        boolean z;
        synchronized (this.li) {
            z = this.rE;
        }
        return z;
    }

    public final void onLoadResource(WebView webView, String url) {
        dw.m822y("Loading resource: " + url);
        Uri parse = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            m838d(parse);
        }
    }

    public final void onPageFinished(WebView webView, String url) {
        if (this.oW != null) {
            this.oW.m836a(this.lC);
            this.oW = null;
        }
    }

    public final void m847q(boolean z) {
        this.rD = z;
    }

    public final void reset() {
        synchronized (this.li) {
            this.rA.clear();
            this.rB = null;
            this.rC = null;
            this.oW = null;
            this.mF = null;
            this.rD = false;
            this.rE = false;
            this.mP = null;
            this.rF = null;
        }
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
        dw.m822y("AdWebView shouldOverrideUrlLoading: " + url);
        Uri parse = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            m838d(parse);
        } else if (this.rD && webView == this.lC && m837c(parse)) {
            return super.shouldOverrideUrlLoading(webView, url);
        } else {
            if (this.lC.willNotDraw()) {
                dw.m823z("AdWebView unable to handle URL: " + url);
            } else {
                Uri uri;
                try {
                    C0410l bJ = this.lC.bJ();
                    if (bJ != null && bJ.m1182a(parse)) {
                        parse = bJ.m1180a(parse, this.lC.getContext());
                    }
                    uri = parse;
                } catch (C0411m e) {
                    dw.m823z("Unable to append parameter to URL: " + url);
                    uri = parse;
                }
                m839a(new cb("android.intent.action.VIEW", uri.toString(), null, null, null, null, null));
            }
        }
        return true;
    }
}
