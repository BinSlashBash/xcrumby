package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.ck.C0852a;
import com.google.android.gms.internal.ea.C0368a;
import org.apache.commons.codec.binary.Hex;

public class cc extends C0852a {
    private dz lC;
    private final Activity nS;
    private ce nT;
    private cg nU;
    private C0342c nV;
    private ch nW;
    private boolean nX;
    private FrameLayout nY;
    private CustomViewCallback nZ;
    private boolean oa;
    private boolean ob;
    private RelativeLayout oc;

    /* renamed from: com.google.android.gms.internal.cc.a */
    private static final class C0340a extends Exception {
        public C0340a(String str) {
            super(str);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.b */
    private static final class C0341b extends RelativeLayout {
        private final dr kF;

        public C0341b(Context context, String str) {
            super(context);
            this.kF = new dr(context, str);
        }

        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.kF.m799c(event);
            return false;
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.c */
    private static final class C0342c {
        public final int index;
        public final LayoutParams oe;
        public final ViewGroup of;

        public C0342c(dz dzVar) throws C0340a {
            this.oe = dzVar.getLayoutParams();
            ViewParent parent = dzVar.getParent();
            if (parent instanceof ViewGroup) {
                this.of = (ViewGroup) parent;
                this.index = this.of.indexOfChild(dzVar);
                this.of.removeView(dzVar);
                dzVar.m835p(true);
                return;
            }
            throw new C0340a("Could not get the parent of the WebView for an overlay.");
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.1 */
    class C08501 implements C0368a {
        final /* synthetic */ cc od;

        C08501(cc ccVar) {
            this.od = ccVar;
        }

        public void m2062a(dz dzVar) {
            dzVar.bG();
        }
    }

    public cc(Activity activity) {
        this.oa = false;
        this.ob = false;
        this.nS = activity;
    }

    private static RelativeLayout.LayoutParams m2981a(int i, int i2, int i3, int i4) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i4);
        layoutParams.setMargins(i, i2, 0, 0);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        return layoutParams;
    }

    public static void m2982a(Context context, ce ceVar) {
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        intent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", ceVar.kK.rt);
        ce.m2064a(intent, ceVar);
        intent.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        if (!(context instanceof Activity)) {
            intent.addFlags(DriveFile.MODE_READ_ONLY);
        }
        context.startActivity(intent);
    }

    private void aN() {
        if (this.nS.isFinishing() && !this.ob) {
            this.ob = true;
            if (this.nS.isFinishing()) {
                if (this.lC != null) {
                    this.lC.bF();
                    this.oc.removeView(this.lC);
                    if (this.nV != null) {
                        this.lC.m835p(false);
                        this.nV.of.addView(this.lC, this.nV.index, this.nV.oe);
                    }
                }
                if (this.nT != null && this.nT.oi != null) {
                    this.nT.oi.m685V();
                }
            }
        }
    }

    private void m2983j(boolean z) throws C0340a {
        if (!this.nX) {
            this.nS.requestWindowFeature(1);
        }
        Window window = this.nS.getWindow();
        window.setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setRequestedOrientation(this.nT.orientation);
        if (VERSION.SDK_INT >= 11) {
            dw.m819v("Enabling hardware acceleration on the AdActivity window.");
            ds.m802a(window);
        }
        this.oc = new C0341b(this.nS, this.nT.or);
        this.oc.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.nS.setContentView(this.oc);
        m2984N();
        boolean bP = this.nT.oj.bI().bP();
        if (z) {
            this.lC = dz.m827a(this.nS, this.nT.oj.m828R(), true, bP, null, this.nT.kK);
            this.lC.bI().m842a(null, null, this.nT.ok, this.nT.oo, true, this.nT.oq);
            this.lC.bI().m841a(new C08501(this));
            if (this.nT.nO != null) {
                this.lC.loadUrl(this.nT.nO);
            } else if (this.nT.on != null) {
                this.lC.loadDataWithBaseURL(this.nT.ol, this.nT.on, "text/html", Hex.DEFAULT_CHARSET_NAME, null);
            } else {
                throw new C0340a("No URL or HTML to display in ad overlay.");
            }
        }
        this.lC = this.nT.oj;
        this.lC.setContext(this.nS);
        this.lC.m831a(this);
        ViewParent parent = this.lC.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this.lC);
        }
        this.oc.addView(this.lC, -1, -1);
        if (!z) {
            this.lC.bG();
        }
        m2988h(bP);
    }

    public void m2984N() {
        this.nX = true;
    }

    public void m2985a(View view, CustomViewCallback customViewCallback) {
        this.nY = new FrameLayout(this.nS);
        this.nY.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.nY.addView(view, -1, -1);
        this.nS.setContentView(this.nY);
        m2984N();
        this.nZ = customViewCallback;
    }

    public cg aK() {
        return this.nU;
    }

    public void aL() {
        if (this.nT != null) {
            setRequestedOrientation(this.nT.orientation);
        }
        if (this.nY != null) {
            this.nS.setContentView(this.oc);
            m2984N();
            this.nY.removeAllViews();
            this.nY = null;
        }
        if (this.nZ != null) {
            this.nZ.onCustomViewHidden();
            this.nZ = null;
        }
    }

    public void aM() {
        this.oc.removeView(this.nW);
        m2988h(true);
    }

    public void m2986b(int i, int i2, int i3, int i4) {
        if (this.nU != null) {
            this.nU.setLayoutParams(m2981a(i, i2, i3, i4));
        }
    }

    public void m2987c(int i, int i2, int i3, int i4) {
        if (this.nU == null) {
            this.nU = new cg(this.nS, this.lC);
            this.oc.addView(this.nU, 0, m2981a(i, i2, i3, i4));
            this.lC.bI().m847q(false);
        }
    }

    public void close() {
        this.nS.finish();
    }

    public void m2988h(boolean z) {
        this.nW = new ch(this.nS, z ? 50 : 32);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.nW.m695i(this.nT.om);
        this.oc.addView(this.nW, layoutParams);
    }

    public void m2989i(boolean z) {
        if (this.nW != null) {
            this.nW.m695i(z);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z = false;
        if (savedInstanceState != null) {
            z = savedInstanceState.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false);
        }
        this.oa = z;
        try {
            this.nT = ce.m2063a(this.nS.getIntent());
            if (this.nT == null) {
                throw new C0340a("Could not get info for ad overlay.");
            }
            if (savedInstanceState == null) {
                if (this.nT.oi != null) {
                    this.nT.oi.m686W();
                }
                if (!(this.nT.op == 1 || this.nT.oh == null)) {
                    this.nT.oh.m1210P();
                }
            }
            switch (this.nT.op) {
                case Std.STD_FILE /*1*/:
                    m2983j(false);
                case Std.STD_URL /*2*/:
                    this.nV = new C0342c(this.nT.oj);
                    m2983j(false);
                case Std.STD_URI /*3*/:
                    m2983j(true);
                case Std.STD_CLASS /*4*/:
                    if (this.oa) {
                        this.nS.finish();
                    } else if (!bz.m678a(this.nS, this.nT.og, this.nT.oo)) {
                        this.nS.finish();
                    }
                default:
                    throw new C0340a("Could not determine ad overlay type.");
            }
        } catch (C0340a e) {
            dw.m823z(e.getMessage());
            this.nS.finish();
        }
    }

    public void onDestroy() {
        if (this.nU != null) {
            this.nU.destroy();
        }
        if (this.lC != null) {
            this.oc.removeView(this.lC);
        }
        aN();
    }

    public void onPause() {
        if (this.nU != null) {
            this.nU.pause();
        }
        aL();
        if (this.lC != null && (!this.nS.isFinishing() || this.nV == null)) {
            dq.m781a(this.lC);
        }
        aN();
    }

    public void onRestart() {
    }

    public void onResume() {
        if (this.nT != null && this.nT.op == 4) {
            if (this.oa) {
                this.nS.finish();
            } else {
                this.oa = true;
            }
        }
        if (this.lC != null) {
            dq.m788b(this.lC);
        }
    }

    public void onSaveInstanceState(Bundle outBundle) {
        outBundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.oa);
    }

    public void onStart() {
    }

    public void onStop() {
        aN();
    }

    public void setRequestedOrientation(int requestedOrientation) {
        this.nS.setRequestedOrientation(requestedOrientation);
    }
}
