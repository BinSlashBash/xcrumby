/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.ee;

public class ct
implements Runnable {
    private final int kr;
    private final int ks;
    protected final dz lC;
    private final Handler oT;
    private final long oU;
    private long oV;
    private ea.a oW;
    protected boolean oX;
    protected boolean oY;

    public ct(ea.a a2, dz dz2, int n2, int n3) {
        this(a2, dz2, n2, n3, 200, 50);
    }

    public ct(ea.a a2, dz dz2, int n2, int n3, long l2, long l3) {
        this.oU = l2;
        this.oV = l3;
        this.oT = new Handler(Looper.getMainLooper());
        this.lC = dz2;
        this.oW = a2;
        this.oX = false;
        this.oY = false;
        this.ks = n3;
        this.kr = n2;
    }

    static /* synthetic */ long c(ct ct2) {
        long l2;
        ct2.oV = l2 = ct2.oV - 1;
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(cz cz2, ee object) {
        this.lC.setWebViewClient((WebViewClient)object);
        dz dz2 = this.lC;
        object = TextUtils.isEmpty((CharSequence)cz2.ol) ? null : dq.r(cz2.ol);
        dz2.loadDataWithBaseURL((String)object, cz2.pm, "text/html", "UTF-8", null);
    }

    public void b(cz cz2) {
        this.a(cz2, new ee(this, this.lC, cz2.pv));
    }

    public void ba() {
        this.oT.postDelayed((Runnable)this, this.oU);
    }

    public void bb() {
        synchronized (this) {
            this.oX = true;
            return;
        }
    }

    public boolean bc() {
        synchronized (this) {
            boolean bl2 = this.oX;
            return bl2;
        }
    }

    public boolean bd() {
        return this.oY;
    }

    @Override
    public void run() {
        if (this.lC == null || this.bc()) {
            this.oW.a(this.lC);
            return;
        }
        new a(this.lC).execute((Object[])new Void[0]);
    }

    protected final class a
    extends AsyncTask<Void, Void, Boolean> {
        private final WebView oZ;
        private Bitmap pa;

        public a(WebView webView) {
            this.oZ = webView;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected /* varargs */ Boolean a(Void ... object) {
            synchronized (this) {
                int n2 = this.pa.getWidth();
                int n3 = this.pa.getHeight();
                if (n2 == 0) return false;
                if (n3 == 0) {
                    return false;
                }
                int n4 = 0;
                int n5 = 0;
                do {
                    if (n4 < n2) {
                    } else {
                        boolean bl2 = (double)n5 / ((double)(n2 * n3) / 100.0) > 0.1;
                        return bl2;
                    }
                    for (int i2 = 0; i2 < n3; i2 += 10) {
                        int n6 = n5;
                        if (this.pa.getPixel(n4, i2) != 0) {
                            n6 = n5 + 1;
                        }
                        n5 = n6;
                    }
                    n4 += 10;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void a(Boolean bl2) {
            ct.c(ct.this);
            if (bl2.booleanValue() || ct.this.bc() || ct.this.oV <= 0) {
                ct.this.oY = bl2;
                ct.this.oW.a(ct.this.lC);
                return;
            } else {
                if (ct.this.oV <= 0) return;
                {
                    if (dw.n(2)) {
                        dw.v("Ad not detected, scheduling another run.");
                    }
                    ct.this.oT.postDelayed((Runnable)ct.this, ct.this.oU);
                    return;
                }
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] arrobject) {
            return this.a((Void[])arrobject);
        }

        protected /* synthetic */ void onPostExecute(Object object) {
            this.a((Boolean)object);
        }

        protected void onPreExecute() {
            synchronized (this) {
                this.pa = Bitmap.createBitmap((int)ct.this.kr, (int)ct.this.ks, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                this.oZ.setVisibility(0);
                this.oZ.measure(View.MeasureSpec.makeMeasureSpec((int)ct.this.kr, (int)0), View.MeasureSpec.makeMeasureSpec((int)ct.this.ks, (int)0));
                this.oZ.layout(0, 0, ct.this.kr, ct.this.ks);
                Canvas canvas = new Canvas(this.pa);
                this.oZ.draw(canvas);
                this.oZ.invalidate();
                return;
            }
        }
    }

}

