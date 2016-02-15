/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ag;
import com.google.android.gms.analytics.c;
import com.google.android.gms.analytics.f;
import com.google.android.gms.analytics.i;
import com.google.android.gms.analytics.r;
import com.google.android.gms.internal.ef;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

class s
implements ag,
c.b,
c.c {
    private final Context mContext;
    private com.google.android.gms.analytics.d sG;
    private final f sH;
    private boolean sJ;
    private volatile long sT;
    private volatile a sU;
    private volatile com.google.android.gms.analytics.b sV;
    private com.google.android.gms.analytics.d sW;
    private final GoogleAnalytics sX;
    private final Queue<d> sY = new ConcurrentLinkedQueue<d>();
    private volatile int sZ;
    private volatile Timer ta;
    private volatile Timer tb;
    private volatile Timer tc;
    private boolean td;
    private boolean te;
    private boolean tf;
    private i tg;
    private long th = 300000;

    s(Context context, f f2) {
        this(context, f2, null, GoogleAnalytics.getInstance(context));
    }

    s(Context context, f f2, com.google.android.gms.analytics.d d2, GoogleAnalytics googleAnalytics) {
        this.sW = d2;
        this.mContext = context;
        this.sH = f2;
        this.sX = googleAnalytics;
        this.tg = new i(){

            @Override
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.sZ = 0;
        this.sU = a.tq;
    }

    private Timer a(Timer timer) {
        if (timer != null) {
            timer.cancel();
        }
        return null;
    }

    private void be() {
        synchronized (this) {
            if (this.sV != null && this.sU == a.tl) {
                this.sU = a.tp;
                this.sV.disconnect();
            }
            return;
        }
    }

    private void co() {
        this.ta = this.a(this.ta);
        this.tb = this.a(this.tb);
        this.tc = this.a(this.tc);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void cq() {
        // MONITORENTER : this
        if (!Thread.currentThread().equals(this.sH.getThread())) {
            this.sH.bZ().add(()new Runnable(){

                @Override
                public void run() {
                    s.this.cq();
                }
            });
            return;
        }
        if (this.td) {
            this.bR();
        }
        switch (.tj[this.sU.ordinal()]) {
            case 1: {
                while (!this.sY.isEmpty()) {
                    var1_1 = this.sY.poll();
                    aa.y("Sending hit to store  " + var1_1);
                    this.sG.a(var1_1.cv(), var1_1.cw(), var1_1.getPath(), var1_1.cx());
                }
                if (!this.sJ) return;
                {
                    this.cr();
                    return;
                }
            }
            case 2: {
                ** GOTO lbl27
            }
            case 6: {
                aa.y("Need to reconnect");
                if (!this.sY.isEmpty()) {
                    this.ct();
                    return;
                }
                // MONITOREXIT : this
                return;
            }
        }
        return;
lbl27: // 1 sources:
        do {
            if (this.sY.isEmpty()) {
                this.sT = this.tg.currentTimeMillis();
                return;
            }
            var1_2 = this.sY.peek();
            aa.y("Sending hit to service   " + var1_2);
            if (!this.sX.isDryRunEnabled()) {
                this.sV.a(var1_2.cv(), var1_2.cw(), var1_2.getPath(), var1_2.cx());
            } else {
                aa.y("Dry run enabled. Hit not actually sent to service.");
            }
            this.sY.poll();
        } while (true);
    }

    private void cr() {
        this.sG.bW();
        this.sJ = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cs() {
        synchronized (this) {
            Object object = this.sU;
            a a2 = a.tm;
            if (object != a2) {
                this.co();
                aa.y("falling back to local store");
                if (this.sW != null) {
                    this.sG = this.sW;
                } else {
                    object = r.ci();
                    object.a(this.mContext, this.sH);
                    this.sG = object.cl();
                }
                this.sU = a.tm;
                this.cq();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ct() {
        synchronized (this) {
            a a2;
            a a3;
            if (!this.tf && this.sV != null && (a3 = this.sU) != (a2 = a.tm)) {
                try {
                    ++this.sZ;
                    this.a(this.tb);
                    this.sU = a.tk;
                    this.tb = new Timer("Failed Connect");
                    this.tb.schedule((TimerTask)new c(), 3000);
                    aa.y("connecting to Analytics service");
                    this.sV.connect();
                }
                catch (SecurityException var1_2) {
                    aa.z("security exception on connectToService");
                    this.cs();
                }
            } else {
                aa.z("client not initialized.");
                this.cs();
            }
            return;
        }
    }

    private void cu() {
        this.ta = this.a(this.ta);
        this.ta = new Timer("Service Reconnect");
        this.ta.schedule((TimerTask)new e(), 5000);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(int n2, Intent intent) {
        synchronized (this) {
            this.sU = a.to;
            if (this.sZ < 2) {
                aa.z("Service unavailable (code=" + n2 + "), will retry.");
                this.cu();
            } else {
                aa.z("Service unavailable (code=" + n2 + "), using local store.");
                this.cs();
            }
            return;
        }
    }

    @Override
    public void b(Map<String, String> map, long l2, String string2, List<ef> list) {
        aa.y("putHit called");
        this.sY.add(new d(map, l2, string2, list));
        this.cq();
    }

    @Override
    public void bR() {
        aa.y("clearHits called");
        this.sY.clear();
        switch (.tj[this.sU.ordinal()]) {
            default: {
                this.td = true;
                return;
            }
            case 1: {
                this.sG.j(0);
                this.td = false;
                return;
            }
            case 2: 
        }
        this.sV.bR();
        this.td = false;
    }

    @Override
    public void bW() {
        switch (.tj[this.sU.ordinal()]) {
            default: {
                this.sJ = true;
            }
            case 2: {
                return;
            }
            case 1: 
        }
        this.cr();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void bY() {
        synchronized (this) {
            boolean bl2 = this.tf;
            if (!bl2) {
                aa.y("setForceLocalDispatch called.");
                this.tf = true;
                switch (.tj[this.sU.ordinal()]) {
                    case 1: 
                    case 4: 
                    case 5: 
                    case 6: {
                        break;
                    }
                    case 2: {
                        this.be();
                        break;
                    }
                    case 3: {
                        this.te = true;
                        break;
                    }
                }
            }
            return;
        }
    }

    @Override
    public void cp() {
        if (this.sV != null) {
            return;
        }
        this.sV = new com.google.android.gms.analytics.c(this.mContext, this, this);
        this.ct();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onConnected() {
        synchronized (this) {
            this.tb = this.a(this.tb);
            this.sZ = 0;
            aa.y("Connected to service");
            this.sU = a.tl;
            if (this.te) {
                this.be();
                this.te = false;
            } else {
                this.cq();
                this.tc = this.a(this.tc);
                this.tc = new Timer("disconnect check");
                this.tc.schedule((TimerTask)new b(), this.th);
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
    public void onDisconnected() {
        synchronized (this) {
            if (this.sU == a.tp) {
                aa.y("Disconnected from service");
                this.co();
                this.sU = a.tq;
            } else {
                aa.y("Unexpected disconnect.");
                this.sU = a.to;
                if (this.sZ < 2) {
                    this.cu();
                } else {
                    this.cs();
                }
            }
            return;
        }
    }

    private static enum a {
        tk,
        tl,
        tm,
        tn,
        to,
        tp,
        tq;
        

        private a() {
        }
    }

    private class b
    extends TimerTask {
        private b() {
        }

        @Override
        public void run() {
            if (s.this.sU == a.tl && s.this.sY.isEmpty() && s.this.sT + s.this.th < s.this.tg.currentTimeMillis()) {
                aa.y("Disconnecting due to inactivity");
                s.this.be();
                return;
            }
            s.this.tc.schedule((TimerTask)new b(), s.this.th);
        }
    }

    private class c
    extends TimerTask {
        private c() {
        }

        @Override
        public void run() {
            if (s.this.sU == a.tk) {
                s.this.cs();
            }
        }
    }

    private static class d {
        private final Map<String, String> ts;
        private final long tt;
        private final String tu;
        private final List<ef> tv;

        public d(Map<String, String> map, long l2, String string2, List<ef> list) {
            this.ts = map;
            this.tt = l2;
            this.tu = string2;
            this.tv = list;
        }

        public Map<String, String> cv() {
            return this.ts;
        }

        public long cw() {
            return this.tt;
        }

        public List<ef> cx() {
            return this.tv;
        }

        public String getPath() {
            return this.tu;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PATH: ");
            stringBuilder.append(this.tu);
            if (this.ts != null) {
                stringBuilder.append("  PARAMS: ");
                for (Map.Entry<String, String> entry : this.ts.entrySet()) {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append(entry.getValue());
                    stringBuilder.append(",  ");
                }
            }
            return stringBuilder.toString();
        }
    }

    private class e
    extends TimerTask {
        private e() {
        }

        @Override
        public void run() {
            s.this.ct();
        }
    }

}

