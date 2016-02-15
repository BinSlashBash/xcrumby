/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ac;
import com.google.android.gms.analytics.af;
import com.google.android.gms.analytics.d;
import com.google.android.gms.analytics.e;
import com.google.android.gms.analytics.f;
import com.google.android.gms.analytics.n;
import com.google.android.gms.analytics.q;
import com.google.android.gms.analytics.u;

class r
extends af {
    private static final Object sF = new Object();
    private static r sR;
    private Context mContext;
    private Handler mHandler;
    private d sG;
    private volatile f sH;
    private int sI = 1800;
    private boolean sJ = true;
    private boolean sK;
    private String sL;
    private boolean sM = true;
    private boolean sN = true;
    private e sO;
    private q sP;
    private boolean sQ;

    private r() {
        this.sO = new e(){

            @Override
            public void r(boolean bl2) {
                r.this.a(bl2, r.this.sM);
            }
        };
        this.sQ = false;
    }

    public static r ci() {
        if (sR == null) {
            sR = new r();
        }
        return sR;
    }

    private void cj() {
        this.sP = new q(this);
        this.sP.o(this.mContext);
    }

    private void ck() {
        this.mHandler = new Handler(this.mContext.getMainLooper(), new Handler.Callback(){

            public boolean handleMessage(Message message) {
                if (1 == message.what && sF.equals(message.obj)) {
                    u.cy().t(true);
                    r.this.dispatchLocalHits();
                    u.cy().t(false);
                    if (r.this.sI > 0 && !r.this.sQ) {
                        r.this.mHandler.sendMessageDelayed(r.this.mHandler.obtainMessage(1, sF), (long)(r.this.sI * 1000));
                    }
                }
                return true;
            }
        });
        if (this.sI > 0) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long)(this.sI * 1000));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(Context context, f f2) {
        synchronized (this) {
            Context context2 = this.mContext;
            if (context2 == null) {
                this.mContext = context.getApplicationContext();
                if (this.sH == null) {
                    void var2_2;
                    this.sH = var2_2;
                    if (this.sJ) {
                        this.dispatchLocalHits();
                        this.sJ = false;
                    }
                    if (this.sK) {
                        this.bY();
                        this.sK = false;
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(boolean bl2, boolean bl3) {
        synchronized (this) {
            boolean bl4;
            if (this.sQ != bl2 || (bl4 = this.sM) != bl3) {
                if ((bl2 || !bl3) && this.sI > 0) {
                    this.mHandler.removeMessages(1, sF);
                }
                if (!bl2 && bl3 && this.sI > 0) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long)(this.sI * 1000));
                }
                StringBuilder stringBuilder = new StringBuilder().append("PowerSaveMode ");
                String string2 = !bl2 && bl3 ? "terminated." : "initiated.";
                aa.y(stringBuilder.append(string2).toString());
                this.sQ = bl2;
                this.sM = bl3;
            }
            return;
        }
    }

    void bY() {
        if (this.sH == null) {
            aa.y("setForceLocalDispatch() queued. It will be called once initialization is complete.");
            this.sK = true;
            return;
        }
        u.cy().a(u.a.uN);
        this.sH.bY();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    d cl() {
        synchronized (this) {
            if (this.sG == null) {
                if (this.mContext == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.sG = new ac(this.sO, this.mContext);
                if (this.sL != null) {
                    this.sG.bX().F(this.sL);
                    this.sL = null;
                }
            }
            if (this.mHandler == null) {
                this.ck();
            }
            if (this.sP != null) return this.sG;
            if (!this.sN) return this.sG;
            this.cj();
            return this.sG;
        }
    }

    @Override
    void cm() {
        synchronized (this) {
            if (!this.sQ && this.sM && this.sI > 0) {
                this.mHandler.removeMessages(1, sF);
                this.mHandler.sendMessage(this.mHandler.obtainMessage(1, sF));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void dispatchLocalHits() {
        synchronized (this) {
            if (this.sH == null) {
                aa.y("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.sJ = true;
            } else {
                u.cy().a(u.a.uA);
                this.sH.bW();
            }
            return;
        }
    }

    @Override
    void s(boolean bl2) {
        synchronized (this) {
            this.a(this.sQ, bl2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void setLocalDispatchPeriod(int n2) {
        synchronized (this) {
            if (this.mHandler == null) {
                aa.y("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
                this.sI = n2;
            } else {
                u.cy().a(u.a.uB);
                if (!this.sQ && this.sM && this.sI > 0) {
                    this.mHandler.removeMessages(1, sF);
                }
                this.sI = n2;
                if (n2 > 0 && !this.sQ && this.sM) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long)(n2 * 1000));
                }
            }
            return;
        }
    }

}

