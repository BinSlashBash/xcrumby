/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.tagmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.c;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.internal.it;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.bf;
import com.google.android.gms.tagmanager.bg;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cd;
import com.google.android.gms.tagmanager.cf;
import com.google.android.gms.tagmanager.co;
import com.google.android.gms.tagmanager.cp;
import com.google.android.gms.tagmanager.cq;
import com.google.android.gms.tagmanager.n;
import com.google.android.gms.tagmanager.r;

class o
extends a.a<ContainerHolder> {
    private final Looper AS;
    private final String WJ;
    private long WO;
    private final TagManager WW;
    private final d WZ;
    private final gl Wv;
    private final cf Xa;
    private final int Xb;
    private f Xc;
    private volatile n Xd;
    private volatile boolean Xe;
    private c.j Xf;
    private String Xg;
    private e Xh;
    private a Xi;
    private final Context mContext;

    /*
     * Enabled aggressive block sorting
     */
    o(Context context, TagManager tagManager, Looper looper, String string2, int n2, f f2, e e2, gl gl2, cf cf2) {
        Looper looper2 = looper == null ? Looper.getMainLooper() : looper;
        super(looper2);
        this.mContext = context;
        this.WW = tagManager;
        context = looper;
        if (looper == null) {
            context = Looper.getMainLooper();
        }
        this.AS = context;
        this.WJ = string2;
        this.Xb = n2;
        this.Xc = f2;
        this.Xh = e2;
        this.WZ = new d();
        this.Xf = new c.j();
        this.Wv = gl2;
        this.Xa = cf2;
        if (this.kk()) {
            this.br(cd.kT().kV());
        }
    }

    public o(Context context, TagManager tagManager, Looper looper, String string2, int n2, r r2) {
        this(context, tagManager, looper, string2, n2, new cp(context, string2), new co(context, string2, r2), gn.ft(), new bf(30, 900000, 5000, "refreshing", gn.ft()));
    }

    private void C(final boolean bl2) {
        this.Xc.a(new b());
        this.Xh.a(new c());
        cq.c c2 = this.Xc.ca(this.Xb);
        if (c2 != null) {
            this.Xd = new n(this.WW, this.AS, new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0, c2), this.WZ);
        }
        this.Xi = new a(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public boolean b(Container container) {
                if (bl2) {
                    if (container.getLastRefreshTime() + 43200000 >= o.this.Wv.currentTimeMillis()) return true;
                    return false;
                }
                if (container.isDefault()) return false;
                return true;
            }
        };
        if (this.kk()) {
            this.Xh.d(0, "");
            return;
        }
        this.Xc.km();
    }

    @Override
    private void a(c.j j2) {
        synchronized (this) {
            if (this.Xc != null) {
                it.a a2 = new it.a();
                a2.aaY = this.WO;
                a2.fK = new c.f();
                a2.aaZ = j2;
                this.Xc.b(a2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(c.j object, long l2, boolean bl2) {
        synchronized (this) {
            if (!bl2 || !(bl2 = this.Xe)) {
                if (!this.isReady() || this.Xd == null) {
                    // empty if block
                }
                this.Xf = object;
                this.WO = l2;
                this.t(Math.max(0, Math.min(43200000, this.WO + 43200000 - this.Wv.currentTimeMillis())));
                object = new Container(this.mContext, this.WW.getDataLayer(), this.WJ, l2, (c.j)object);
                if (this.Xd == null) {
                    this.Xd = new n(this.WW, this.AS, (Container)object, this.WZ);
                } else {
                    this.Xd.a((Container)object);
                }
                if (!this.isReady() && this.Xi.b((Container)object)) {
                    this.a(this.Xd);
                }
            }
            return;
        }
    }

    static /* synthetic */ c.j c(o o2) {
        return o2.Xf;
    }

    private boolean kk() {
        cd cd2 = cd.kT();
        if ((cd2.kU() == cd.a.YU || cd2.kU() == cd.a.YV) && this.WJ.equals(cd2.getContainerId())) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void t(long l2) {
        synchronized (this) {
            if (this.Xh == null) {
                bh.z("Refresh requested, but no network load scheduler.");
            } else {
                this.Xh.d(l2, this.Xf.fL);
            }
            return;
        }
    }

    protected ContainerHolder ac(Status status) {
        if (this.Xd != null) {
            return this.Xd;
        }
        if (status == Status.By) {
            bh.w("timer expired: setting result to failure");
        }
        return new n(status);
    }

    void br(String string2) {
        synchronized (this) {
            this.Xg = string2;
            if (this.Xh != null) {
                this.Xh.bu(string2);
            }
            return;
        }
    }

    @Override
    protected /* synthetic */ Result d(Status status) {
        return this.ac(status);
    }

    String ke() {
        synchronized (this) {
            String string2 = this.Xg;
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void kh() {
        Object object = this.Xc.ca(this.Xb);
        if (object != null) {
            object = new Container(this.mContext, this.WW.getDataLayer(), this.WJ, 0, (cq.c)object);
            this.a(new n(this.WW, this.AS, (Container)object, new n.a(){

                @Override
                public void br(String string2) {
                    o.this.br(string2);
                }

                @Override
                public String ke() {
                    return o.this.ke();
                }

                @Override
                public void kg() {
                    bh.z("Refresh ignored: container loaded as default only.");
                }
            }));
        } else {
            bh.w("Default was requested, but no default container was found");
            this.a(this.ac(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.Xh = null;
        this.Xc = null;
    }

    public void ki() {
        this.C(false);
    }

    public void kj() {
        this.C(true);
    }

    static interface a {
        public boolean b(Container var1);
    }

    private class b
    implements bg<it.a> {
        private b() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void a(it.a a2) {
            c.j j2;
            if (a2.aaZ != null) {
                j2 = a2.aaZ;
            } else {
                c.f f2 = a2.fK;
                j2 = new c.j();
                j2.fK = f2;
                j2.fJ = null;
                j2.fL = f2.fg;
            }
            o.this.a(j2, a2.aaY, true);
        }

        @Override
        public void a(bg.a a2) {
            if (!o.this.Xe) {
                o.this.t(0);
            }
        }

        @Override
        public /* synthetic */ void i(Object object) {
            this.a((it.a)object);
        }

        @Override
        public void kl() {
        }
    }

    private class c
    implements bg<c.j> {
        private c() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(bg.a a2) {
            if (o.this.Xd != null) {
                o.this.a(o.this.Xd);
            } else {
                o.this.a(o.this.ac(Status.By));
            }
            o.this.t(3600000);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void b(c.j j2) {
            o o2 = o.this;
            synchronized (o2) {
                if (j2.fK == null) {
                    if (o.c((o)o.this).fK == null) {
                        bh.w("Current resource is null; network resource is also null");
                        o.this.t(3600000);
                        return;
                    }
                    j2.fK = o.c((o)o.this).fK;
                }
                o.this.a(j2, o.this.Wv.currentTimeMillis(), false);
                bh.y("setting refresh time to current time: " + o.this.WO);
                if (!o.this.kk()) {
                    o.this.a(j2);
                }
                return;
            }
        }

        @Override
        public /* synthetic */ void i(Object object) {
            this.b((c.j)object);
        }

        @Override
        public void kl() {
        }
    }

    private class d
    implements n.a {
        private d() {
        }

        @Override
        public void br(String string2) {
            o.this.br(string2);
        }

        @Override
        public String ke() {
            return o.this.ke();
        }

        @Override
        public void kg() {
            if (o.this.Xa.cS()) {
                o.this.t(0);
            }
        }
    }

    static interface e
    extends Releasable {
        public void a(bg<c.j> var1);

        public void bu(String var1);

        public void d(long var1, String var3);
    }

    static interface f
    extends Releasable {
        public void a(bg<it.a> var1);

        public void b(it.a var1);

        public cq.c ca(int var1);

        public void km();
    }

}

