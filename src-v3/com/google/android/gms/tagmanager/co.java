package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0339c.C1364j;
import com.google.android.gms.tagmanager.C1418o.C1086e;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class co implements C1086e {
    private final String WJ;
    private String Xg;
    private bg<C1364j> Zf;
    private C0529r Zg;
    private final ScheduledExecutorService Zi;
    private final C0502a Zj;
    private ScheduledFuture<?> Zk;
    private boolean mClosed;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.co.a */
    interface C0502a {
        cn m1406a(C0529r c0529r);
    }

    /* renamed from: com.google.android.gms.tagmanager.co.b */
    interface C0503b {
        ScheduledExecutorService la();
    }

    /* renamed from: com.google.android.gms.tagmanager.co.1 */
    class C10651 implements C0503b {
        final /* synthetic */ co Zl;

        C10651(co coVar) {
            this.Zl = coVar;
        }

        public ScheduledExecutorService la() {
            return Executors.newSingleThreadScheduledExecutor();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.co.2 */
    class C10662 implements C0502a {
        final /* synthetic */ co Zl;

        C10662(co coVar) {
            this.Zl = coVar;
        }

        public cn m2500a(C0529r c0529r) {
            return new cn(this.Zl.mContext, this.Zl.WJ, c0529r);
        }
    }

    public co(Context context, String str, C0529r c0529r) {
        this(context, str, c0529r, null, null);
    }

    co(Context context, String str, C0529r c0529r, C0503b c0503b, C0502a c0502a) {
        this.Zg = c0529r;
        this.mContext = context;
        this.WJ = str;
        if (c0503b == null) {
            c0503b = new C10651(this);
        }
        this.Zi = c0503b.la();
        if (c0502a == null) {
            this.Zj = new C10662(this);
        } else {
            this.Zj = c0502a;
        }
    }

    private cn bK(String str) {
        cn a = this.Zj.m1406a(this.Zg);
        a.m1405a(this.Zf);
        a.bu(this.Xg);
        a.bJ(str);
        return a;
    }

    private synchronized void kZ() {
        if (this.mClosed) {
            throw new IllegalStateException("called method after closed");
        }
    }

    public synchronized void m3209a(bg<C1364j> bgVar) {
        kZ();
        this.Zf = bgVar;
    }

    public synchronized void bu(String str) {
        kZ();
        this.Xg = str;
    }

    public synchronized void m3210d(long j, String str) {
        bh.m1386y("loadAfterDelay: containerId=" + this.WJ + " delay=" + j);
        kZ();
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.Zk != null) {
            this.Zk.cancel(false);
        }
        this.Zk = this.Zi.schedule(bK(str), j, TimeUnit.MILLISECONDS);
    }

    public synchronized void release() {
        kZ();
        if (this.Zk != null) {
            this.Zk.cancel(false);
        }
        this.Zi.shutdown();
        this.mClosed = true;
    }
}
