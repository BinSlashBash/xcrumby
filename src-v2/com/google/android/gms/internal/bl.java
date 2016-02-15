/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.bk;
import com.google.android.gms.internal.bn;
import com.google.android.gms.internal.bs;

public final class bl
extends bs.a {
    private final Object li = new Object();
    private bn.a nl;
    private bk nm;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void P() {
        Object object = this.li;
        synchronized (object) {
            if (this.nm != null) {
                this.nm.X();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(bk bk2) {
        Object object = this.li;
        synchronized (object) {
            this.nm = bk2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(bn.a a2) {
        Object object = this.li;
        synchronized (object) {
            this.nl = a2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onAdClosed() {
        Object object = this.li;
        synchronized (object) {
            if (this.nm != null) {
                this.nm.Y();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onAdFailedToLoad(int n2) {
        Object object = this.li;
        synchronized (object) {
            if (this.nl != null) {
                n2 = n2 == 3 ? 1 : 2;
                this.nl.f(n2);
                this.nl = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onAdLeftApplication() {
        Object object = this.li;
        synchronized (object) {
            if (this.nm != null) {
                this.nm.Z();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onAdLoaded() {
        Object object = this.li;
        synchronized (object) {
            if (this.nl != null) {
                this.nl.f(0);
                this.nl = null;
                return;
            }
            if (this.nm != null) {
                this.nm.ab();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onAdOpened() {
        Object object = this.li;
        synchronized (object) {
            if (this.nm != null) {
                this.nm.aa();
            }
            return;
        }
    }
}

