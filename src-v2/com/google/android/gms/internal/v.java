/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentCallbacks
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.ViewSwitcher
 */
package com.google.android.gms.internal;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ViewSwitcher;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.aa;
import com.google.android.gms.internal.ab;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.ap;
import com.google.android.gms.internal.ar;
import com.google.android.gms.internal.az;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.bk;
import com.google.android.gms.internal.bl;
import com.google.android.gms.internal.bo;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.ce;
import com.google.android.gms.internal.cf;
import com.google.android.gms.internal.ci;
import com.google.android.gms.internal.cm;
import com.google.android.gms.internal.cn;
import com.google.android.gms.internal.co;
import com.google.android.gms.internal.cr;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.di;
import com.google.android.gms.internal.dj;
import com.google.android.gms.internal.dl;
import com.google.android.gms.internal.dm;
import com.google.android.gms.internal.do;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dr;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.k;
import com.google.android.gms.internal.l;
import com.google.android.gms.internal.u;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.x;
import com.google.android.gms.internal.y;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;

public class v
extends ap.a
implements az,
bc,
bk,
cf,
ci,
cr.a,
dl,
u {
    private final x kA;
    private final aa kB;
    private boolean kC;
    private final ComponentCallbacks kD;
    private final bq ky;
    private final b kz;

    public v(Context context, ak ak2, String string2, bq bq2, dx dx2) {
        this.kD = new ComponentCallbacks(){

            public void onConfigurationChanged(Configuration configuration) {
                if (v.this.kz != null && v.a((v)v.this).kO != null && v.a((v)v.this).kO.oj != null) {
                    v.a((v)v.this).kO.oj.bE();
                }
            }

            public void onLowMemory() {
            }
        };
        this.kz = new b(context, ak2, string2, dx2);
        this.ky = bq2;
        this.kA = new x(this);
        this.kB = new aa();
        dw.x("Use AdRequest.Builder.addTestDevice(\"" + dv.l(context) + "\") to get test ads on this device.");
        dq.i(context);
        this.S();
    }

    private void S() {
        if (Build.VERSION.SDK_INT >= 14 && this.kz != null && this.kz.kI != null) {
            this.kz.kI.registerComponentCallbacks(this.kD);
        }
    }

    private void T() {
        if (Build.VERSION.SDK_INT >= 14 && this.kz != null && this.kz.kI != null) {
            this.kz.kI.unregisterComponentCallbacks(this.kD);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void a(int n2) {
        dw.z("Failed to load ad: " + n2);
        if (this.kz.kL == null) return;
        try {
            this.kz.kL.onAdFailedToLoad(n2);
            return;
        }
        catch (RemoteException var2_2) {
            dw.c("Could not call AdListener.onAdFailedToLoad().", (Throwable)var2_2);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void ad() {
        dw.x("Ad closing.");
        if (this.kz.kL == null) return;
        try {
            this.kz.kL.onAdClosed();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not call AdListener.onAdClosed().", (Throwable)var1_1);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void ae() {
        dw.x("Ad leaving application.");
        if (this.kz.kL == null) return;
        try {
            this.kz.kL.onAdLeftApplication();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not call AdListener.onAdLeftApplication().", (Throwable)var1_1);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void af() {
        dw.x("Ad opening.");
        if (this.kz.kL == null) return;
        try {
            this.kz.kL.onAdOpened();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not call AdListener.onAdOpened().", (Throwable)var1_1);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void ag() {
        dw.x("Ad finished loading.");
        if (this.kz.kL == null) return;
        try {
            this.kz.kL.onAdLoaded();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not call AdListener.onAdLoaded().", (Throwable)var1_1);
            return;
        }
    }

    private boolean ah() {
        boolean bl2 = true;
        if (!dq.a(this.kz.kI.getPackageManager(), this.kz.kI.getPackageName(), "android.permission.INTERNET")) {
            if (!this.kz.kN.lT) {
                dv.a((ViewGroup)this.kz.kG, this.kz.kN, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            }
            bl2 = false;
        }
        if (!dq.h(this.kz.kI)) {
            if (!this.kz.kN.lT) {
                dv.a((ViewGroup)this.kz.kG, this.kz.kN, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            }
            bl2 = false;
        }
        if (!bl2 && !this.kz.kN.lT) {
            this.kz.kG.setVisibility(0);
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ai() {
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping click URLs.");
            return;
        } else {
            dw.v("Pinging click URLs.");
            this.kz.kP.bl();
            if (this.kz.kO.ne != null) {
                dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.ne);
            }
            if (this.kz.kO.qt == null || this.kz.kO.qt.ne == null) return;
            {
                bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, false, this.kz.kO.qt.ne);
                return;
            }
        }
    }

    private void aj() {
        if (this.kz.kO != null) {
            this.kz.kO.oj.destroy();
            this.kz.kO = null;
        }
    }

    private void b(View view) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        this.kz.kG.addView(view, layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(boolean bl2) {
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping impression URLs.");
            return;
        } else {
            dw.v("Pinging Impression URLs.");
            this.kz.kP.bk();
            if (this.kz.kO.nf != null) {
                dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.nf);
            }
            if (this.kz.kO.qt != null && this.kz.kO.qt.nf != null) {
                bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, bl2, this.kz.kO.qt.nf);
            }
            if (this.kz.kO.nx == null || this.kz.kO.nx.na == null) return;
            {
                bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, bl2, this.kz.kO.nx.na);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean b(dh dh2) {
        if (dh2.po) {
            try {
                dh2 = (View)e.d(dh2.ny.getView());
            }
            catch (RemoteException var1_2) {
                dw.c("Could not get View from mediation adapter.", (Throwable)var1_2);
                return false;
            }
            View view = this.kz.kG.getNextView();
            if (view != null) {
                this.kz.kG.removeView(view);
            }
            try {
                this.b((View)dh2);
            }
            catch (Throwable var1_3) {
                dw.c("Could not add mediation view to view hierarchy.", var1_3);
                return false;
            }
        } else if (dh2.qu != null) {
            dh2.oj.a(dh2.qu);
            this.kz.kG.removeAllViews();
            this.kz.kG.setMinimumWidth(dh2.qu.widthPixels);
            this.kz.kG.setMinimumHeight(dh2.qu.heightPixels);
            this.b((View)dh2.oj);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.showNext();
        }
        if (this.kz.kO != null) {
            dh2 = this.kz.kG.getNextView();
            if (dh2 instanceof dz) {
                ((dz)((Object)dh2)).a(this.kz.kI, this.kz.kN);
            } else if (dh2 != null) {
                this.kz.kG.removeView((View)dh2);
            }
            if (this.kz.kO.ny != null) {
                try {
                    this.kz.kO.ny.destroy();
                }
                catch (RemoteException var1_4) {
                    dw.z("Could not destroy previous mediation adapter.");
                }
            }
        }
        this.kz.kG.setVisibility(0);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private cx.a c(ah ah2) {
        Object object;
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo = this.kz.kI.getApplicationInfo();
        try {
            packageInfo = this.kz.kI.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        }
        catch (PackageManager.NameNotFoundException var7_4) {
            packageInfo = null;
        }
        if (!this.kz.kN.lT && this.kz.kG.getParent() != null) {
            object = new int[2];
            this.kz.kG.getLocationOnScreen((int[])object);
            Object object2 = object[0];
            Object object3 = object[1];
            object = this.kz.kI.getResources().getDisplayMetrics();
            int n2 = this.kz.kG.getWidth();
            int n3 = this.kz.kG.getHeight();
            int n4 = this.kz.kG.isShown() && object2 + n2 > 0 && object3 + n3 > 0 && object2 <= object.widthPixels && object3 <= object.heightPixels ? 1 : 0;
            object = new Bundle(5);
            object.putInt("x", (int)object2);
            object.putInt("y", (int)object3);
            object.putInt("width", n2);
            object.putInt("height", n3);
            object.putInt("visible", n4);
        } else {
            object = null;
        }
        String string2 = dj.bs();
        this.kz.kP = new di(string2, this.kz.kH);
        this.kz.kP.f(ah2);
        Bundle bundle = dj.a(this, string2);
        return new cx.a((Bundle)object, ah2, this.kz.kN, this.kz.kH, applicationInfo, packageInfo, string2, dj.qK, this.kz.kK, bundle);
    }

    @Override
    public void P() {
        this.ai();
    }

    @Override
    public d Q() {
        fq.aj("getAdFrame must be called on the main UI thread.");
        return e.h(this.kz.kG);
    }

    @Override
    public ak R() {
        fq.aj("getAdSize must be called on the main UI thread.");
        return this.kz.kN;
    }

    @Override
    public void U() {
        this.ae();
    }

    @Override
    public void V() {
        this.kB.d(this.kz.kO);
        if (this.kz.kN.lT) {
            this.aj();
        }
        this.kC = false;
        this.ad();
        this.kz.kP.bm();
    }

    @Override
    public void W() {
        if (this.kz.kN.lT) {
            this.b(false);
        }
        this.kC = true;
        this.af();
    }

    @Override
    public void X() {
        this.P();
    }

    @Override
    public void Y() {
        this.V();
    }

    @Override
    public void Z() {
        this.U();
    }

    @Override
    public void a(ak ak2) {
        fq.aj("setAdSize must be called on the main UI thread.");
        this.kz.kN = ak2;
        if (this.kz.kO != null) {
            this.kz.kO.oj.a(ak2);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.removeView(this.kz.kG.getNextView());
        }
        this.kz.kG.setMinimumWidth(ak2.widthPixels);
        this.kz.kG.setMinimumHeight(ak2.heightPixels);
        this.kz.kG.requestLayout();
    }

    @Override
    public void a(ao ao2) {
        fq.aj("setAdListener must be called on the main UI thread.");
        this.kz.kL = ao2;
    }

    @Override
    public void a(ar ar2) {
        fq.aj("setAppEventListener must be called on the main UI thread.");
        this.kz.kQ = ar2;
    }

    @Override
    public void a(co co2) {
        fq.aj("setInAppPurchaseListener must be called on the main UI thread.");
        this.kz.kR = co2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(dh dh2) {
        int n2;
        int n3 = 0;
        this.kz.kM = null;
        if (dh2.errorCode != -2 && dh2.errorCode != 3) {
            dj.b(this.kz.ak());
        }
        if (dh2.errorCode == -1) {
            return;
        }
        boolean bl2 = dh2.pg.extras != null ? dh2.pg.extras.getBoolean("_noRefresh", false) : false;
        if (this.kz.kN.lT) {
            dq.a(dh2.oj);
        } else if (dh2.ni > 0) {
            this.kA.a(dh2.pg, dh2.ni);
        } else if (dh2.qt != null && dh2.qt.ni > 0) {
            this.kA.a(dh2.pg, dh2.qt.ni);
        } else if (!dh2.po && dh2.errorCode == 2) {
            this.kA.d(dh2.pg);
        }
        if (dh2.errorCode == 3 && dh2.qt != null && dh2.qt.ng != null) {
            dw.v("Pinging no fill URLs.");
            bo.a(this.kz.kI, this.kz.kK.rq, dh2, this.kz.kH, false, dh2.qt.ng);
        }
        if (dh2.errorCode != -2) {
            this.a(dh2.errorCode);
            return;
        }
        if (!this.kz.kN.lT) {
            if (!this.b(dh2)) {
                this.a(0);
                return;
            }
            if (this.kz.kG != null) {
                this.kz.kG.kF.t(dh2.pt);
            }
        }
        if (this.kz.kO != null && this.kz.kO.nA != null) {
            this.kz.kO.nA.a((bk)null);
        }
        if (dh2.nA != null) {
            dh2.nA.a(this);
        }
        this.kB.d(this.kz.kO);
        this.kz.kO = dh2;
        if (dh2.qu != null) {
            this.kz.kN = dh2.qu;
        }
        this.kz.kP.h(dh2.qv);
        this.kz.kP.i(dh2.qw);
        this.kz.kP.m(this.kz.kN.lT);
        this.kz.kP.n(dh2.po);
        if (!this.kz.kN.lT) {
            this.b(false);
        }
        if (this.kz.kS == null) {
            this.kz.kS = new dm(this.kz.kH);
        }
        if (dh2.qt != null) {
            n2 = dh2.qt.nj;
            n3 = dh2.qt.nk;
        } else {
            n2 = 0;
        }
        this.kz.kS.a(n2, n3);
        if (!this.kz.kN.lT && dh2.oj != null && (dh2.oj.bI().bP() || dh2.qs != null)) {
            ab ab2 = this.kB.a(this.kz.kN, this.kz.kO);
            if (dh2.oj.bI().bP() && ab2 != null) {
                ab2.a(new w(dh2.oj));
            }
        }
        this.kz.kO.oj.bE();
        this.ag();
    }

    @Override
    public void a(String string2, ArrayList<String> arrayList) {
        if (this.kz.kR == null) {
            dw.z("InAppPurchaseListener is not set");
            return;
        }
        try {
            this.kz.kR.a(new cm(string2, arrayList, this.kz.kI, this.kz.kK.rq));
            return;
        }
        catch (RemoteException var1_2) {
            dw.z("Could not start In-App purchase.");
            return;
        }
    }

    @Override
    public void a(HashSet<di> hashSet) {
        this.kz.a(hashSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean a(ah object) {
        fq.aj("loadAd must be called on the main UI thread.");
        if (this.kz.kM != null) {
            dw.z("An ad request is already in progress. Aborting.");
            return false;
        }
        if (this.kz.kN.lT && this.kz.kO != null) {
            dw.z("An interstitial is already loading. Aborting.");
            return false;
        }
        if (!this.ah()) return false;
        dw.x("Starting ad request.");
        this.kA.cancel();
        cx.a a2 = this.c((ah)object);
        if (this.kz.kN.lT) {
            object = dz.a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
            object.bI().a(this, null, this, this, true, this);
        } else {
            object = this.kz.kG.getNextView();
            if (object instanceof dz) {
                object = (dz)((Object)object);
                object.a(this.kz.kI, this.kz.kN);
            } else {
                dz dz2;
                if (object != null) {
                    this.kz.kG.removeView((View)object);
                }
                object = dz2 = dz.a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
                if (this.kz.kN.lU == null) {
                    this.b((View)dz2);
                    object = dz2;
                }
            }
            object.bI().a(this, this, this, this, false, this);
        }
        this.kz.kM = cr.a(this.kz.kI, a2, this.kz.kJ, (dz)((Object)object), this.ky, this);
        return true;
    }

    @Override
    public void aa() {
        this.W();
    }

    @Override
    public void ab() {
        if (this.kz.kO != null) {
            dw.z("Mediation adapter " + this.kz.kO.nz + " refreshed, but mediation adapters should never refresh.");
        }
        this.b(true);
        this.ag();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void ac() {
        fq.aj("recordManualImpression must be called on the main UI thread.");
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping manual tracking URLs.");
            return;
        } else {
            dw.v("Pinging manual tracking URLs.");
            if (this.kz.kO.pq == null) return;
            {
                dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.pq);
                return;
            }
        }
    }

    public void b(ah ah2) {
        ViewParent viewParent = this.kz.kG.getParent();
        if (viewParent instanceof View && ((View)viewParent).isShown() && dq.by() && !this.kC) {
            this.a(ah2);
            return;
        }
        dw.x("Ad is not visible. Not refreshing ad.");
        this.kA.d(ah2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void destroy() {
        fq.aj("destroy must be called on the main UI thread.");
        this.T();
        this.kz.kL = null;
        this.kz.kQ = null;
        this.kA.cancel();
        this.stopLoading();
        if (this.kz.kG != null) {
            this.kz.kG.removeAllViews();
        }
        if (this.kz.kO != null && this.kz.kO.oj != null) {
            this.kz.kO.oj.destroy();
        }
        if (this.kz.kO == null || this.kz.kO.ny == null) return;
        try {
            this.kz.kO.ny.destroy();
            return;
        }
        catch (RemoteException var1_1) {
            dw.z("Could not destroy mediation adapter.");
            return;
        }
    }

    @Override
    public boolean isReady() {
        fq.aj("isLoaded must be called on the main UI thread.");
        if (this.kz.kM == null && this.kz.kO != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onAppEvent(String string2, String string3) {
        if (this.kz.kQ == null) return;
        try {
            this.kz.kQ.onAppEvent(string2, string3);
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call the AppEventListener.", (Throwable)var1_2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void pause() {
        fq.aj("pause must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.a(this.kz.kO.oj);
        }
        if (this.kz.kO != null && this.kz.kO.ny != null) {
            try {
                this.kz.kO.ny.pause();
            }
            catch (RemoteException var1_1) {
                dw.z("Could not pause mediation adapter.");
            }
        }
        this.kA.pause();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void resume() {
        fq.aj("resume must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.b(this.kz.kO.oj);
        }
        if (this.kz.kO != null && this.kz.kO.ny != null) {
            try {
                this.kz.kO.ny.resume();
            }
            catch (RemoteException var1_1) {
                dw.z("Could not resume mediation adapter.");
            }
        }
        this.kA.resume();
    }

    @Override
    public void showInterstitial() {
        Object object;
        fq.aj("showInterstitial must be called on the main UI thread.");
        if (!this.kz.kN.lT) {
            dw.z("Cannot call showInterstitial on a banner ad.");
            return;
        }
        if (this.kz.kO == null) {
            dw.z("The interstitial has not loaded.");
            return;
        }
        if (this.kz.kO.oj.bL()) {
            dw.z("The interstitial is already showing.");
            return;
        }
        this.kz.kO.oj.p(true);
        if (this.kz.kO.oj.bI().bP() || this.kz.kO.qs != null) {
            object = this.kB.a(this.kz.kN, this.kz.kO);
            if (this.kz.kO.oj.bI().bP() && object != null) {
                object.a(new w(this.kz.kO.oj));
            }
        }
        if (this.kz.kO.po) {
            try {
                this.kz.kO.ny.showInterstitial();
                return;
            }
            catch (RemoteException var1_2) {
                dw.c("Could not show interstitial.", (Throwable)var1_2);
                this.aj();
                return;
            }
        }
        object = new ce((u)this, (cf)this, (ci)this, this.kz.kO.oj, this.kz.kO.orientation, this.kz.kK, this.kz.kO.pt);
        cc.a(this.kz.kI, (ce)object);
    }

    @Override
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

    private static final class a
    extends ViewSwitcher {
        private final dr kF;

        public a(Context context) {
            super(context);
            this.kF = new dr(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            this.kF.c(motionEvent);
            return false;
        }
    }

    private static final class b {
        public final a kG;
        public final String kH;
        public final Context kI;
        public final l kJ;
        public final dx kK;
        public ao kL;
        public do kM;
        public ak kN;
        public dh kO;
        public di kP;
        public ar kQ;
        public co kR;
        public dm kS = null;
        private HashSet<di> kT = null;

        /*
         * Enabled aggressive block sorting
         */
        public b(Context context, ak ak2, String string2, dx dx2) {
            if (ak2.lT) {
                this.kG = null;
            } else {
                this.kG = new a(context);
                this.kG.setMinimumWidth(ak2.widthPixels);
                this.kG.setMinimumHeight(ak2.heightPixels);
                this.kG.setVisibility(4);
            }
            this.kN = ak2;
            this.kH = string2;
            this.kI = context;
            this.kJ = new l(k.a(dx2.rq, context));
            this.kK = dx2;
        }

        public void a(HashSet<di> hashSet) {
            this.kT = hashSet;
        }

        public HashSet<di> ak() {
            return this.kT;
        }
    }

}

