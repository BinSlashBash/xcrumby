package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.co.C0858a;

public final class cp extends C0858a {
    private final InAppPurchaseListener mp;

    public cp(InAppPurchaseListener inAppPurchaseListener) {
        this.mp = inAppPurchaseListener;
    }

    public void m2992a(cn cnVar) {
        this.mp.onInAppPurchaseRequested(new cq(cnVar));
    }
}
