/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.webkit.WebChromeClient
 *  android.webkit.WebChromeClient$CustomViewCallback
 */
package com.google.android.gms.internal;

import android.view.View;
import android.webkit.WebChromeClient;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.eb;

public final class ed
extends eb {
    public ed(dz dz2) {
        super(dz2);
    }

    public void onShowCustomView(View view, int n2, WebChromeClient.CustomViewCallback customViewCallback) {
        this.a(view, n2, customViewCallback);
    }
}

