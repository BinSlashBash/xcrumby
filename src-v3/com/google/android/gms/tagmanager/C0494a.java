package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import java.io.IOException;

/* renamed from: com.google.android.gms.tagmanager.a */
class C0494a {
    private static C0494a Wx;
    private static Object sf;
    private volatile long Ws;
    private volatile long Wt;
    private volatile long Wu;
    private final gl Wv;
    private C0493a Ww;
    private volatile boolean mClosed;
    private final Context mContext;
    private final Thread qY;
    private volatile Info sh;

    /* renamed from: com.google.android.gms.tagmanager.a.2 */
    class C04922 implements Runnable {
        final /* synthetic */ C0494a Wy;

        C04922(C0494a c0494a) {
            this.Wy = c0494a;
        }

        public void run() {
            this.Wy.jU();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.a.a */
    public interface C0493a {
        Info jW();
    }

    /* renamed from: com.google.android.gms.tagmanager.a.1 */
    class C10601 implements C0493a {
        final /* synthetic */ C0494a Wy;

        C10601(C0494a c0494a) {
            this.Wy = c0494a;
        }

        public Info jW() {
            Info info = null;
            try {
                info = AdvertisingIdClient.getAdvertisingIdInfo(this.Wy.mContext);
            } catch (IllegalStateException e) {
                bh.m1387z("IllegalStateException getting Advertising Id Info");
            } catch (GooglePlayServicesRepairableException e2) {
                bh.m1387z("GooglePlayServicesRepairableException getting Advertising Id Info");
            } catch (IOException e3) {
                bh.m1387z("IOException getting Ad Id Info");
            } catch (GooglePlayServicesNotAvailableException e4) {
                bh.m1387z("GooglePlayServicesNotAvailableException getting Advertising Id Info");
            } catch (Exception e5) {
                bh.m1387z("Unknown exception. Could not get the Advertising Id Info.");
            }
            return info;
        }
    }

    static {
        sf = new Object();
    }

    private C0494a(Context context) {
        this(context, null, gn.ft());
    }

    C0494a(Context context, C0493a c0493a, gl glVar) {
        this.Ws = 900000;
        this.Wt = 30000;
        this.mClosed = false;
        this.Ww = new C10601(this);
        this.Wv = glVar;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (c0493a != null) {
            this.Ww = c0493a;
        }
        this.qY = new Thread(new C04922(this));
    }

    static C0494a m1356E(Context context) {
        if (Wx == null) {
            synchronized (sf) {
                if (Wx == null) {
                    Wx = new C0494a(context);
                    Wx.start();
                }
            }
        }
        return Wx;
    }

    private void jU() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            try {
                this.sh = this.Ww.jW();
                Thread.sleep(this.Ws);
            } catch (InterruptedException e) {
                bh.m1385x("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void jV() {
        if (this.Wv.currentTimeMillis() - this.Wu >= this.Wt) {
            interrupt();
            this.Wu = this.Wv.currentTimeMillis();
        }
    }

    void interrupt() {
        this.qY.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        jV();
        return this.sh == null ? true : this.sh.isLimitAdTrackingEnabled();
    }

    public String jT() {
        jV();
        return this.sh == null ? null : this.sh.getId();
    }

    void start() {
        this.qY.start();
    }
}
