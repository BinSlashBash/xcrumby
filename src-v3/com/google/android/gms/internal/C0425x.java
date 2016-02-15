package com.google.android.gms.internal;

import android.os.Handler;
import java.lang.ref.WeakReference;

/* renamed from: com.google.android.gms.internal.x */
public final class C0425x {
    private final C0424a kV;
    private final Runnable kW;
    private ah kX;
    private boolean kY;
    private boolean kZ;
    private long la;

    /* renamed from: com.google.android.gms.internal.x.1 */
    class C04231 implements Runnable {
        private final WeakReference<C1396v> lb;
        final /* synthetic */ C1396v lc;
        final /* synthetic */ C0425x ld;

        C04231(C0425x c0425x, C1396v c1396v) {
            this.ld = c0425x;
            this.lc = c1396v;
            this.lb = new WeakReference(this.lc);
        }

        public void run() {
            this.ld.kY = false;
            C1396v c1396v = (C1396v) this.lb.get();
            if (c1396v != null) {
                c1396v.m3170b(this.ld.kX);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.x.a */
    public static class C0424a {
        private final Handler mHandler;

        public C0424a(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long timeFromNowInMillis) {
            return this.mHandler.postDelayed(runnable, timeFromNowInMillis);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public C0425x(C1396v c1396v) {
        this(c1396v, new C0424a(dv.rp));
    }

    C0425x(C1396v c1396v, C0424a c0424a) {
        this.kY = false;
        this.kZ = false;
        this.la = 0;
        this.kV = c0424a;
        this.kW = new C04231(this, c1396v);
    }

    public void m1215a(ah ahVar, long j) {
        if (this.kY) {
            dw.m823z("An ad refresh is already scheduled.");
            return;
        }
        this.kX = ahVar;
        this.kY = true;
        this.la = j;
        if (!this.kZ) {
            dw.m821x("Scheduling ad refresh " + j + " milliseconds from now.");
            this.kV.postDelayed(this.kW, j);
        }
    }

    public void cancel() {
        this.kY = false;
        this.kV.removeCallbacks(this.kW);
    }

    public void m1216d(ah ahVar) {
        m1215a(ahVar, 60000);
    }

    public void pause() {
        this.kZ = true;
        if (this.kY) {
            this.kV.removeCallbacks(this.kW);
        }
    }

    public void resume() {
        this.kZ = false;
        if (this.kY) {
            this.kY = false;
            m1215a(this.kX, this.la);
        }
    }
}
