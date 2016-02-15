package com.google.android.gms.internal;

import android.os.Bundle;

public class dk {
    private final Object li;
    private final String qL;
    private int qQ;
    private long qR;
    private long qS;
    private int qT;
    private int qU;

    public dk(String str) {
        this.li = new Object();
        this.qQ = 0;
        this.qR = -1;
        this.qS = -1;
        this.qT = 0;
        this.qU = -1;
        this.qL = str;
    }

    public void m767b(ah ahVar, long j) {
        synchronized (this.li) {
            if (this.qS == -1) {
                this.qS = j;
                this.qR = this.qS;
            } else {
                this.qR = j;
            }
            if (ahVar.extras == null || ahVar.extras.getInt("gw", 2) != 1) {
                this.qU++;
                return;
            }
        }
    }

    public void bk() {
        synchronized (this.li) {
            this.qT++;
        }
    }

    public void bl() {
        synchronized (this.li) {
            this.qQ++;
        }
    }

    public long bw() {
        return this.qS;
    }

    public Bundle m768q(String str) {
        Bundle bundle;
        synchronized (this.li) {
            bundle = new Bundle();
            bundle.putString("session_id", this.qL);
            bundle.putLong("basets", this.qS);
            bundle.putLong("currts", this.qR);
            bundle.putString("seq_num", str);
            bundle.putInt("preqs", this.qU);
            bundle.putInt("pclick", this.qQ);
            bundle.putInt("pimp", this.qT);
        }
        return bundle;
    }
}
