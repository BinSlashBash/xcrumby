package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.common.api.C0245a.C0789a;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.C0339c.C1360f;
import com.google.android.gms.internal.C0339c.C1364j;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.internal.it.C1393a;
import com.google.android.gms.tagmanager.C1417n.C0526a;
import com.google.android.gms.tagmanager.bg.C0499a;
import com.google.android.gms.tagmanager.cd.C0501a;
import com.google.android.gms.tagmanager.cq.C0509c;

/* renamed from: com.google.android.gms.tagmanager.o */
class C1418o extends C0789a<ContainerHolder> {
    private final Looper AS;
    private final String WJ;
    private long WO;
    private final TagManager WW;
    private final C1085d WZ;
    private final gl Wv;
    private final cf Xa;
    private final int Xb;
    private C1087f Xc;
    private volatile C1417n Xd;
    private volatile boolean Xe;
    private C1364j Xf;
    private String Xg;
    private C1086e Xh;
    private C0528a Xi;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.o.a */
    interface C0528a {
        boolean m1482b(Container container);
    }

    /* renamed from: com.google.android.gms.tagmanager.o.1 */
    class C10811 implements C0526a {
        final /* synthetic */ C1418o Xj;

        C10811(C1418o c1418o) {
            this.Xj = c1418o;
        }

        public void br(String str) {
            this.Xj.br(str);
        }

        public String ke() {
            return this.Xj.ke();
        }

        public void kg() {
            bh.m1387z("Refresh ignored: container loaded as default only.");
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.o.2 */
    class C10822 implements C0528a {
        final /* synthetic */ C1418o Xj;
        final /* synthetic */ boolean Xk;

        C10822(C1418o c1418o, boolean z) {
            this.Xj = c1418o;
            this.Xk = z;
        }

        public boolean m2532b(Container container) {
            return this.Xk ? container.getLastRefreshTime() + 43200000 >= this.Xj.Wv.currentTimeMillis() : !container.isDefault();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.o.b */
    private class C1083b implements bg<C1393a> {
        final /* synthetic */ C1418o Xj;

        private C1083b(C1418o c1418o) {
            this.Xj = c1418o;
        }

        public void m2533a(C1393a c1393a) {
            C1364j c1364j;
            if (c1393a.aaZ != null) {
                c1364j = c1393a.aaZ;
            } else {
                C1360f c1360f = c1393a.fK;
                c1364j = new C1364j();
                c1364j.fK = c1360f;
                c1364j.fJ = null;
                c1364j.fL = c1360f.fg;
            }
            this.Xj.m3231a(c1364j, c1393a.aaY, true);
        }

        public void m2534a(C0499a c0499a) {
            if (!this.Xj.Xe) {
                this.Xj.m3241t(0);
            }
        }

        public /* synthetic */ void m2535i(Object obj) {
            m2533a((C1393a) obj);
        }

        public void kl() {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.o.c */
    private class C1084c implements bg<C1364j> {
        final /* synthetic */ C1418o Xj;

        private C1084c(C1418o c1418o) {
            this.Xj = c1418o;
        }

        public void m2536a(C0499a c0499a) {
            if (this.Xj.Xd != null) {
                this.Xj.m1659a(this.Xj.Xd);
            } else {
                this.Xj.m1659a(this.Xj.ac(Status.By));
            }
            this.Xj.m3241t(3600000);
        }

        public void m2537b(C1364j c1364j) {
            synchronized (this.Xj) {
                if (c1364j.fK == null) {
                    if (this.Xj.Xf.fK == null) {
                        bh.m1384w("Current resource is null; network resource is also null");
                        this.Xj.m3241t(3600000);
                        return;
                    }
                    c1364j.fK = this.Xj.Xf.fK;
                }
                this.Xj.m3231a(c1364j, this.Xj.Wv.currentTimeMillis(), false);
                bh.m1386y("setting refresh time to current time: " + this.Xj.WO);
                if (!this.Xj.kk()) {
                    this.Xj.m3230a(c1364j);
                }
            }
        }

        public /* synthetic */ void m2538i(Object obj) {
            m2537b((C1364j) obj);
        }

        public void kl() {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.o.d */
    private class C1085d implements C0526a {
        final /* synthetic */ C1418o Xj;

        private C1085d(C1418o c1418o) {
            this.Xj = c1418o;
        }

        public void br(String str) {
            this.Xj.br(str);
        }

        public String ke() {
            return this.Xj.ke();
        }

        public void kg() {
            if (this.Xj.Xa.cS()) {
                this.Xj.m3241t(0);
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.o.e */
    interface C1086e extends Releasable {
        void m2539a(bg<C1364j> bgVar);

        void bu(String str);

        void m2540d(long j, String str);
    }

    /* renamed from: com.google.android.gms.tagmanager.o.f */
    interface C1087f extends Releasable {
        void m2541a(bg<C1393a> bgVar);

        void m2542b(C1393a c1393a);

        C0509c ca(int i);

        void km();
    }

    C1418o(Context context, TagManager tagManager, Looper looper, String str, int i, C1087f c1087f, C1086e c1086e, gl glVar, cf cfVar) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.WW = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.AS = looper;
        this.WJ = str;
        this.Xb = i;
        this.Xc = c1087f;
        this.Xh = c1086e;
        this.WZ = new C1085d();
        this.Xf = new C1364j();
        this.Wv = glVar;
        this.Xa = cfVar;
        if (kk()) {
            br(cd.kT().kV());
        }
    }

    public C1418o(Context context, TagManager tagManager, Looper looper, String str, int i, C0529r c0529r) {
        this(context, tagManager, looper, str, i, new cp(context, str), new co(context, str, c0529r), gn.ft(), new bf(30, 900000, 5000, "refreshing", gn.ft()));
    }

    private void m3228C(boolean z) {
        this.Xc.m2541a(new C1083b());
        this.Xh.m2539a(new C1084c());
        C0509c ca = this.Xc.ca(this.Xb);
        if (ca != null) {
            this.Xd = new C1417n(this.WW, this.AS, new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0, ca), this.WZ);
        }
        this.Xi = new C10822(this, z);
        if (kk()) {
            this.Xh.m2540d(0, UnsupportedUrlFragment.DISPLAY_NAME);
        } else {
            this.Xc.km();
        }
    }

    private synchronized void m3230a(C1364j c1364j) {
        if (this.Xc != null) {
            C1393a c1393a = new C1393a();
            c1393a.aaY = this.WO;
            c1393a.fK = new C1360f();
            c1393a.aaZ = c1364j;
            this.Xc.m2542b(c1393a);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m3231a(com.google.android.gms.internal.C0339c.C1364j r9, long r10, boolean r12) {
        /*
        r8 = this;
        r6 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        monitor-enter(r8);
        if (r12 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r8.Xe;	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r8);
        return;
    L_0x000c:
        r0 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r8.Xd;	 Catch:{ all -> 0x006a }
        if (r0 != 0) goto L_0x0016;
    L_0x0016:
        r8.Xf = r9;	 Catch:{ all -> 0x006a }
        r8.WO = r10;	 Catch:{ all -> 0x006a }
        r0 = 0;
        r2 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        r4 = r8.WO;	 Catch:{ all -> 0x006a }
        r4 = r4 + r6;
        r6 = r8.Wv;	 Catch:{ all -> 0x006a }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x006a }
        r4 = r4 - r6;
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ all -> 0x006a }
        r0 = java.lang.Math.max(r0, r2);	 Catch:{ all -> 0x006a }
        r8.m3241t(r0);	 Catch:{ all -> 0x006a }
        r0 = new com.google.android.gms.tagmanager.Container;	 Catch:{ all -> 0x006a }
        r1 = r8.mContext;	 Catch:{ all -> 0x006a }
        r2 = r8.WW;	 Catch:{ all -> 0x006a }
        r2 = r2.getDataLayer();	 Catch:{ all -> 0x006a }
        r3 = r8.WJ;	 Catch:{ all -> 0x006a }
        r4 = r10;
        r6 = r9;
        r0.<init>(r1, r2, r3, r4, r6);	 Catch:{ all -> 0x006a }
        r1 = r8.Xd;	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x006d;
    L_0x0049:
        r1 = new com.google.android.gms.tagmanager.n;	 Catch:{ all -> 0x006a }
        r2 = r8.WW;	 Catch:{ all -> 0x006a }
        r3 = r8.AS;	 Catch:{ all -> 0x006a }
        r4 = r8.WZ;	 Catch:{ all -> 0x006a }
        r1.<init>(r2, r3, r0, r4);	 Catch:{ all -> 0x006a }
        r8.Xd = r1;	 Catch:{ all -> 0x006a }
    L_0x0056:
        r1 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x000a;
    L_0x005c:
        r1 = r8.Xi;	 Catch:{ all -> 0x006a }
        r0 = r1.m1482b(r0);	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000a;
    L_0x0064:
        r0 = r8.Xd;	 Catch:{ all -> 0x006a }
        r8.m1659a(r0);	 Catch:{ all -> 0x006a }
        goto L_0x000a;
    L_0x006a:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x006d:
        r1 = r8.Xd;	 Catch:{ all -> 0x006a }
        r1.m3227a(r0);	 Catch:{ all -> 0x006a }
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.o.a(com.google.android.gms.internal.c$j, long, boolean):void");
    }

    private boolean kk() {
        cd kT = cd.kT();
        return (kT.kU() == C0501a.CONTAINER || kT.kU() == C0501a.CONTAINER_DEBUG) && this.WJ.equals(kT.getContainerId());
    }

    private synchronized void m3241t(long j) {
        if (this.Xh == null) {
            bh.m1387z("Refresh requested, but no network load scheduler.");
        } else {
            this.Xh.m2540d(j, this.Xf.fL);
        }
    }

    protected ContainerHolder ac(Status status) {
        if (this.Xd != null) {
            return this.Xd;
        }
        if (status == Status.By) {
            bh.m1384w("timer expired: setting result to failure");
        }
        return new C1417n(status);
    }

    synchronized void br(String str) {
        this.Xg = str;
        if (this.Xh != null) {
            this.Xh.bu(str);
        }
    }

    protected /* synthetic */ Result m3242d(Status status) {
        return ac(status);
    }

    synchronized String ke() {
        return this.Xg;
    }

    public void kh() {
        C0509c ca = this.Xc.ca(this.Xb);
        if (ca != null) {
            m1659a(new C1417n(this.WW, this.AS, new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0, ca), new C10811(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            bh.m1384w(str);
            m1659a(ac(new Status(10, str, null)));
        }
        this.Xh = null;
        this.Xc = null;
    }

    public void ki() {
        m3228C(false);
    }

    public void kj() {
        m3228C(true);
    }
}
