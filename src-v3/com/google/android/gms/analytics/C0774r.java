package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.android.gms.analytics.C0232u.C0231a;
import com.google.android.gms.location.GeofenceStatusCodes;

/* renamed from: com.google.android.gms.analytics.r */
class C0774r extends af {
    private static final Object sF;
    private static C0774r sR;
    private Context mContext;
    private Handler mHandler;
    private C0206d sG;
    private volatile C0208f sH;
    private int sI;
    private boolean sJ;
    private boolean sK;
    private String sL;
    private boolean sM;
    private boolean sN;
    private C0207e sO;
    private C0218q sP;
    private boolean sQ;

    /* renamed from: com.google.android.gms.analytics.r.2 */
    class C02192 implements Callback {
        final /* synthetic */ C0774r sS;

        C02192(C0774r c0774r) {
            this.sS = c0774r;
        }

        public boolean handleMessage(Message msg) {
            if (1 == msg.what && C0774r.sF.equals(msg.obj)) {
                C0232u.cy().m69t(true);
                this.sS.dispatchLocalHits();
                C0232u.cy().m69t(false);
                if (this.sS.sI > 0 && !this.sS.sQ) {
                    this.sS.mHandler.sendMessageDelayed(this.sS.mHandler.obtainMessage(1, C0774r.sF), (long) (this.sS.sI * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE));
                }
            }
            return true;
        }
    }

    /* renamed from: com.google.android.gms.analytics.r.1 */
    class C07731 implements C0207e {
        final /* synthetic */ C0774r sS;

        C07731(C0774r c0774r) {
            this.sS = c0774r;
        }

        public void m1595r(boolean z) {
            this.sS.m1601a(z, this.sS.sM);
        }
    }

    static {
        sF = new Object();
    }

    private C0774r() {
        this.sI = 1800;
        this.sJ = true;
        this.sM = true;
        this.sN = true;
        this.sO = new C07731(this);
        this.sQ = false;
    }

    public static C0774r ci() {
        if (sR == null) {
            sR = new C0774r();
        }
        return sR;
    }

    private void cj() {
        this.sP = new C0218q(this);
        this.sP.m67o(this.mContext);
    }

    private void ck() {
        this.mHandler = new Handler(this.mContext.getMainLooper(), new C02192(this));
        if (this.sI > 0) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long) (this.sI * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE));
        }
    }

    synchronized void m1600a(Context context, C0208f c0208f) {
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
            if (this.sH == null) {
                this.sH = c0208f;
                if (this.sJ) {
                    dispatchLocalHits();
                    this.sJ = false;
                }
                if (this.sK) {
                    bY();
                    this.sK = false;
                }
            }
        }
    }

    synchronized void m1601a(boolean z, boolean z2) {
        if (!(this.sQ == z && this.sM == z2)) {
            if (z || !z2) {
                if (this.sI > 0) {
                    this.mHandler.removeMessages(1, sF);
                }
            }
            if (!z && z2 && this.sI > 0) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long) (this.sI * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE));
            }
            StringBuilder append = new StringBuilder().append("PowerSaveMode ");
            String str = (z || !z2) ? "initiated." : "terminated.";
            aa.m34y(append.append(str).toString());
            this.sQ = z;
            this.sM = z2;
        }
    }

    void bY() {
        if (this.sH == null) {
            aa.m34y("setForceLocalDispatch() queued. It will be called once initialization is complete.");
            this.sK = true;
            return;
        }
        C0232u.cy().m68a(C0231a.SET_FORCE_LOCAL_DISPATCH);
        this.sH.bY();
    }

    synchronized C0206d cl() {
        if (this.sG == null) {
            if (this.mContext == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.sG = new ac(this.sO, this.mContext);
            if (this.sL != null) {
                this.sG.bX().m60F(this.sL);
                this.sL = null;
            }
        }
        if (this.mHandler == null) {
            ck();
        }
        if (this.sP == null && this.sN) {
            cj();
        }
        return this.sG;
    }

    synchronized void cm() {
        if (!this.sQ && this.sM && this.sI > 0) {
            this.mHandler.removeMessages(1, sF);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, sF));
        }
    }

    synchronized void dispatchLocalHits() {
        if (this.sH == null) {
            aa.m34y("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.sJ = true;
        } else {
            C0232u.cy().m68a(C0231a.DISPATCH);
            this.sH.bW();
        }
    }

    synchronized void m1602s(boolean z) {
        m1601a(this.sQ, z);
    }

    synchronized void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        if (this.mHandler == null) {
            aa.m34y("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
            this.sI = dispatchPeriodInSeconds;
        } else {
            C0232u.cy().m68a(C0231a.SET_DISPATCH_PERIOD);
            if (!this.sQ && this.sM && this.sI > 0) {
                this.mHandler.removeMessages(1, sF);
            }
            this.sI = dispatchPeriodInSeconds;
            if (dispatchPeriodInSeconds > 0 && !this.sQ && this.sM) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, sF), (long) (dispatchPeriodInSeconds * GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE));
            }
        }
    }
}
