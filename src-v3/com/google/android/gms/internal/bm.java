package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.bn.C0328a;

public final class bm implements C0328a {
    private final ah kX;
    private final bq ky;
    private final Object li;
    private final Context mContext;
    private final String nn;
    private final long no;
    private final bi np;
    private final ak nq;
    private final dx nr;
    private br ns;
    private int nt;

    /* renamed from: com.google.android.gms.internal.bm.1 */
    class C03271 implements Runnable {
        final /* synthetic */ bl nu;
        final /* synthetic */ bm nv;

        C03271(bm bmVar, bl blVar) {
            this.nv = bmVar;
            this.nu = blVar;
        }

        public void run() {
            synchronized (this.nv.li) {
                if (this.nv.nt != -2) {
                    return;
                }
                this.nv.ns = this.nv.aJ();
                if (this.nv.ns == null) {
                    this.nv.m2051f(4);
                    return;
                }
                this.nu.m2909a(this.nv);
                this.nv.m2044a(this.nu);
            }
        }
    }

    public bm(Context context, String str, bq bqVar, bj bjVar, bi biVar, ah ahVar, ak akVar, dx dxVar) {
        this.li = new Object();
        this.nt = -2;
        this.mContext = context;
        this.nn = str;
        this.ky = bqVar;
        this.no = bjVar.nd != -1 ? bjVar.nd : 10000;
        this.np = biVar;
        this.kX = ahVar;
        this.nq = akVar;
        this.nr = dxVar;
    }

    private void m2043a(long j, long j2, long j3, long j4) {
        while (this.nt == -2) {
            m2047b(j, j2, j3, j4);
        }
    }

    private void m2044a(bl blVar) {
        try {
            if (this.nr.rs < 4100000) {
                if (this.nq.lT) {
                    this.ns.m669a(C1324e.m2710h(this.mContext), this.kX, this.np.nb, blVar);
                } else {
                    this.ns.m671a(C1324e.m2710h(this.mContext), this.nq, this.kX, this.np.nb, (bs) blVar);
                }
            } else if (this.nq.lT) {
                this.ns.m670a(C1324e.m2710h(this.mContext), this.kX, this.np.nb, this.np.mW, (bs) blVar);
            } else {
                this.ns.m672a(C1324e.m2710h(this.mContext), this.nq, this.kX, this.np.nb, this.np.mW, blVar);
            }
        } catch (Throwable e) {
            dw.m817c("Could not request ad from mediation adapter.", e);
            m2051f(5);
        }
    }

    private br aJ() {
        dw.m821x("Instantiating mediation adapter: " + this.nn);
        try {
            return this.ky.m668m(this.nn);
        } catch (Throwable e) {
            dw.m815a("Could not instantiate mediation adapter: " + this.nn, e);
            return null;
        }
    }

    private void m2047b(long j, long j2, long j3, long j4) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j5 = j2 - (elapsedRealtime - j);
        elapsedRealtime = j4 - (elapsedRealtime - j3);
        if (j5 <= 0 || elapsedRealtime <= 0) {
            dw.m821x("Timed out waiting for adapter.");
            this.nt = 3;
            return;
        }
        try {
            this.li.wait(Math.min(j5, elapsedRealtime));
        } catch (InterruptedException e) {
            this.nt = -1;
        }
    }

    public bn m2050b(long j, long j2) {
        bn bnVar;
        synchronized (this.li) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            bl blVar = new bl();
            dv.rp.post(new C03271(this, blVar));
            m2043a(elapsedRealtime, this.no, j, j2);
            bnVar = new bn(this.np, this.ns, this.nn, blVar, this.nt);
        }
        return bnVar;
    }

    public void cancel() {
        synchronized (this.li) {
            try {
                if (this.ns != null) {
                    this.ns.destroy();
                }
            } catch (Throwable e) {
                dw.m817c("Could not destroy mediation adapter.", e);
            }
            this.nt = -1;
            this.li.notify();
        }
    }

    public void m2051f(int i) {
        synchronized (this.li) {
            this.nt = i;
            this.li.notify();
        }
    }
}
