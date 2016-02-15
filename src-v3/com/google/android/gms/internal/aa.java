package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.WeakHashMap;

public final class aa implements ac {
    private final Object li;
    private WeakHashMap<dh, ab> lj;
    private ArrayList<ab> lk;

    public aa() {
        this.li = new Object();
        this.lj = new WeakHashMap();
        this.lk = new ArrayList();
    }

    public ab m1994a(ak akVar, dh dhVar) {
        ab abVar;
        synchronized (this.li) {
            if (m1996c(dhVar)) {
                abVar = (ab) this.lj.get(dhVar);
            } else {
                abVar = new ab(akVar, dhVar);
                abVar.m593a((ac) this);
                this.lj.put(dhVar, abVar);
                this.lk.add(abVar);
            }
        }
        return abVar;
    }

    public void m1995a(ab abVar) {
        synchronized (this.li) {
            if (!abVar.at()) {
                this.lk.remove(abVar);
            }
        }
    }

    public boolean m1996c(dh dhVar) {
        boolean z;
        synchronized (this.li) {
            ab abVar = (ab) this.lj.get(dhVar);
            z = abVar != null && abVar.at();
        }
        return z;
    }

    public void m1997d(dh dhVar) {
        synchronized (this.li) {
            ab abVar = (ab) this.lj.get(dhVar);
            if (abVar != null) {
                abVar.ar();
            }
        }
    }
}
