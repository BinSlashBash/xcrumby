/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.bh;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.bl;
import com.google.android.gms.internal.bn;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.cr;
import com.google.android.gms.internal.ct;
import com.google.android.gms.internal.cu;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.do;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.l;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class cs
extends do
implements cu.a,
ea.a {
    private final bq ky;
    private final dz lC;
    private final Object li = new Object();
    private final Context mContext;
    private bj mR;
    private final cr.a oG;
    private final Object oH = new Object();
    private final cx.a oI;
    private final l oJ;
    private do oK;
    private cz oL;
    private boolean oM = false;
    private bh oN;
    private bn oO;

    public cs(Context context, cx.a a2, l l2, dz dz2, bq bq2, cr.a a3) {
        this.ky = bq2;
        this.oG = a3;
        this.lC = dz2;
        this.mContext = context;
        this.oI = a2;
        this.oJ = l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ak a(cx cx2) throws a {
        if (this.oL.pr == null) {
            throw new a("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] arrstring = this.oL.pr.split("x");
        if (arrstring.length != 2) {
            throw new a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
        try {}
        catch (NumberFormatException var1_2) {
            throw new a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
        int n2 = Integer.parseInt(arrstring[0]);
        int n3 = Integer.parseInt(arrstring[1]);
        arrstring = cx2.kN.lU;
        int n4 = arrstring.length;
        int n5 = 0;
        do {
            if (n5 >= n4) {
                throw new a("The ad size from the ad response was not one of the requested sizes: " + this.oL.pr, 0);
            }
            String string2 = arrstring[n5];
            float f2 = this.mContext.getResources().getDisplayMetrics().density;
            int n6 = string2.width == -1 ? (int)((float)string2.widthPixels / f2) : string2.width;
            int n7 = string2.height == -2 ? (int)((float)string2.heightPixels / f2) : string2.height;
            if (n2 == n6 && n3 == n7) {
                return new ak((ak)((Object)string2), cx2.kN.lU);
            }
            ++n5;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(cx cx2, long l2) throws a {
        Object object = this.oH;
        synchronized (object) {
            this.oN = new bh(this.mContext, cx2, this.ky, this.mR);
        }
        this.oO = this.oN.a(l2, 60000);
        switch (this.oO.nw) {
            default: {
                throw new a("Unexpected mediation result: " + this.oO.nw, 0);
            }
            case 1: {
                throw new a("No fill from any mediation ad networks.", 3);
            }
            case 0: 
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void aZ() throws a {
        if (this.oL.errorCode == -3) {
            return;
        }
        if (TextUtils.isEmpty((CharSequence)this.oL.pm)) {
            throw new a("No fill from ad server.", 3);
        }
        if (!this.oL.po) return;
        try {
            this.mR = new bj(this.oL.pm);
            return;
        }
        catch (JSONException var1_1) {
            throw new a("Could not parse mediation config: " + this.oL.pm, 0);
        }
    }

    private void b(long l2) throws a {
        dv.rp.post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = cs.this.li;
                synchronized (object) {
                    if (cs.c((cs)cs.this).errorCode != -2) {
                        return;
                    }
                    cs.this.lC.bI().a(cs.this);
                    if (cs.c((cs)cs.this).errorCode == -3) {
                        dw.y("Loading URL in WebView: " + cs.c((cs)cs.this).ol);
                        cs.this.lC.loadUrl(cs.c((cs)cs.this).ol);
                    } else {
                        dw.y("Loading HTML in WebView.");
                        cs.this.lC.loadDataWithBaseURL(dq.r(cs.c((cs)cs.this).ol), cs.c((cs)cs.this).pm, "text/html", "UTF-8", null);
                    }
                    return;
                }
            }
        });
        this.e(l2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void d(long l2) throws a {
        do {
            if (this.f(l2)) continue;
            throw new a("Timed out waiting for ad response.", 2);
        } while (this.oL == null);
        Object object = this.oH;
        // MONITORENTER : object
        this.oK = null;
        // MONITOREXIT : object
        if (this.oL.errorCode == -2) return;
        if (this.oL.errorCode == -3) return;
        throw new a("There was a problem getting an ad response. ErrorCode: " + this.oL.errorCode, this.oL.errorCode);
    }

    private void e(long l2) throws a {
        do {
            if (this.f(l2)) continue;
            throw new a("Timed out waiting for WebView to finish loading.", 2);
        } while (!this.oM);
    }

    private boolean f(long l2) throws a {
        l2 = 60000 - (SystemClock.elapsedRealtime() - l2);
        if (l2 <= 0) {
            return false;
        }
        try {
            this.li.wait(l2);
            return true;
        }
        catch (InterruptedException var3_2) {
            throw new a("Ad request cancelled.", -1);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(cz cz2) {
        Object object = this.li;
        synchronized (object) {
            dw.v("Received ad response.");
            this.oL = cz2;
            this.li.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(dz object) {
        object = this.li;
        synchronized (object) {
            dw.v("WebView finished loading.");
            this.oM = true;
            this.li.notify();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void aY() {
        var16_1 = this.li;
        // MONITORENTER : var16_1
        dw.v("AdLoaderBackgroundTask started.");
        var10_2 = this.oJ.y().a(this.mContext);
        var13_5 = new cx(this.oI, (String)var10_2);
        var10_2 = null;
        var12_6 = null;
        var1_7 = -2;
        var3_9 = var5_8 = -1;
        var11_10 = var12_6;
        var7_12 = SystemClock.elapsedRealtime();
        var3_9 = var5_8;
        var11_10 = var12_6;
        var15_13 = cu.a(this.mContext, (cx)var13_5, this);
        var3_9 = var5_8;
        var11_10 = var12_6;
        var14_14 = this.oH;
        var3_9 = var5_8;
        var11_10 = var12_6;
        // MONITORENTER : var14_14
        try {
            this.oK = var15_13;
            if (this.oK == null) {
                throw new a("Could not start the ad request service.", 0);
            }
            ** GOTO lbl33
        }
        catch (Throwable var10_3) {
            // MONITOREXIT : var14_14
            var3_9 = var5_8;
            var11_10 = var12_6;
            throw var10_3;
lbl33: // 1 sources:
            // MONITOREXIT : var14_14
            var3_9 = var5_8;
            var11_10 = var12_6;
            this.d(var7_12);
            var3_9 = var5_8;
            var11_10 = var12_6;
            var3_9 = var5_8 = SystemClock.elapsedRealtime();
            var11_10 = var12_6;
            this.aZ();
            var3_9 = var5_8;
            var11_10 = var12_6;
            if (var13_5.kN.lU != null) {
                var3_9 = var5_8;
                var11_10 = var12_6;
                var10_2 = this.a((cx)var13_5);
            }
            var3_9 = var5_8;
            var11_10 = var10_2;
            if (!this.oL.po) ** GOTO lbl56
            var3_9 = var5_8;
            var11_10 = var10_2;
            this.a((cx)var13_5, var7_12);
            ** GOTO lbl78
lbl56: // 1 sources:
            var3_9 = var5_8;
            var11_10 = var10_2;
            if (!this.oL.pu) ** GOTO lbl63
            var3_9 = var5_8;
            var11_10 = var10_2;
            this.c(var7_12);
            ** GOTO lbl78
lbl63: // 1 sources:
            var3_9 = var5_8;
            var11_10 = var10_2;
            try {
                this.b(var7_12);
                ** GOTO lbl78
            }
            catch (a var10_4) {
                block19 : {
                    var1_7 = var10_4.getErrorCode();
                    if (var1_7 == 3 || var1_7 == -1) {
                        dw.x(var10_4.getMessage());
                    } else {
                        dw.z(var10_4.getMessage());
                    }
                    this.oL = this.oL == null ? new cz(var1_7) : new cz(var1_7, this.oL.ni);
                    dv.rp.post(new Runnable(){

                        @Override
                        public void run() {
                            cs.this.onStop();
                        }
                    });
                    var10_2 = var11_10;
                    ** GOTO lbl79
lbl78: // 3 sources:
                    var3_9 = var5_8;
lbl79: // 2 sources:
                    if (!(var9_15 = TextUtils.isEmpty((CharSequence)this.oL.pw))) {
                        try {
                            var11_10 = new JSONObject(this.oL.pw);
                            break block19;
                        }
                        catch (Exception var11_11) {
                            dw.b("Error parsing the JSON for Active View.", var11_11);
                        }
                    }
                    var11_10 = null;
                }
                var17_16 = var13_5.pg;
                var18_17 = this.lC;
                var19_18 = this.oL.ne;
                var20_19 = this.oL.nf;
                var21_20 = this.oL.pq;
                var2_21 = this.oL.orientation;
                var5_8 = this.oL.ni;
                var22_22 = var13_5.pj;
                var9_15 = this.oL.po;
                var12_6 = this.oO != null ? this.oO.nx : null;
                var13_5 = this.oO != null ? this.oO.ny : null;
                var14_14 = this.oO != null ? this.oO.nz : null;
                var23_23 = this.mR;
                var15_13 = this.oO != null ? this.oO.nA : null;
                var10_2 = new dh(var17_16, var18_17, var19_18, var1_7, var20_19, var21_20, var2_21, var5_8, var22_22, var9_15, (bi)var12_6, (br)var13_5, (String)var14_14, var23_23, (bl)var15_13, this.oL.pp, (ak)var10_2, this.oL.pn, var3_9, this.oL.ps, this.oL.pt, (JSONObject)var11_10);
                dv.rp.post(new Runnable((dh)var10_2){
                    final /* synthetic */ dh oQ;

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        Object object = cs.this.li;
                        synchronized (object) {
                            cs.this.oG.a(this.oQ);
                            return;
                        }
                    }
                });
                // MONITOREXIT : var16_1
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void c(long l2) throws a {
        int n2;
        int n3;
        Object object = this.lC.R();
        if (object.lT) {
            n2 = this.mContext.getResources().getDisplayMetrics().widthPixels;
            n3 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        } else {
            n2 = object.widthPixels;
            n3 = object.heightPixels;
        }
        object = new ct(this, this.lC, n2, n3);
        dv.rp.post(new Runnable((ct)object){
            final /* synthetic */ ct oR;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = cs.this.li;
                synchronized (object) {
                    if (cs.c((cs)cs.this).errorCode != -2) {
                        return;
                    }
                    cs.this.lC.bI().a(cs.this);
                    this.oR.b(cs.this.oL);
                    return;
                }
            }
        });
        this.e(l2);
        if (object.bc()) {
            dw.v("Ad-Network indicated no fill with passback URL.");
            throw new a("AdNetwork sent passback url", 3);
        }
        if (!object.bd()) {
            throw new a("AdNetwork timed out", 2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onStop() {
        Object object = this.oH;
        synchronized (object) {
            if (this.oK != null) {
                this.oK.cancel();
            }
            this.lC.stopLoading();
            dq.a(this.lC);
            if (this.oN != null) {
                this.oN.cancel();
            }
            return;
        }
    }

    private static final class a
    extends Exception {
        private final int oS;

        public a(String string2, int n2) {
            super(string2);
            this.oS = n2;
        }

        public int getErrorCode() {
            return this.oS;
        }
    }

}

