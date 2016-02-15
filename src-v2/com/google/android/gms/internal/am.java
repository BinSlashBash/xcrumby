/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.internal.ar;

public final class am
extends ar.a {
    private final AppEventListener lV;

    public am(AppEventListener appEventListener) {
        this.lV = appEventListener;
    }

    @Override
    public void onAppEvent(String string2, String string3) {
        this.lV.onAppEvent(string2, string3);
    }
}

