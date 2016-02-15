/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.c;
import com.google.android.gms.tagmanager.bg;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cn;
import com.google.android.gms.tagmanager.o;
import com.google.android.gms.tagmanager.r;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class co
implements o.e {
    private final String WJ;
    private String Xg;
    private bg<c.j> Zf;
    private r Zg;
    private final ScheduledExecutorService Zi;
    private final a Zj;
    private ScheduledFuture<?> Zk;
    private boolean mClosed;
    private final Context mContext;

    public co(Context context, String string2, r r2) {
        this(context, string2, r2, null, null);
    }

    co(Context object, String string2, r r2, b b2, a a2) {
        this.Zg = r2;
        this.mContext = object;
        this.WJ = string2;
        object = b2;
        if (b2 == null) {
            object = new b(){

                @Override
                public ScheduledExecutorService la() {
                    return Executors.newSingleThreadScheduledExecutor();
                }
            };
        }
        this.Zi = object.la();
        if (a2 == null) {
            this.Zj = new a(){

                @Override
                public cn a(r r2) {
                    return new cn(co.this.mContext, co.this.WJ, r2);
                }
            };
            return;
        }
        this.Zj = a2;
    }

    private cn bK(String string2) {
        cn cn2 = this.Zj.a(this.Zg);
        cn2.a(this.Zf);
        cn2.bu(this.Xg);
        cn2.bJ(string2);
        return cn2;
    }

    private void kZ() {
        synchronized (this) {
            if (this.mClosed) {
                throw new IllegalStateException("called method after closed");
            }
            return;
        }
    }

    @Override
    public void a(bg<c.j> bg2) {
        synchronized (this) {
            this.kZ();
            this.Zf = bg2;
            return;
        }
    }

    @Override
    public void bu(String string2) {
        synchronized (this) {
            this.kZ();
            this.Xg = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void d(long l2, String string2) {
        synchronized (this) {
            bh.y("loadAfterDelay: containerId=" + this.WJ + " delay=" + l2);
            this.kZ();
            if (this.Zf == null) {
                throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
            }
            if (this.Zk != null) {
                this.Zk.cancel(false);
            }
            this.Zk = this.Zi.schedule(this.bK(string2), l2, TimeUnit.MILLISECONDS);
            return;
        }
    }

    @Override
    public void release() {
        synchronized (this) {
            this.kZ();
            if (this.Zk != null) {
                this.Zk.cancel(false);
            }
            this.Zi.shutdown();
            this.mClosed = true;
            return;
        }
    }

    static interface a {
        public cn a(r var1);
    }

    static interface b {
        public ScheduledExecutorService la();
    }

}

