/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.tagmanager.bh;
import java.io.IOException;

class a {
    private static a Wx;
    private static Object sf;
    private volatile long Ws = 900000;
    private volatile long Wt = 30000;
    private volatile long Wu;
    private final gl Wv;
    private a Ww;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private final Thread qY;
    private volatile AdvertisingIdClient.Info sh;

    static {
        sf = new Object();
    }

    private a(Context context) {
        this(context, null, gn.ft());
    }

    /*
     * Enabled aggressive block sorting
     */
    a(Context context, a a2, gl gl2) {
        this.Ww = new a(){

            @Override
            public AdvertisingIdClient.Info jW() {
                try {
                    AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(a.this.mContext);
                    return info;
                }
                catch (IllegalStateException var1_2) {
                    bh.z("IllegalStateException getting Advertising Id Info");
                    return null;
                }
                catch (GooglePlayServicesRepairableException var1_3) {
                    bh.z("GooglePlayServicesRepairableException getting Advertising Id Info");
                    return null;
                }
                catch (IOException var1_4) {
                    bh.z("IOException getting Ad Id Info");
                    return null;
                }
                catch (GooglePlayServicesNotAvailableException var1_5) {
                    bh.z("GooglePlayServicesNotAvailableException getting Advertising Id Info");
                    return null;
                }
                catch (Exception var1_6) {
                    bh.z("Unknown exception. Could not get the Advertising Id Info.");
                    return null;
                }
            }
        };
        this.Wv = gl2;
        this.mContext = context != null ? context.getApplicationContext() : context;
        if (a2 != null) {
            this.Ww = a2;
        }
        this.qY = new Thread(new Runnable(){

            @Override
            public void run() {
                a.this.jU();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static a E(Context context) {
        if (Wx == null) {
            Object object = sf;
            synchronized (object) {
                if (Wx == null) {
                    Wx = new a(context);
                    Wx.start();
                }
            }
        }
        return Wx;
    }

    private void jU() {
        Process.setThreadPriority((int)10);
        while (!this.mClosed) {
            try {
                this.sh = this.Ww.jW();
                Thread.sleep(this.Ws);
            }
            catch (InterruptedException var1_1) {
                bh.x("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void jV() {
        if (this.Wv.currentTimeMillis() - this.Wu < this.Wt) {
            return;
        }
        this.interrupt();
        this.Wu = this.Wv.currentTimeMillis();
    }

    void interrupt() {
        this.qY.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        this.jV();
        if (this.sh == null) {
            return true;
        }
        return this.sh.isLimitAdTrackingEnabled();
    }

    public String jT() {
        this.jV();
        if (this.sh == null) {
            return null;
        }
        return this.sh.getId();
    }

    void start() {
        this.qY.start();
    }

    public static interface a {
        public AdvertisingIdClient.Info jW();
    }

}

