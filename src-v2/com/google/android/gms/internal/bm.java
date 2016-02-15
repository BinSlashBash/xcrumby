/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.RemoteException
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.bl;
import com.google.android.gms.internal.bn;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;

public final class bm
implements bn.a {
    private final ah kX;
    private final bq ky;
    private final Object li = new Object();
    private final Context mContext;
    private final String nn;
    private final long no;
    private final bi np;
    private final ak nq;
    private final dx nr;
    private br ns;
    private int nt = -2;

    /*
     * Enabled aggressive block sorting
     */
    public bm(Context context, String string2, bq bq2, bj bj2, bi bi2, ah ah2, ak ak2, dx dx2) {
        this.mContext = context;
        this.nn = string2;
        this.ky = bq2;
        long l2 = bj2.nd != -1 ? bj2.nd : 10000;
        this.no = l2;
        this.np = bi2;
        this.kX = ah2;
        this.nq = ak2;
        this.nr = dx2;
    }

    private void a(long l2, long l3, long l4, long l5) {
        while (this.nt == -2) {
            this.b(l2, l3, l4, l5);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(bl bl2) {
        try {
            if (this.nr.rs < 4100000) {
                if (this.nq.lT) {
                    this.ns.a(e.h(this.mContext), this.kX, this.np.nb, bl2);
                    return;
                }
                this.ns.a(e.h(this.mContext), this.nq, this.kX, this.np.nb, (bs)bl2);
                return;
            }
            if (this.nq.lT) {
                this.ns.a(e.h(this.mContext), this.kX, this.np.nb, this.np.mW, (bs)bl2);
                return;
            }
        }
        catch (RemoteException var1_2) {
            dw.c("Could not request ad from mediation adapter.", (Throwable)var1_2);
            this.f(5);
            return;
        }
        this.ns.a(e.h(this.mContext), this.nq, this.kX, this.np.nb, this.np.mW, bl2);
    }

    private br aJ() {
        dw.x("Instantiating mediation adapter: " + this.nn);
        try {
            br br2 = this.ky.m(this.nn);
            return br2;
        }
        catch (RemoteException var1_2) {
            dw.a("Could not instantiate mediation adapter: " + this.nn, (Throwable)var1_2);
            return null;
        }
    }

    private void b(long l2, long l3, long l4, long l5) {
        long l6 = SystemClock.elapsedRealtime();
        l2 = l3 - (l6 - l2);
        l3 = l5 - (l6 - l4);
        if (l2 <= 0 || l3 <= 0) {
            dw.x("Timed out waiting for adapter.");
            this.nt = 3;
            return;
        }
        try {
            this.li.wait(Math.min(l2, l3));
            return;
        }
        catch (InterruptedException var11_6) {
            this.nt = -1;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public bn b(long l2, long l3) {
        Object object = this.li;
        synchronized (object) {
            long l4 = SystemClock.elapsedRealtime();
            final bl bl2 = new bl();
            dv.rp.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    Object object = bm.this.li;
                    synchronized (object) {
                        if (bm.this.nt != -2) {
                            return;
                        }
                        bm.this.ns = bm.this.aJ();
                        if (bm.this.ns == null) {
                            bm.this.f(4);
                            return;
                        }
                        bl2.a(bm.this);
                        bm.this.a(bl2);
                        return;
                    }
                }
            });
            this.a(l4, this.no, l2, l3);
            return new bn(this.np, this.ns, this.nn, bl2, this.nt);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        Object object = this.li;
        synchronized (object) {
            try {
                if (this.ns != null) {
                    this.ns.destroy();
                }
            }
            catch (RemoteException var2_2) {
                dw.c("Could not destroy mediation adapter.", (Throwable)var2_2);
            }
            this.nt = -1;
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
    public void f(int n2) {
        Object object = this.li;
        synchronized (object) {
            this.nt = n2;
            this.li.notify();
            return;
        }
    }

}

