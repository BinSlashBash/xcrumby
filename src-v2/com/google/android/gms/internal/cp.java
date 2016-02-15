/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchase;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.cn;
import com.google.android.gms.internal.co;
import com.google.android.gms.internal.cq;

public final class cp
extends co.a {
    private final InAppPurchaseListener mp;

    public cp(InAppPurchaseListener inAppPurchaseListener) {
        this.mp = inAppPurchaseListener;
    }

    @Override
    public void a(cn cn2) {
        this.mp.onInAppPurchaseRequested(new cq(cn2));
    }
}

