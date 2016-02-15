package com.google.android.gms.internal;

import com.google.android.gms.internal.bn.C0328a;
import com.google.android.gms.internal.bs.C0849a;

public final class bl extends C0849a {
    private final Object li;
    private C0328a nl;
    private bk nm;

    public bl() {
        this.li = new Object();
    }

    public void m2907P() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.m662X();
            }
        }
    }

    public void m2908a(bk bkVar) {
        synchronized (this.li) {
            this.nm = bkVar;
        }
    }

    public void m2909a(C0328a c0328a) {
        synchronized (this.li) {
            this.nl = c0328a;
        }
    }

    public void onAdClosed() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.m663Y();
            }
        }
    }

    public void onAdFailedToLoad(int error) {
        synchronized (this.li) {
            if (this.nl != null) {
                this.nl.m665f(error == 3 ? 1 : 2);
                this.nl = null;
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.m664Z();
            }
        }
    }

    public void onAdLoaded() {
        synchronized (this.li) {
            if (this.nl != null) {
                this.nl.m665f(0);
                this.nl = null;
                return;
            }
            if (this.nm != null) {
                this.nm.ab();
            }
        }
    }

    public void onAdOpened() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.aa();
            }
        }
    }
}
