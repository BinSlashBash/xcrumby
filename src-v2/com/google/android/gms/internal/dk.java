/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.ah;

public class dk {
    private final Object li = new Object();
    private final String qL;
    private int qQ = 0;
    private long qR = -1;
    private long qS = -1;
    private int qT = 0;
    private int qU = -1;

    public dk(String string2) {
        this.qL = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(ah ah2, long l2) {
        Object object = this.li;
        synchronized (object) {
            this.qR = this.qS == -1 ? (this.qS = l2) : l2;
            if (ah2.extras != null && ah2.extras.getInt("gw", 2) == 1) {
                return;
            }
            ++this.qU;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bk() {
        Object object = this.li;
        synchronized (object) {
            ++this.qT;
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
            ++this.qQ;
            return;
        }
    }

    public long bw() {
        return this.qS;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bundle q(String string2) {
        Object object = this.li;
        synchronized (object) {
            Bundle bundle = new Bundle();
            bundle.putString("session_id", this.qL);
            bundle.putLong("basets", this.qS);
            bundle.putLong("currts", this.qR);
            bundle.putString("seq_num", string2);
            bundle.putInt("preqs", this.qU);
            bundle.putInt("pclick", this.qQ);
            bundle.putInt("pimp", this.qT);
            return bundle;
        }
    }
}

