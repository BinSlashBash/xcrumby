/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bn;
import com.google.android.gms.internal.bq;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import java.util.Iterator;
import java.util.List;

public final class bh {
    private final bq ky;
    private final Object li = new Object();
    private final Context mContext;
    private final cx mQ;
    private final bj mR;
    private boolean mS = false;
    private bm mT;

    public bh(Context context, cx cx2, bq bq2, bj bj2) {
        this.mContext = context;
        this.mQ = cx2;
        this.ky = bq2;
        this.mR = bj2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public bn a(long l2, long l3) {
        dw.v("Starting mediation.");
        Iterator<bi> iterator = this.mR.nc.iterator();
        block3 : while (iterator.hasNext()) {
            bi bi2 = iterator.next();
            dw.x("Trying mediation network: " + bi2.mX);
            Iterator<String> iterator2 = bi2.mY.iterator();
            do {
                if (!iterator2.hasNext()) continue block3;
                String string2 = iterator2.next();
                Object object = this.li;
                synchronized (object) {
                    if (this.mS) {
                        return new bn(-1);
                    }
                    this.mT = new bm(this.mContext, string2, this.ky, this.mR, bi2, this.mQ.pg, this.mQ.kN, this.mQ.kK);
                }
                object = this.mT.b(l2, l3);
                if (object.nw == 0) {
                    dw.v("Adapter succeeded.");
                    return object;
                }
                if (object.ny == null) continue;
                dv.rp.post(new Runnable((bn)object){
                    final /* synthetic */ bn mU;

                    @Override
                    public void run() {
                        try {
                            this.mU.ny.destroy();
                            return;
                        }
                        catch (RemoteException var1_1) {
                            dw.c("Could not destroy mediation adapter.", (Throwable)var1_1);
                            return;
                        }
                    }
                });
            } while (true);
            break;
        }
        return new bn(1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        Object object = this.li;
        synchronized (object) {
            this.mS = true;
            if (this.mT != null) {
                this.mT.cancel();
            }
            return;
        }
    }

}

