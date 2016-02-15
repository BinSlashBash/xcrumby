package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.webkit.WebView;
import com.google.android.gms.internal.ea.C0368a;
import org.apache.commons.codec.binary.Hex;

public class ct implements Runnable {
    private final int kr;
    private final int ks;
    protected final dz lC;
    private final Handler oT;
    private final long oU;
    private long oV;
    private C0368a oW;
    protected boolean oX;
    protected boolean oY;

    /* renamed from: com.google.android.gms.internal.ct.a */
    protected final class C0352a extends AsyncTask<Void, Void, Boolean> {
        private final WebView oZ;
        private Bitmap pa;
        final /* synthetic */ ct pb;

        public C0352a(ct ctVar, WebView webView) {
            this.pb = ctVar;
            this.oZ = webView;
        }

        protected synchronized Boolean m702a(Void... voidArr) {
            Boolean valueOf;
            int width = this.pa.getWidth();
            int height = this.pa.getHeight();
            if (width == 0 || height == 0) {
                valueOf = Boolean.valueOf(false);
            } else {
                int i = 0;
                for (int i2 = 0; i2 < width; i2 += 10) {
                    for (int i3 = 0; i3 < height; i3 += 10) {
                        if (this.pa.getPixel(i2, i3) != 0) {
                            i++;
                        }
                    }
                }
                valueOf = Boolean.valueOf(((double) i) / (((double) (width * height)) / 100.0d) > 0.1d);
            }
            return valueOf;
        }

        protected void m703a(Boolean bool) {
            ct.m706c(this.pb);
            if (bool.booleanValue() || this.pb.bc() || this.pb.oV <= 0) {
                this.pb.oY = bool.booleanValue();
                this.pb.oW.m836a(this.pb.lC);
            } else if (this.pb.oV > 0) {
                if (dw.m818n(2)) {
                    dw.m819v("Ad not detected, scheduling another run.");
                }
                this.pb.oT.postDelayed(this.pb, this.pb.oU);
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] x0) {
            return m702a((Void[]) x0);
        }

        protected /* synthetic */ void onPostExecute(Object x0) {
            m703a((Boolean) x0);
        }

        protected synchronized void onPreExecute() {
            this.pa = Bitmap.createBitmap(this.pb.kr, this.pb.ks, Config.ARGB_8888);
            this.oZ.setVisibility(0);
            this.oZ.measure(MeasureSpec.makeMeasureSpec(this.pb.kr, 0), MeasureSpec.makeMeasureSpec(this.pb.ks, 0));
            this.oZ.layout(0, 0, this.pb.kr, this.pb.ks);
            this.oZ.draw(new Canvas(this.pa));
            this.oZ.invalidate();
        }
    }

    public ct(C0368a c0368a, dz dzVar, int i, int i2) {
        this(c0368a, dzVar, i, i2, 200, 50);
    }

    public ct(C0368a c0368a, dz dzVar, int i, int i2, long j, long j2) {
        this.oU = j;
        this.oV = j2;
        this.oT = new Handler(Looper.getMainLooper());
        this.lC = dzVar;
        this.oW = c0368a;
        this.oX = false;
        this.oY = false;
        this.ks = i2;
        this.kr = i;
    }

    static /* synthetic */ long m706c(ct ctVar) {
        long j = ctVar.oV - 1;
        ctVar.oV = j;
        return j;
    }

    public void m711a(cz czVar, ee eeVar) {
        this.lC.setWebViewClient(eeVar);
        this.lC.loadDataWithBaseURL(TextUtils.isEmpty(czVar.ol) ? null : dq.m795r(czVar.ol), czVar.pm, "text/html", Hex.DEFAULT_CHARSET_NAME, null);
    }

    public void m712b(cz czVar) {
        m711a(czVar, new ee(this, this.lC, czVar.pv));
    }

    public void ba() {
        this.oT.postDelayed(this, this.oU);
    }

    public synchronized void bb() {
        this.oX = true;
    }

    public synchronized boolean bc() {
        return this.oX;
    }

    public boolean bd() {
        return this.oY;
    }

    public void run() {
        if (this.lC == null || bc()) {
            this.oW.m836a(this.lC);
        } else {
            new C0352a(this, this.lC).execute(new Void[0]);
        }
    }
}
