package com.google.android.gms.internal;

import android.os.Bundle;

public class dm {
    private final Object li;
    private final String qA;
    private int qV;
    private int qW;
    private final dj qx;

    dm(dj djVar, String str) {
        this.li = new Object();
        this.qx = djVar;
        this.qA = str;
    }

    public dm(String str) {
        this(dj.bq(), str);
    }

    public void m770a(int i, int i2) {
        synchronized (this.li) {
            this.qV = i;
            this.qW = i2;
            this.qx.m764a(this.qA, this);
        }
    }

    public Bundle toBundle() {
        Bundle bundle;
        synchronized (this.li) {
            bundle = new Bundle();
            bundle.putInt("pmnli", this.qV);
            bundle.putInt("pmnll", this.qW);
        }
        return bundle;
    }
}
