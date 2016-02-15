package com.google.android.gms.internal;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ViewSwitcher;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.ap.C0831a;
import com.google.android.gms.internal.cr.C0346a;
import com.google.android.gms.internal.cx.C0354a;
import java.util.ArrayList;
import java.util.HashSet;

/* renamed from: com.google.android.gms.internal.v */
public class C1396v extends C0831a implements az, bc, bk, cf, ci, C0346a, dl, C0419u {
    private final C0425x kA;
    private final aa kB;
    private boolean kC;
    private final ComponentCallbacks kD;
    private final bq ky;
    private final C0422b kz;

    /* renamed from: com.google.android.gms.internal.v.1 */
    class C04201 implements ComponentCallbacks {
        final /* synthetic */ C1396v kE;

        C04201(C1396v c1396v) {
            this.kE = c1396v;
        }

        public void onConfigurationChanged(Configuration newConfig) {
            if (this.kE.kz != null && this.kE.kz.kO != null && this.kE.kz.kO.oj != null) {
                this.kE.kz.kO.oj.bE();
            }
        }

        public void onLowMemory() {
        }
    }

    /* renamed from: com.google.android.gms.internal.v.a */
    private static final class C0421a extends ViewSwitcher {
        private final dr kF;

        public C0421a(Context context) {
            super(context);
            this.kF = new dr(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.kF.m799c(event);
            return false;
        }
    }

    /* renamed from: com.google.android.gms.internal.v.b */
    private static final class C0422b {
        public final C0421a kG;
        public final String kH;
        public final Context kI;
        public final C0410l kJ;
        public final dx kK;
        public ao kL;
        public C0359do kM;
        public ak kN;
        public dh kO;
        public di kP;
        public ar kQ;
        public co kR;
        public dm kS;
        private HashSet<di> kT;

        public C0422b(Context context, ak akVar, String str, dx dxVar) {
            this.kS = null;
            this.kT = null;
            if (akVar.lT) {
                this.kG = null;
            } else {
                this.kG = new C0421a(context);
                this.kG.setMinimumWidth(akVar.widthPixels);
                this.kG.setMinimumHeight(akVar.heightPixels);
                this.kG.setVisibility(4);
            }
            this.kN = akVar;
            this.kH = str;
            this.kI = context;
            this.kJ = new C0410l(C1499k.m3432a(dxVar.rq, context));
            this.kK = dxVar;
        }

        public void m1212a(HashSet<di> hashSet) {
            this.kT = hashSet;
        }

        public HashSet<di> ak() {
            return this.kT;
        }
    }

    public C1396v(Context context, ak akVar, String str, bq bqVar, dx dxVar) {
        this.kD = new C04201(this);
        this.kz = new C0422b(context, akVar, str, dxVar);
        this.ky = bqVar;
        this.kA = new C0425x(this);
        this.kB = new aa();
        dw.m821x("Use AdRequest.Builder.addTestDevice(\"" + dv.m813l(context) + "\") to get test ads on this device.");
        dq.m790i(context);
        m3145S();
    }

    private void m3145S() {
        if (VERSION.SDK_INT >= 14 && this.kz != null && this.kz.kI != null) {
            this.kz.kI.registerComponentCallbacks(this.kD);
        }
    }

    private void m3146T() {
        if (VERSION.SDK_INT >= 14 && this.kz != null && this.kz.kI != null) {
            this.kz.kI.unregisterComponentCallbacks(this.kD);
        }
    }

    private void m3148a(int i) {
        dw.m823z("Failed to load ad: " + i);
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdFailedToLoad(i);
            } catch (Throwable e) {
                dw.m817c("Could not call AdListener.onAdFailedToLoad().", e);
            }
        }
    }

    private void ad() {
        dw.m821x("Ad closing.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdClosed();
            } catch (Throwable e) {
                dw.m817c("Could not call AdListener.onAdClosed().", e);
            }
        }
    }

    private void ae() {
        dw.m821x("Ad leaving application.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdLeftApplication();
            } catch (Throwable e) {
                dw.m817c("Could not call AdListener.onAdLeftApplication().", e);
            }
        }
    }

    private void af() {
        dw.m821x("Ad opening.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdOpened();
            } catch (Throwable e) {
                dw.m817c("Could not call AdListener.onAdOpened().", e);
            }
        }
    }

    private void ag() {
        dw.m821x("Ad finished loading.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdLoaded();
            } catch (Throwable e) {
                dw.m817c("Could not call AdListener.onAdLoaded().", e);
            }
        }
    }

    private boolean ah() {
        boolean z = true;
        if (!dq.m784a(this.kz.kI.getPackageManager(), this.kz.kI.getPackageName(), "android.permission.INTERNET")) {
            if (!this.kz.kN.lT) {
                dv.m812a(this.kz.kG, this.kz.kN, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            }
            z = false;
        }
        if (!dq.m789h(this.kz.kI)) {
            if (!this.kz.kN.lT) {
                dv.m812a(this.kz.kG, this.kz.kN, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            }
            z = false;
        }
        if (!(z || this.kz.kN.lT)) {
            this.kz.kG.setVisibility(0);
        }
        return z;
    }

    private void ai() {
        if (this.kz.kO == null) {
            dw.m823z("Ad state was null when trying to ping click URLs.");
            return;
        }
        dw.m819v("Pinging click URLs.");
        this.kz.kP.bl();
        if (this.kz.kO.ne != null) {
            dq.m779a(this.kz.kI, this.kz.kK.rq, this.kz.kO.ne);
        }
        if (this.kz.kO.qt != null && this.kz.kO.qt.ne != null) {
            bo.m667a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, false, this.kz.kO.qt.ne);
        }
    }

    private void aj() {
        if (this.kz.kO != null) {
            this.kz.kO.oj.destroy();
            this.kz.kO = null;
        }
    }

    private void m3149b(View view) {
        this.kz.kG.addView(view, new LayoutParams(-2, -2));
    }

    private void m3150b(boolean z) {
        if (this.kz.kO == null) {
            dw.m823z("Ad state was null when trying to ping impression URLs.");
            return;
        }
        dw.m819v("Pinging Impression URLs.");
        this.kz.kP.bk();
        if (this.kz.kO.nf != null) {
            dq.m779a(this.kz.kI, this.kz.kK.rq, this.kz.kO.nf);
        }
        if (!(this.kz.kO.qt == null || this.kz.kO.qt.nf == null)) {
            bo.m667a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, z, this.kz.kO.qt.nf);
        }
        if (this.kz.kO.nx != null && this.kz.kO.nx.na != null) {
            bo.m667a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, z, this.kz.kO.nx.na);
        }
    }

    private boolean m3151b(dh dhVar) {
        View view;
        if (dhVar.po) {
            try {
                view = (View) C1324e.m2709d(dhVar.ny.getView());
                View nextView = this.kz.kG.getNextView();
                if (nextView != null) {
                    this.kz.kG.removeView(nextView);
                }
                try {
                    m3149b(view);
                } catch (Throwable th) {
                    dw.m817c("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            } catch (Throwable th2) {
                dw.m817c("Could not get View from mediation adapter.", th2);
                return false;
            }
        } else if (dhVar.qu != null) {
            dhVar.oj.m830a(dhVar.qu);
            this.kz.kG.removeAllViews();
            this.kz.kG.setMinimumWidth(dhVar.qu.widthPixels);
            this.kz.kG.setMinimumHeight(dhVar.qu.heightPixels);
            m3149b(dhVar.oj);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.showNext();
        }
        if (this.kz.kO != null) {
            view = this.kz.kG.getNextView();
            if (view instanceof dz) {
                ((dz) view).m829a(this.kz.kI, this.kz.kN);
            } else if (view != null) {
                this.kz.kG.removeView(view);
            }
            if (this.kz.kO.ny != null) {
                try {
                    this.kz.kO.ny.destroy();
                } catch (RemoteException e) {
                    dw.m823z("Could not destroy previous mediation adapter.");
                }
            }
        }
        this.kz.kG.setVisibility(0);
        return true;
    }

    private C0354a m3152c(ah ahVar) {
        PackageInfo packageInfo;
        Bundle bundle;
        ApplicationInfo applicationInfo = this.kz.kI.getApplicationInfo();
        try {
            packageInfo = this.kz.kI.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        if (this.kz.kN.lT || this.kz.kG.getParent() == null) {
            bundle = null;
        } else {
            int[] iArr = new int[2];
            this.kz.kG.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            DisplayMetrics displayMetrics = this.kz.kI.getResources().getDisplayMetrics();
            int width = this.kz.kG.getWidth();
            int height = this.kz.kG.getHeight();
            int i3 = (!this.kz.kG.isShown() || i + width <= 0 || i2 + height <= 0 || i > displayMetrics.widthPixels || i2 > displayMetrics.heightPixels) ? 0 : 1;
            bundle = new Bundle(5);
            bundle.putInt("x", i);
            bundle.putInt("y", i2);
            bundle.putInt("width", width);
            bundle.putInt("height", height);
            bundle.putInt("visible", i3);
        }
        String bs = dj.bs();
        this.kz.kP = new di(bs, this.kz.kH);
        this.kz.kP.m756f(ahVar);
        return new C0354a(bundle, ahVar, this.kz.kN, this.kz.kH, applicationInfo, packageInfo, bs, dj.qK, this.kz.kK, dj.m761a((dl) this, bs));
    }

    public void m3153P() {
        ai();
    }

    public C0306d m3154Q() {
        fq.aj("getAdFrame must be called on the main UI thread.");
        return C1324e.m2710h(this.kz.kG);
    }

    public ak m3155R() {
        fq.aj("getAdSize must be called on the main UI thread.");
        return this.kz.kN;
    }

    public void m3156U() {
        ae();
    }

    public void m3157V() {
        this.kB.m1997d(this.kz.kO);
        if (this.kz.kN.lT) {
            aj();
        }
        this.kC = false;
        ad();
        this.kz.kP.bm();
    }

    public void m3158W() {
        if (this.kz.kN.lT) {
            m3150b(false);
        }
        this.kC = true;
        af();
    }

    public void m3159X() {
        m3153P();
    }

    public void m3160Y() {
        m3157V();
    }

    public void m3161Z() {
        m3156U();
    }

    public void m3162a(ak akVar) {
        fq.aj("setAdSize must be called on the main UI thread.");
        this.kz.kN = akVar;
        if (this.kz.kO != null) {
            this.kz.kO.oj.m830a(akVar);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.removeView(this.kz.kG.getNextView());
        }
        this.kz.kG.setMinimumWidth(akVar.widthPixels);
        this.kz.kG.setMinimumHeight(akVar.heightPixels);
        this.kz.kG.requestLayout();
    }

    public void m3163a(ao aoVar) {
        fq.aj("setAdListener must be called on the main UI thread.");
        this.kz.kL = aoVar;
    }

    public void m3164a(ar arVar) {
        fq.aj("setAppEventListener must be called on the main UI thread.");
        this.kz.kQ = arVar;
    }

    public void m3165a(co coVar) {
        fq.aj("setInAppPurchaseListener must be called on the main UI thread.");
        this.kz.kR = coVar;
    }

    public void m3166a(dh dhVar) {
        int i = 0;
        this.kz.kM = null;
        if (!(dhVar.errorCode == -2 || dhVar.errorCode == 3)) {
            dj.m762b(this.kz.ak());
        }
        if (dhVar.errorCode != -1) {
            boolean z = dhVar.pg.extras != null ? dhVar.pg.extras.getBoolean("_noRefresh", false) : false;
            if (this.kz.kN.lT) {
                dq.m781a(dhVar.oj);
            } else if (!z) {
                if (dhVar.ni > 0) {
                    this.kA.m1215a(dhVar.pg, dhVar.ni);
                } else if (dhVar.qt != null && dhVar.qt.ni > 0) {
                    this.kA.m1215a(dhVar.pg, dhVar.qt.ni);
                } else if (!dhVar.po && dhVar.errorCode == 2) {
                    this.kA.m1216d(dhVar.pg);
                }
            }
            if (!(dhVar.errorCode != 3 || dhVar.qt == null || dhVar.qt.ng == null)) {
                dw.m819v("Pinging no fill URLs.");
                bo.m667a(this.kz.kI, this.kz.kK.rq, dhVar, this.kz.kH, false, dhVar.qt.ng);
            }
            if (dhVar.errorCode != -2) {
                m3148a(dhVar.errorCode);
                return;
            }
            int i2;
            if (!this.kz.kN.lT) {
                if (!m3151b(dhVar)) {
                    m3148a(0);
                    return;
                } else if (this.kz.kG != null) {
                    this.kz.kG.kF.m800t(dhVar.pt);
                }
            }
            if (!(this.kz.kO == null || this.kz.kO.nA == null)) {
                this.kz.kO.nA.m2908a(null);
            }
            if (dhVar.nA != null) {
                dhVar.nA.m2908a((bk) this);
            }
            this.kB.m1997d(this.kz.kO);
            this.kz.kO = dhVar;
            if (dhVar.qu != null) {
                this.kz.kN = dhVar.qu;
            }
            this.kz.kP.m757h(dhVar.qv);
            this.kz.kP.m758i(dhVar.qw);
            this.kz.kP.m759m(this.kz.kN.lT);
            this.kz.kP.m760n(dhVar.po);
            if (!this.kz.kN.lT) {
                m3150b(false);
            }
            if (this.kz.kS == null) {
                this.kz.kS = new dm(this.kz.kH);
            }
            if (dhVar.qt != null) {
                i2 = dhVar.qt.nj;
                i = dhVar.qt.nk;
            } else {
                i2 = 0;
            }
            this.kz.kS.m770a(i2, i);
            if (!(this.kz.kN.lT || dhVar.oj == null || (!dhVar.oj.bI().bP() && dhVar.qs == null))) {
                ab a = this.kB.m1994a(this.kz.kN, this.kz.kO);
                if (dhVar.oj.bI().bP() && a != null) {
                    a.m596a(new C0939w(dhVar.oj));
                }
            }
            this.kz.kO.oj.bE();
            ag();
        }
    }

    public void m3167a(String str, ArrayList<String> arrayList) {
        if (this.kz.kR == null) {
            dw.m823z("InAppPurchaseListener is not set");
            return;
        }
        try {
            this.kz.kR.m699a(new cm(str, arrayList, this.kz.kI, this.kz.kK.rq));
        } catch (RemoteException e) {
            dw.m823z("Could not start In-App purchase.");
        }
    }

    public void m3168a(HashSet<di> hashSet) {
        this.kz.m1212a(hashSet);
    }

    public boolean m3169a(ah ahVar) {
        fq.aj("loadAd must be called on the main UI thread.");
        if (this.kz.kM != null) {
            dw.m823z("An ad request is already in progress. Aborting.");
            return false;
        } else if (this.kz.kN.lT && this.kz.kO != null) {
            dw.m823z("An interstitial is already loading. Aborting.");
            return false;
        } else if (!ah()) {
            return false;
        } else {
            dz dzVar;
            dw.m821x("Starting ad request.");
            this.kA.cancel();
            C0354a c = m3152c(ahVar);
            if (this.kz.kN.lT) {
                dz a = dz.m827a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
                a.bI().m842a(this, null, this, this, true, this);
                dzVar = a;
            } else {
                dz dzVar2;
                View nextView = this.kz.kG.getNextView();
                if (nextView instanceof dz) {
                    dzVar2 = (dz) nextView;
                    dzVar2.m829a(this.kz.kI, this.kz.kN);
                } else {
                    if (nextView != null) {
                        this.kz.kG.removeView(nextView);
                    }
                    nextView = dz.m827a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
                    if (this.kz.kN.lU == null) {
                        m3149b(nextView);
                    }
                }
                dzVar2.bI().m842a(this, this, this, this, false, this);
                dzVar = dzVar2;
            }
            this.kz.kM = cr.m701a(this.kz.kI, c, this.kz.kJ, dzVar, this.ky, this);
            return true;
        }
    }

    public void aa() {
        m3158W();
    }

    public void ab() {
        if (this.kz.kO != null) {
            dw.m823z("Mediation adapter " + this.kz.kO.nz + " refreshed, but mediation adapters should never refresh.");
        }
        m3150b(true);
        ag();
    }

    public void ac() {
        fq.aj("recordManualImpression must be called on the main UI thread.");
        if (this.kz.kO == null) {
            dw.m823z("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        dw.m819v("Pinging manual tracking URLs.");
        if (this.kz.kO.pq != null) {
            dq.m779a(this.kz.kI, this.kz.kK.rq, this.kz.kO.pq);
        }
    }

    public void m3170b(ah ahVar) {
        ViewParent parent = this.kz.kG.getParent();
        if ((parent instanceof View) && ((View) parent).isShown() && dq.by() && !this.kC) {
            m3169a(ahVar);
            return;
        }
        dw.m821x("Ad is not visible. Not refreshing ad.");
        this.kA.m1216d(ahVar);
    }

    public void destroy() {
        fq.aj("destroy must be called on the main UI thread.");
        m3146T();
        this.kz.kL = null;
        this.kz.kQ = null;
        this.kA.cancel();
        stopLoading();
        if (this.kz.kG != null) {
            this.kz.kG.removeAllViews();
        }
        if (!(this.kz.kO == null || this.kz.kO.oj == null)) {
            this.kz.kO.oj.destroy();
        }
        if (this.kz.kO != null && this.kz.kO.ny != null) {
            try {
                this.kz.kO.ny.destroy();
            } catch (RemoteException e) {
                dw.m823z("Could not destroy mediation adapter.");
            }
        }
    }

    public boolean isReady() {
        fq.aj("isLoaded must be called on the main UI thread.");
        return this.kz.kM == null && this.kz.kO != null;
    }

    public void onAppEvent(String name, String info) {
        if (this.kz.kQ != null) {
            try {
                this.kz.kQ.onAppEvent(name, info);
            } catch (Throwable e) {
                dw.m817c("Could not call the AppEventListener.", e);
            }
        }
    }

    public void pause() {
        fq.aj("pause must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.m781a(this.kz.kO.oj);
        }
        if (!(this.kz.kO == null || this.kz.kO.ny == null)) {
            try {
                this.kz.kO.ny.pause();
            } catch (RemoteException e) {
                dw.m823z("Could not pause mediation adapter.");
            }
        }
        this.kA.pause();
    }

    public void resume() {
        fq.aj("resume must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.m788b(this.kz.kO.oj);
        }
        if (!(this.kz.kO == null || this.kz.kO.ny == null)) {
            try {
                this.kz.kO.ny.resume();
            } catch (RemoteException e) {
                dw.m823z("Could not resume mediation adapter.");
            }
        }
        this.kA.resume();
    }

    public void showInterstitial() {
        fq.aj("showInterstitial must be called on the main UI thread.");
        if (!this.kz.kN.lT) {
            dw.m823z("Cannot call showInterstitial on a banner ad.");
        } else if (this.kz.kO == null) {
            dw.m823z("The interstitial has not loaded.");
        } else if (this.kz.kO.oj.bL()) {
            dw.m823z("The interstitial is already showing.");
        } else {
            this.kz.kO.oj.m835p(true);
            if (this.kz.kO.oj.bI().bP() || this.kz.kO.qs != null) {
                ab a = this.kB.m1994a(this.kz.kN, this.kz.kO);
                if (this.kz.kO.oj.bI().bP() && a != null) {
                    a.m596a(new C0939w(this.kz.kO.oj));
                }
            }
            if (this.kz.kO.po) {
                try {
                    this.kz.kO.ny.showInterstitial();
                    return;
                } catch (Throwable e) {
                    dw.m817c("Could not show interstitial.", e);
                    aj();
                    return;
                }
            }
            cc.m2982a(this.kz.kI, new ce((C0419u) this, (cf) this, (ci) this, this.kz.kO.oj, this.kz.kO.orientation, this.kz.kK, this.kz.kO.pt));
        }
    }

    public void stopLoading() {
        fq.aj("stopLoading must be called on the main UI thread.");
        if (this.kz.kO != null) {
            this.kz.kO.oj.stopLoading();
            this.kz.kO = null;
        }
        if (this.kz.kM != null) {
            this.kz.kM.cancel();
        }
    }
}
