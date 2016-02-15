/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.SystemClock;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.dj;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class di {
    private final Object li = new Object();
    private boolean pV = false;
    private final String qA;
    private long qB = -1;
    private long qC = -1;
    private long qD = -1;
    private long qE = 0;
    private long qF = -1;
    private long qG = -1;
    private final dj qx;
    private final LinkedList<a> qy;
    private final String qz;

    public di(dj dj2, String string2, String string3) {
        this.qx = dj2;
        this.qz = string2;
        this.qA = string3;
        this.qy = new LinkedList();
    }

    public di(String string2, String string3) {
        this(dj.bq(), string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bk() {
        Object object = this.li;
        synchronized (object) {
            if (this.qG != -1 && this.qC == -1) {
                this.qC = SystemClock.elapsedRealtime();
                this.qx.a(this);
            }
            dj dj2 = this.qx;
            dj.bu().bk();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bl() {
        Object object = this.li;
        synchronized (object) {
            if (this.qG != -1) {
                Object object2 = new a();
                object2.bp();
                this.qy.add((a)object2);
                ++this.qE;
                object2 = this.qx;
                dj.bu().bl();
                this.qx.a(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bm() {
        Object object = this.li;
        synchronized (object) {
            a a2;
            if (this.qG != -1 && !this.qy.isEmpty() && (a2 = this.qy.getLast()).bn() == -1) {
                a2.bo();
                this.qx.a(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void f(ah ah2) {
        Object object = this.li;
        synchronized (object) {
            this.qF = SystemClock.elapsedRealtime();
            dj dj2 = this.qx;
            dj.bu().b(ah2, this.qF);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void h(long l2) {
        Object object = this.li;
        synchronized (object) {
            this.qG = l2;
            if (this.qG != -1) {
                this.qx.a(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void i(long l2) {
        Object object = this.li;
        synchronized (object) {
            if (this.qG != -1) {
                this.qB = l2;
                this.qx.a(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void m(boolean bl2) {
        Object object = this.li;
        synchronized (object) {
            if (this.qG != -1) {
                this.qD = SystemClock.elapsedRealtime();
                if (!bl2) {
                    this.qC = this.qD;
                    this.qx.a(this);
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
    public void n(boolean bl2) {
        Object object = this.li;
        synchronized (object) {
            if (this.qG != -1) {
                this.pV = bl2;
                this.qx.a(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bundle toBundle() {
        Object object = this.li;
        synchronized (object) {
            Bundle bundle = new Bundle();
            bundle.putString("seqnum", this.qz);
            bundle.putString("slotid", this.qA);
            bundle.putBoolean("ismediation", this.pV);
            bundle.putLong("treq", this.qF);
            bundle.putLong("tresponse", this.qG);
            bundle.putLong("timp", this.qC);
            bundle.putLong("tload", this.qD);
            bundle.putLong("pcc", this.qE);
            bundle.putLong("tfetch", this.qB);
            ArrayList<Bundle> arrayList = new ArrayList<Bundle>();
            Iterator<a> iterator = this.qy.iterator();
            do {
                if (!iterator.hasNext()) {
                    bundle.putParcelableArrayList("tclick", arrayList);
                    return bundle;
                }
                arrayList.add(iterator.next().toBundle());
            } while (true);
        }
    }

    private static final class a {
        private long qH = -1;
        private long qI = -1;

        public long bn() {
            return this.qI;
        }

        public void bo() {
            this.qI = SystemClock.elapsedRealtime();
        }

        public void bp() {
            this.qH = SystemClock.elapsedRealtime();
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong("topen", this.qH);
            bundle.putLong("tclose", this.qI);
            return bundle;
        }
    }

}

