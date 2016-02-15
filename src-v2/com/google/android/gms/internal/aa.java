/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.ab;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.dh;
import java.util.ArrayList;
import java.util.WeakHashMap;

public final class aa
implements ac {
    private final Object li = new Object();
    private WeakHashMap<dh, ab> lj = new WeakHashMap();
    private ArrayList<ab> lk = new ArrayList();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ab a(ak object, dh dh2) {
        Object object2 = this.li;
        synchronized (object2) {
            if (this.c(dh2)) {
                return this.lj.get(dh2);
            }
            object = new ab((ak)object, dh2);
            object.a(this);
            this.lj.put(dh2, (ab)object);
            this.lk.add((ab)object);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(ab ab2) {
        Object object = this.li;
        synchronized (object) {
            if (!ab2.at()) {
                this.lk.remove(ab2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean c(dh object) {
        Object object2 = this.li;
        synchronized (object2) {
            object = this.lj.get(object);
            if (object == null) return false;
            if (!object.at()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void d(dh object) {
        Object object2 = this.li;
        synchronized (object2) {
            object = this.lj.get(object);
            if (object != null) {
                object.ar();
            }
            return;
        }
    }
}

